package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.PlayerJsObjectModifierExtension;

public class JsPlayerJsObjectUserExtension extends JsExtension implements
		PlayerJsObjectModifierExtension {

	protected JavaScriptObject playerJsObject;
	
	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_PLAYER_JS_OBJECT_USER;
	}

	@Override
	public void init() {
		setPlayerJsObjectJs(extensionJsObject, playerJsObject);
	}
	
	@Override
	public void setPlayerJsObject(JavaScriptObject playerJsObject) {
		this.playerJsObject = playerJsObject;
	}
	
	private native void setPlayerJsObjectJs(JavaScriptObject extension,JavaScriptObject jsObject)/*-{
		if (typeof extension.setPlayerJsObject == 'function'){
			extension.setPlayerJsObject(jsObject);
		}
	}-*/;


}
