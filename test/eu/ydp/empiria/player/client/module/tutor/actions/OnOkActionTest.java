package eu.ydp.empiria.player.client.module.tutor.actions;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.controller.variables.processor.OutcomeAccessor;
import eu.ydp.empiria.player.client.module.tutor.ActionType;

@RunWith(MockitoJUnitRunner.class)
public class OnOkActionTest {

	@InjectMocks
	private OnOkAction action;

	@Mock
	private OutcomeAccessor accessor;

	@Test
	public void actionOccured_selectionNoMistake() {
		// given
		when(accessor.isLastActionSelection()).thenReturn(true);
		when(accessor.isCurrentPageLastMistaken()).thenReturn(false);

		// when
		boolean occured = action.actionOccured();

		// then
		assertThat(occured).isTrue();
	}

	@Test
	public void actionOccured_selectionMistake() {
		// given
		when(accessor.isLastActionSelection()).thenReturn(true);
		when(accessor.isCurrentPageLastMistaken()).thenReturn(true);

		// when
		boolean occured = action.actionOccured();

		// then
		assertThat(occured).isFalse();
	}

	@Test
	public void actionOccured_unselectionNoMistake() {
		// given
		when(accessor.isLastActionSelection()).thenReturn(false);
		when(accessor.isCurrentPageLastMistaken()).thenReturn(false);

		// when
		boolean occured = action.actionOccured();

		// then
		assertThat(occured).isFalse();
	}

	@Test
	public void actionOccured_unselectionMistake() {
		// given
		when(accessor.isLastActionSelection()).thenReturn(false);
		when(accessor.isCurrentPageLastMistaken()).thenReturn(true);

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
		assertThat(type).isEqualTo(ActionType.ON_OK);
	}
}
