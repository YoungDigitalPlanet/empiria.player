package eu.ydp.empiria.player.client.controller.variables.processor.module;

import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;

import java.util.ArrayList;
import java.util.List;

public class OrderedResponseChangesFinder {

    public LastAnswersChanges findChangesOfAnswers(List<String> previousAnswers, List<String> currentAnswers) {

        if (isEqual(previousAnswers, currentAnswers)) {
            return new LastAnswersChanges(new ArrayList<String>(), new ArrayList<String>());
        }

        return new LastAnswersChanges(currentAnswers, previousAnswers);
    }

    private boolean isEqual(List<String> previousAnswers, List<String> currentAnswers) {
        return previousAnswers.equals(currentAnswers);
    }
}
