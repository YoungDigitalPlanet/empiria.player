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

package eu.ydp.empiria.player.client.module.media.button;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.media.MediaStyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

public class MuteMediaButton extends AbstractMediaButton {

    private final EventsBus eventsBus;
    private final PageScopeFactory pageScopeFactory;

    @Inject
    public MuteMediaButton(MediaStyleNameConstants styleNames, EventsBus eventsBus, PageScopeFactory pageScopeFactory) {
        super(styleNames.QP_MEDIA_MUTE());
        this.eventsBus = eventsBus;
        this.pageScopeFactory = pageScopeFactory;
    }

    @Override
    protected void onClick() {
        eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.MUTE, getMediaWrapper()), getMediaWrapper());
    }

    @Override
    public void init() {
        super.init();
        MediaEventHandler eventHandler = new MediaEventHandler() {
            @Override
            public void onMediaEvent(MediaEvent event) {
                if (event.getMediaWrapper().isMuted()) {
                    setActive(true);
                } else {
                    setActive(false);
                }
                changeStyleForClick();
            }
        };
        eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_VOLUME_CHANGE), getMediaWrapper(), eventHandler, pageScopeFactory.getCurrentPageScope());
    }

    @Override
    public boolean isSupported() {
        return getMediaAvailableOptions().isMuteSupported();
    }
}
