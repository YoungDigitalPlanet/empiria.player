package eu.ydp.empiria.player.client.module.selection.controller;

import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;

import java.util.Comparator;

public class NoAnswerPriorityComparator implements Comparator<SelectionAnswerDto> {

    @Override
    public int compare(SelectionAnswerDto o1, SelectionAnswerDto o2) {
        return 0;
    }
}
