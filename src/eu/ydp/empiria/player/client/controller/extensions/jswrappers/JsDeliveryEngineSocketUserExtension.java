package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.communication.DisplayOptions;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngineSocket;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEngineSocketUserExtension;

public class JsDeliveryEngineSocketUserExtension extends JsExtension implements
		DeliveryEngineSocketUserExtension {

	protected DeliveryEngineSocket deliveryEngineSocket;
	protected JavaScriptObject socket;

	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_SOCKET_USER_DELIVERY_ENGINE;
	}

	@Override
	public void init() {
		socket = createDeliveryEngineSocketJs();
		setDeliveryEngineSocketJs(extensionJsObject, socket);
	}
	
	@Override
	public void setDeliveryEngineSocket(DeliveryEngineSocket des) {
		deliveryEngineSocket = des;
	}

	private native JavaScriptObject createDeliveryEngineSocketJs()/*-{
		var instance = this;
		var socket = [];
		socket.setFlowOptions = function(o){
			instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsDeliveryEngineSocketUserExtension::setFlowOptions(Lcom/google/gwt/core/client/JavaScriptObject;)(o);
		}
		socket.setDisplayOptions = function(o){
			instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsDeliveryEngineSocketUserExtension::setDisplayOptions(Lcom/google/gwt/core/client/JavaScriptObject;)(o);
		}
		socket.setStateString = function(s){
			instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsDeliveryEngineSocketUserExtension::setStateString(Ljava/lang/String;)(s);
		}
		socket.getStateString = function(){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsDeliveryEngineSocketUserExtension::getStateString()();
		}
		socket.getEngineMode = function(s){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsDeliveryEngineSocketUserExtension::getEngineMode()();
		}
		return socket;
	}-*/;
	
	private void setFlowOptions(JavaScriptObject fo){
		deliveryEngineSocket.setFlowOptions(FlowOptions.fromJsObject(fo));
	}
	
	private void setDisplayOptions(JavaScriptObject doo){
		deliveryEngineSocket.setDisplayOptions(DisplayOptions.fromJsObject(doo));
	}
	
	private void setStateString(String state){
		deliveryEngineSocket.setStateString(state);
	}
	
	private String getStateString(){
		return deliveryEngineSocket.getStateString();
	}
	
	private String getEngineMode(){
		return deliveryEngineSocket.getEngineMode();
	}
	
	private native void setDeliveryEngineSocketJs(JavaScriptObject extension, JavaScriptObject socket)/*-{
		if (typeof extension.setDeliveryEngineSocket == 'function'){
			extension.setDeliveryEngineSocket(socket);
		}
	}-*/;
}
