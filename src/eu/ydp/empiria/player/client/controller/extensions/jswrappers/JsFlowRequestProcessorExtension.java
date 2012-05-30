package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestProcessorExtension;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequest;

public class JsFlowRequestProcessorExtension extends JsExtension implements FlowRequestProcessorExtension {
	
	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_PROCESSOR_FLOW_REQUEST;
	}

	@Override
	public void init() {
	}
	
	@Override
	public boolean isRequestSupported(IFlowRequest request) {
		return isRequestSupportedJs(extensionJsObject, request.getName());
	}
	private native boolean isRequestSupportedJs(JavaScriptObject extenstionObject, String requestName)/*-{
		return extenstionObject.isFlowRequestSupported(requestName);
	}-*/;
	
	@Override
	public void processRequest(IFlowRequest request) {
		processRequestJs(extensionJsObject, request.toJsObject());
	}
	private native void processRequestJs(JavaScriptObject extenstionObject, JavaScriptObject requestJsObject)/*-{
		extenstionObject.processFlowRequest(requestJsObject);
	}-*/;

}
