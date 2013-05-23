package eu.ydp.empiria.player.client.controller.variables.processor.results;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;


public class ModulesProcessingResults {

	private final InitialProcessingResultFactory processingResultFactory;
	private Map<String, DtoModuleProcessingResult> responseIdToProcessingResults = Maps.newHashMap();

	@Inject
	public ModulesProcessingResults(InitialProcessingResultFactory processingResultFactory) {
		this.processingResultFactory = processingResultFactory;
	}

	public DtoModuleProcessingResult getProcessingResultsForResponseId(String responseId){
		DtoModuleProcessingResult processingResult = getOrCreateProcessingResultForResponseId(responseId);
		return processingResult;
	}
	
	public List<DtoModuleProcessingResult> getListOfProcessingResults(){
		Collection<DtoModuleProcessingResult> processingResults = responseIdToProcessingResults.values();
		return new ArrayList<DtoModuleProcessingResult>(processingResults);
	}
	
	public Set<String> getIdsOfProcessedResponses(){
		return responseIdToProcessingResults.keySet();
	}
	
	private DtoModuleProcessingResult getOrCreateProcessingResultForResponseId(String responseId){
		if(responseIdToProcessingResults.containsKey(responseId)){
			return responseIdToProcessingResults.get(responseId);
		} else {
			DtoModuleProcessingResult newProcessingResults = processingResultFactory.createProcessingResultWithInitialValues();
			responseIdToProcessingResults.put(responseId, newProcessingResults);
			return newProcessingResults;
		}
	}
}
