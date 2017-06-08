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

package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import eu.ydp.empiria.gwtflashmedia.client.FlashVideo;
import eu.ydp.empiria.gwtflashmedia.client.FlashVideoFactory;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.SwfMediaWrapper;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.media.texttrack.TextTrack;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackCue;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackKind;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;

public class VideoExecutorSwf extends ExecutorSwf {

    @Inject
    private PageScopeFactory pageScopeFactory;

    @Override
    public void init() {
        FlowPanel flowPanel = new FlowPanel();
        FlashVideo video = FlashVideoFactory.createVideo(source, flowPanel, baseMediaConfiguration.getWidth(), baseMediaConfiguration.getHeight());
        flashMedia = video;
        if (this.mediaWrapper instanceof SwfMediaWrapper) {
            ((SwfMediaWrapper) this.mediaWrapper).setMediaWidget(flowPanel);
        }
        if (baseMediaConfiguration.getNarrationText().trim().length() > 0) {
            final TextTrack textTrack = textTrackFactory.getTextTrack(TextTrackKind.SUBTITLES, mediaWrapper);
            // FIXME do poprawy gdy narrationScript bedzie zawieral informacje o czasie wyswietlania
            // zamiast Double.MAX_VALUE tu powinna sie znalezc wartosc czasowa okreslajaca
            // kiedy napis znika poniewaz w tej chwili narrationScript nie posiada takiej informacji
            // przypisuje najwieksza dostepna wartosc
            textTrack.addCue(new TextTrackCue(Document.get().createUniqueId(), 0, Double.MAX_VALUE, baseMediaConfiguration.getNarrationText()));
            eventsBus.addHandlerToSource(MediaEvent.getType(MediaEventTypes.ON_TIME_UPDATE), mediaWrapper, new MediaEventHandler() {

                @Override
                public void onMediaEvent(MediaEvent event) {
                    textTrack.setCurrentTime(mediaWrapper.getCurrentTime());
                }
            }, pageScopeFactory.getCurrentPageScope());

        }
        super.init();
    }

    @Override
    public void play(String src) {
        flashMedia.setSrc(src);
        flashMedia.play();

    }

    @Override
    public void stop() {
        flashMedia.stop();
        eventsBus.fireEventFromSource(new MediaEvent(MediaEventTypes.ON_STOP, getMediaWrapper()), getMediaWrapper());
    }

}
