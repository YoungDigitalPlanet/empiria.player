package eu.ydp.empiria.player.client.controller.variables.processor.global;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.CheckMode;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.ConstantVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GeneralVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.UserInteractionVariables;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class GlobalVariablesTestHelper {

	public static Response createResponse(String id) {
		return new Response(new CorrectAnswers(), Lists.<String> newArrayList(), Lists.<String> newArrayList(), id, Evaluate.USER, Cardinality.SINGLE);
	}

	public static Response createExpressionResponse(String id, ExpressionBean expression) {
		Response response = new Response(new CorrectAnswers(), Lists.<String> newArrayList(), Lists.<String> newArrayList(), id, Evaluate.USER,
				Cardinality.SINGLE, CountMode.SINGLE, expression, CheckMode.EXPRESSION);
		expression.getResponses().add(response);
		return response;
	}

	public static DtoModuleProcessingResult prepareProcessingResults(final int todo, final int done, final int errors, final int mistakes,
			final boolean lastMistaken) {
		DtoModuleProcessingResult processingResult = DtoModuleProcessingResult.fromDefaultVariables();
		processingResult.setConstantVariables(new ConstantVariables(todo));
		processingResult.setGeneralVariables(new GeneralVariables(Lists.<String> newArrayList(), Lists.<Boolean> newArrayList(), errors, done));
		processingResult.setUserInteractionVariables(new UserInteractionVariables(new LastAnswersChanges(), lastMistaken, mistakes));
		return processingResult;
	}
}
