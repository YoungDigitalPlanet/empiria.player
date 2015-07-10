package eu.ydp.empiria.player.client.module.media.info;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.AbstractMediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;

/**
 * Widget prezentujacy dlugosc pliku audio lub video
 */
public class MediaTotalTime extends AbstractMediaTime {

    @Inject
    public MediaTotalTime(StyleNameConstants styleNames) {
        super(styleNames.QP_MEDIA_TOTALTIME());
    }

    @Override
    public void init() {
        AbstractMediaEventHandler handler = new AbstractMediaEventHandler() {
            @Override
            public void onMediaEvent(MediaEvent event) {
                double duration = getMediaWrapper().getDuration();
                double timeModulo = duration % 60;
                getElement().setInnerText(getInnerText((duration - timeModulo) / 60f, timeModulo));
            }
        };
        if (isSupported()) {
            eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE), getMediaWrapper(), handler, new CurrentPageScope());
        }
    }
}
