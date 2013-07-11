package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.controller.extensions.types.TutorExtension;

public class JsTutorExtension implements JsExtension, TutorExtension {

	private JavaScriptObject extensionJsObject;

	@Override
	public void initJs(JavaScriptObject extensionJsObject) {
		this.extensionJsObject = extensionJsObject;
	}
	
	@Override
	public String getTutorId() {
		return getTutorIdNative(extensionJsObject);
	}

	private final native String getTutorIdNative(JavaScriptObject extensionJsObject)/*-{
		return extensionJsObject.getTutorId(); 
	}-*/;

	@Override
	public TutorConfig getTutorConfig() {
		return null; // TODO implement me
	}

}
