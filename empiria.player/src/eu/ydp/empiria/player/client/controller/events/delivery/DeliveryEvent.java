package eu.ydp.empiria.player.client.controller.events.delivery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.flow.processing.events.ActivityProcessingEvent;
import eu.ydp.empiria.player.client.controller.flow.processing.events.FlowProcessingEvent;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;
import eu.ydp.empiria.player.client.util.js.JSArrayUtils;

public class DeliveryEvent {

	protected DeliveryEventType type;
	protected Map<String, Object> params;
	
	public DeliveryEvent(DeliveryEventType type){
		this.type = type;
		params = new HashMap<String, Object>();
	}
	
	public DeliveryEvent(DeliveryEventType type, Map<String, Object> params){
		this.type = type;
		this.params = params;
	}
	
	public DeliveryEventType getType(){
		return type;
	}

	public Map<String, Object> getParams() {
		return params;
	}
	
	public JavaScriptObject toJsObject(){
		JavaScriptObject paramsArr = JavaScriptObject.createArray();
		for (String key : params.keySet()){
			Object currParam = params.get(key); 
			if (currParam instanceof String){
				JSArrayUtils.fillArray(paramsArr, key, (String)currParam);
			} else if (currParam instanceof Boolean) {
				JSArrayUtils.fillArray(paramsArr, key, ((Boolean)currParam).booleanValue() );				
			} else if (currParam instanceof IInteractionModule) {
				JSArrayUtils.fillArray(paramsArr, key, ((IInteractionModule)currParam).getJsSocket() ) ;				
			}
		}
		return createJsObject(type.toString(), paramsArr);
	}
	
	private native JavaScriptObject createJsObject(String type, JavaScriptObject params)/*-{
		var obj = [];
		obj.type = type;
		obj.params = params;
		return obj;
	}-*/;

	public static DeliveryEvent fromFlowProcessingEvent(FlowProcessingEvent event) {
		DeliveryEvent de = null;
		
		DeliveryEventType type = null;

		for (DeliveryEventType currType : DeliveryEventType.values()){
			if (currType.toString().equals(event.getType().toString())){
				type = currType;
				break;
			}				
		}
		
		if (type == null)
			return null;
		
		if (event instanceof ActivityProcessingEvent){
			GroupIdentifier gi = ((ActivityProcessingEvent)event).getGroupIdentifier();
			if (gi != null){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("groupIdentifier", gi);
				de = new DeliveryEvent(type, params);	
			}
		}
		if (de == null)
			de = new DeliveryEvent(type);
		
		return de;
	}
}
