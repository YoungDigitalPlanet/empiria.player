package eu.ydp.empiria.player.client.controller.flow.request;

import com.google.gwt.core.client.JavaScriptObject;

public interface IFlowRequest {

	public String getName();
	
	public JavaScriptObject toJsObject();
}
