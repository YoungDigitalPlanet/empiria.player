package eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public abstract class TutorActionJs extends JavaScriptObject {
	public abstract String getType();
	public abstract JsArray<TutorCommandJs> getCommands();

}