package eu.ydp.empiria.player.client.module.tutor.actions;

import eu.ydp.empiria.player.client.controller.variables.processor.OutcomeAccessor;
import eu.ydp.empiria.player.client.module.tutor.ActionType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken.*;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OnWrongActionTest {

    @InjectMocks
    private OnWrongAction action;

    @Mock
    private OutcomeAccessor accessor;

    @Test
    public void actionOccured_selectionCorrect() {
        // given
        when(accessor.isLastActionSelection()).thenReturn(true);
        when(accessor.getCurrentPageLastMistaken()).thenReturn(CORRECT);

        // when
        boolean occured = action.actionOccured();

        // then
        assertThat(occured).isFalse();
    }

    @Test
    public void actionOccured_selectionNone() {
        // given
        when(accessor.isLastActionSelection()).thenReturn(true);
        when(accessor.getCurrentPageLastMistaken()).thenReturn(NONE);

        // when
        boolean occured = action.actionOccured();

        // then
        assertThat(occured).isFalse();
    }

    @Test
    public void actionOccured_selectionWrong() {
        // given
        when(accessor.isLastActionSelection()).thenReturn(true);
        when(accessor.getCurrentPageLastMistaken()).thenReturn(WRONG);

        // when
        boolean occured = action.actionOccured();

        // then
        assertThat(occured).isTrue();
    }

    @Test
    public void actionOccured_unselectionCorrect() {
        // given
        when(accessor.isLastActionSelection()).thenReturn(false);
        when(accessor.getCurrentPageLastMistaken()).thenReturn(CORRECT);

        // when
        boolean occured = action.actionOccured();

        // then
        assertThat(occured).isFalse();
    }

    @Test
    public void actionOccured_unselectionNone() {
        // given
        when(accessor.isLastActionSelection()).thenReturn(false);
        when(accessor.getCurrentPageLastMistaken()).thenReturn(NONE);

        // when
        boolean occured = action.actionOccured();

        // then
        assertThat(occured).isFalse();
    }

    @Test
    public void actionOccured_unselectionWrong() {
        // given
        when(accessor.isLastActionSelection()).thenReturn(false);
        when(accessor.getCurrentPageLastMistaken()).thenReturn(WRONG);

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
        assertThat(type).isEqualTo(ActionType.ON_WRONG);
    }
}
