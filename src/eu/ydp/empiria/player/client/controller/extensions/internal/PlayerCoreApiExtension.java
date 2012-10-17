package eu.ydp.empiria.player.client.controller.extensions.internal;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.communication.DisplayOptions;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngineSocket;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEngineSocketUserExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEventsListenerExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.PlayerJsObjectModifierExtension;

public class PlayerCoreApiExtension extends
		InternalExtension implements DeliveryEngineSocketUserExtension, PlayerJsObjectModifierExtension, DeliveryEventsListenerExtension {

	protected JavaScriptObject playerJsObject;
	protected DeliveryEngineSocket deliveryEngineSocket;
	
	@Override
	public void init() {
		initExportStateStringJs(playerJsObject);
	}

	@Override
	public void setDeliveryEngineSocket(DeliveryEngineSocket des) {
		deliveryEngineSocket = des;
	}

	@Override
	public void setPlayerJsObject(JavaScriptObject playerJsObject) {
		this.playerJsObject = playerJsObject;
	}

	@Override
	public void onDeliveryEvent(DeliveryEvent deliveryEvent) {
		if (deliveryEvent.getType() == DeliveryEventType.ASSESSMENT_LOADING){
			setOptions();
		}
		if (deliveryEvent.getType() == DeliveryEventType.ASSESSMENT_STARTING){
			importInitialItemIndex();
			importState();
		}
	}
	
	private void setOptions(){
		JavaScriptObject flowOptionsJs = callImportFlowOptionsJs(playerJsObject);
		
		if (flowOptionsJs != null){
			FlowOptions flowOptions = FlowOptions.fromJsObject(flowOptionsJs);
			deliveryEngineSocket.setFlowOptions(flowOptions);
		}

		JavaScriptObject displayOptionsJs = callImportDisplayOptionsJs(playerJsObject);
		
		if (displayOptionsJs != null){
			DisplayOptions displayOptions = DisplayOptions.fromJsObject(displayOptionsJs);
			deliveryEngineSocket.setDisplayOptions(displayOptions);
		}
	}
	
	private native JavaScriptObject callImportFlowOptionsJs(JavaScriptObject playerJsObject)/*-{
		if (typeof playerJsObject.importFlowOptions == 'function')
			return playerJsObject.importFlowOptions();
		return null;
	}-*/;
	
	private native JavaScriptObject callImportDisplayOptionsJs(JavaScriptObject playerJsObject)/*-{
		if (typeof playerJsObject.importDisplayOptions == 'function')
			return playerJsObject.importDisplayOptions();
		return null;
	}-*/;
	
	private void importState(){
		String state = callImportStateStringJs(playerJsObject);
		if (!"".equals(state)){
			deliveryEngineSocket.setStateString(state);
		}
	}	

	private native String callImportStateStringJs(JavaScriptObject playerJsObject)/*-{
		if (typeof playerJsObject.importStateString == 'function')
			return playerJsObject.importStateString();
		return "";
	}-*/;

	private String exportState(){
		return deliveryEngineSocket.getStateString();
	}

	private native void initExportStateStringJs(JavaScriptObject playerJsObject)/*-{
		var instance = this;
		playerJsObject.exportStateString = function(){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.internal.PlayerCoreApiExtension::exportState()();
		}
	}-*/;
	
	private void importInitialItemIndex(){
		int importedItemIndex = callImportInitialItemIndex(playerJsObject);
		Integer itemIndex = (importedItemIndex > -1) ? Integer.valueOf(importedItemIndex) : null;
		deliveryEngineSocket.setInitialItemIndex(itemIndex);
	}
	
	private native int callImportInitialItemIndex(JavaScriptObject playerJsObject)/*-{
		if (typeof playerJsObject.importInitialItemIndex == 'function')
			return playerJsObject.importInitialItemIndex();
			
		return -1;		
	}-*/;
}
