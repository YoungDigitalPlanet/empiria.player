package eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js;

import com.google.gwt.core.client.JavaScriptObject;

public abstract class TutorJs extends JavaScriptObject {

	protected TutorJs() {
	}

	public native String getName()/*-{
		return this.name;
	}-*/;

	public native int getFps()/*-{
		return this.fps;
	}-*/;

	public native int getWidth()/*-{
		return this.width;
	}-*/;

	public native int getHeight()/*-{
		return this.height;
	}-*/;
}
