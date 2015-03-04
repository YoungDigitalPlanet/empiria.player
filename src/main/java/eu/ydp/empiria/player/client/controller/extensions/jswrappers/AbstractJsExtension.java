package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.extensions.Extension;

public abstract class AbstractJsExtension implements Extension, JsExtension {

	protected JavaScriptObject extensionJsObject;

	@Override
	public void initJs(JavaScriptObject extensionJsObject) {
		this.extensionJsObject = extensionJsObject;
	}

}
