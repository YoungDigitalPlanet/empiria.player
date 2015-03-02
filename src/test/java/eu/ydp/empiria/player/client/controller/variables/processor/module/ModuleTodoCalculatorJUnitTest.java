package eu.ydp.empiria.player.client.controller.variables.processor.module;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;

public class ModuleTodoCalculatorJUnitTest {

	private final ModuleTodoCalculator moduleTodoCalculator = new ModuleTodoCalculator();

	@Test
	public void shouldCalculateTodoWhenCountModeSingle() throws Exception {
		Response response = new ResponseBuilder().withCorrectAnswers("first", "second").withCountMode(CountMode.SINGLE).build();

		int calculatedTodo = moduleTodoCalculator.calculateTodoForResponse(response);

		assertThat(calculatedTodo, equalTo(1));
	}

	@Test
	public void shouldCalculateTodoWhenCountModeSingleAndThereIsNoCorrectAnswers() throws Exception {
		Response response = new ResponseBuilder().withCorrectAnswers().withCountMode(CountMode.SINGLE).build();

		int calculatedTodo = moduleTodoCalculator.calculateTodoForResponse(response);

		assertThat(calculatedTodo, equalTo(0));
	}

	@Test
	public void shouldCalculateTodoWhenCountModeCorrectAnswers() throws Exception {
		Response response = new ResponseBuilder().withCorrectAnswers("first", "second").withCountMode(CountMode.CORRECT_ANSWERS).build();

		int calculatedTodo = moduleTodoCalculator.calculateTodoForResponse(response);

		assertThat(calculatedTodo, equalTo(2));
	}

	@Test
	public void shouldCalculateTodoWhenCountModeCorrectAnswersAndThereIsNoCorrectAnswers() throws Exception {
		Response response = new ResponseBuilder().withCorrectAnswers().withCountMode(CountMode.CORRECT_ANSWERS).build();

		int calculatedTodo = moduleTodoCalculator.calculateTodoForResponse(response);

		assertThat(calculatedTodo, equalTo(0));
	}
}
