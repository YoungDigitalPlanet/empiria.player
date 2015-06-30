package eu.ydp.empiria.player.client.module.tutor.actions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class OutcomeDrivenActionTypeProviderTest {

    @InjectMocks
    OutcomeDrivenActionTypeProvider actionTypeProvider;

    @Mock
    OnOkAction okAction;
    @Mock
    OnWrongAction wrongAction;
    @Mock
    OnPageAllOkAction pageAllOkAction;

    @Test
    public void shouldReturnPageAllOkActionPriorToOnOkAction() {
        // when
        Set<OutcomeDrivenAction> actions = actionTypeProvider.getActions();

        // then
        List<OutcomeDrivenAction> list = new ArrayList<OutcomeDrivenAction>(actions);
        int indexOfPageAllOk = list.indexOf(pageAllOkAction);
        int indexOfOk = list.indexOf(okAction);
        assertThat(indexOfPageAllOk, is(lessThan(indexOfOk)));
    }
}
