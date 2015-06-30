package eu.ydp.empiria.player.client.module.tutor.actions;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import eu.ydp.empiria.player.client.controller.extensions.internal.tutor.TutorConfig;
import eu.ydp.empiria.player.client.module.tutor.ActionType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OutcomeDrivenActionTypeGeneratorTest {

    @InjectMocks
    private OutcomeDrivenActionTypeGenerator actionTypeGenerator;

    @Mock
    private OutcomeDrivenAction okAction;
    @Mock
    private OutcomeDrivenAction wrongAction;
    @Mock
    private OutcomeDrivenAction pageAllOkAction;
    @Mock
    private TutorConfig tutorConfig;
    @Mock
    private OutcomeDrivenActionTypeProvider actionTypeProvider;

    @Before
    public void setUp() {
        when(actionTypeProvider.getActions()).thenReturn(ImmutableSet.of(pageAllOkAction, okAction, wrongAction));
    }

    @Test
    public void findActionType_occuredSupported() {
        // given
        when(pageAllOkAction.actionOccured()).thenReturn(true);
        when(pageAllOkAction.getActionType()).thenReturn(ActionType.ON_PAGE_ALL_OK);
        when(tutorConfig.supportsAction(ActionType.ON_PAGE_ALL_OK)).thenReturn(true);

        // when
        Optional<ActionType> actionType = actionTypeGenerator.findActionType();

        // then
        assertThat(actionType.get()).isEqualTo(ActionType.ON_PAGE_ALL_OK);
    }

    @Test
    public void findActionType_occuredNotSupported() {
        // given
        when(pageAllOkAction.actionOccured()).thenReturn(true);
        when(pageAllOkAction.getActionType()).thenReturn(ActionType.ON_PAGE_ALL_OK);
        when(tutorConfig.supportsAction(ActionType.ON_PAGE_ALL_OK)).thenReturn(false);

        // when
        Optional<ActionType> actionType = actionTypeGenerator.findActionType();

        // then
        assertThat(actionType.isPresent()).isFalse();
    }

    @Test
    public void findActionType_notOccuredNotSupported() {
        // given
        when(pageAllOkAction.actionOccured()).thenReturn(false);
        when(pageAllOkAction.getActionType()).thenReturn(ActionType.ON_PAGE_ALL_OK);
        when(tutorConfig.supportsAction(ActionType.ON_PAGE_ALL_OK)).thenReturn(false);

        // when
        Optional<ActionType> actionType = actionTypeGenerator.findActionType();

        // then
        assertThat(actionType.isPresent()).isFalse();
    }

    @Test
    public void findActionType_notOccuredSupported() {
        // given
        when(pageAllOkAction.actionOccured()).thenReturn(false);
        when(pageAllOkAction.getActionType()).thenReturn(ActionType.ON_PAGE_ALL_OK);
        when(tutorConfig.supportsAction(ActionType.ON_PAGE_ALL_OK)).thenReturn(true);

        // when
        Optional<ActionType> actionType = actionTypeGenerator.findActionType();

        // then
        assertThat(actionType.isPresent()).isFalse();
    }
}
