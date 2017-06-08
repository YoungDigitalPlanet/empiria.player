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

import com.google.common.collect.Lists;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.external.FullscreenVideoConnector;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.gwtutil.client.event.factory.Command;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class VideoPlayerForBookshelfOnAndroidTest {

    private VideoPlayerForBookshelfOnAndroid testObj;
    @Mock
    private VideoView view;
    @Mock
    private VideoPlayer videoPlayer;
    @Mock
    private FullscreenVideoConnector fullscreenVideoConnector;
    @Mock
    private SourceForBookshelfFilter sourceForBookshelfFilter;

    @Captor
    private ArgumentCaptor<Command> commandCaptor;
    @Captor
    private ArgumentCaptor<List<String>> listCaptor;

    @Test
    public void shouldOpenFullscreen() {
        // given
        String source = "video_source";
        List<String> sources = Lists.newArrayList(source);
        String playerId = "player_id";

        when(videoPlayer.getSources()).thenReturn(sources);
        when(videoPlayer.getId()).thenReturn(playerId);
        when(sourceForBookshelfFilter.getFilteredSources(sources)).thenReturn(sources);

        testObj = new VideoPlayerForBookshelfOnAndroid(videoPlayer, fullscreenVideoConnector, sourceForBookshelfFilter);
        testObj.init(view);

        verify(view).preparePlayForBookshelf(commandCaptor.capture());

        // when
        commandCaptor.getValue().execute(null);

        // then
        verify(fullscreenVideoConnector).openFullscreen(eq(playerId), listCaptor.capture(), eq(0.0));
        assertThat(listCaptor.getValue().get(0)).isEqualTo(source);
    }

    @Test
    public void shouldNotOpenFullscreen_whenEmptySourceList() {
        // given
        List<String> sources = Lists.newArrayList();
        String playerId = "player_id";

        when(videoPlayer.getSources()).thenReturn(sources);
        when(videoPlayer.getId()).thenReturn(playerId);
        when(sourceForBookshelfFilter.getFilteredSources(sources)).thenReturn(sources);

        testObj = new VideoPlayerForBookshelfOnAndroid(videoPlayer, fullscreenVideoConnector, sourceForBookshelfFilter);
        testObj.init(view);

        verify(view).preparePlayForBookshelf(commandCaptor.capture());

        // when
        commandCaptor.getValue().execute(null);

        // then
        verifyZeroInteractions(fullscreenVideoConnector);
    }
}
