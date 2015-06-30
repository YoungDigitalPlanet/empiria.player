package eu.ydp.empiria.player.client.controller.variables.processor;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GeneralVariables;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScoped;

import java.util.List;
import java.util.logging.Logger;

/**
 * inject in {@link PageScoped}
 */
public class AnswerEvaluationSupplier {

    private static final Logger LOGGER = Logger.getLogger(VariableProcessingAdapter.class.getName());

    private ModulesProcessingResults modulesProcessingResults;

    public void updateModulesProcessingResults(ModulesProcessingResults modulesProcessingResults) {
        this.modulesProcessingResults = modulesProcessingResults;
    }

    public List<Boolean> evaluateAnswer(Response response) {
        if (modulesProcessingResults == null) {
            return handleInvalidEvaluationRequest();
        }
        return doEvaluation(response);
    }

    private List<Boolean> handleInvalidEvaluationRequest() {
        String message = "Cannot evaluate answers before first variables processing! Returning empty answerEvaluations list";
        LOGGER.warning(message);
        return Lists.newArrayList();
    }

    private List<Boolean> doEvaluation(Response response) {
        DtoModuleProcessingResult processingResult = modulesProcessingResults.getProcessingResultsForResponseId(response.getID());
        GeneralVariables generalVariables = processingResult.getGeneralVariables();
        List<Boolean> answersEvaluation = generalVariables.getAnswersEvaluation();
        return answersEvaluation;
    }
}
