package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js.TutorConfigJs;
import eu.ydp.empiria.player.client.controller.extensions.types.TutorExtension;

public class JsTutorExtension extends AbstractJsExtension implements TutorExtension {
	
	private JavaScriptObject playerJsObject;

	@Override
	public void init() {	
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
		JavaScriptObject tutorConfigJso = getTutorConfigNative(extensionJsObject);
		TutorConfigJs tutorConfigJs = tutorConfigJso.cast();
		return new TutorConfig(tutorConfigJs);
	}

	private final native JavaScriptObject getTutorConfigNative(JavaScriptObject extensionJsObject)/*-{
		return extensionJsObject.getTutorConfig(); 
	}-*/;

	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_TUTOR;
	}
	
}
