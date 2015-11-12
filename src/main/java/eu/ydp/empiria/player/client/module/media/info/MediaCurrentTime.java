package eu.ydp.empiria.player.client.module.media.info;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.media.MediaStyleNameConstants;
import eu.ydp.empiria.player.client.module.media.progress.ProgressUpdateLogic;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

/**
 * Widget wyswietlajacy pozycje w skaznika w pliku w postaci czasu. Dokladnosc 1
 * sekunda
 */
public class MediaCurrentTime extends AbstractMediaTime {

    private final ProgressUpdateLogic progressUpdateLogic;
    private final PageScopeFactory pageScopeFactory;

    @Inject
    public MediaCurrentTime(MediaStyleNameConstants styleNames, ProgressUpdateLogic progressUpdateLogic, PageScopeFactory pageScopeFactory) {
        super(styleNames.QP_MEDIA_CURRENTTIME());
        this.progressUpdateLogic = progressUpdateLogic;
        this.pageScopeFactory = pageScopeFactory;
    }

    @Override
    public void init() {
        MediaEventHandler handler = new MediaEventHandler() {
            // -1 aby przy pierwszym zdarzeniu pokazal sie timer
            int lastTime = -1;

            @Override
            public void onMediaEvent(MediaEvent event) {
                double currentTime = getMediaWrapper().getCurrentTime();
                if (progressUpdateLogic.isReadyToUpdate(currentTime, lastTime)) {
                    lastTime = (int) currentTime;
                    double timeModulo = currentTime % 60;
                    getElement().setInnerText(getInnerText((currentTime - timeModulo) / 60f, timeModulo));
                }
            }
        };
        if (isSupported()) {
            eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), getMediaWrapper(), handler, pageScopeFactory.getCurrentPageScope());
        }
    }
}
