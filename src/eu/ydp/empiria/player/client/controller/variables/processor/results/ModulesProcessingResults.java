package eu.ydp.empiria.player.client.controller.variables.processor.results;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.thirdparty.guava.common.collect.Maps;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GeneralVariables;


public class ModulesProcessingResults {

	private final InitialProcessingResultFactory processingResultFactory;
	private Map<String, DtoModuleProcessingResult> responseIdToProcessingResults = Maps.newHashMap();

	@Inject
	public ModulesProcessingResults(InitialProcessingResultFactory processingResultFactory) {
		this.processingResultFactory = processingResultFactory;
	}

	public List<String> getPreviousAnswersForResponseId(String responseId){
		DtoModuleProcessingResult processingResult = getOrCreateProcessingResultForResponseId(responseId);
		GeneralVariables generalVariables = processingResult.getGeneralVariables();
		return generalVariables.getAnswers();
	}
	
	public DtoModuleProcessingResult getProcessingResultsForResponseId(String responseId){
		DtoModuleProcessingResult processingResult = getOrCreateProcessingResultForResponseId(responseId);
		return processingResult;
	}
	
	public void setProcessingResultsForResponseId(String responseId, DtoModuleProcessingResult processingResult){
		responseIdToProcessingResults.put(responseId, processingResult);
	}
	
	public List<DtoModuleProcessingResult> getListOfProcessingResults(){
		Collection<DtoModuleProcessingResult> processingResults = responseIdToProcessingResults.values();
		return new ArrayList<DtoModuleProcessingResult>(processingResults);
	}
	
	public Set<String> getIdsOfProcessedResponses(){
		return responseIdToProcessingResults.keySet();
	}
	
	private DtoModuleProcessingResult getOrCreateProcessingResultForResponseId(String responseId){
		DtoModuleProcessingResult processingResult = responseIdToProcessingResults.get(responseId);
		if(processingResult == null){
			return processingResultFactory.createProcessingResultWithInitialValues();
		}else{
			return processingResult;
		}
	}
}
