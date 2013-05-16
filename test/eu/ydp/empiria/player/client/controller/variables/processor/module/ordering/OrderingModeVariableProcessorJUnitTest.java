package eu.ydp.empiria.player.client.controller.variables.processor.module.ordering;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;

public class OrderingModeVariableProcessorJUnitTest {

	private OrderingModeVariableProcessor orderingModeVariableProcessor;

	@Before
	public void before() {
		orderingModeVariableProcessor = new OrderingModeVariableProcessor();
	}

	@Test
	public void calculateErrorsTest() {
		assertEquals(0, orderingModeVariableProcessor.calculateErrors(null));
	}

	@Test
	public void calculateMistakesTest() {
		assertEquals(0, orderingModeVariableProcessor.calculateMistakes(false, 0));
	}

	@Test
	public void checkLastmistakenTest() {
		assertFalse(orderingModeVariableProcessor.checkLastmistaken(null, null));
	}

	@Test
	public void calculateDoneTest_correctSolution() {
		ResponseBuilder responseBuilder = new ResponseBuilder();
		responseBuilder.withCurrentUserAnswers(new String[] { "a", "b", "b", "c", "a" });
		responseBuilder.withCorrectAnswers(new String[] { "a", "b", "b", "c", "a" });

		int calculateDone = orderingModeVariableProcessor.calculateDone(responseBuilder.build());

		assertEquals(1, calculateDone);
	}

	@Test
	public void calculateDoneTest_wrongSolution() {
		ResponseBuilder responseBuilder = new ResponseBuilder();
		responseBuilder.withCurrentUserAnswers(new String[] { "a", "b", "c", "b", "a" });
		responseBuilder.withCorrectAnswers(new String[] { "a", "b", "b", "c", "a" });

		int calculateDone = orderingModeVariableProcessor.calculateDone(responseBuilder.build());

		assertEquals(0, calculateDone);
	}

	@Test
	public void evaluateAnswersTest() {

		ResponseBuilder responseBuilder = new ResponseBuilder();
		responseBuilder.withCurrentUserAnswers(new String[] { "a", "b", "c", "b", "a" });
		responseBuilder.withCorrectAnswers(new String[] { "a", "b", "b", "c", "a" });

		List<Boolean> result = orderingModeVariableProcessor.evaluateAnswers(responseBuilder.build());

		assertEquals(5, result.size());
		assertEquals(Boolean.TRUE, result.get(0));
		assertEquals(Boolean.TRUE, result.get(1));
		assertEquals(Boolean.FALSE, result.get(2));
		assertEquals(Boolean.FALSE, result.get(3));
		assertEquals(Boolean.TRUE, result.get(4));
	}
}
