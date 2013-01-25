package eu.ydp.empiria.player.client.controller.extensions.internal.bookmark;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.HasParent;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.bookmark.BookmarkingHelper;
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
	boolean currBookmarkNewlyCreated;
	List<List<IBookmarkable>> modules = new LinkedList<List<IBookmarkable>>();
	List<StackMap<Integer, IBookmarkProperties>> bookmarks = new LinkedList<StackMap<Integer, IBookmarkProperties>>();
	Mode mode = Mode.IDLE;
	Integer bookmarkIndex;
	private JavaScriptObject playerJsObject;

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
			bookmarks.add(new StackMap<Integer, IBookmarkProperties>());
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
						resetMode();
					} if (mode == Mode.CLEARING){
						if (isModuleBookmarked(accModule, currItemIndex)){
							bookmarkModule(accModule, false);
							resetMode();
						}
					} else if (mode == Mode.EDITING){
						if (isModuleBookmarked(accModule, currItemIndex)){
							editBookmark(accModule);
							resetMode();
						}
					}
				}
			});
		}
	}
	
	void resetMode(){
		setMode(Mode.IDLE);
		notifyModeChange();
	}
	
	native void notifyModeChange()/*-{
		var playerJso = this.@eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension::playerJsObject;
		if (typeof playerJso != 'undefined'  && playerJso != null  &&  typeof playerJso.bookmarkingClearButtons == 'function'){		 
			playerJso.bookmarkingClearButtons();
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
		IBookmarkProperties moduleProps = getBookmarkPropertiesForModule(module);
		if (moduleProps == null){
			moduleProps = createBookmarkProperties(module);
			addBookmark(module, moduleProps);
			currBookmarkNewlyCreated = true;
		} else {
			moduleProps.setBookmarkIndex(bookmarkIndex);
			currBookmarkNewlyCreated = false;
		}
		updateBookmarkedModule(module);
	}
	
	IBookmarkProperties createBookmarkProperties(IBookmarkable module){
		BookmarkProperties bp = BookmarkProperties.newInstance();
		bp.setBookmarkIndex(bookmarkIndex);
		bp.setBookmarkTitle(getDefaultTitleForModule(module));
		return bp;
	}
	
	String getDefaultTitleForModule(IBookmarkable module){
		String moduleTitle = module.getDefaultBookmarkTitle();
		if (moduleTitle == null  ||  "".equals(moduleTitle) ){
			moduleTitle = dataSourceSupplier.getItemTitle(currItemIndex);
			moduleTitle = BookmarkingHelper.getDefaultBookmarkTitle(moduleTitle);
		}
		return moduleTitle;
	}
	
	void addBookmark(IBookmarkable module, IBookmarkProperties props){
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
		IBookmarkProperties props = getBookmarkPropertiesForModule(module);
		bookmarkPopup.setBookmarkTitle(props.getBookmarkTitle());
		bookmarkPopup.show(module.getViewArea());
	}
		
	private void registerModuleInList(IBookmarkable module){
		getModulesForCurrentItem().add(module);
	}

	@Override
	public void setPlayerJsObject(JavaScriptObject playerJsObject) {
		this.playerJsObject = playerJsObject;
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
		playerJsObject.bookmarkingClearAll = function(){
			self.@eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension::clearAll()();
		}
		playerJsObject.bookmarkingEditing = function(editing){
			self.@eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension::setEditingMode(Z)(editing);
		}
		playerJsObject.getBookmarks = function(){
			return self.@eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension::getState()();
		}
	}-*/;

	void setClearingMode(boolean clearing){
		if (clearing){
			setMode(Mode.CLEARING);
		} else if (mode == Mode.CLEARING){
			setMode(Mode.IDLE);
		}
	}

	void clearAll(){
		getBookmarksForCurrentItem().clear();
		resetMode();
		updateNotBookmarkedModules();
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
	
	/**
	 * When the mode is other than target one, the mode is changed and the modules are updated.
	 */
	void setMode(Mode newMode){
		if (mode != newMode){
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
		for (int i = 0 ; i < modules.size() ; i ++){
			updateModules(i, updateBookmarkedModules);
		}
	}
	
	/**
	 * Updates all modules for the selected item.
	 * 
	 * @param itemIndex selected item index
	 */
	void updateModules(int itemIndex, boolean updateBookmarkedModules){
		if (itemIndex < modules.size()){
			List<IBookmarkable> itemModules = modules.get(itemIndex);
			for (IBookmarkable module : itemModules){
				if (!isModuleBookmarked(module, itemIndex)){
					updateNotBookmarkedModule(module);
				} else if (updateBookmarkedModules){
					updateBookmarkedModule(module);
				}
			}
		}
	}
	
	/**
	 * Updates module with the assumption that the module is currently NOT bookmarked.
	 * @param module module to update
	 */
	void updateNotBookmarkedModule(IBookmarkable module){
		if (mode == Mode.BOOKMARKING){
			module.setBookmarkingStyleName(styleNames.QP_BOOKMARK_SELECTABLE());
		} else {
			module.removeBookmarkingStyleName();
		}
	}

	/**
	 * Updates module with the assumption that the module is currently not bookmarked.
	 * @param module module to update
	 */
	void updateBookmarkedModule(IBookmarkable module){
		module.setBookmarkingStyleName(styleNames.QP_BOOKMARK_SELECTED() + "-" + getBookmarkPropertiesForModule(module).getBookmarkIndex());
	}

	IBookmarkProperties getBookmarkPropertiesForModule(IBookmarkable module) {
		for (int m = 0 ; m < modules.size() ; m++){
			List<IBookmarkable> itemModules = modules.get(m);
			if (itemModules.contains(module)){
				return bookmarks.get(m).get(itemModules.indexOf(module));
			}
		}
		return null;
	}

	StackMap<Integer, IBookmarkProperties> getBookmarksForCurrentItem() {
		return bookmarks.get(currItemIndex);
	}

	List<IBookmarkable> getModulesForCurrentItem() {
		return modules.get(currItemIndex);
	}

	private boolean isModuleBookmarked(IBookmarkable module, int itemIndex){
		return bookmarks.get(itemIndex).keySet().contains(modules.get(itemIndex).indexOf(module));
	}

	private boolean isModuleBookmarkedWithCurrentIndex(IBookmarkable module, int itemIndex){
		IBookmarkProperties props = bookmarks.get(itemIndex).get(modules.get(itemIndex).indexOf(module));
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
	
	private JSONObject getItemState(StackMap<Integer, IBookmarkProperties> map){
		JSONObject obj = new JSONObject();
		for (Integer key : map.keySet()){
			IBookmarkProperties bp = map.get(key);
			obj.put(key.toString(), new JSONObject((JavaScriptObject)bp));
		}
		return obj;
	}

	@Override
	public void setState(JSONArray newState) {		
		
		JavaScriptObject externalBookmarks = getExternalBookmarks();
		
		JSONArray externalState = null;
		if(externalBookmarks != null){
			externalState = (JSONArray)JSONParser.parseLenient(externalBookmarks.toString());
		}
		
		JSONArray state = externalState == null ? newState : externalState;
		
		bookmarks.clear();
		for (int i = 0 ; i < state.size() ; i ++ ){
			bookmarks.add( decodeItemState(state.get(i).isObject()) );
		}
	}
	
	private native JavaScriptObject getExternalBookmarks()/*-{
		var playerJso = this.@eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension::playerJsObject;		
		if (typeof playerJso != 'undefined'  && playerJso != null && typeof playerJso.getExternalBookmarks == 'function'){			
			return playerJso.getExternalBookmarks();
		}
		return null;
	}-*/;

	private StackMap<Integer, IBookmarkProperties> decodeItemState(JSONObject object) {
		StackMap<Integer, IBookmarkProperties> map = new StackMap<Integer, IBookmarkProperties>();
		for (String key : object.keySet()){
			Integer keyInt = NumberUtils.tryParseInt(key, null);
			IBookmarkProperties props = object.get(key).isObject().getJavaScriptObject().<BookmarkProperties>cast();
			if (keyInt != null  &&  props != null){
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
		if (currBookmarkNewlyCreated){
			onBookmarkPopupClosed();
			removeBookmark(currEditingModule);
		}
	}
	
	private void onBookmarkPopupClosed(){
		currBookmarkNewlyCreated = false;		
	}

}