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

import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.gwtmockito.GwtMockito;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.media.Video;
import eu.ydp.empiria.player.client.media.texttrack.TextTrack;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackCue;
import eu.ydp.empiria.player.client.media.texttrack.TextTrackKind;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.UniqueIdGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;

import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class HTML5VideoMediaExecutorJUnitTest extends AbstractHTML5MediaExecutorJUnitBase {

    private static final String NARRATION_TEXT = "text";
    private static final String POSTER_URL = "poster";
    private static final int WIDTH = 30;
    private static final int HEIGHT = 20;

    @Mock
    private TextTrack track;

    @Mock
    private UniqueIdGenerator uniqueIdGenerator;

    @Before
    public void setUp() {
        GwtMockito.initMocks(this);

        instance = (AbstractHTML5MediaExecutor) new HTML5VideoMediaExecutor(mediaEventMapper, html5MediaNativeListeners, uniqueIdGenerator);
        Video video = mock(Video.class);
        mediaBase = video;

        Map<String, String> sources = Maps.newHashMap();
        sources.put("http://dummy", "video/mp4");

        mediaConfiguration = mock(BaseMediaConfiguration.class);
        when(mediaConfiguration.getSources()).thenReturn(sources);
        when(mediaConfiguration.getMediaType()).thenReturn(MediaType.VIDEO);
        when(mediaConfiguration.getPoster()).thenReturn(POSTER_URL);
        when(mediaConfiguration.getHeight()).thenReturn(HEIGHT);
        when(mediaConfiguration.getWidth()).thenReturn(WIDTH);
        when(mediaConfiguration.getNarrationText()).thenReturn(NARRATION_TEXT);
        when(mediaConfiguration.isTemplate()).thenReturn(true);
        doReturn(track).when(video).addTrack(Matchers.any(TextTrackKind.class));
    }

    @Test
    public void testInitExecutor() {
        // given
        instance.setBaseMediaConfiguration(mediaConfiguration);
        MediaWrapper<Video> mediaWrapper = mock(MediaWrapper.class);
        when(mediaWrapper.getMediaObject()).thenReturn(mock(Video.class));
        instance.setMedia(mediaBase);

        // when
        instance.initExecutor();

        // then
        verify(mediaBase).setHeight(Matchers.eq(HEIGHT + "px"));
        verify(mediaBase).setWidth(Matchers.eq(WIDTH + "px"));
        verify((Video) mediaBase).setPoster(Matchers.eq(POSTER_URL));
        verify((Video) mediaBase).addTrack(Matchers.eq(TextTrackKind.SUBTITLES));
        verify(track).addCue(Matchers.any(TextTrackCue.class));
    }
}
