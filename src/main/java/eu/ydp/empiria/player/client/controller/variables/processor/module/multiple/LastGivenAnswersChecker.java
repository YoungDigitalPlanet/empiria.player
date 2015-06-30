package eu.ydp.empiria.player.client.controller.variables.processor.module.multiple;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;

import java.util.List;

public class LastGivenAnswersChecker {

    public boolean isAnyAsnwerIncorrect(List<String> answers, CorrectAnswers correctAnswers) {
        int amountOfIncorrectAnswers = countHowManyOfAnswersAreIncorrect(answers, correctAnswers);
        boolean isAnyIncorrect = amountOfIncorrectAnswers > 0;
        return isAnyIncorrect;
    }

    private int countHowManyOfAnswersAreIncorrect(List<String> answers, CorrectAnswers correctAnswers) {
        int countOfInorrectAnswers = 0;
        for (String answer : answers) {
            if (!correctAnswers.containsAnswer(answer)) {
                countOfInorrectAnswers++;
            }
        }
        return countOfInorrectAnswers;
    }
}
