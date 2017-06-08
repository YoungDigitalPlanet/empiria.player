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

package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.common.base.Optional;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.inject.assistedinject.Assisted;
import com.google.web.bindery.event.shared.HandlerRegistration;
import eu.ydp.empiria.player.client.module.video.VideoPlayerControl;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.EventType;

import javax.inject.Inject;

public class VideoPlayerAttachHandler implements Handler {
    private static final EventType<PlayerEventHandler, PlayerEventTypes> PAGE_CHANGE_EVENT_TYPE = PlayerEvent.getType(PlayerEventTypes.PAGE_CHANGE);

    private final EventsBus eventsBus;
    private final VideoPlayer videoPlayer;

    private Optional<HandlerRegistration> handlerRegistration = Optional.absent();

    @Inject
    public VideoPlayerAttachHandler(@Assisted VideoPlayer videoPlayer, EventsBus eventsBus) {
        this.eventsBus = eventsBus;
        this.videoPlayer = videoPlayer;
    }

    @Override
    public void onAttachOrDetach(AttachEvent event) {
        if (event.isAttached()) {
            final HandlerRegistration pauseHandlerRegistration = registerPauseHandlerOnPageChange();
            this.handlerRegistration = Optional.of(pauseHandlerRegistration);
        } else {
            clearHandler();
        }
    }

    private void clearHandler() {
        if (handlerRegistration.isPresent()) {
            this.handlerRegistration.get().removeHandler();
            this.handlerRegistration = Optional.absent();
        }
    }

    private HandlerRegistration registerPauseHandlerOnPageChange() {
        final VideoPlayerControl videoPlayerControl = videoPlayer.getControl();
        final AutoPauseOnPageChangeHandler pauseHandler = new AutoPauseOnPageChangeHandler(videoPlayerControl);

        return eventsBus.addHandler(PAGE_CHANGE_EVENT_TYPE, pauseHandler);
    }
}
