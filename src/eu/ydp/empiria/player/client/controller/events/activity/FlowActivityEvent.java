package eu.ydp.empiria.player.client.controller.events.activity;

import com.google.gwt.core.client.JavaScriptObject;

public class FlowActivityEvent {

	protected FlowActivityEventType type;
	
	public FlowActivityEvent(FlowActivityEventType type){
		this.type = type;
	}
	
	public FlowActivityEventType getType(){
		return type;
	}
	
	public static FlowActivityEvent fromJsObject(JavaScriptObject jsObject){
		String currTypeString = getTypeJs(jsObject);
		if (currTypeString == null)
			return null;
		currTypeString = currTypeString.trim().toUpperCase();
		for (FlowActivityEventType currType : FlowActivityEventType.values()){
			if (currType.toString().equals(currTypeString)){
				return new FlowActivityEvent(currType);
			}
		}
		return null;
	}
	
	private static native String getTypeJs(JavaScriptObject jsObject)/*-{
		if (typeof jsObject.type == 'string'){
			return jsObject.type;
		}
		return "";
	}-*/;

}
