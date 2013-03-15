package eu.ydp.empiria.player.client.controller.variables.processor.item.functional;

import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.DONE;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.ERRORS;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.LASTMISTAKEN;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.MISTAKES;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.TODO;

public class OrderedVariableProcessorFunctionalJUnitTest extends VariableProcessorFunctionalTestBase{

	@Ignore("Functionality not implemented yet")
	@Test
	public void shouldRecognizeWrongOrderOfAnswers() throws Exception {
		// given
		Response response = new ResponseBuilder()
				.withCardinality(Cardinality.ORDERED)
				.withCorrectAnswers("firstAnswer", "secondAnswer")
				.withCurrentUserAnswers("secondAnswer", "firstAnswer")
				.build();

		Map<String, Response> responsesMap = convertToMap(response);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		// when
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(DONE), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(ERRORS, MISTAKES, LASTMISTAKEN, TODO), outcomes);

		assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("0"), Lists.newArrayList(DONE), outcomes);
		assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("1"), Lists.newArrayList(TODO, MISTAKES, LASTMISTAKEN, ERRORS), outcomes);
	}

	@Ignore("Functionality not implemented yet")
	@Test
	public void shouldRecognizeCorrectOrderOfAnswers() throws Exception {
		// given
		Response response = new ResponseBuilder()
				.withCardinality(Cardinality.ORDERED)
				.withCorrectAnswers("firstAnswer", "secondAnswer")
				.withCurrentUserAnswers("firstAnswer", "secondAnswer")
				.build();

		Map<String, Response> responsesMap = convertToMap(response);
		Map<String, Outcome> outcomes = prepareInitialOutcomes(responsesMap);

		// when
		defaultVariableProcessor.processResponseVariables(responsesMap, outcomes, processingMode);

		// then
		assertGlobalOutcomesHaveValue(Lists.newArrayList("1"), Lists.newArrayList(DONE, TODO), outcomes);
		assertGlobalOutcomesHaveValue(Lists.newArrayList("0"), Lists.newArrayList(ERRORS, MISTAKES, LASTMISTAKEN), outcomes);

		assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("1"), Lists.newArrayList(DONE, TODO), outcomes);
		assertResponseRelatedOutcomesHaveValue(response, Lists.newArrayList("0"), Lists.newArrayList(MISTAKES, LASTMISTAKEN, ERRORS), outcomes);
	}
	
}
