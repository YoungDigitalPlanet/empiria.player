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
import com.google.gwt.dom.client.SourceElement;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.video.structure.SourceBean;
import eu.ydp.empiria.player.client.module.video.wrappers.SourceElementWrapper;
import eu.ydp.empiria.player.client.module.video.wrappers.VideoElementWrapper;
import eu.ydp.empiria.player.client.module.video.wrappers.poster.DefaultPosterUriProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VideoElementWrapperBuilderTest {

    @InjectMocks
    private VideoElementWrapperBuilder builder;
    @Mock
    private Provider<SourceElementWrapper> sourceElementWrapperProvider;
    @Mock
    private Provider<VideoElementWrapper> videoElementProvider;
    @Mock
    private DefaultPosterUriProvider defaultPosterUriProvider;

    private VideoElementWrapper videoElement;

    @Before
    public void setup() {
        videoElement = mock(VideoElementWrapper.class);
        when(videoElementProvider.get()).thenReturn(videoElement);

        when(defaultPosterUriProvider.getDefaultPosterUri()).thenReturn("defaultPosterUri");
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowExceptionOnEmptySources() {
        // when
        builder.build();
    }

    @Test
    public void shouldBuildVideoElement_withGivenValues() {
        // given
        final boolean controls = false;
        final int height = 42;
        final int width = 69;
        final String poster = "POSTER";
        final String preload = "AUTO";
        final String skinName = "SKIN_NAME";
        final List<SourceBean> sources = Lists.newArrayList(mock(SourceBean.class), mock(SourceBean.class), mock(SourceBean.class));
        when(sourceElementWrapperProvider.get()).thenReturn(mock(SourceElementWrapper.class));

        // when
        builder.withControls(controls);
        builder.withHeight(height);
        builder.withWidth(width);
        builder.withPoster(poster);
        builder.withPreload(preload);
        builder.withSkinName(skinName);
        builder.withSources(sources);
        builder.build();

        verify(videoElement).setControls(controls);
        verify(videoElement).setHeight(height);
        verify(videoElement).setWidth(width);
        verify(videoElement).setPoster(poster);
        verify(videoElement).setPreload(preload);
        verify(videoElement).addClassName(skinName);
        verify(videoElement, times(3)).appendChild(any(SourceElement.class));
    }

    @Test
    public void shouldBuildVideoElement_withDefaultValues() {
        // given
        final boolean controls = true;
        final int height = 0;
        final int width = 0;
        final String preload = "none";
        final String skinName = "vjs-default-skin";
        SourceBean source = mock(SourceBean.class);
        when(sourceElementWrapperProvider.get()).thenReturn(mock(SourceElementWrapper.class));

        // when
        builder.withSource(source);
        builder.build();

        verify(videoElement).setControls(controls);
        verify(videoElement).setHeight(height);
        verify(videoElement).setWidth(width);
        verify(videoElement).setPoster("defaultPosterUri");
        verify(videoElement).setPreload(preload);
        verify(videoElement).addClassName(skinName);
        verify(videoElement).appendChild(any(SourceElement.class));
    }
}
