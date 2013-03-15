package eu.ydp.empiria.player.client.controller.variables.processor.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.DtoProcessedResponse;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.item.ResponseDifferenceFinder;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;

public class ResponseChangesFinder {

	private ResponseDifferenceFinder responseDifferenceFinder;
	
	@Inject
	public ResponseChangesFinder(ResponseDifferenceFinder responseDifferenceFinder) {
		this.responseDifferenceFinder = responseDifferenceFinder;
	}
	
	public List<DtoProcessedResponse> findChangesOfAnswers(ModulesProcessingResults processingResults, Map<String, Response> responses) {
		List<DtoProcessedResponse> changedResponses = new ArrayList<DtoProcessedResponse>();
		
		for(String responseIdentifier : responses.keySet()){
			DtoProcessedResponse changedResponse = getChangedResponseForResponseId(responseIdentifier, responses, processingResults);
			changedResponses.add(changedResponse);
		}
		
		return changedResponses;
	}

	private DtoProcessedResponse getChangedResponseForResponseId(String responseIdentifier, Map<String, Response> responses, ModulesProcessingResults processingResults) {
		Response response = responses.get(responseIdentifier);
		DtoModuleProcessingResult processingResult = processingResults.getProcessingResultsForResponseId(responseIdentifier);
		
		List<String> currentAnswers = getAnswersOrEmptyList(response);
		List<String> previousAnswers = processingResults.getPreviousAnswersForResponseId(responseIdentifier);
		
		LastAnswersChanges changesOfAnswers = responseDifferenceFinder.findChangesOfAnswers(previousAnswers, currentAnswers);
		
		processingResult.getUserInteractionVariables().setLastAnswerChanges(changesOfAnswers);
		
		DtoProcessedResponse changedResponse = new DtoProcessedResponse(response, processingResult, changesOfAnswers);
		return changedResponse;
	}

	private List<String> getAnswersOrEmptyList(Response response) {
		List<String> answers;
		if(response == null){
			answers = Lists.newArrayList();
		}else{
			answers = response.values;
		}
		return answers;
	}

}
