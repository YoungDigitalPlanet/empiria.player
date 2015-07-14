package eu.ydp.empiria.player.client.controller.extensions.internal.media.external;

public class ExternalFullscreenVideoAvailability {

    public boolean isAvailable() {
        return isAvailableJs();
    }

    private native boolean isAvailableJs()/*-{
        return typeof $wnd.empiriaMediaFullscreenVideoSupported == 'function' && $wnd.empiriaMediaFullscreenVideoSupported();
    }-*/;
}
