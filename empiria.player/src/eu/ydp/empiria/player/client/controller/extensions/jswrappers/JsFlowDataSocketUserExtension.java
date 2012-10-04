package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.FlowDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;

public class JsFlowDataSocketUserExtension extends JsExtension implements
		FlowDataSocketUserExtension {

	protected JavaScriptObject socketJs;
	protected FlowDataSupplier supplier;
	
	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_SOCKET_USER_FLOW_DATA_CLIENT;
	}

	@Override
	public void init() {
		socketJs = createFlowDataSocketJs();
		setFlowDataSocketJs(extensionJsObject, socketJs);
	}

	@Override
	public void setFlowDataSupplier(FlowDataSupplier supplier) {
		this.supplier = supplier;
		
	}
	
	private native JavaScriptObject createFlowDataSocketJs()/*-{
		var instance = this;
		var socket = [];
		socket.getFlowOptions = function(){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsFlowDataSocketUserExtension::getFlowOptionsJs()();
		}
		socket.getCurrentPageIndex = function(){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsFlowDataSocketUserExtension::getCurrentPageIndexJs()();
		}
		socket.getCurrentPageType = function(){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsFlowDataSocketUserExtension::getCurrentPageTypeJs()();
		}
		socket.getActivityMode = function(){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsFlowDataSocketUserExtension::getActivityMode()();
		}
		return socket;
	}-*/;

	private JavaScriptObject getFlowOptionsJs(){
		return supplier.getFlowOptions().toJsObject();
	}
	private int getCurrentPageIndexJs(){
		return supplier.getCurrentPageIndex();
	}
	private String getCurrentPageTypeJs(){
		return supplier.getCurrentPageType().toString();
	}
	private String getActivityMode(){
		return supplier.getActivityMode().toString();
	}

	private native void setFlowDataSocketJs(JavaScriptObject extension, JavaScriptObject socket)/*-{
		if (typeof extension.setFlowDataSocket == 'function'){
			extension.setFlowDataSocket(socket);
		}
	}-*/;
}
