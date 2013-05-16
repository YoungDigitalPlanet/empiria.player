package eu.ydp.empiria.player.client.controller.variables.processor.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.response.DtoProcessedResponse;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.UserInteractionVariables;

public class ResponseChangesFinder {

	private final ResponseDifferenceFinder responseDifferenceFinder;
	private final OrderedResponseChangesFinder orderedResponseChangesFinder;

	@Inject
	public ResponseChangesFinder(ResponseDifferenceFinder responseDifferenceFinder, OrderedResponseChangesFinder ordererResponseChangesFinder) {
		this.responseDifferenceFinder = responseDifferenceFinder;
		this.orderedResponseChangesFinder = ordererResponseChangesFinder;
	}

	public List<DtoProcessedResponse> findChangesOfAnswers(ModulesProcessingResults processingResults, Map<String, Response> responses) {
		List<DtoProcessedResponse> changedResponses = new ArrayList<DtoProcessedResponse>();

		for (String responseIdentifier : responses.keySet()) {
			Response response = responses.get(responseIdentifier);
			DtoModuleProcessingResult previousProcessingResult = processingResults.getProcessingResultsForResponseId(responseIdentifier);

			DtoProcessedResponse changedResponse = getChangedResponseForResponseId(response, previousProcessingResult);
			changedResponses.add(changedResponse);
		}

		return changedResponses;
	}

	private DtoProcessedResponse getChangedResponseForResponseId(Response response, DtoModuleProcessingResult previousProcessingResult) {
		List<String> currentAnswers = getAnswersOrEmptyList(response);
		List<String> previousAnswers = getPreviousAnswers(previousProcessingResult);

		LastAnswersChanges changesOfAnswers;

		if (response.cardinality == Cardinality.ORDERED) {
			changesOfAnswers = orderedResponseChangesFinder.findChangesOfAnswers(previousAnswers, currentAnswers);
		} else {
			changesOfAnswers = responseDifferenceFinder.findChangesOfAnswers(previousAnswers, currentAnswers);
		}

		UserInteractionVariables userInteractionVariables = previousProcessingResult.getUserInteractionVariables();
		userInteractionVariables.setLastAnswerChanges(changesOfAnswers);

		DtoProcessedResponse changedResponse = new DtoProcessedResponse(response, previousProcessingResult, changesOfAnswers);
		return changedResponse;
	}

	private List<String> getPreviousAnswers(DtoModuleProcessingResult previousProcessingResult) {
		List<String> previousAnswers = previousProcessingResult.getGeneralVariables().getAnswers();
		return previousAnswers;
	}

	private List<String> getAnswersOrEmptyList(Response response) {
		List<String> currentAnswers = response.values;
		List<String> answers;
		if (currentAnswers == null) {
			answers = Lists.newArrayList();
		} else {
			answers = response.values;
		}
		return answers;
	}
}
