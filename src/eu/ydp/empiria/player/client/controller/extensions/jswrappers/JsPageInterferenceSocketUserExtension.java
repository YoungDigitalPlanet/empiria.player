package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.communication.sockets.PageInterferenceSocket;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.PageInterferenceSocketUserExtension;

public class JsPageInterferenceSocketUserExtension extends JsExtension implements PageInterferenceSocketUserExtension {

	protected PageInterferenceSocket pageInterferenceSocket;
	protected JavaScriptObject assessmentInterferenceSocketJs;

	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_SOCKET_USER_INTERFERENCE_PAGE;
	}

	@Override
	public void init() {
		assessmentInterferenceSocketJs = createPageInterferenceSocketJs(pageInterferenceSocket.getJsSocket());
		setInterferenceSocketJs(extensionJsObject, assessmentInterferenceSocketJs);
	}
	
	@Override
	public void setPageInterferenceSocket(PageInterferenceSocket pcs) {
		pageInterferenceSocket = pcs;
	}
	
	private native JavaScriptObject createPageInterferenceSocketJs(JavaScriptObject socketJs)/*-{
		var instance = this;
		var socket = [];
		var ais = socketJs;
		socket.getPageInterferenceSocket = function(){
			return ais;
		}
		return socket;
	}-*/;
	
	private native void setInterferenceSocketJs(JavaScriptObject extension, JavaScriptObject socket)/*-{
		if (typeof extension.setInterferenceSocket == 'function'){
			extension.setInterferenceSocket(socket);
		}
	}-*/;

}
