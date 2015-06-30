package eu.ydp.empiria.player.client.controller.variables.processor.module.counting;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

public class DoneToCountModeAdjuster {

    public int adjustValueToCountMode(int amountOfGivenCorrectAnswers, Response response, CountMode countMode) {
        int adjustedValue;
        if (countMode == CountMode.CORRECT_ANSWERS) {
            adjustedValue = reducePointsByErrors(amountOfGivenCorrectAnswers, response);
        } else {
            adjustedValue = adjustDoneValueToSingleCountMode(amountOfGivenCorrectAnswers, response);
        }

        return adjustedValue;
    }

    private int reducePointsByErrors(int amountCorrectAnswers, Response response) {
        int allAnswers = response.values.size();
        int errorAnswers = allAnswers - amountCorrectAnswers;
        int points = amountCorrectAnswers - errorAnswers;

        if (points < 0) {
            points = 0;
        }
        return points;
    }

    private int adjustDoneValueToSingleCountMode(int amountOfGivenCorrectAnswers, Response response) {
        int adjustedValue;
        if (correctAnswersExistsAndIsSolvedWithoutErrors(amountOfGivenCorrectAnswers, response)) {
            adjustedValue = 1;
        } else {
            adjustedValue = 0;
        }
        return adjustedValue;
    }

    private boolean correctAnswersExistsAndIsSolvedWithoutErrors(int amountOfGivenCorrectAnswers, Response response) {
        return existsCorrectAnswersIn(response) && isSolvedWithoutErrors(amountOfGivenCorrectAnswers, response);
    }

    private boolean existsCorrectAnswersIn(Response response) {
        return response.correctAnswers.getAnswersCount() > 0;
    }

    private boolean isSolvedWithoutErrors(int amountOfGivenCorrectAnswers, Response response) {
        boolean isSolvedWithoutErrors = areAllGivenAnswersCorrect(amountOfGivenCorrectAnswers, response)
                && allRequiredAnswersGiven(amountOfGivenCorrectAnswers, response);
        return isSolvedWithoutErrors;
    }

    private boolean allRequiredAnswersGiven(int amountOfGivenCorrectAnswers, Response response) {
        Cardinality cardinality = response.cardinality;
        if (cardinality == Cardinality.SINGLE) {
            return amountOfGivenCorrectAnswers == 1;
        } else {
            return amountOfGivenCorrectAnswers == response.correctAnswers.getAnswersCount();
        }
    }

    private boolean areAllGivenAnswersCorrect(int amountOfGivenCorrectAnswers, Response response) {
        int amountOfGivenAnswers = response.values.size();
        return amountOfGivenAnswers == amountOfGivenCorrectAnswers;
    }

}
