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

package eu.ydp.empiria.player.client.module.video;

import com.google.gwt.dom.client.NativeEvent;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.FullscreenVideoConnector;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.client.event.factory.Command;

import java.util.List;

public class VideoPlayerForBookshelfOnAndroid {

    private final FullscreenVideoConnector fullscreenVideoConnector;
    private final String playerId;
    private final List<String> sources;

    @Inject
    public VideoPlayerForBookshelfOnAndroid(@Assisted VideoPlayer videoPlayer, FullscreenVideoConnector fullscreenVideoConnector,
                                            SourceForBookshelfFilter sourceForBookshelfFilter) {
        this.sources = sourceForBookshelfFilter.getFilteredSources(videoPlayer.getSources());
        this.playerId = videoPlayer.getId();
        this.fullscreenVideoConnector = fullscreenVideoConnector;
    }

    public void init(VideoView view) {
        view.preparePlayForBookshelf(new Command() {
            @Override
            public void execute(NativeEvent nativeEvent) {
                if (!sources.isEmpty()) {
                    fullscreenVideoConnector.openFullscreen(playerId, sources, 0);
                }
            }
        });
    }
}
