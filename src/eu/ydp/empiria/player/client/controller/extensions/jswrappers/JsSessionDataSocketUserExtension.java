package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.SessionDataSocketUserExtension;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;

public class JsSessionDataSocketUserExtension extends JsExtension implements
		SessionDataSocketUserExtension {


	protected JavaScriptObject sessionDataSocketJs;
	protected SessionDataSupplier supplier;

	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_SOCKET_USER_SESSION_DATA_CLIENT;
	}

	@Override
	public void init() {
		sessionDataSocketJs = createSessionDataSocketJs();
		setSessionDataSocketJs(extensionJsObject, sessionDataSocketJs);
	}
	
	@Override
	public void setSessionDataSupplier(SessionDataSupplier supplier) {
		this.supplier = supplier;
	}
	
	private native JavaScriptObject createSessionDataSocketJs()/*-{
		var instance = this;
		var socket = [];
		socket.getItemSessionDataSocket = function(index){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsSessionDataSocketUserExtension::getItemSessionDataSocket(I)(index);
		}
		socket.getAssessmentSessionDataSocket = function(){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsSessionDataSocketUserExtension::getAssessmentSessionDataSocket()();
		}
		return socket;
	}-*/;
	
	private JavaScriptObject getItemSessionDataSocket(int index){
		return supplier.getItemSessionDataSocket(index).getJsSocket();
	}
	
	private JavaScriptObject getAssessmentSessionDataSocket(){
		return supplier.getAssessmentSessionDataSocket().getJsSocket();
	}
	
	private native void setSessionDataSocketJs(JavaScriptObject extension, JavaScriptObject socket)/*-{
		if (typeof extension.setSessionDataSocket == 'function'){
			extension.setSessionDataSocket(socket);
		}
	}-*/;

}
