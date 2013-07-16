package eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js;

import com.google.gwt.core.client.JsArray;

public abstract class TutorConfigJs {
	public abstract JsArray<TutorActionJs> getActions();
	public abstract JsArray<TutorJs> getTutors();
}