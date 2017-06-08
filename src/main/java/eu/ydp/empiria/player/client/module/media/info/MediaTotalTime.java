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
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

/**
 * Widget prezentujacy dlugosc pliku audio lub video
 */
public class MediaTotalTime extends AbstractMediaTime {

    private final PageScopeFactory pageScopeFactory;

    @Inject
    public MediaTotalTime(MediaStyleNameConstants styleNames, PageScopeFactory pageScopeFactory) {
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
