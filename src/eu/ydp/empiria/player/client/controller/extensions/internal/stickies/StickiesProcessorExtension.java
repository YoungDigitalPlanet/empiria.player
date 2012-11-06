package eu.ydp.empiria.player.client.controller.extensions.internal.stickies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.body.PlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.extensions.internal.InternalExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DataSourceDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.PlayerJsObjectModifierExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.StatefulExtension;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

public class StickiesProcessorExtension extends InternalExtension implements DataSourceDataSocketUserExtension, PlayerJsObjectModifierExtension, 
	StatefulExtension {
	
	@Inject PlayerContainersAccessor itemBodyAccessor;
	@Inject StyleNameConstants styleNames;
	@Inject EventsBus eventsBus;
	@Inject Provider<IStickieView> viewProvider;

	List<List<StickieProperties>> stickies = new ArrayList<List<StickieProperties>>();
	Map<StickieProperties, IStickieView> views = new HashMap<StickieProperties, IStickieView>(); 
	private DataSourceDataSupplier dataSourceSupplier;
	int currItemIndex = 0;
	JavaScriptObject playerJsObject;

	@Override
	public void init() {
		int itemsCount = dataSourceSupplier.getItemsCount();
		stickies.clear();
		for (int i = 0 ; i < itemsCount ; i ++){
			stickies.add(new ArrayList<StickieProperties>());
		}
		
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE), new PlayerEventHandler() {
			
			@Override
			public void onPlayerEvent(PlayerEvent event) {
				currItemIndex = (Integer) event.getValue();
			}
		});
		
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.PAGE_INITIALIZED), new PlayerEventHandler() {
			
			@Override
			public void onPlayerEvent(PlayerEvent event) {
				initStickies(currItemIndex);
			}
		});
	}
	
	@Override
	public JSONArray getState() {
		JSONArray stateArray = new JSONArray();
		for (int i = 0 ; i < stickies.size() ; i ++ ){
			stateArray.set(i, getItemState(stickies.get(i)));
		}
		return stateArray;
	}

	private JSONArray getItemState(List<StickieProperties> list) {
		JSONArray arr = new JSONArray();
		for (StickieProperties sp : list){
			arr.set(arr.size(), sp.toJSON());
		}
		return arr;
	}

	@Override
	public void setState(JSONArray newState) {
		String externalStickies = getExternalStickies();
		
		JSONArray externalState = null;
		if(externalStickies != null){
			externalState = (JSONArray)JSONParser.parseLenient(externalStickies.toString());
		}
		
		JSONArray state = externalState == null ? newState : externalState;
		
		stickies.clear();
		for (int i = 0 ; i < state.size() ; i ++ ){
			stickies.add( decodeItemState(state.get(i).isArray()) );
		}
	}
	
	private List<StickieProperties> decodeItemState(JSONArray array) {
		List<StickieProperties> itemStickies = new ArrayList<StickieProperties>();
		for (int i = 0 ; i < array.size() ; i ++ ){
			StickieProperties sp = StickieProperties.fromJSON(array.get(i).isArray());
			itemStickies.add(sp);
		}
		return itemStickies;
	}

	private native String getExternalStickies()/*-{
		var playerJso = this.@eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiesProcessorExtension::playerJsObject;		
		if (typeof playerJso != 'undefined'  && playerJso != null && typeof playerJso.getExternalStickies == 'function'){			
			return playerJso.getExternalStickies();
		}
		return null;
	}-*/;

	@Override
	public void setPlayerJsObject(JavaScriptObject playerJsObject) {
		this.playerJsObject = playerJsObject;
		initJsApi(playerJsObject);
	}
	
	private native void initJsApi(JavaScriptObject playerJsObject) /*-{
		var self = this;
		playerJsObject.stickiesAdd = function(colorIndex){
			self.@eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiesProcessorExtension::addStickie(I)(colorIndex);
		}
		playerJsObject.stickiesClearAll = function(){
			self.@eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiesProcessorExtension::clearAll()();
		}
		playerJsObject.getStickies = function(){
			return self.@eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiesProcessorExtension::getState()();
		}
		playerJsObject.setStickies = function(stickiesStateJson){
			self.@eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiesProcessorExtension::setState(Lcom/google/gwt/json/client/JSONArray;)(stickiesStateJson);
		}
	}-*/;

	@Override
	public void setDataSourceDataSupplier(DataSourceDataSupplier supplier) {
		dataSourceSupplier = supplier;
	}
	
	void addStickie(int colorIndex){
		StickieProperties sp = new StickieProperties(colorIndex, "", -2000, -2000);
		getStickiesForCurrentItem().add(sp);
		addStickieView(sp);
	}
	
	void addStickieView(final StickieProperties sp){
		final IStickieView view = viewProvider.get();
		view.setColorIndex(sp.getColorIndex());
		view.setPresenter(new IStickieView.IPresenter() {
			
			@Override
			public void stickieMinimize() {
				sp.setMinimized(!sp.getMinimized());
				view.setMinimized(sp.getMinimized());
			}
			
			@Override
			public void stickieDelete() {
				deleteStickie(sp);
			}
			
			@Override
			public void stickieChange() {
				sp.setStickieContent(view.getText());
				sp.setX(view.getX());
				sp.setY(view.getY());
			}
		});
		view.setViewParent(itemBodyAccessor.getItemBodyContainer(currItemIndex));
	}
	
	void deleteStickie(StickieProperties sp){
		deleteStickieView(sp);
		getStickiesForCurrentItem().remove(sp);
	}
	
	void deleteStickieView(StickieProperties sp){
		views.get(sp).remove();
	}

	private void initStickies(int itemIndex) {
		
	}
	
	void clearAll(){
		Iterator<StickieProperties> iter = getStickiesForCurrentItem().iterator();
		while (iter.hasNext()){
			deleteStickie(iter.next());
		}
	}
	
	List<StickieProperties> getStickiesForCurrentItem(){
		return stickies.get(currItemIndex);
	}
	
}
