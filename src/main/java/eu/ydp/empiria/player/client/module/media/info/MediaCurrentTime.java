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
