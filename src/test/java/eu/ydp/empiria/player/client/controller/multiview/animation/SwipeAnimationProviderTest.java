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

package eu.ydp.empiria.player.client.controller.multiview.animation;

import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.multiview.swipe.SwipeType;
import eu.ydp.empiria.player.client.inject.Instance;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SwipeAnimationProviderTest {

    @InjectMocks
    private SwipeAnimationProvider testObj;

    @Mock
    private Provider<SwipeType> swipeTypeProvider;
    @Mock
    private Instance<AnimationBase> animationBase;
    @Mock
    private Instance<NoAnimation> noAnimation;

    @Test
    public void getWhenSwipeIsDisabled() throws Exception {
        when(swipeTypeProvider.get()).thenReturn(SwipeType.DISABLED);
        NoAnimation animation = mock(NoAnimation.class);
        doReturn(animation).when(noAnimation).get();
        assertThat(testObj.get()).isEqualTo(animation);
    }

    @Test
    public void getWhenSwipeIsEnabled() throws Exception {
        when(swipeTypeProvider.get()).thenReturn(SwipeType.DEFAULT);
        AnimationBase animation = mock(AnimationBase.class);
        doReturn(animation).when(animationBase).get();
        assertThat(testObj.get()).isEqualTo(animation);
    }
}
