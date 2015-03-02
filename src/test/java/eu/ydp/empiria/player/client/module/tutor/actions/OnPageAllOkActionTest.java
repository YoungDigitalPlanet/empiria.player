package eu.ydp.empiria.player.client.module.tutor.actions;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.controller.variables.processor.FeedbackActionConditions;
import eu.ydp.empiria.player.client.module.tutor.ActionType;

@RunWith(MockitoJUnitRunner.class)
public class OnPageAllOkActionTest {

	@InjectMocks
	private OnPageAllOkAction action;

	@Mock
	private FeedbackActionConditions actionConditions;

	@Test
	public void actionOccured() {
		// given
		when(actionConditions.isPageAllOk()).thenReturn(true);

		// when
		boolean occured = action.actionOccured();

		// then
		assertThat(occured).isTrue();
	}

	@Test
	public void actionNotOccured() {
		// given
		when(actionConditions.isPageAllOk()).thenReturn(false);

		// when
		boolean occured = action.actionOccured();

		// then
		assertThat(occured).isFalse();
	}

	@Test
	public void actionNotOccured_noActivities() {
		// given
		when(actionConditions.isPageAllOk()).thenReturn(false);

		// when
		boolean occured = action.actionOccured();

		// then
		assertThat(occured).isFalse();
	}

	@Test
	public void type() {
		// when
		ActionType type = action.getActionType();

		// then
		assertThat(type).isEqualTo(ActionType.ON_PAGE_ALL_OK);
	}

}
