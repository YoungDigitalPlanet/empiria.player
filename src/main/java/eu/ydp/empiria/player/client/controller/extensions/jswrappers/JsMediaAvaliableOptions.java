package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;

/**
 * Overlay type dla konfiguracji w formacie JSON
 */
public class JsMediaAvaliableOptions extends JavaScriptObject implements MediaAvailableOptions {

    protected JsMediaAvaliableOptions() {

    }

    @Override
    public native final boolean isPlaySupported() /*-{
        return this.playSupported == true;
    }-*/;

    @Override
    public native final boolean isPauseSupported() /*-{
        return this.pauseSupported == true;
    }-*/;

    @Override
    public native final boolean isMuteSupported() /*-{
        return this.muteSupported == true;
    }-*/;

    @Override
    public native final boolean isVolumeChangeSupported() /*-{
        return this.volumeChangeSupported == true;
    }-*/;

    @Override
    public native final boolean isStopSupported() /*-{
        return this.stopSupported == true;
    }-*/;

    @Override
    public native final boolean isSeekSupported() /*-{
        return this.seekSupported == true;
    }-*/;

    @Override
    public native final boolean isFullScreenSupported() /*-{
        return this.fullScreenSupported == true;
    }-*/;

    @Override
    public native final boolean isMediaMetaAvailable() /*-{
        return this.mediaMetaAvailable == true;
    }-*/;

    @Override
    public native final boolean isTemplateSupported() /*-{
        return this.templateSupported == true;
    }-*/;
}
