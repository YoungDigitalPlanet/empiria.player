package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;

/**
 * Overlay type dla konfiguracji w formacie JSON
 *
 */
public class JsMediaAvaliableOptions extends JavaScriptObject implements MediaAvailableOptions {
	protected JsMediaAvaliableOptions() {

	}

	@Override
	public native boolean isPlaySupported() /*-{
		return this.playSupported ? true : false;
	}-*/;

	@Override
	public native boolean isPauseSupported() /*-{
		return this.pauseSupported ? true : false;
	}-*/;

	@Override
	public native boolean isMuteSupported() /*-{
		return this.muteSupported ? true : false;
	}-*/;

	@Override
	public native boolean isVolumeChangeSupported() /*-{
		return this.volumeChangeSupported ? true : false;
	}-*/;

	@Override
	public native boolean isStopSupported() /*-{
		return this.stopSupported ? true : false;
	}-*/;

	@Override
	public native boolean isSeekSupported() /*-{
		return this.seekSupported ? true : false;
	}-*/;

	@Override
	public native boolean isFullScreenSupported() /*-{
		return this.fullScreenSupported ? true : false;
	}-*/;

	@Override
	public native boolean isMediaMetaAvailable() /*-{
		return this.mediaMetaAvailable ? true : false;
	}-*/;

	@Override
	public native boolean isTemplateSupported() /*-{
		return this.templateSupported ? true : false;
	}-*/;
}
