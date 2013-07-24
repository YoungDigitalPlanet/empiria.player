package eu.ydp.empiria.player.client.controller.extensions.internal.tutor.js;

import com.google.gwt.core.client.JavaScriptObject;

public abstract class TutorJs extends JavaScriptObject {

	protected TutorJs() {
	}

	public final native String getName()/*-{
		return this.name;
	}-*/;

	public final native int getFps()/*-{
		return this.fps;
	}-*/;

	public final native int getWidth()/*-{
		return this.width;
	}-*/;

	public final native int getHeight()/*-{
		return this.height;
	}-*/;
}
