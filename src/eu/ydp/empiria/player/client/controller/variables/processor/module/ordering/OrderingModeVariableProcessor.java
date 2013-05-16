package eu.ydp.empiria.player.client.controller.variables.processor.module.ordering;

import java.util.ArrayList;
import java.util.List;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.module.VariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;

public class OrderingModeVariableProcessor implements VariableProcessor {

	@Override
	public int calculateErrors(Response response) {
		return 0;
	}

	@Override
	public int calculateDone(Response response) {
		List<String> allCorrectAnswers = getAllCorrectAnswers(response);
		List<String> allUserAnswers = response.values;

		for (int i = 0; i < allCorrectAnswers.size(); i++) {
			String correctAnswer = allCorrectAnswers.get(i);
			String userAnswer = allUserAnswers.get(i);

			if (!correctAnswer.equals(userAnswer)) {
				return 0;
			}
		}

		return 1;
	}

	@Override
	public boolean checkLastmistaken(Response response, LastAnswersChanges answersChanges) {
		return false;
	}

	@Override
	public int calculateMistakes(boolean lastmistaken, int previousMistakes) {
		return 0;
	}

	@Override
	public List<Boolean> evaluateAnswers(Response response) {

		List<String> allCorrectAnswers = getAllCorrectAnswers(response);
		List<String> allUserAnswers = response.values;

		List<Boolean> result = new ArrayList<Boolean>();

		for (int i = 0; i < allCorrectAnswers.size(); i++) {
			String correctAnswer = allCorrectAnswers.get(i);
			String userAnswer = allUserAnswers.get(i);

			result.add(correctAnswer.equals(userAnswer));
		}
		return result;
	}

	private List<String> getAllCorrectAnswers(Response response) {
		CorrectAnswers correctAnswers = response.correctAnswers;
		return correctAnswers.getAllAnswers();
	}

}
