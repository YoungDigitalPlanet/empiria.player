package eu.ydp.empiria.player.client.controller.variables.processor.counting;

import java.util.List;

import com.google.gwt.thirdparty.guava.common.base.Predicate;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;

public class GeneralAnswersCounter {

	private ValueToCountModeAdjuster toCountModeAdjuster;
	
	@Inject
	public GeneralAnswersCounter(ValueToCountModeAdjuster toCountModeAdjuster) {
		this.toCountModeAdjuster = toCountModeAdjuster;
	}

	public int countAnswersMatchingPredicateAdjustedToMode(List<String> answers, CountMode countMode, Predicate<String> predicate){
		int counter = countAnswersMatchingPredicate(answers, predicate);
		int counterAdjustedToCountMode = toCountModeAdjuster.adjustValueToCountMode(counter, countMode);
		return counterAdjustedToCountMode;
	}
	
	private int countAnswersMatchingPredicate(List<String> answers, Predicate<String> predicate){
		int counter = 0;
		for (String answer : answers) {
			if(predicate.apply(answer)){
				counter++;
			}
		}
		return counter;
	}
	
}
