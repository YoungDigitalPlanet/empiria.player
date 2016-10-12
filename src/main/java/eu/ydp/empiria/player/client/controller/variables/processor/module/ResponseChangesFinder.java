package eu.ydp.empiria.player.client.controller.variables.processor.module;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.manager.VariableManager;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.response.DtoProcessedResponse;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.UserInteractionVariables;

import java.util.ArrayList;
import java.util.List;

public class ResponseChangesFinder {

    private final ResponseDifferenceFinder responseDifferenceFinder;
    private final OrderedResponseChangesFinder orderedResponseChangesFinder;

    @Inject
    public ResponseChangesFinder(ResponseDifferenceFinder responseDifferenceFinder, OrderedResponseChangesFinder ordererResponseChangesFinder) {
        this.responseDifferenceFinder = responseDifferenceFinder;
        this.orderedResponseChangesFinder = ordererResponseChangesFinder;
    }

    public List<DtoProcessedResponse> findChangesOfAnswers(ModulesProcessingResults processingResults, VariableManager<Response> responseManager) {
        List<DtoProcessedResponse> changedResponses = new ArrayList<DtoProcessedResponse>();

        for (String responseIdentifier : responseManager.getVariableIdentifiers()) {
            Response response = responseManager.getVariable(responseIdentifier);
            DtoModuleProcessingResult previousProcessingResult = processingResults.getProcessingResultsForResponseId(responseIdentifier);

            DtoProcessedResponse changedResponse = getChangedResponseForResponseId(response, previousProcessingResult);
            changedResponses.add(changedResponse);
        }

        return changedResponses;
    }

    private DtoProcessedResponse getChangedResponseForResponseId(Response response, DtoModuleProcessingResult previousProcessingResult) {
        List<String> currentAnswers = getAnswersOrEmptyList(response);
        List<String> previousAnswers = getPreviousAnswers(previousProcessingResult);

        LastAnswersChanges changesOfAnswers = getChangesOfAnswers(response, currentAnswers, previousAnswers);

        UserInteractionVariables userInteractionVariables = previousProcessingResult.getUserInteractionVariables();
        userInteractionVariables.setLastAnswerChanges(changesOfAnswers);

        DtoProcessedResponse changedResponse = new DtoProcessedResponse(response, previousProcessingResult, changesOfAnswers);
        return changedResponse;
    }

    private LastAnswersChanges getChangesOfAnswers(Response response, List<String> currentAnswers, List<String> previousAnswers) {
        LastAnswersChanges changesOfAnswers;
        if (response.cardinality == Cardinality.ORDERED) {
            changesOfAnswers = orderedResponseChangesFinder.findChangesOfAnswers(previousAnswers, currentAnswers);
        } else {
            changesOfAnswers = responseDifferenceFinder.findChangesOfAnswers(previousAnswers, currentAnswers);
        }
        return changesOfAnswers;
    }

    private List<String> getPreviousAnswers(DtoModuleProcessingResult previousProcessingResult) {
        List<String> previousAnswers = previousProcessingResult.getGeneralVariables().getAnswers();
        return previousAnswers;
    }

    private List<String> getAnswersOrEmptyList(Response response) {
        return Objects.firstNonNull(response.values, new ArrayList<String>());
    }
}
