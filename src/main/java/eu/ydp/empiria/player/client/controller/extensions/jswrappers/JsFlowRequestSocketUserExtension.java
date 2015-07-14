package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowRequestSocketUserExtension;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.controller.flow.request.IFlowRequest;

public class JsFlowRequestSocketUserExtension extends AbstractJsExtension implements FlowRequestSocketUserExtension {

    protected JavaScriptObject flowRequestsSocketJs;
    protected FlowRequestInvoker flowRequestInvoker;

    @Override
    public ExtensionType getType() {
        return ExtensionType.EXTENSION_SOCKET_USER_FLOW_REQUEST;
    }

    @Override
    public void init() {
        flowRequestsSocketJs = createFlowRequestsSocketJs();
        setFlowRequestsSocketJs(extensionJsObject, flowRequestsSocketJs);
    }

    @Override
    public void setFlowRequestsInvoker(FlowRequestInvoker fri) {
        flowRequestInvoker = fri;
    }

    protected void invokeFlowRequest(JavaScriptObject requestJs) {
        IFlowRequest request = FlowRequest.fromJsObject(requestJs);
        flowRequestInvoker.invokeRequest(request);
    }

    private native JavaScriptObject createFlowRequestsSocketJs()/*-{
        var instance = this;
        var socket = [];
        socket.invokeFlowRequest = function (request) {
            instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsFlowRequestSocketUserExtension::invokeFlowRequest(Lcom/google/gwt/core/client/JavaScriptObject;)(request);
        }
        return socket;
    }-*/;

    private native void setFlowRequestsSocketJs(JavaScriptObject extension, JavaScriptObject socket)/*-{
        if (typeof extension.setFlowRequestsSocket == 'function') {
            extension.setFlowRequestsSocket(socket);
        }
    }-*/;

}
