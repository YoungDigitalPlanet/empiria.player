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

import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.empiria.player.client.module.tutor.TutorEndHandler;
import eu.ydp.empiria.player.client.module.tutor.view.TutorView;
import eu.ydp.gwtutil.client.util.geom.Size;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ShowImageCommandTest {

    ShowImageCommand command;
    @Mock
    TutorView view;
    @Mock
    TutorEndHandler endHandler;

    final static String ASSET_PATH = "ALEX_JUMP";
    private final Size size = new Size(43, 15);

    @Before
    public void setUp() {
        ShowImageDTO showImageDTO = new ShowImageDTO(ASSET_PATH, size);
        command = new ShowImageCommand(view, showImageDTO, endHandler);
    }

    @Test
    public void shouldExecuteCommand() {
        // when
        command.execute();

        // then
        verify(view).setBackgroundImage(ASSET_PATH, size);
        assertThat(command.isFinished(), is(true));
        verify(endHandler).onEnd();
    }

    @Test
    public void shouldTerminateCommand() {
        // when
        command.terminate();

        // then
        assertThat(command.isFinished(), is(true));
        verify(endHandler, never()).onEnd();
        verify(endHandler, never()).onEndWithDefaultAction();
    }
}
