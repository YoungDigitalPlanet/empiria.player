package eu.ydp.empiria.player.client.controller.events.delivery;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.module.IInteractionModule;
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
}
