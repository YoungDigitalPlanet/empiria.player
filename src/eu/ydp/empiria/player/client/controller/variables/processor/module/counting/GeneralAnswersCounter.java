package eu.ydp.empiria.player.client.controller.variables.processor.module.counting;

import java.util.List;

import com.google.common.base.Predicate;

public class GeneralAnswersCounter {

	public int countAnswersMatchingPredicate(List<String> answers, Predicate<String> predicate) {
		int counter = 0;
		for (String answer : answers) {
			if (predicate.apply(answer)) {
				counter++;
			}
		}
		return counter;
	}

}
