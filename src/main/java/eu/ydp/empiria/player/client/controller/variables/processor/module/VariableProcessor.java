package eu.ydp.empiria.player.client.controller.variables.processor.module;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;

import java.util.List;

public interface VariableProcessor {

    int calculateErrors(Response response);

    int calculateDone(Response response);

    LastMistaken checkLastmistaken(Response response, LastAnswersChanges answersChanges);

    int calculateMistakes(LastMistaken lastmistaken, int previousMistakes);

    List<Boolean> evaluateAnswers(Response response);
}
