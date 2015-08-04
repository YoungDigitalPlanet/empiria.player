package eu.ydp.empiria.player.client.module.media.info;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

/**
 * Widget prezentujacy dlugosc pliku audio lub video
 */
public class MediaTotalTime extends AbstractMediaTime {

    private final PageScopeFactory pageScopeFactory;

    @Inject
    public MediaTotalTime(StyleNameConstants styleNames, PageScopeFactory pageScopeFactory) {
        super(styleNames.QP_MEDIA_TOTALTIME());
        this.pageScopeFactory = pageScopeFactory;
    }

    @Override
    public void init() {
        MediaEventHandler handler = new MediaEventHandler() {
            @Override
            public void onMediaEvent(MediaEvent event) {
                double duration = getMediaWrapper().getDuration();
                double timeModulo = duration % 60;
                getElement().setInnerText(getInnerText((duration - timeModulo) / 60f, timeModulo));
            }
        };
        if (isSupported()) {
            eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_DURATION_CHANGE), getMediaWrapper(), handler, pageScopeFactory.getCurrentPageScope());
        }
    }
}
