package eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js;

import com.google.gwt.core.client.JavaScriptObject;

public class TutorCommandJs extends JavaScriptObject {

	protected TutorCommandJs() {
	}

	public native String getType()/*-{
		return this.type;
	}-*/;

	public native String getAsset()/*-{
		return this.asset;
	}-*/;
}
