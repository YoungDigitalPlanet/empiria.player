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

package eu.ydp.empiria.player.client.module.tutor.commands;

import eu.ydp.empiria.player.client.module.tutor.TutorEndHandler;
import eu.ydp.gwtutil.client.animation.Animation;
import eu.ydp.gwtutil.client.animation.AnimationEndHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AnimationCommandTest {
    @InjectMocks
    AnimationCommand command;
    @Mock
    private TutorEndHandler handler;
    @Mock
    private Animation animation;

    @Test
    public void shouldStartAnimation() {
        // when
        command.execute();

        // then
        verify(animation).start(any(AnimationEndHandler.class));
        assertThat(command.isFinished(), is(false));
    }

    @Test
    public void shouldtestName() {
        // when
        command.execute();
        command.animationHandler.onEnd();

        // then
        verify(handler).onEndWithDefaultAction();
        assertThat(command.isFinished(), is(true));
    }

    @Test
    public void shouldTerminateMethod() {
        // given

        // when
        command.terminate();

        // then
        verify(handler, never()).onEnd();
        verify(handler, never()).onEndWithDefaultAction();
        assertThat(command.isFinished(), is(true));
    }
}
