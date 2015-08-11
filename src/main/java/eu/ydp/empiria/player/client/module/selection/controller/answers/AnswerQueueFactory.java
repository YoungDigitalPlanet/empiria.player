package eu.ydp.empiria.player.client.module.selection.controller.answers;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.selection.controller.NoAnswerPriorityComparator;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import java.util.*;

public class AnswerQueueFactory {

    private NoAnswerPriorityComparator noPriorityComparator;

    @Inject
    public AnswerQueueFactory(NoAnswerPriorityComparator noPriorityComparator) {
        this.noPriorityComparator = noPriorityComparator;
    }

    public Queue<SelectionAnswerDto> createAnswerQueue(boolean isMulti, int maxSelected) {
        if (isMulti) {
            return new PriorityQueue<>(maxSelected, noPriorityComparator);
        } else {
            return new PriorityQueue<>(1, noPriorityComparator);
        }
    }
}
