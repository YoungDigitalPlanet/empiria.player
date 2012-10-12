package eu.ydp.empiria.player.client.controller.extensions.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Command;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.extensions.types.DataSourceDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.ModuleHandlerExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.PlayerJsObjectModifierExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.StatefulExtension;
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
	DataSourceDataSocketUserExtension, PlayerJsObjectModifierExtension, StatefulExtension {

	private DataSourceDataSupplier dataSourceSupplier;
	int currItemIndex = 0;
	List<List<IBookmarkable>> modules = new LinkedList<List<IBookmarkable>>();
	List<StackMap<Integer, Integer>> bookmarks = new LinkedList<StackMap<Integer, Integer>>();
	boolean bookmarking = false;
	boolean clearing = false;
	EventsBus eventsBus;
	StyleNameConstants styleNames;
	Integer bookmarkIndex;

	@Override
	public void init() {
		initInjection();
		int itemsCount = dataSourceSupplier.getItemsCount();
		modules.clear();
		for (int i = 0 ; i < itemsCount ; i ++){
			modules.add(new ArrayList<IBookmarkable>());
		}
		bookmarks.clear();
		for (int i = 0 ; i < itemsCount ; i ++){
			bookmarks.add(new StackMap<Integer, Integer>());
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
			}
		});
		
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_INITIALIZED), new PlayerEventHandler() {
			
			@Override
			public void onPlayerEvent(PlayerEvent event) {
				if (bookmarking){
					updateModules(currItemIndex);
				}
			}
		});
	}
	
	void initInjection(){
		styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();
		eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
	}

	boolean accepts(IModule module) {
		return module instanceof IBookmarkable  &&  !parentRegistered(module)  &&  !containsInteraction(module);
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
					if (bookmarking){
						bookmarkModule(accModule);
					} else if (clearing){
						bookmarkModule(accModule, false);
					}
				}
			});
		}
	}
	
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
		int moduleIndex = getModulesForCurrentItem().indexOf(module);
		if (bookmarkIt  &&  !isModuleBookmarkedWithCurrentIndex(module, currItemIndex)){
			getBookmarksForCurrentItem().put(moduleIndex, bookmarkIndex);
			module.setBookmarkingStyleName(styleNames.QP_BOOKMARK_SELECTED() + "-" + bookmarkIndex);
		} else if (!bookmarkIt  &&  isModuleBookmarked(module, currItemIndex)){
			getBookmarksForCurrentItem().remove(moduleIndex);
			if (bookmarking){
				module.setBookmarkingStyleName(styleNames.QP_BOOKMARK_SELECTABLE());
			} else if (clearing){
				module.removeBookmarkingStyleName();
			}
		}
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
			self.@eu.ydp.empiria.player.client.controller.extensions.internal.BookmarkProcessorExtension::startBookmarking(I)(bookmarkIndex);
		}
		playerJsObject.bookmarkingStop = function(){
			self.@eu.ydp.empiria.player.client.controller.extensions.internal.BookmarkProcessorExtension::stopBookmarking()();
		}
		playerJsObject.bookmarkingClearing = function(clearing){
			self.@eu.ydp.empiria.player.client.controller.extensions.internal.BookmarkProcessorExtension::setClearingMode(Z)(clearing);
		}
	}-*/;
	
	void setClearingMode(boolean clearing){
		if (clearing){
			if (bookmarking){
				bookmarking = false;
			}
		}
		this.clearing  = clearing;
		updateModules();
	}
	
	void stopBookmarking(){
		bookmarking = false;
		updateModules();
	}
	
	void startBookmarking(int bookmarkIndex){
		this.bookmarkIndex = bookmarkIndex;
		bookmarking = true;
		clearing = false;
		updateModules();
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
				if (bookmarking){
					module.setBookmarkingStyleName(styleNames.QP_BOOKMARK_SELECTABLE());
				} else {
					module.removeBookmarkingStyleName();
				}
			}
		}		
	}

	Integer getBookmarkIndexForModule(IBookmarkable module) {
		for (int m = 0 ; m < modules.size() ; m++){
			List<IBookmarkable> itemModules = modules.get(m);
			if (itemModules.contains(module)){
				return bookmarks.get(m).get(itemModules.indexOf(module));
			}
		}
		return -1;
	}

	StackMap<Integer, Integer> getBookmarksForCurrentItem() {
		return bookmarks.get(currItemIndex);
	}

	List<IBookmarkable> getModulesForCurrentItem() {
		return modules.get(currItemIndex);
	}

	private boolean isModuleBookmarked(IBookmarkable module, int itemIndex){
		return bookmarks.get(itemIndex).keySet().contains(modules.get(itemIndex).indexOf(module));
	}

	private boolean isModuleBookmarkedWithCurrentIndex(IBookmarkable module, int itemIndex){
		return bookmarkIndex.equals(bookmarks.get(itemIndex).get(modules.get(itemIndex).indexOf(module)));
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
	
	private JSONObject getItemState(StackMap<Integer, Integer> map){
		JSONObject obj = new JSONObject();
		for (Integer key : map.keySet()){
			obj.put(key.toString(), new JSONString(map.get(key).toString()));
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

	private StackMap<Integer, Integer> decodeItemState(JSONObject object) {
		StackMap<Integer, Integer> map = new StackMap<Integer, Integer>();
		for (String key : object.keySet()){
			Integer keyInt = NumberUtils.tryParseInt(key, null);
			Integer valueInt = NumberUtils.tryParseInt(object.get(key).isString().stringValue(), null);
			if (keyInt != null  &&  valueInt != null){
				map.put(keyInt, valueInt);
			}
		}
		return map;
	}

}