package eu.ydp.empiria.player.client.module.tutor;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.google.common.base.Optional;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.PersonaService;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorPersonaProperties;
import eu.ydp.empiria.player.client.module.EndHandler;
import eu.ydp.empiria.player.client.module.tutor.actions.OutcomeDrivenActionTypeGenerator;
import eu.ydp.gwtutil.client.util.geom.Size;

@RunWith(MockitoJUnitRunner.class)
public class ActionEventGeneratorTest {

	@InjectMocks
	private ActionEventGenerator actionEventGenerator;

	@Mock
	private ActionExecutorService executorService;
	@Mock
	private OutcomeDrivenActionTypeGenerator actionTypeGenerator;
	@Mock
	private PersonaService personaService;
	@Mock
	private TutorEndHandler tutorEndHandler;
	@Mock
	private EndHandler moduleEndHandler;

	@Test
	public void start() {
		// when
		actionEventGenerator.start();

		// then
		verify(executorService).execute(eq(ActionType.DEFAULT), any(TutorEndHandler.class));
	}

	@Test
	public void stop() {
		// when
		actionEventGenerator.stop();

		// then
		verify(executorService).execute(eq(ActionType.DEFAULT), any(TutorEndHandler.class));
	}

	@Test
	public void testTutorChanged() {
		// given
		int personaIndex = 7;

		// when
		actionEventGenerator.tutorChanged(personaIndex);

		// then
		verify(personaService).setCurrentPersonaIndex(personaIndex);
	}

	@Test
	public void stateChanged_interactivePersona() {
		// given
		boolean interactive = true;
		TutorPersonaProperties personaProperties = createPersonaProperties(interactive);

		when(personaService.getPersonaProperties()).thenReturn(personaProperties);

		when(actionTypeGenerator.findActionType()).thenReturn(Optional.of(ActionType.ON_PAGE_ALL_OK));

		// when
		actionEventGenerator.stateChanged(moduleEndHandler);

		// then
		verify(executorService).execute(eq(ActionType.ON_PAGE_ALL_OK), any(TutorEndHandler.class));
	}

	@Test
	public void stateChanged_nonInteractivePersona() {
		// given
		boolean interactive = false;
		TutorPersonaProperties personaProperties = createPersonaProperties(interactive);

		when(actionTypeGenerator.findActionType()).thenReturn(Optional.of(ActionType.ON_PAGE_ALL_OK));
		when(personaService.getPersonaProperties()).thenReturn(personaProperties);

		// when
		actionEventGenerator.stateChanged(moduleEndHandler);

		// then
		verify(executorService, never()).execute(any(ActionType.class), any(TutorEndHandler.class));
	}

	@Test
	public void stateChanged_noActionType() {
		// given
		boolean interactive = true;
		TutorPersonaProperties personaProperties = createPersonaProperties(interactive);

		when(personaService.getPersonaProperties()).thenReturn(personaProperties);

		when(actionTypeGenerator.findActionType()).thenReturn(Optional.<ActionType> absent());

		// when
		actionEventGenerator.stateChanged(moduleEndHandler);

		// then
		verify(executorService, never()).execute(any(ActionType.class), any(TutorEndHandler.class));
	}

	private TutorPersonaProperties createPersonaProperties(boolean isInteractive) {
		if (isInteractive) {
			doAnswer(new Answer<Void>() {

				@Override
				public Void answer(InvocationOnMock invocation) throws Throwable {
					TutorEndHandler handler = (TutorEndHandler) invocation.getArguments()[1];
					handler.onEnd();
					return null;
				}
			}).when(executorService).execute(any(ActionType.class), any(TutorEndHandler.class));
		}
		return new TutorPersonaProperties(7, new Size(), 60, "name", isInteractive, "avatarFilename");
	}
}
