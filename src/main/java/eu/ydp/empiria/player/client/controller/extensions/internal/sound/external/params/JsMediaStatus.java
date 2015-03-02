package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.params;

import com.google.gwt.core.client.JavaScriptObject;

public final class JsMediaStatus extends JavaScriptObject implements MediaStatus {

	protected JsMediaStatus() {
	}

	@Override
	public native int getCurrentTimeMillis() /*-{
												return this.currentTime;
												}-*/;

}
