package eu.ydp.empiria.player.client.module.media.info;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.media.MediaStyleNameConstants;
import eu.ydp.empiria.player.client.module.media.progress.ProgressUpdateLogic;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;

/**
 * Widget wyswietlajacy pozycje w strumieniu oraz dlugosc calego strumienia
 */
public class PositionInMediaStream extends AbstractMediaTime {

    private ProgressUpdateLogic progressUpdateLogic;
    private final PageScopeFactory pageScopeFactory;

    @Inject
    public PositionInMediaStream(MediaStyleNameConstants styleNames, ProgressUpdateLogic progressUpdateLogic, PageScopeFactory pageScopeFactory) {
        super(styleNames.QP_MEDIA_POSITIONINSTREAM());
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
                    StringBuilder innerText = new StringBuilder(getInnerText((currentTime - timeModulo) / 60f, timeModulo));
                    innerText.append(" / ");
                    double duration = getMediaWrapper().getDuration();
                    timeModulo = duration % 60;
                    innerText.append(getInnerText((duration - timeModulo) / 60f, timeModulo));
                    getElement().setInnerText(innerText.toString());
                }
            }
        };
        if (isSupported()) {
            CurrentPageScope scope = pageScopeFactory.getCurrentPageScope();
            eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), getMediaWrapper(), handler, scope);
            eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE), getMediaWrapper(), handler, scope);
        }
    }
}
