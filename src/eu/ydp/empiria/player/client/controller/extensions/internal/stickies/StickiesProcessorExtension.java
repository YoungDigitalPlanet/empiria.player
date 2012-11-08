package eu.ydp.empiria.player.client.controller.extensions.internal.stickies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
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
	@Inject Provider<IStickieProperties> propertiesProvider;

	List<List<IStickieProperties>> stickies = new ArrayList<List<IStickieProperties>>();
	Map<IStickieProperties, IStickieView> views = new HashMap<IStickieProperties, IStickieView>(); 
	private DataSourceDataSupplier dataSourceSupplier;
	int currItemIndex = 0;
	JavaScriptObject playerJsObject;

	@Override
	public void init() {
		int itemsCount = dataSourceSupplier.getItemsCount();
		stickies.clear();
		for (int i = 0 ; i < itemsCount ; i ++){
			stickies.add(new ArrayList<IStickieProperties>());
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

	private JSONArray getItemState(List<IStickieProperties> list) {
		JSONArray arr = new JSONArray();
		for (IStickieProperties sp : list){
			arr.set(arr.size(), new JSONObject((JavaScriptObject)sp));
		}
		return arr;
	}

	@Override
	public void setState(JSONArray newState) {
		JavaScriptObject externalStickies = getExternalStickies();
		
		JSONArray externalState = null;
		if(externalStickies != null){
			externalState = (JSONArray)JSONParser.parseLenient(externalStickies.toString());
		} else {
			externalState = newState;
		}
		
		stickies.clear();
		for (int i = 0 ; i < externalState.size() ; i ++ ){
			stickies.add( decodeItemState(externalState.get(i).isArray()) );
		}
	}
	
	private List<IStickieProperties> decodeItemState(JSONArray array) {
		List<IStickieProperties> itemStickies = new ArrayList<IStickieProperties>();
		for (int i = 0 ; i < array.size() ; i ++ ){
			IStickieProperties sp = array.get(i).isObject().getJavaScriptObject().<StickieProperties>cast();
			itemStickies.add(sp);
		}
		return itemStickies;
	}

	private native JavaScriptObject getExternalStickies()/*-{
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
		IStickieProperties sp = propertiesProvider.get();
		sp.setColorIndex(colorIndex);
		sp.updateTimestamp();
		getStickiesForCurrentItem().add(sp);
		addStickieView(sp);
	}
	
	void addStickieView(final IStickieProperties sp){
		final IStickieView view = viewProvider.get();
		view.setColorIndex(sp.getColorIndex());
		view.setPresenter(new IStickieView.IPresenter() {
			
			@Override
			public void stickieMinimize() {
				sp.setMinimized(!sp.isMinimized());
				view.setMinimized(sp.isMinimized());
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
		views.put(sp, view);
		checkStickieOverlay(sp);
	}
	
	private void checkStickieOverlay(IStickieProperties sp) {
		for (int s = 0 ; s < getStickiesForCurrentItem().size() ; s ++){
			IStickieProperties refSp = getStickiesForCurrentItem().get(s);
			if (refSp != sp){
				if (calculateDistance(sp.getX(), sp.getY(), refSp.getX(), refSp.getY()) < 30){
					views.get(sp).setX(refSp.getX() + 20);
					views.get(sp).setY(refSp.getY() + 20);
				}
			}
		}
	}
	
	private double calculateDistance(int x1, int y1, int x2, int y2){
		return Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
	}

	void deleteStickie(IStickieProperties sp){
		deleteStickieView(sp);
		getStickiesForCurrentItem().remove(sp);
	}
	
	void deleteStickieView(IStickieProperties sp){
		views.get(sp).remove();
	}

	private void initStickies(int itemIndex) {
		
	}
	
	void clearAll(){
		List<IStickieProperties> currStickies = getStickiesForCurrentItem();
		while (currStickies.size() > 0){
			deleteStickie(currStickies.get(0));
		}
	}
	
	List<IStickieProperties> getStickiesForCurrentItem(){
		return stickies.get(currItemIndex);
	}
	
}
