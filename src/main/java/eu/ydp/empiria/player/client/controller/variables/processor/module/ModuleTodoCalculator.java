package eu.ydp.empiria.player.client.controller.variables.processor.module;

import java.util.logging.Logger;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

public class ModuleTodoCalculator {

	private static final Logger LOGGER = Logger.getLogger(ModuleTodoCalculator.class.getName());

	public int calculateTodoForResponse(Response response) {
		CountMode countMode = response.getAppropriateCountMode();

		int todoCount;
		int correctAnswersCount = response.correctAnswers.getAnswersCount();

		if (countMode == CountMode.SINGLE) {
			boolean isSomethingToDo = correctAnswersCount > 0;
			if (isSomethingToDo) {
				todoCount = 1;
			} else {
				todoCount = 0;
			}
		} else if (countMode == CountMode.CORRECT_ANSWERS) {
			todoCount = correctAnswersCount;
		} else {
			todoCount = 1;
			LOGGER.warning("Unsupported TODO countMode: " + countMode);
		}
		return todoCount;
	}
}
