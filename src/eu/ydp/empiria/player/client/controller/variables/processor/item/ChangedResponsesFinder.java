package eu.ydp.empiria.player.client.controller.variables.processor.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.DtoChangedResponse;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.LastAnswersChanges;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;

public class ChangedResponsesFinder {

	private ResponseDifferenceFinder responseDifferenceFinder;
	
	@Inject
	public ChangedResponsesFinder(ResponseDifferenceFinder responseDifferenceFinder) {
		this.responseDifferenceFinder = responseDifferenceFinder;
	}
	
	public List<DtoChangedResponse> findResponsesWhereAnswersChanged(ModulesProcessingResults processingResults, Map<String, Response> responses) {
		List<DtoChangedResponse> changedResponses = new ArrayList<DtoChangedResponse>();
		
		for(String responseIdentifier : responses.keySet()){
			DtoChangedResponse changedResponse = getChangedResponseForResponseId(responseIdentifier, responses, processingResults);
			if(containsChanges(changedResponse)){
				changedResponses.add(changedResponse);
			}
		}
		
		return changedResponses;
	}

	private DtoChangedResponse getChangedResponseForResponseId(String responseIdentifier, Map<String, Response> responses, ModulesProcessingResults processingResults) {
		Response response = responses.get(responseIdentifier);
		DtoModuleProcessingResult processingResult = processingResults.getProcessingResultsForResponseId(responseIdentifier);
		
		List<String> currentAnswers = getAnswersOrEmptyList(response);
		List<String> previousAnswers = processingResults.getPreviousAnswersForResponseId(responseIdentifier);
		
		LastAnswersChanges changesOfAnswers = responseDifferenceFinder.findChangesOfAnswers(previousAnswers, currentAnswers);
		
		DtoChangedResponse changedResponse = new DtoChangedResponse(response, processingResult, changesOfAnswers);
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

	private boolean containsChanges(DtoChangedResponse changedResponse) {
		LastAnswersChanges lastAnswerChanges = changedResponse.getLastAnswersChanges();
		List<String> addedAnswers = lastAnswerChanges.getAddedAnswers();
		List<String> removedAnswers = lastAnswerChanges.getRemovedAnswers();
		
		boolean containChanges = !addedAnswers.isEmpty() || !removedAnswers.isEmpty();
		return containChanges;
	}
}
