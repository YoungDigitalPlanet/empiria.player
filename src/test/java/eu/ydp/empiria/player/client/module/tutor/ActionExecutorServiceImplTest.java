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

package eu.ydp.empiria.player.client.module.tutor;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ActionExecutorServiceImplTest {
    ActionExecutorServiceImpl executor;
    CommandFactory commandFactory;
    TutorEndHandler handler;

    @Before
    public void setUp() {
        commandFactory = mock(CommandFactory.class);
        handler = mock(TutorEndHandler.class);

        executor = new ActionExecutorServiceImpl(commandFactory);
    }

    @Test
    public void shouldExecuteCommand() {
        // given
        TutorCommand command = mock(TutorCommand.class);
        when(command.isFinished()).thenReturn(false);

        when(commandFactory.createCommand(ActionType.DEFAULT, handler)).thenReturn(command);

        // when
        executor.execute(ActionType.DEFAULT, handler);

        // then
        verify(command).execute();
    }

    @Test
    public void shouldNotTerminatePreviousCommandIfFinished() {
        // given
        TutorCommand command1 = mock(TutorCommand.class);
        when(command1.isFinished()).thenReturn(true);
        TutorCommand command2 = mock(TutorCommand.class);

        when(commandFactory.createCommand(ActionType.DEFAULT, handler)).thenReturn(command1);
        when(commandFactory.createCommand(ActionType.ON_PAGE_ALL_OK, handler)).thenReturn(command2);

        // when
        executor.execute(ActionType.DEFAULT, handler);
        executor.execute(ActionType.ON_PAGE_ALL_OK, handler);

        // then
        verify(command1, never()).terminate();
        verify(command2).execute();
    }

    @Test
    public void shouldTerminatePreviousCommandIfNotFinished() {
        // given
        TutorCommand command1 = mock(TutorCommand.class);
        when(command1.isFinished()).thenReturn(false);
        TutorCommand command2 = mock(TutorCommand.class);

        when(commandFactory.createCommand(ActionType.DEFAULT, handler)).thenReturn(command1);
        when(commandFactory.createCommand(ActionType.ON_PAGE_ALL_OK, handler)).thenReturn(command2);

        // when
        executor.execute(ActionType.DEFAULT, handler);
        executor.execute(ActionType.ON_PAGE_ALL_OK, handler);

        // then
        verify(command1).terminate();
        verify(command2).execute();
    }
}
