package eu.ydp.empiria.player.client.module.tutor;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Optional;

import eu.ydp.empiria.player.client.module.tutor.actions.OutcomeDrivenActionTypeGenerator;

@RunWith(MockitoJUnitRunner.class)
public class ActionEventGeneratorTest {

	@InjectMocks
	private ActionEventGenerator actionEventGenerator;
	
	@Mock
	private ActionExecutorService executorService;
	@Mock
	private OutcomeDrivenActionTypeGenerator actionTypeGenerator;
	
	@Test
	public void start(){
		// when
		actionEventGenerator.start();
		
		// then
		verify(executorService).execute(eq(ActionType.DEFAULT), any(EndHandler.class));
	}
	
	@Test
	public void stop(){
		// when
		actionEventGenerator.stop();
		
		// then
		verify(executorService).execute(eq(ActionType.DEFAULT), any(EndHandler.class));
	}
	
	@Test
	public void stateChanged(){
		// given
		when(actionTypeGenerator.findActionType()).thenReturn(Optional.of(ActionType.ON_PAGE_ALL_OK));
		
		// when
		actionEventGenerator.stateChanged();
		
		// then
		verify(executorService).execute(eq(ActionType.ON_PAGE_ALL_OK), any(EndHandler.class));
	}
	
	@Test
	public void stateChanged_noActionType(){
		// given
		when(actionTypeGenerator.findActionType()).thenReturn(Optional.<ActionType>absent());
		
		// when
		actionEventGenerator.stateChanged();
		
		// then
		verify(executorService, never()).execute(any(ActionType.class), any(EndHandler.class));
	}
	
	@Test
	public void actionEndHandling(){
		// given
		when(actionTypeGenerator.findActionType()).thenReturn(Optional.<ActionType>absent());
		
		// when
		actionEventGenerator.stateChanged();
		
		// then
		verify(executorService, never()).execute(any(ActionType.class), any(EndHandler.class));
	}
	
	
}
