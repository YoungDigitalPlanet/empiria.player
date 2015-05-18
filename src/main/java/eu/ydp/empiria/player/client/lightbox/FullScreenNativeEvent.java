package eu.ydp.empiria.player.client.lightbox;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.util.events.fullscreen.FullScreenEvent;

public final class FullScreenNativeEvent extends JavaScriptObject implements FullScreenEvent {

	protected FullScreenNativeEvent() {
		//
	}

	@Override
	public native boolean isInFullScreen()/*-{
											return this.isInFullScreen;
											}-*/;
}
