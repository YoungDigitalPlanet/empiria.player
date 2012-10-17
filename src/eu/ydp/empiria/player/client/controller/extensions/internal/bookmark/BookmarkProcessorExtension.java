package eu.ydp.empiria.player.client.controller.extensions.internal.bookmark;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Command;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DataSourceDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.ModuleHandlerExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.PlayerJsObjectModifierExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.StatefulExtension;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.HasParent;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.bookmark.IBookmarkable;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.collections.StackMap;

public class BookmarkProcessorExtension extends InternalExtension implements ModuleHandlerExtension,
	DataSourceDataSocketUserExtension, PlayerJsObjectModifierExtension, StatefulExtension, IBookmarkPopupPresenter {
	
	static enum Mode {IDLE, BOOKMARKING, CLEARING, EDITING}
	
	@Inject IBookmarkPopupView bookmarkPopup;
	@Inject StyleNameConstants styleNames;
	@Inject EventsBus eventsBus;

	DataSourceDataSupplier dataSourceSupplier;
	int currItemIndex = 0;
	IBookmarkable currEditingModule;
	List<List<IBookmarkable>> modules = new LinkedList<List<IBookmarkable>>();
	List<StackMap<Integer, BookmarkProperties>> bookmarks = new LinkedList<StackMap<Integer, BookmarkProperties>>();
	Mode mode = Mode.IDLE;
	Integer bookmarkIndex;

	@Override
	public void init() {
		bookmarkPopup.init();
		bookmarkPopup.setPresenter(this);
		int itemsCount = dataSourceSupplier.getItemsCount();
		modules.clear();
		for (int i = 0 ; i < itemsCount ; i ++){
			modules.add(new ArrayList<IBookmarkable>());
		}
		bookmarks.clear();
		for (int i = 0 ; i < itemsCount ; i ++){
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
				stopBookmarking();
				setClearingMode(false);
				setEditingMode(false);
				__clearButtons();
			}
		});
	}
	
	void initInjection(){
		styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();
		eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
	}

	boolean accepts(IModule module) {
		return module instanceof IBookmarkable  &&  !parentRegistered(module)  &&  !containsInteraction(module)  &&
				((IBookmarkable)module).getBookmarkHtmlBody() != null  &&  ((IBookmarkable)module).getBookmarkHtmlBody().length() > 0;
	}

	boolean containsInteraction(IModule module) {
		if (module instanceof HasChildren){
			List<IModule> children = ((HasChildren)module).getChildrenModules();
			for (IModule childModule : children){
				if (containsInteraction(childModule)){
					return true;
				}
			}
		} else if (module instanceof IInteractionModule){
			return true;
		}
		return false;
	}

	/**
	 * Checks whether any parent of the given module is already registered.
	 */
	boolean parentRegistered(IModule module) {
		HasParent currParent = ((HasParent)module).getParentModule();
		while (currParent != null){
			if (getModulesForCurrentItem().contains(currParent)){
				return true;
			}
			currParent = currParent.getParentModule();
		}
		return false;
	}

	@Override
	public void register(IModule module) {
		if (accepts(module)){
			removeChildren(module);
			final IBookmarkable accModule = (IBookmarkable)module;
			registerModuleInList(accModule);
			accModule.setClickCommand(new Command() {
				@Override
				public void execute() {
					if (mode == Mode.BOOKMARKING){
						bookmarkModule(accModule);
					} else if (mode == Mode.CLEARING){
						bookmarkModule(accModule, false);
					} else if (mode == Mode.EDITING){
						editBookmark(accModule);
					}
					setMode(Mode.IDLE);
					BookmarkProcessorExtension.this.__clearButtons();
				}
			});
		}
	}
	
	native void __clearButtons()/*-{
		if (typeof $wnd.bookmarkingClearButtons == 'function'){
			$wnd.bookmarkingClearButtons();
		}
	}-*/;
	
	void removeChildren(IModule parentModule) {
		Iterator<IBookmarkable> iter = getModulesForCurrentItem().iterator();
		while (iter.hasNext()){
			if (isChildOf(iter.next(), parentModule)){
				iter.remove();
			}
		}
	}

	boolean isChildOf(IModule module, IModule parentModule) {
		HasChildren currParent = module.getParentModule();
		while (currParent != null){
			if (currParent == parentModule){
				return true;
			}
			currParent = currParent.getParentModule();
		}
		return false;
	}

	private void bookmarkModule(IBookmarkable module){
		bookmarkModule(module, !isModuleBookmarkedWithCurrentIndex(module, currItemIndex));
	}

	void bookmarkModule(IBookmarkable module, boolean bookmarkIt){
		if (bookmarkIt  &&  !isModuleBookmarkedWithCurrentIndex(module, currItemIndex)){
			addOrSetBookmark(module);
			editBookmark(module);
		} else if (!bookmarkIt  &&  isModuleBookmarked(module, currItemIndex)){
			removeBookmark(module);
		}
	}
	
	void addOrSetBookmark(IBookmarkable module){
		BookmarkProperties moduleProps = getBookmarkPropertiesForModule(module);
		if (moduleProps == null){
			moduleProps = new BookmarkProperties(bookmarkIndex, getDefaultTitleForModule(module));
			addBookmark(module, moduleProps);
		} else {
			moduleProps.setBookmarkIndex(bookmarkIndex);
		}
		updateBookmarkedModule(module);
	}
	
	String getDefaultTitleForModule(IBookmarkable module){
		String moduleTitle = module.getDefaultBookmarkTitle();
		if (moduleTitle == null  ||  "".equals(moduleTitle) ){
			moduleTitle = dataSourceSupplier.getItemTitle(currItemIndex);
		}
		return moduleTitle;
	}
	
	void addBookmark(IBookmarkable module, BookmarkProperties props){
		int index = getModulesForCurrentItem().indexOf(module);
		getBookmarksForCurrentItem().put(index, props);
	}
	
	void removeBookmark(IBookmarkable module){
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
		
	private void registerModuleInList(IBookmarkable module){
		getModulesForCurrentItem().add(module);
	}

	@Override
	public void setPlayerJsObject(JavaScriptObject playerJsObject) {
		initJsApi(playerJsObject);
	}

	private native void initJsApi(JavaScriptObject playerJsObject) /*-{
		var self = this;
		playerJsObject.bookmarkingStart = function(bookmarkIndex){
			self.@eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension::startBookmarking(I)(bookmarkIndex);
		}
		playerJsObject.bookmarkingStop = function(){
			self.@eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension::stopBookmarking()();
		}
		playerJsObject.bookmarkingClearing = function(clearing){
			self.@eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension::setClearingMode(Z)(clearing);
		}
		playerJsObject.bookmarkingEditing = function(editing){
			self.@eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension::setEditingMode(Z)(editing);
		}
	}-*/;

	void setClearingMode(boolean clearing){
		if (clearing){
			setMode(Mode.CLEARING);
		} else if (mode == Mode.CLEARING){
			setMode(Mode.IDLE);
		}
	}
	
	void setEditingMode(boolean editing){
		if (editing){
			setMode(Mode.EDITING);
		} else if (mode == Mode.EDITING){
			setMode(Mode.IDLE);
		}
	}
	
	void stopBookmarking(){
		setMode(Mode.IDLE);
	}
	
	void startBookmarking(int bookmarkIndex){
		this.bookmarkIndex = bookmarkIndex;
		setMode(Mode.BOOKMARKING);
	}
	
	void setMode(Mode newMode){
		if (mode != newMode){
			mode = newMode;
			updateModules();
		}
	}

	void updateModules() {
		for (int i = 0 ; i < modules.size() ; i ++){
			updateModules(i);
		}
	}
	
	void updateModules(int itemIndex){
		List<IBookmarkable> itemModules = modules.get(itemIndex);
		for (IBookmarkable module : itemModules){
			if (!isModuleBookmarked(module, itemIndex)){
				updateNotBookmarkedModule(module);
			}
		}
	}
	
	void updateNotBookmarkedModule(IBookmarkable module){
		if (mode == Mode.BOOKMARKING){
			module.setBookmarkingStyleName(styleNames.QP_BOOKMARK_SELECTABLE());
		} else {
			module.removeBookmarkingStyleName();
		}
	}
	
	void updateBookmarkedModule(IBookmarkable module){
		module.setBookmarkingStyleName(styleNames.QP_BOOKMARK_SELECTED() + "-" + bookmarkIndex);
	}

	BookmarkProperties getBookmarkPropertiesForModule(IBookmarkable module) {
		for (int m = 0 ; m < modules.size() ; m++){
			List<IBookmarkable> itemModules = modules.get(m);
			if (itemModules.contains(module)){
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

	private boolean isModuleBookmarked(IBookmarkable module, int itemIndex){
		return bookmarks.get(itemIndex).keySet().contains(modules.get(itemIndex).indexOf(module));
	}

	private boolean isModuleBookmarkedWithCurrentIndex(IBookmarkable module, int itemIndex){
		BookmarkProperties props = bookmarks.get(itemIndex).get(modules.get(itemIndex).indexOf(module));
		return props != null  &&  bookmarkIndex.equals(props.getBookmarkIndex());
	}

	@Override
	public void setDataSourceDataSupplier(DataSourceDataSupplier supplier) {
		dataSourceSupplier = supplier;
	}

	@Override
	public JSONArray getState() {
		JSONArray stateArray = new JSONArray();
		for (int i = 0 ; i < bookmarks.size() ; i ++ ){
			stateArray.set(i, getItemState(bookmarks.get(i)));
		}
		return stateArray;
	}
	
	private JSONObject getItemState(StackMap<Integer, BookmarkProperties> map){
		JSONObject obj = new JSONObject();
		for (Integer key : map.keySet()){
			obj.put(key.toString(), map.get(key).toJSON());
		}
		return obj;
	}

	@Override
	public void setState(JSONArray newState) {
		bookmarks.clear();
		for (int i = 0 ; i < newState.size() ; i ++ ){
			bookmarks.add( decodeItemState(newState.get(i).isObject()) );
		}
	}

	private StackMap<Integer, BookmarkProperties> decodeItemState(JSONObject object) {
		StackMap<Integer, BookmarkProperties> map = new StackMap<Integer, BookmarkProperties>();
		for (String key : object.keySet()){
			Integer keyInt = NumberUtils.tryParseInt(key, null);
			BookmarkProperties props = BookmarkProperties.fromJSON(object.get(key));
			if (keyInt != null  &&  props != null){
				map.put(keyInt, props);
			}
		}
		return map;
	}

	@Override
	public void applyBookmark() {
		getBookmarkPropertiesForModule(currEditingModule).setBookmarkTitle(bookmarkPopup.getBookmarkTitle());
	}

	@Override
	public void deleteBookmark() {
		removeBookmark(currEditingModule);
	}
	
	JSONValue exportBookmarks(){
		return null;
	}
	
	void importBookmarks(JSONValue json){
	}

}