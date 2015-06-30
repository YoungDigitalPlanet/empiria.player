package eu.ydp.empiria.player.client.controller.variables.processor.module.counting;

import com.google.common.base.Predicate;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.module.counting.predicates.CorrectAnswerPredicate;

public class CorrectAnswersCounter {

    private final GeneralAnswersCounter generalAnswersCounter;
    private final DoneToCountModeAdjuster doneToCountModeAdjuster;

    @Inject
    public CorrectAnswersCounter(GeneralAnswersCounter generalAnswersCounter, DoneToCountModeAdjuster doneToCountModeAdjuster) {
        this.generalAnswersCounter = generalAnswersCounter;
        this.doneToCountModeAdjuster = doneToCountModeAdjuster;
    }

    public int countCorrectAnswersAdjustedToCountMode(Response response) {
        int amountOfGivenCorrectAnswers = countCorrectForNotOrderedAnswers(response);
        CountMode countMode = response.getAppropriateCountMode();
        int adjustedValue = doneToCountModeAdjuster.adjustValueToCountMode(amountOfGivenCorrectAnswers, response, countMode);
        return adjustedValue;
    }

    private int countCorrectForNotOrderedAnswers(Response response) {
        CorrectAnswers correctAnswers = response.correctAnswers;
        Predicate<String> correctAnswerPredicate = new CorrectAnswerPredicate(correctAnswers);

        int amountOfCorrectAnswers = generalAnswersCounter.countAnswersMatchingPredicate(response.values, correctAnswerPredicate);
        return amountOfCorrectAnswers;
    }
}
