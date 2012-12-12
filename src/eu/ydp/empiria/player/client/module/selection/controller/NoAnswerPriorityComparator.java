package eu.ydp.empiria.player.client.module.selection.controller;

import java.util.Comparator;

import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;

public class NoAnswerPriorityComparator implements Comparator<SelectionAnswerDto> {

	@Override
	public int compare(SelectionAnswerDto o1, SelectionAnswerDto o2) {
		return 0;
	}
}
