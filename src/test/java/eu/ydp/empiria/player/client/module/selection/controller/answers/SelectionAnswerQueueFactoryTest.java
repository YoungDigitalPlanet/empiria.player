package eu.ydp.empiria.player.client.module.selection.controller.answers;

import static org.fest.assertions.api.Assertions.*;

import eu.ydp.empiria.player.client.module.selection.controller.NoAnswerPriorityComparator;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import java.util.Queue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SelectionAnswerQueueFactoryTest {

    @InjectMocks
    private SelectionAnswerQueueFactory testObj;
    @Mock
    private NoAnswerPriorityComparator comparator;

    @Test
    public void shouldCreateQueue() {
        // given
        boolean isMulti = false;
        int maxSelected = 10;

        // when
        Queue<SelectionAnswerDto> answerQueue = testObj.createAnswerQueue(isMulti, maxSelected);

        // then
        assertThat(answerQueue).isNotNull();
    }
}