package eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class TutorActionJs extends JavaScriptObject {

	protected TutorActionJs() {
	}

	public final native String getType()/*-{
										return this.type;
										}-*/;

	public final native JsArray<TutorCommandJs> getCommands()/*-{
																return this.commands;
																}-*/;
}
