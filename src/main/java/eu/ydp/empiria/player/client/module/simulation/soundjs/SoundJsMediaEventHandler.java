package eu.ydp.empiria.player.client.module.simulation.soundjs;

import com.google.gwt.media.client.MediaBase;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.gwtutil.client.debug.log.Logger;

public class SoundJsMediaEventHandler implements MediaEventHandler {

    private SoundJsNative soundJsNative;

    @Inject
    private Logger logger;

    public void setSoundJsNative(SoundJsNative soundJsNative) {
        this.soundJsNative = soundJsNative;
    }

    @Override
    public void onMediaEvent(MediaEvent event) {
        Object mediaObject = event.getMediaWrapper().getMediaObject();

        if (!(mediaObject instanceof MediaBase)) {
            logger.error("MediaObject does not extend a MediaBase");
            return;
        }

        MediaBase mediaBase = (MediaBase) mediaObject;
        String src = mediaBase.getCurrentSrc();

        switch (event.getType()) {
            case ON_END:
                soundJsNative.onComplete(src);
                break;
            default:
                break;
        }
    }

}
