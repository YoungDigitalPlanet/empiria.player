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

package eu.ydp.empiria.player.client.module.media.fullscreen;

import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Timer;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

public class VideoControlHideTimer implements TouchEndHandler, ClickHandler, MouseMoveHandler {
    private final Timer controlsHideTimer;
    private static final int CONTROLS_SHOW_TIME = 3000;
    private final VideoFullScreenView view;

    private final EventsBus eventsBus;

    @Inject
    public VideoControlHideTimer(@Assisted VideoFullScreenView view, EventsBus eventsBus) {
        this.view = view;
        this.eventsBus = eventsBus;
        view.getContainer().addDomHandler(this, TouchEndEvent.getType());
        view.getContainer().addDomHandler(this, ClickEvent.getType());
        view.getContainer().addDomHandler(this, MouseMoveEvent.getType());
        controlsHideTimer = new Timer() {

            @Override
            public void run() {
                hideCotrols();
            }
        };
        controlsHideTimer.schedule(CONTROLS_SHOW_TIME);
    }

    private void hideCotrols() {
        view.getControls().setVisible(false);
    }

    private void showControls() {
        if (!view.getControls().isVisible()) {
            view.getControls().setVisible(true);
            eventsBus.fireEvent(new MediaEvent(MediaEventTypes.ON_FULL_SCREEN_SHOW_CONTROLS));
        }
        controlsHideTimer.cancel();
        controlsHideTimer.schedule(CONTROLS_SHOW_TIME);
    }

    @Override
    public void onMouseMove(MouseMoveEvent event) {
        showControls();
    }

    @Override
    public void onClick(ClickEvent event) {
        showControls();
    }

    @Override
    public void onTouchEnd(TouchEndEvent event) {
        showControls();
    }
}
