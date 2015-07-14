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
