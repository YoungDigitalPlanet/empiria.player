package eu.ydp.empiria.player.client.controller.variables.processor.global;

import static com.google.inject.Guice.createInjector;
import static eu.ydp.empiria.player.client.controller.variables.processor.global.GlobalVariablesTestHelper.createExpressionResponse;
import static eu.ydp.empiria.player.client.controller.variables.processor.global.GlobalVariablesTestHelper.createResponse;
import static eu.ydp.empiria.player.client.controller.variables.processor.global.GlobalVariablesTestHelper.prepareProcessingResults;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult.fromDefaultVariables;
import static org.fest.assertions.api.Assertions.assertThat;

import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.ResultVariables;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

public class ResultVariablesConverterJUnitTest {

	private final ResultVariablesConverter converter = createInjector().getInstance(ResultVariablesConverter.class);

	@Test
	public void singleDefaultResponseWithoutChanges() {
		// given
		String ID = "id1";
		Map<String, DtoModuleProcessingResult> modulesProcessingResults = ImmutableMap.of(ID, fromDefaultVariables());
		Map<String, Response> responses = ImmutableMap.of(ID, createResponse(ID));

		// when
		Iterable<ResultVariables> resultVariables = converter.convertToResultVariables(modulesProcessingResults, responses);

		// then
		assertThat(resultVariables).containsOnly(new GlobalVariables(0, 0, 0, 0, false));
	}

	@Test
	public void singleDefaultResponse() {
		// given
		String ID = "id1";
		final int TODO = 4;
		final int ERRORS = 3;
		final int DONE = 1;
		final boolean LAST_MISTAKEN = true;
		final int MISTAKES = 9;

		DtoModuleProcessingResult processingResult = prepareProcessingResults(TODO, DONE, ERRORS, MISTAKES, LAST_MISTAKEN);
		Map<String, DtoModuleProcessingResult> modulesProcessingResults = ImmutableMap.of(ID, processingResult);
		Map<String, Response> responses = ImmutableMap.of(ID, createResponse(ID));

		// when
		Iterable<ResultVariables> resultVariables = converter.convertToResultVariables(modulesProcessingResults, responses);

		// then
		assertThat(resultVariables).containsOnly(new GlobalVariables(TODO, DONE, ERRORS, MISTAKES, LAST_MISTAKEN));
	}

	@Test
	public void multipleDefaultResponsesWithoutChanges() {
		// given
		String ID_0 = "id0";
		String ID_1 = "id1";
		Map<String, DtoModuleProcessingResult> modulesProcessingResults = ImmutableMap.of(ID_0, fromDefaultVariables(), ID_1, fromDefaultVariables());
		Map<String, Response> responses = ImmutableMap.of(ID_0, createResponse(ID_0), ID_1, createResponse(ID_1));

		// when
		Iterable<ResultVariables> resultVariables = converter.convertToResultVariables(modulesProcessingResults, responses);

		// then
		assertThat(resultVariables).containsOnly(new GlobalVariables(0, 0, 0, 0, false), new GlobalVariables(0, 0, 0, 0, false));
	}

	@Test
	public void multipleDefaultResponses() {
		// given
		String ID_0 = "id0";
		final int TODO_0 = 4;
		final int ERRORS_0 = 3;
		final int DONE_0 = 1;
		final boolean LAST_MISTAKEN_0 = true;
		final int MISTAKES_0 = 9;

		String ID_1 = "id1";
		final int TODO_1 = 2;
		final int ERRORS_1 = 2;
		final int DONE_1 = 3;
		final boolean LAST_MISTAKEN_1 = false;
		final int MISTAKES_1 = 83;

		DtoModuleProcessingResult processingResult0 = prepareProcessingResults(TODO_0, DONE_0, ERRORS_0, MISTAKES_0, LAST_MISTAKEN_0);
		DtoModuleProcessingResult processingResult1 = prepareProcessingResults(TODO_1, DONE_1, ERRORS_1, MISTAKES_1, LAST_MISTAKEN_1);
		Map<String, DtoModuleProcessingResult> modulesProcessingResults = ImmutableMap.of(ID_0, processingResult0, ID_1, processingResult1);
		Map<String, Response> responses = ImmutableMap.of(ID_0, createResponse(ID_0), ID_1, createResponse(ID_1));

		// when
		Iterable<ResultVariables> resultVariables = converter.convertToResultVariables(modulesProcessingResults, responses);

		// then
		assertThat(resultVariables).hasSize(2);
		assertThat(resultVariables).contains(new GlobalVariables(TODO_0, DONE_0, ERRORS_0, MISTAKES_0, LAST_MISTAKEN_0));
		assertThat(resultVariables).contains(new GlobalVariables(TODO_1, DONE_1, ERRORS_1, MISTAKES_1, LAST_MISTAKEN_1));
	}

	@Test
	public void singleExpressionResponses() {
		// given
		String ID_0 = "id0";
		final int TODO_0 = 1;
		final int DONE_0 = 0;
		final int ERRORS_0 = 1;
		final boolean LAST_MISTAKEN_0 = true;
		final int MISTAKES_0 = 9;

		String ID_1 = "id1";
		final int TODO_1 = 1;
		final int DONE_1 = 1;
		final int ERRORS_1 = 0;
		final boolean LAST_MISTAKEN_1 = false;
		final int MISTAKES_1 = 83;

		final int EXPR_TODO = 1;
		final int EXPR_DONE = 0;
		final int EXPR_ERRORS = 1;
		final boolean EXPR_LAST_MISTAKEN = true;
		final int EXPR_MISTAKES = 92;

		DtoModuleProcessingResult processingResult0 = prepareProcessingResults(TODO_0, DONE_0, ERRORS_0, MISTAKES_0, LAST_MISTAKEN_0);
		DtoModuleProcessingResult processingResult1 = prepareProcessingResults(TODO_1, DONE_1, ERRORS_1, MISTAKES_1, LAST_MISTAKEN_1);
		Map<String, DtoModuleProcessingResult> modulesProcessingResults = ImmutableMap.of(ID_0, processingResult0, ID_1, processingResult1);
		ExpressionBean expressionBean = new ExpressionBean();
		Response response0 = createExpressionResponse(ID_0, expressionBean);
		Response response1 = createExpressionResponse(ID_1, expressionBean);
		Map<String, Response> responses = ImmutableMap.of(ID_0, response0, ID_1, response1);

		// when
		Iterable<ResultVariables> resultVariables = converter.convertToResultVariables(modulesProcessingResults, responses);

		// then
		assertThat(resultVariables).hasSize(1);
		assertThat(resultVariables).contains(new GlobalVariables(EXPR_TODO, EXPR_DONE, EXPR_ERRORS, EXPR_MISTAKES, EXPR_LAST_MISTAKEN));
	}

	@Test
	public void multipleExpressionsResponses() {
		// given
		// expression 0
		String ID_0_0 = "id00";
		final int TODO_0_0 = 1;
		final int DONE_0_0 = 0;
		final int ERRORS_0_0 = 1;
		final boolean LAST_MISTAKEN_0_0 = true;
		final int MISTAKES_0_0 = 9;

		String ID_0_1 = "id01";
		final int TODO_0_1 = 1;
		final int DONE_0_1 = 1;
		final int ERRORS_0_1 = 0;
		final boolean LAST_MISTAKEN_0_1 = false;
		final int MISTAKES_0_1 = 83;

		final int EXPR_TODO_0 = 1;
		final int EXPR_DONE_0 = 0;
		final int EXPR_ERRORS_0 = 1;
		final boolean EXPR_LAST_MISTAKEN_0 = true;
		final int EXPR_MISTAKES_0 = 92;

		// expression 1
		String ID_1_0 = "id10";
		final int TODO_1_0 = 1;
		final int DONE_1_0 = 1;
		final int ERRORS_1_0 = 0;
		final boolean LAST_MISTAKEN_1_0 = false;
		final int MISTAKES_1_0 = 2;

		String ID_1_1 = "id11";
		final int TODO_1_1 = 1;
		final int DONE_1_1 = 1;
		final int ERRORS_1_1 = 0;
		final boolean LAST_MISTAKEN_1_1 = false;
		final int MISTAKES_1_1 = 3;

		final int EXPR_TODO_1 = 1;
		final int EXPR_DONE_1 = 1;
		final int EXPR_ERRORS_1 = 0;
		final boolean EXPR_LAST_MISTAKEN_1 = false;
		final int EXPR_MISTAKES_1 = 5;

		DtoModuleProcessingResult processingResult00 = prepareProcessingResults(TODO_0_0, DONE_0_0, ERRORS_0_0, MISTAKES_0_0, LAST_MISTAKEN_0_0);
		DtoModuleProcessingResult processingResult01 = prepareProcessingResults(TODO_0_1, DONE_0_1, ERRORS_0_1, MISTAKES_0_1, LAST_MISTAKEN_0_1);

		DtoModuleProcessingResult processingResult10 = prepareProcessingResults(TODO_1_0, DONE_1_0, ERRORS_1_0, MISTAKES_1_0, LAST_MISTAKEN_1_0);
		DtoModuleProcessingResult processingResult11 = prepareProcessingResults(TODO_1_1, DONE_1_1, ERRORS_1_1, MISTAKES_1_1, LAST_MISTAKEN_1_1);

		Map<String, DtoModuleProcessingResult> modulesProcessingResults = ImmutableMap.of(ID_0_0, processingResult00, ID_0_1, processingResult01, ID_1_0,
				processingResult10, ID_1_1, processingResult11);

		ExpressionBean expressionBean0 = new ExpressionBean();
		Response response00 = createExpressionResponse(ID_0_0, expressionBean0);
		Response response01 = createExpressionResponse(ID_0_1, expressionBean0);

		ExpressionBean expressionBean1 = new ExpressionBean();
		Response response10 = createExpressionResponse(ID_1_0, expressionBean1);
		Response response11 = createExpressionResponse(ID_1_1, expressionBean1);

		Map<String, Response> responses = ImmutableMap.of(ID_0_0, response00, ID_0_1, response01, ID_1_0, response10, ID_1_1, response11);

		// when
		Iterable<ResultVariables> resultVariables = converter.convertToResultVariables(modulesProcessingResults, responses);

		// then
		assertThat(resultVariables).containsOnly(new GlobalVariables(EXPR_TODO_0, EXPR_DONE_0, EXPR_ERRORS_0, EXPR_MISTAKES_0, EXPR_LAST_MISTAKEN_0),
				new GlobalVariables(EXPR_TODO_1, EXPR_DONE_1, EXPR_ERRORS_1, EXPR_MISTAKES_1, EXPR_LAST_MISTAKEN_1));
	}

	@Test
	public void mixedDefaultAndExpressionResponses() {
		// given
		// expression
		String ID_0 = "id0";
		final int TODO_0 = 1;
		final int DONE_0 = 0;
		final int ERRORS_0 = 1;
		final boolean LAST_MISTAKEN_0 = true;
		final int MISTAKES_0 = 9;

		String ID_1 = "id1";
		final int TODO_1 = 1;
		final int DONE_1 = 1;
		final int ERRORS_1 = 0;
		final boolean LAST_MISTAKEN_1 = false;
		final int MISTAKES_1 = 83;

		final int EXPR_TODO = 1;
		final int EXPR_DONE = 0;
		final int EXPR_ERRORS = 1;
		final boolean EXPR_LAST_MISTAKEN = true;
		final int EXPR_MISTAKES = 92;

		// default
		String ID_2 = "id3";
		final int TODO_2 = 4;
		final int ERRORS_2 = 3;
		final int DONE_2 = 1;
		final boolean LAST_MISTAKEN_2 = true;
		final int MISTAKES_2 = 9;

		DtoModuleProcessingResult processingResult0 = prepareProcessingResults(TODO_0, DONE_0, ERRORS_0, MISTAKES_0, LAST_MISTAKEN_0);
		DtoModuleProcessingResult processingResult1 = prepareProcessingResults(TODO_1, DONE_1, ERRORS_1, MISTAKES_1, LAST_MISTAKEN_1);
		DtoModuleProcessingResult processingResult2 = prepareProcessingResults(TODO_2, DONE_2, ERRORS_2, MISTAKES_2, LAST_MISTAKEN_2);
		Map<String, DtoModuleProcessingResult> modulesProcessingResults = ImmutableMap.of(ID_0, processingResult0, ID_1, processingResult1, ID_2,
				processingResult2);
		ExpressionBean expressionBean = new ExpressionBean();
		Response response0 = createExpressionResponse(ID_0, expressionBean);
		Response response1 = createExpressionResponse(ID_1, expressionBean);
		Response response2 = createResponse(ID_2);
		Map<String, Response> responses = ImmutableMap.of(ID_0, response0, ID_1, response1, ID_2, response2);

		// when
		Iterable<ResultVariables> resultVariables = converter.convertToResultVariables(modulesProcessingResults, responses);

		// then
		assertThat(resultVariables).contains(new GlobalVariables(EXPR_TODO, EXPR_DONE, EXPR_ERRORS, EXPR_MISTAKES, EXPR_LAST_MISTAKEN),
				new GlobalVariables(TODO_2, DONE_2, ERRORS_2, MISTAKES_2, LAST_MISTAKEN_2));
	}

}
