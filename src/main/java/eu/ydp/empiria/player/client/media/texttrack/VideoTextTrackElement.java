package eu.ydp.empiria.player.client.media.texttrack;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.media.button.AbstractMediaController;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

public class VideoTextTrackElement extends AbstractMediaController<VideoTextTrackElement> implements MediaEventHandler {
    private final EventsBus eventsBus;
    private final StyleNameConstants styleNames;
    protected final VideoTextTrackElementPresenter presenter;

    private final TextTrackKind kind;
    private TextTrackCue textTrackCue;
    private final PageScopeFactory pageScopeFactory;

    @Inject
    public VideoTextTrackElement(EventsBus eventsBus, StyleNameConstants styleNames, VideoTextTrackElementPresenter presenter,
                                 PageScopeFactory pageScopeFactory, @Assisted TextTrackKind kind) {
        this.kind = kind;
        this.eventsBus = eventsBus;
        this.styleNames = styleNames;
        this.presenter = presenter;
        this.pageScopeFactory = pageScopeFactory;
        setStyleNames();
    }

    @Override
    public final void setStyleNames() {
        String toAdd = getSuffixToAdd();
        presenter.setStyleName(styleNames.QP_MEDIA_TEXT_TRACK() + toAdd);
        presenter.addStyleName(styleNames.QP_MEDIA_TEXT_TRACK() + toAdd + "-" + kind.name().toLowerCase());
    }

    @Override
    public VideoTextTrackElement getNewInstance() {
        return null;
    }

    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public void init() {
        eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.TEXT_TRACK_UPDATE), getMediaWrapper(), this,
                pageScopeFactory.getCurrentPageScope());
        eventsBus.addAsyncHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), getMediaWrapper(), this, pageScopeFactory.getCurrentPageScope());
    }

    protected void showHideText(TextTrackCue textTrackCue) {
        if (textTrackCue.getEndTime() < getMediaWrapper().getCurrentTime()) {
            presenter.setInnerText("");
        } else if (textTrackCue.getStartTime() < getMediaWrapper().getCurrentTime()) {
            presenter.setInnerText(textTrackCue.getText());
        }
    }

    @Override
    public void onMediaEvent(MediaEvent event) {
        if (event.getType() == MediaEventTypes.TEXT_TRACK_UPDATE && event.getTextTrackCue() != null) {
            TextTrackCue trackCue = event.getTextTrackCue();
            if (trackCue.getTextTrack().getKind() == kind) {
                textTrackCue = trackCue;
                showHideText(trackCue);
            }
        } else if (event.getType() == MediaEventTypes.ON_TIME_UPDATE && textTrackCue != null) {
            showHideText(textTrackCue);
        }
    }

    @Override
    public Widget asWidget() {
        return (Widget) presenter;
    }
}
