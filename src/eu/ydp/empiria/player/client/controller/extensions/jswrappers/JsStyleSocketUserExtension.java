package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.StyleSocketUserExtension;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.js.JSArrayUtils;

public class JsStyleSocketUserExtension extends JsExtension implements StyleSocketUserExtension {

	protected JavaScriptObject styleSocketJs;
	protected StyleSocket styleSocket;
	
	public JsStyleSocketUserExtension(){
		super();
	}
	
	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_SOCKET_USER_STYLE_CLIENT;
	}

	@Override
	public void init() {
		styleSocketJs = createStyleSocketJs();
		setStyleSocketJs(extensionJsObject, styleSocketJs);
	}
	
	public void setStyleSocket(StyleSocket ss){
		styleSocket = ss;
	}
	
	private native JavaScriptObject createStyleSocketJs()/*-{
		var instance = this;
		var socket = [];
		socket.getStyle = function(element){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsStyleSocketUserExtension::getStyle(Lcom/google/gwt/dom/client/Element;)(element);
		}
		return socket;
	}-*/;
	
	private native void setStyleSocketJs(JavaScriptObject extension, JavaScriptObject socket)/*-{
		if (typeof extension.setStyleSocket == 'function'){
			extension.setStyleSocket(socket);
		}
	}-*/;
	
	private JavaScriptObject getStyle(com.google.gwt.dom.client.Element element){
		Element xmlElement = XMLParser.createDocument().createElement(element.getNodeName().toLowerCase());
		Map<String, String> styles = styleSocket.getStyles(xmlElement);
		JavaScriptObject stylesJsArray = JavaScriptObject.createObject();
		
		for (String currKey : styles.keySet()){
			JSArrayUtils.fillArray(stylesJsArray, currKey, styles.get(currKey));
		}
		
		return stylesJsArray;
	} 

}
