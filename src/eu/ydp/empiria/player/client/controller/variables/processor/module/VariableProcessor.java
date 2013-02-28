package eu.ydp.empiria.player.client.controller.variables.processor.module;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.LastAnswersChanges;

public interface VariableProcessor {

	int calculateErrors(Response response);
	
	int calculateDone(Response response);
	
	boolean checkLastmistaken(Response response, LastAnswersChanges answersChanges);
	
	int calculateMistakes(boolean lastmistaken, int previousMistakes);
	
}
