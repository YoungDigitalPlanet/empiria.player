package eu.ydp.empiria.player.client.controller.variables.objects.response;

import eu.ydp.empiria.player.client.controller.variables.processor.results.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.LastAnswersChanges;

public class DtoChangedResponse {

	private Response currentResponse;
	private DtoModuleProcessingResult previousProcessingResult;
	private LastAnswersChanges lastAnswersChanges;

	public DtoChangedResponse(Response currentResponse, DtoModuleProcessingResult previousProcessingResult, LastAnswersChanges lastAnswerChanges) {
		this.currentResponse = currentResponse;
		this.previousProcessingResult = previousProcessingResult;
		this.lastAnswersChanges = lastAnswerChanges;
	}

	public Response getCurrentResponse() {
		return currentResponse;
	}

	public void setCurrentResponse(Response currentResponse) {
		this.currentResponse = currentResponse;
	}

	public DtoModuleProcessingResult getPreviousProcessingResult() {
		return previousProcessingResult;
	}

	public void setPreviousProcessingResult(DtoModuleProcessingResult previousProcessingResult) {
		this.previousProcessingResult = previousProcessingResult;
	}

	public LastAnswersChanges getLastAnswersChanges() {
		return lastAnswersChanges;
	}

	public void setLastAnswersChanges(LastAnswersChanges lastAnswerChanges) {
		this.lastAnswersChanges = lastAnswerChanges;
	}

}
