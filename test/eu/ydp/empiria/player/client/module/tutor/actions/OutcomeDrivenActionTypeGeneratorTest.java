package eu.ydp.empiria.player.client.module.tutor.actions;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Optional;

import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.module.tutor.ActionType;

@RunWith(MockitoJUnitRunner.class)
public class OutcomeDrivenActionTypeGeneratorTest {

	@InjectMocks
	private OutcomeDrivenActionTypeGenerator actionTypeGenerator;
	
	@Mock 
	private OnPageAllOkAction pageAllOk;
	@Mock private TutorConfig tutorConfig;
	
	@Test
	public void findActionType(){
		// given
		when(pageAllOk.actionOccured()).thenReturn(true);
		when(pageAllOk.getActionType()).thenReturn(ActionType.ON_PAGE_ALL_OK);
		when(tutorConfig.supportsAction(ActionType.ON_PAGE_ALL_OK)).thenReturn(true);
		
		// when
		Optional<ActionType> actionType = actionTypeGenerator.findActionType();
		
		// then
		assertThat(actionType.get()).isEqualTo(ActionType.ON_PAGE_ALL_OK);
	}
	
	@Test
	public void findActionType_notSupported(){
		// given
		when(pageAllOk.actionOccured()).thenReturn(true);
		when(pageAllOk.getActionType()).thenReturn(ActionType.ON_PAGE_ALL_OK);
		when(tutorConfig.supportsAction(ActionType.ON_PAGE_ALL_OK)).thenReturn(false);
		
		// when
		Optional<ActionType> actionType = actionTypeGenerator.findActionType();
		
		// then
		assertThat(actionType.isPresent()).isFalse();
	}
	
	@Test
	public void findActionType_notOccured(){
		// given
		when(pageAllOk.actionOccured()).thenReturn(false);
		when(pageAllOk.getActionType()).thenReturn(ActionType.ON_PAGE_ALL_OK);
		when(tutorConfig.supportsAction(ActionType.ON_PAGE_ALL_OK)).thenReturn(false);
		
		// when
		Optional<ActionType> actionType = actionTypeGenerator.findActionType();
		
		// then
		assertThat(actionType.isPresent()).isFalse();
	}
}
