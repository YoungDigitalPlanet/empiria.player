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

package eu.ydp.empiria.player.client.module.video.presenter;

import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.gin.factory.VideoModuleFactory;
import eu.ydp.empiria.player.client.module.video.structure.VideoBean;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class VideoPlayerBuilderTest {

    @InjectMocks
    private VideoPlayerBuilder testObj;
    @Mock
    private VideoPlayerFactory playerPactory;
    @Mock
    private VideoModuleFactory moduleFactory;
    @Mock
    private VideoBean videoBean;

    @Test
    public void shouldCreateVideoPlayer() {
        // given
        VideoPlayer videoPlayer = mock(VideoPlayer.class);
        when(playerPactory.create(videoBean)).thenReturn(videoPlayer);

        final VideoPlayerAttachHandler mockHandler = mock(VideoPlayerAttachHandler.class);
        when(moduleFactory.createAttachHandlerForRegisteringPauseEvent(videoPlayer)).thenReturn(mockHandler);

        // when
        testObj.build();

        // then
        verify(moduleFactory).createAttachHandlerForRegisteringPauseEvent(videoPlayer);
        verify(videoPlayer).addAttachHandler(mockHandler);

    }

}
