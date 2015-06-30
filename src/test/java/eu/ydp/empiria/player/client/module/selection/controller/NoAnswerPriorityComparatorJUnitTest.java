package eu.ydp.empiria.player.client.module.selection.controller;

import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NoAnswerPriorityComparatorJUnitTest {

    private NoAnswerPriorityComparator comparator = new NoAnswerPriorityComparator();

    @Test
    public void testCompare() {

        SelectionAnswerDto o1 = new SelectionAnswerDto("id1");
        SelectionAnswerDto o2 = new SelectionAnswerDto("id2");

        int compare = comparator.compare(o1, o2);
        assertEquals(0, compare);

        compare = comparator.compare(o2, o1);
        assertEquals(0, compare);
    }

}
