package eu.ydp.empiria.player.client.controller.variables.processor.module;

import java.util.logging.Logger;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

public class ModuleTodoCalculator {

	private static final Logger LOGGER = Logger.getLogger(ModuleTodoCalculator.class.getName());
	
	public int calculateTodoForResponse(Response response){
		CountMode countMode = response.getAppropriateCountMode();
		
		int todoCount = 0;
		if(countMode == CountMode.SINGLE){
			todoCount = 1;
		}else if(countMode == CountMode.CORRECT_ANSWERS){
			todoCount = response.correctAnswers.getResponseValuesCount();
		}else{
			todoCount = 1;
			LOGGER.warning("Unsupported TODO countMode: "+countMode);
		}
		return todoCount;
	}
	
}
