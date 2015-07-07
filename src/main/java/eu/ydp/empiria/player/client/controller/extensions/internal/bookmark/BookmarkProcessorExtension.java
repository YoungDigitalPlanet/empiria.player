package eu.ydp.empiria.player.client.controller.extensions.internal.bookmark;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Command;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DataSourceDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.ModuleHandlerExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.PlayerJsObjectModifierExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.StatefulExtension;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.HasParent;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.bookmark.BookmarkingHelper;
import eu.ydp.empiria.player.client.module.bookmark.IBookmarkable;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.collections.StackMap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BookmarkProcessorExtension extends InternalExtension implements ModuleHandlerExtension, DataSourceDataSocketUserExtension,
        PlayerJsObjectModifierExtension, StatefulExtension, IBookmarkPopupPresenter {

    enum Mode {
        IDLE, BOOKMARKING, CLEARING, EDITING
    }

    @Inject
    IBookmarkPopupView bookmarkPopup;
    @Inject
    StyleNameConstants styleNames;
    @Inject
    EventsBus eventsBus;

    DataSourceDataSupplier dataSourceSupplier;
    int currItemIndex = 0;
    IBookmarkable currEditingModule;
    boolean currBookmarkNewlyCreated;
    List<List<IBookmarkable>> modules = new LinkedList<>();
    List<StackMap<Integer, BookmarkProperties>> bookmarks = new LinkedList<>();
    Mode mode = Mode.IDLE;
    Integer bookmarkIndex;
    private JavaScriptObject playerJsObject;

    @Override
    public void init() {
        bookmarkPopup.init();
        bookmarkPopup.setPresenter(this);
        int itemsCount = dataSourceSupplier.getItemsCount();
        modules.clear();
        for (int i = 0; i < itemsCount; i++) {
            modules.add(new ArrayList<IBookmarkable>());
        }
        bookmarks.clear();
        for (int i = 0; i < itemsCount; i++) {
            bookmarks.add(new StackMap<Integer, BookmarkProperties>());
        }

        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_REMOVED), new PlayerEventHandler() {

            @Override
            public void onPlayerEvent(PlayerEvent event) {
                int pageNumber = (Integer) event.getValue();
                modules.get(pageNumber).clear();
            }
        });

        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE), new PlayerEventHandler() {

            @Override
            public void onPlayerEvent(PlayerEvent event) {
                currItemIndex = (Integer) event.getValue();
                resetMode();
            }
        });

        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_INITIALIZED), new PlayerEventHandler() {

            @Override
            public void onPlayerEvent(PlayerEvent event) {
                updateModules(currItemIndex, true);
            }
        });

        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.ASSESSMENT_STARTING), new PlayerEventHandler() {
            @Override
            public void onPlayerEvent(PlayerEvent event) {
                parseExternalBookmarks();
            }
        });
    }

    void parseExternalBookmarks() {
        JavaScriptObject externalBookmarks = getExternalBookmarks();
        if (externalBookmarks != null) {
            JSONArray externalState = (JSONArray) JSONParser.parseLenient(externalBookmarks.toString());
            if (externalState.size() > 0) {
                fillBookmarks(externalState);
            }
        }
    }

    boolean accepts(IModule module) {
        return module instanceof IBookmarkable && !parentRegistered(module) && !containsInteraction(module)
                && ((IBookmarkable) module).getBookmarkHtmlBody() != null && ((IBookmarkable) module).getBookmarkHtmlBody().length() > 0;
    }

    boolean containsInteraction(IModule module) {
        if (module instanceof HasChildren) {
            HasChildren parent = (HasChildren) module;
            for (HasParent m : parent.getNestedChildren()) {
                if (m instanceof IInteractionModule) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether any parent of the given module is already registered.
     */
    boolean parentRegistered(IModule module) {
        List<HasChildren> parents = module.getNestedParents();
        List<IBookmarkable> modulesForCurrentItem = getModulesForCurrentItem();
        for (IBookmarkable bookmarkable : modulesForCurrentItem) {
            if (bookmarkable instanceof HasChildren && parents.contains(bookmarkable)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void register(IModule module) {
        if (accepts(module)) {
            removeBookmarkableChildren(module);
            final IBookmarkable accModule = (IBookmarkable) module;
            registerModuleInList(accModule);
            accModule.setClickCommand(createClickCommand(accModule));
        }
    }

    private Command createClickCommand(final IBookmarkable accModule) {
        return new Command() {
            @Override
            public void execute() {
                if (mode == Mode.BOOKMARKING) {
                    bookmarkModule(accModule);
                    resetMode();
                }
                if (mode == Mode.CLEARING) {
                    if (isModuleBookmarked(accModule, currItemIndex)) {
                        bookmarkModule(accModule, false);
                        resetMode();
                    }
                } else if (mode == Mode.EDITING) {
                    if (isModuleBookmarked(accModule, currItemIndex)) {
                        editBookmark(accModule);
                        resetMode();
                    }
                }
            }
        };
    }

    void resetMode() {
        setMode(Mode.IDLE);
        notifyModeChange();
    }

    native void notifyModeChange()/*-{
        var playerJso = this.@eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension::playerJsObject;
        if (typeof playerJso != 'undefined' && playerJso != null && typeof playerJso.bookmarkingClearButtons == 'function') {
            playerJso.bookmarkingClearButtons();
        }
    }-*/;

    void removeBookmarkableChildren(IModule parentModule) {
        List<IBookmarkable> modulesForCurrentItem = getModulesForCurrentItem();
        if (parentModule instanceof HasChildren) {
            List<HasParent> allChildren = ((HasChildren) parentModule).getNestedChildren();
            for (IBookmarkable b : modulesForCurrentItem) {
                if (allChildren.contains(b)) {
                    modulesForCurrentItem.remove(b);
                }
            }
        }
    }

    private void bookmarkModule(IBookmarkable module) {
        bookmarkModule(module, !isModuleBookmarkedWithCurrentIndex(module, currItemIndex));
    }

    void bookmarkModule(IBookmarkable module, boolean bookmarkIt) {
        if (bookmarkIt && !isModuleBookmarkedWithCurrentIndex(module, currItemIndex)) {
            addOrSetBookmark(module);
            editBookmark(module);
        } else if (!bookmarkIt && isModuleBookmarked(module, currItemIndex)) {
            removeBookmark(module);
        }
    }

    void addOrSetBookmark(IBookmarkable module) {
        BookmarkProperties moduleProps = getBookmarkPropertiesForModule(module);
        if (moduleProps == null) {
            moduleProps = new BookmarkProperties(bookmarkIndex, getDefaultTitleForModule(module));
            addBookmark(module, moduleProps);
            currBookmarkNewlyCreated = true;
        } else {
            moduleProps.setBookmarkIndex(bookmarkIndex);
            currBookmarkNewlyCreated = false;
        }
        updateBookmarkedModule(module);
    }

    String getDefaultTitleForModule(IBookmarkable module) {
        String moduleTitle = module.getDefaultBookmarkTitle();
        if (moduleTitle == null || "".equals(moduleTitle)) {
            moduleTitle = dataSourceSupplier.getItemTitle(currItemIndex);
            moduleTitle = BookmarkingHelper.getDefaultBookmarkTitle(moduleTitle);
        }
        return moduleTitle;
    }

    void addBookmark(IBookmarkable module, BookmarkProperties props) {
        int index = getModulesForCurrentItem().indexOf(module);
        getBookmarksForCurrentItem().put(index, props);
    }

    void removeBookmark(IBookmarkable module) {
        int index = getModulesForCurrentItem().indexOf(module);
        getBookmarksForCurrentItem().remove(index);
        updateNotBookmarkedModule(module);
    }

    void editBookmark(IBookmarkable module) {
        currEditingModule = module;
        BookmarkProperties props = getBookmarkPropertiesForModule(module);
        bookmarkPopup.setBookmarkTitle(props.getBookmarkTitle());
        bookmarkPopup.show(module.getViewArea());
    }

    private void registerModuleInList(IBookmarkable module) {
        getModulesForCurrentItem().add(module);
    }

    @Override
    public void setPlayerJsObject(JavaScriptObject playerJsObject) {
        this.playerJsObject = playerJsObject;
        initJsApi(playerJsObject);
    }

    private native void initJsApi(JavaScriptObject playerJsObject) /*-{
        var self = this;
        playerJsObject.bookmarkingStart = function (bookmarkIndex) {
            self.@eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension::startBookmarking(I)(bookmarkIndex);
        }
        playerJsObject.bookmarkingStop = function () {
            self.@eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension::stopBookmarking()();
        }
        playerJsObject.bookmarkingClearing = function (clearing) {
            self.@eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension::setClearingMode(Z)(clearing);
        }
        playerJsObject.bookmarkingClearAll = function () {
            self.@eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension::clearAll()();
        }
        playerJsObject.bookmarkingEditing = function (editing) {
            self.@eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension::setEditingMode(Z)(editing);
        }
        playerJsObject.getBookmarks = function () {
            return self.@eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension::getState()();
        }
    }-*/;

    void setClearingMode(boolean clearing) {
        if (clearing) {
            setMode(Mode.CLEARING);
        } else if (mode == Mode.CLEARING) {
            setMode(Mode.IDLE);
        }
    }

    void clearAll() {
        getBookmarksForCurrentItem().clear();
        resetMode();
        updateNotBookmarkedModules();
    }

    void setEditingMode(boolean editing) {
        if (editing) {
            setMode(Mode.EDITING);
        } else if (mode == Mode.EDITING) {
            setMode(Mode.IDLE);
        }
    }

    void stopBookmarking() {
        setMode(Mode.IDLE);
    }

    void startBookmarking(int bookmarkIndex) {
        this.bookmarkIndex = bookmarkIndex;
        setMode(Mode.BOOKMARKING);
    }

    /**
     * When the mode is other than target one, the mode is changed and the modules are updated.
     */
    void setMode(Mode newMode) {
        if (mode != newMode) {
            mode = newMode;
            updateNotBookmarkedModules();
        }
    }

    /**
     * Updates not bookmarked modules in all items.
     */
    void updateNotBookmarkedModules() {
        updateModules(false);
    }

    /**
     * Updates all modules in all items.
     */
    void updateModules(boolean updateBookmarkedModules) {
        for (int i = 0; i < modules.size(); i++) {
            updateModules(i, updateBookmarkedModules);
        }
    }

    /**
     * Updates all modules for the selected item.
     *
     * @param itemIndex selected item index
     */
    void updateModules(int itemIndex, boolean updateBookmarkedModules) {
        if (itemIndex < modules.size()) {
            List<IBookmarkable> itemModules = modules.get(itemIndex);
            for (IBookmarkable module : itemModules) {
                if (!isModuleBookmarked(module, itemIndex)) {
                    updateNotBookmarkedModule(module);
                } else if (updateBookmarkedModules) {
                    updateBookmarkedModule(module);
                }
            }
        }
    }

    /**
     * Updates module with the assumption that the module is currently NOT bookmarked.
     *
     * @param module module to update
     */
    void updateNotBookmarkedModule(IBookmarkable module) {
        if (mode == Mode.BOOKMARKING) {
            module.setBookmarkingStyleName(styleNames.QP_BOOKMARK_SELECTABLE());
        } else {
            module.removeBookmarkingStyleName();
        }
    }

    /**
     * Updates module with the assumption that the module is currently not bookmarked.
     *
     * @param module module to update
     */
    void updateBookmarkedModule(IBookmarkable module) {
        module.setBookmarkingStyleName(styleNames.QP_BOOKMARK_SELECTED() + "-" + getBookmarkPropertiesForModule(module).getBookmarkIndex());
    }

    BookmarkProperties getBookmarkPropertiesForModule(IBookmarkable module) {
        for (int m = 0; m < modules.size(); m++) {
            List<IBookmarkable> itemModules = modules.get(m);
            if (itemModules.contains(module)) {
                return bookmarks.get(m).get(itemModules.indexOf(module));
            }
        }
        return null;
    }

    StackMap<Integer, BookmarkProperties> getBookmarksForCurrentItem() {
        return bookmarks.get(currItemIndex);
    }

    List<IBookmarkable> getModulesForCurrentItem() {
        return modules.get(currItemIndex);
    }

    private boolean isModuleBookmarked(IBookmarkable module, int itemIndex) {
        return bookmarks.get(itemIndex).keySet().contains(modules.get(itemIndex).indexOf(module));
    }

    private boolean isModuleBookmarkedWithCurrentIndex(IBookmarkable module, int itemIndex) {
        BookmarkProperties props = bookmarks.get(itemIndex).get(modules.get(itemIndex).indexOf(module));
        return props != null && bookmarkIndex.equals(props.getBookmarkIndex());
    }

    @Override
    public void setDataSourceDataSupplier(DataSourceDataSupplier supplier) {
        dataSourceSupplier = supplier;
    }

    @Override
    public JSONArray getState() {
        JSONArray stateArray = new JSONArray();
        for (int i = 0; i < bookmarks.size(); i++) {
            stateArray.set(i, getItemState(bookmarks.get(i)));
        }
        return stateArray;
    }

    private JSONObject getItemState(StackMap<Integer, BookmarkProperties> map) {
        JSONObject obj = new JSONObject();
        for (Integer key : map.keySet()) {
            obj.put(key.toString(), map.get(key).toJSON());
        }
        return obj;
    }

    @Override
    public void setState(JSONArray newState) {
        if (bookmarks.isEmpty()) {
            fillBookmarks(newState);
        }

    }

    private void fillBookmarks(JSONArray state) {
        bookmarks.clear();
        for (int i = 0; i < state.size(); i++) {
            bookmarks.add(decodeItemState(state.get(i).isObject()));
        }
    }

    native JavaScriptObject getExternalBookmarks()/*-{
        var playerJso = this.@eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension::playerJsObject;
        if (typeof playerJso != 'undefined' && playerJso != null && typeof playerJso.getExternalBookmarks == 'function') {
            return playerJso.getExternalBookmarks();
        }
        return null;
    }-*/;

    private StackMap<Integer, BookmarkProperties> decodeItemState(JSONObject object) {
        StackMap<Integer, BookmarkProperties> map = new StackMap<Integer, BookmarkProperties>();
        for (String key : object.keySet()) {
            Integer keyInt = NumberUtils.tryParseInt(key, null);
            BookmarkProperties props = BookmarkProperties.fromJSON(object.get(key));
            if (keyInt != null && props != null) {
                map.put(keyInt, props);
            }
        }
        return map;
    }

    @Override
    public void applyBookmark() {
        onBookmarkPopupClosed();
        getBookmarkPropertiesForModule(currEditingModule).setBookmarkTitle(bookmarkPopup.getBookmarkTitle());
    }

    @Override
    public void deleteBookmark() {
        onBookmarkPopupClosed();
        removeBookmark(currEditingModule);
    }

    @Override
    public void discardBookmarkChanges() {
        if (currBookmarkNewlyCreated) {
            onBookmarkPopupClosed();
            removeBookmark(currEditingModule);
        }
    }

    private void onBookmarkPopupClosed() {
        currBookmarkNewlyCreated = false;
    }

}
