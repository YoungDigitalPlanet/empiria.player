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

package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.natives.HTML5MediaNativeListeners;
import eu.ydp.empiria.player.client.media.Video;
import eu.ydp.empiria.player.client.media.texttrack.TextTrack;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackCue;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackKind;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.util.UniqueIdGenerator;

public class HTML5VideoMediaExecutor extends AbstractHTML5MediaExecutor<Video> {

    private final UniqueIdGenerator uniqueIdGenerator;

    @Inject
    public HTML5VideoMediaExecutor(HTML5MediaEventMapper mediaEventMapper, HTML5MediaNativeListeners html5MediaNativeListeners,
                                   UniqueIdGenerator uniqueIdGenerator) {
        super(mediaEventMapper, html5MediaNativeListeners);
        this.uniqueIdGenerator = uniqueIdGenerator;
    }

    @Override
    public void initExecutor() {
        BaseMediaConfiguration baseMediaConfiguration = getBaseMediaConfiguration();
        if (baseMediaConfiguration != null) {
            Video media = getMedia();
            setPosterIfPresent(baseMediaConfiguration, media);
            setWidthIfPresent(baseMediaConfiguration, media);
            setHeightIfPresent(baseMediaConfiguration, media);
            setNarrationTextIfPresent(baseMediaConfiguration, media);
        }

    }

    private void setNarrationTextIfPresent(BaseMediaConfiguration baseMediaConfiguration, Video media) {
        if (baseMediaConfiguration.getNarrationText().trim().length() > 0) {
            TextTrack textTrack = media.addTrack(TextTrackKind.SUBTITLES);
            // FIXME do poprawy gdy narrationScript bedzie zawieral informacje o
            // czasie wyswietlania
            // zamiast Double.MAX_VALUE tu powinna sie znalezc wartosc czasowa
            // okreslajaca
            // kiedy napis znika poniewaz w tej chwili narrationScript nie
            // posiada takiej informacji
            // przypisuje najwieksza dostepna wartosc
            textTrack.addCue(new TextTrackCue(uniqueIdGenerator.createUniqueId(), 0, Double.MAX_VALUE, baseMediaConfiguration.getNarrationText()));
        }
    }

    private void setPosterIfPresent(BaseMediaConfiguration baseMediaConfiguration, Video media) {
        if (baseMediaConfiguration.getPoster() != null && baseMediaConfiguration.getPoster().trim().length() > 0) {
            media.setPoster(baseMediaConfiguration.getPoster());
        }
    }

    private void setHeightIfPresent(BaseMediaConfiguration baseMediaConfiguration, Video media) {
        if (baseMediaConfiguration.getHeight() > 0) {
            media.setHeight(baseMediaConfiguration.getHeight() + "px");
        }
    }

    private void setWidthIfPresent(BaseMediaConfiguration baseMediaConfiguration, Video media) {
        if (baseMediaConfiguration.getWidth() > 0) {
            media.setWidth(baseMediaConfiguration.getWidth() + "px");
        }
    }
}
