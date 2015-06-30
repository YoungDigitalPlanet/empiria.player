package eu.ydp.empiria.player.client.util;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.media.client.Video;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.fullscreen.VideoFullScreenEvent;
import eu.ydp.empiria.player.client.util.events.internal.fullscreen.VideoFullScreenEventHandler;

import java.util.HashSet;
import java.util.Set;

/**
 * Klasa pomocnicza do obslugi trybu pelnoekranowego w przegladarkach
 */
public class NativeHTML5FullScreenHelper {

    private final Set<VideoFullScreenEventHandler> eventHandlers = new HashSet<VideoFullScreenEventHandler>();

    /**
     * Dodaje handlera nasluchujacego na zmiany trybu pelnoekranowego
     *
     * @param handler
     */
    public void addFullScreenEventHandler(VideoFullScreenEventHandler handler) {
        if (handler == null) {
            return;
        }
        if (eventHandlers.isEmpty()) {
            addFullScreenChangeHandler();
        }
        eventHandlers.add(handler);
    }

    /**
     * probuje wyswietlic podany element w trybie fullScreen
     *
     * @param element
     * @return true jezeli sie uda false w przeciwnym razie
     */
    public boolean requestFullScreen(Element element) {
        nativeExitFullScreen();
        return nativeRequestFullScrean(element);
    }

    private native void addFullScreenHandlerForWebkit(JavaScriptObject element)/*-{
        var thiss = this;
        if (this.beginFullscreenHandler) {
            element.removeEventListener("webkitbeginfullscreen", this.beginFullscreenHandler);
        } else {
            this.beginFullscreenHandler = function () {
                thiss.@eu.ydp.empiria.player.client.util.NativeHTML5FullScreenHelper::handleEvent(Z)(true);
            }
        }
        if (this.endFullscreenHandler) {
            element.removeEventListener("webkitendfullscreen", this.endFullscreenHandler);
        } else {
            this.endFullscreenHandler = function () {
                thiss.@eu.ydp.empiria.player.client.util.NativeHTML5FullScreenHelper::handleEvent(Z)(false);
            }
        }

        element.addEventListener("webkitbeginfullscreen", this.beginFullscreenHandler, false);
        element.addEventListener("webkitendfullscreen", this.endFullscreenHandler, false);

    }-*/;

    private native boolean nativeRequestFullScrean(Element element)/*-{
        try {
            if (element.webkitExitFullscreen) {
                element.webkitExitFullscreen();
            } else if (element.mozCancelFullScreen) {
                element.mozCancelFullScreen();
            }
            if (element.requestFullscreen) {
                element.requestFullscreen();
                return true;
            } else if (element.mozRequestFullScreen) {
                element.mozRequestFullScreen();
                return true;
            } else if (element.webkitRequestFullscreen) {
                this.@eu.ydp.empiria.player.client.util.NativeHTML5FullScreenHelper::addFullScreenHandlerForWebkit(Lcom/google/gwt/core/client/JavaScriptObject;)(element);
                element.webkitRequestFullscreen();
                return true;
            } else if (element.webkitEnterFullscreen) {
                this.@eu.ydp.empiria.player.client.util.NativeHTML5FullScreenHelper::addFullScreenHandlerForWebkit(Lcom/google/gwt/core/client/JavaScriptObject;)(element);
                element.webkitEnterFullscreen();
                return true;
            }
        } catch (e) {
            //console.log(e);
        }
        return false;
    }-*/;

    /**
     * Probuje opuscic tryb fullScreen
     *
     * @param element
     * @return
     */
    public boolean exitFullScreen() {
        return nativeExitFullScreen();
    }

    private native boolean nativeExitFullScreen()/*-{
        try {
            if ($doc.exitFullscreen) {
                $doc.exitFullscreen();
                return true;
            } else if ($doc.mozCancelFullScreen) {
                $doc.mozCancelFullScreen();
                return true;
            } else if ($doc.webkitCancelFullScreen) {
                $doc.webkitCancelFullScreen();
                return true;
            }

        } catch (e) {
        }
        return false;
    }-*/;

    public native boolean isInFullScrean(Element element)/*-{
        return !!element.webkitDisplayingFullscreen
            || !!element.webkitIsFullScreen;
    }-*/;

    /**
     * metoda wywolywana z poziomu jsni dla obslugi zdarzen fullScreen
     */
    protected void handleEvent() {
        final boolean inFullScreen = isInFullScreen();
        handleEvent(inFullScreen);
    }

    private void handleEvent(final boolean inFullScreen) {
        VideoFullScreenEvent fse = new VideoFullScreenEvent() {

            @Override
            public boolean isInFullScreen() {
                return inFullScreen;
            }

            @Override
            public MediaWrapper<?> getMediaWrapper() {
                return null;
            }
        };
        for (VideoFullScreenEventHandler h : eventHandlers) {
            h.handleEvent(fse);
        }
    }

    /**
     * Czy w danym momencie jest aktywny tryb pelnoekranowy
     *
     * @return
     */
    public native boolean isInFullScreen()/*-{
        try {
            return !!$doc.fullscreen || !!$doc.mozFullScreen
            || !!$doc.webkitIsFullScreen
            || !!$doc.webkitDisplayingFullscreen ? true : false;
        } catch (e) {
        }
        return false;
    }-*/;

    private native void addFullScreenChangeHandler()/*-{
        try {
            var thhis = this;
            $doc.addEventListener(
                "fullscreenchange", function () {
                    thhis.@eu.ydp.empiria.player.client.util.NativeHTML5FullScreenHelper::handleEvent()()
                },
                false);
            $doc.addEventListener(
                "mozfullscreenchange", function () {
                    thhis.@eu.ydp.empiria.player.client.util.NativeHTML5FullScreenHelper::handleEvent()()
                },
                false);
            $doc.addEventListener(
                "webkitfullscreenchange", function () {
                    thhis.@eu.ydp.empiria.player.client.util.NativeHTML5FullScreenHelper::handleEvent()()
                },
                false);
        } catch (e) {
        }
    }-*/;

    public static native boolean isFullScreenSupported()/*-{
        try {
            var browserPrefixes = ["moz", "webkit"];
            if (typeof $doc.cancelFullScreen != 'undefined') {
                return true;
            } else {
                // check for fullscreen support by vendor prefix
                for (var i = 0, il = browserPrefixes.length; i < il; i++) {
                    var prefix = browserPrefixes[i];
                    if (typeof $doc[prefix + 'CancelFullScreen'] != 'undefined') {
                        return true;
                    }
                }
            }
        } catch (e) {
        }
        return false;
    }-*/;

    /**
     * sprawdza czy dany element mozna wyswietlic w trybie pelnoekranowym
     *
     * @param video
     * @return true jezeli mozna false w przeciwnym razie
     */
    public native boolean supportsFullScreen(Video video)/*-{
        try {
            return !!video.getElement().webkitSupportsFullscreen;
        } catch (e) {
        }
    }-*/;

}
