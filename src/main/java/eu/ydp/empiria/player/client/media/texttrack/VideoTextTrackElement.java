/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.media.texttrack;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.media.MediaStyleNameConstants;
import eu.ydp.empiria.player.client.module.media.button.AbstractMediaController;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

public class VideoTextTrackElement extends AbstractMediaController implements MediaEventHandler {
    private final EventsBus eventsBus;
    private final MediaStyleNameConstants styleNames;
    protected final VideoTextTrackElementPresenter presenter;

    private final TextTrackKind kind;
    private TextTrackCue textTrackCue;
    private final PageScopeFactory pageScopeFactory;

    @Inject
    public VideoTextTrackElement(EventsBus eventsBus, MediaStyleNameConstants styleNames, VideoTextTrackElementPresenter presenter,
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
