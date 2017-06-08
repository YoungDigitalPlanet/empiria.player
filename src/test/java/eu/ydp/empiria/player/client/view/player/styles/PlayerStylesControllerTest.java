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

package eu.ydp.empiria.player.client.view.player.styles;

import com.google.common.base.Optional;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.view.player.PlayerContentView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class PlayerStylesControllerTest {

    @InjectMocks
    private PlayerStylesController testObj;
    @Mock
    private PlayerContentView playerView;
    @Mock
    private CurrentItemStyleProvider styleProvider;
    @Mock
    private EventsBus eventsBus;
    @Mock
    private PlayerEvent playerEvent;

    @Test
    public void shouldAddTemplateStyle_onPageLoaded_whenIsPresent() {
        // given
        String style = "style";
        Optional<String> optionalStyle = Optional.of(style);
        when(styleProvider.getCurrentItemStyle()).thenReturn(optionalStyle);
        when(playerEvent.getType()).thenReturn(PlayerEventTypes.PAGE_LOADED);

        // when
        testObj.onPlayerEvent(playerEvent);

        // then
        verify(playerView).addStyleName(style);
    }

    @Test
    public void shouldRemoveTemplateStyle_beforeFlow_whenIsPresent() {
        // given
        String style = "style";
        Optional<String> optionalStyle = Optional.of(style);
        when(styleProvider.getCurrentItemStyle()).thenReturn(optionalStyle);
        when(playerEvent.getType()).thenReturn(PlayerEventTypes.BEFORE_FLOW);

        // when
        testObj.onPlayerEvent(playerEvent);

        // then
        verify(playerView).removeStyleName(style);
    }

    @Test
    public void shouldNotAddTemplateStyle_onPageLoaded_whenIsAbsent() {
        // given
        Optional<String> optionalStyle = Optional.absent();
        when(styleProvider.getCurrentItemStyle()).thenReturn(optionalStyle);
        when(playerEvent.getType()).thenReturn(PlayerEventTypes.PAGE_LOADED);

        // when
        testObj.onPlayerEvent(playerEvent);

        // then
        verify(playerView, never()).addStyleName(anyString());
    }

    @Test
    public void shouldNotRemoveTemplateStyle_beforeFlow_whenIsAbsent() {
        // given
        Optional<String> optionalStyle = Optional.absent();
        when(styleProvider.getCurrentItemStyle()).thenReturn(optionalStyle);
        when(playerEvent.getType()).thenReturn(PlayerEventTypes.BEFORE_FLOW);

        // when
        testObj.onPlayerEvent(playerEvent);

        // then
        verify(playerView, never()).removeStyleName(anyString());
    }
}