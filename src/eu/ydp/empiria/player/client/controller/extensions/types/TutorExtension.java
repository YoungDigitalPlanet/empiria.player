package eu.ydp.empiria.player.client.controller.extensions.types;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorService;

public interface TutorExtension {

	String getTutorId();
	
	TutorConfig getTutorConfig();

	void initJsApi(JavaScriptObject playerJsObject, TutorService tutorService);
}
