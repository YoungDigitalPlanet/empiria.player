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
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.video.view.VideoPlayer;
import eu.ydp.empiria.player.client.module.video.view.VideoView;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.internal.EventType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class VideoPlayerReattacherTest {

    @InjectMocks
    private VideoPlayerReattacher testObj;
    @Mock
    private EventsBus eventsBus;
    @Mock
    private VideoPlayerBuilder videoPlayerBuilder;
    @Mock
    private PageScopeFactory pageScopeProvider;
    @Mock
    private VideoView view;

    private PlayerEventHandler playerEventHandler;
    private CurrentPageScope currentPageScope;

    @Before
    public void setUp() {
        preparePageScope();
        prepareHandler();
    }

    @Test
    public void shouldAtachNewVideoPlayer() {
        // given
        testObj.registerReattachHandlerToView(view);
        final VideoPlayer mockPlayer = mock(VideoPlayer.class);
        when(videoPlayerBuilder.build()).thenReturn(mockPlayer);

        // when
        playerEventHandler.onPlayerEvent(null);

        // then
        verify(view).attachVideoPlayer(mockPlayer);
    }

    private void preparePageScope() {
        currentPageScope = mock(CurrentPageScope.class);
        when(pageScopeProvider.getCurrentPageScope()).thenReturn(currentPageScope);
    }

    private void prepareHandler() {
        EventType<PlayerEventHandler, PlayerEventTypes> eventType = PlayerEvent.getType(PlayerEventTypes.TEST_PAGE_LOADED);

        Answer<Void> answer = new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                playerEventHandler = (PlayerEventHandler) invocation.getArguments()[1];
                return null;
            }

        };
        doAnswer(answer).when(eventsBus)
                .addHandler(eq(eventType), any(PlayerEventHandler.class), eq(currentPageScope));
    }
}
