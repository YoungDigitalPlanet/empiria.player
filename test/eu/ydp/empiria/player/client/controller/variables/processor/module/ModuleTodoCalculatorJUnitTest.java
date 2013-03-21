package eu.ydp.empiria.player.client.controller.variables.processor.module;

import org.junit.Test;

import eu.ydp.empiria.player.client.controller.variables.objects.response.CountMode;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;

public class ModuleTodoCalculatorJUnitTest {

	private ModuleTodoCalculator moduleTodoCalculator = new ModuleTodoCalculator();

	@Test
	public void shouldCalculateTodoWhenCountModeSingle() throws Exception {
		Response response = new ResponseBuilder().withCountMode(CountMode.SINGLE)
				.build();

		int calculatedTodo = moduleTodoCalculator.calculateTodoForResponse(response);

		assertThat(calculatedTodo, equalTo(1));
	}

	@Test
	public void shouldCalculateTodoWhenCountModeCorrect() throws Exception {
		Response response = new ResponseBuilder().withCorrectAnswers("first", "second")
				.withCountMode(CountMode.CORRECT_ANSWERS)
				.build();

		int calculatedTodo = moduleTodoCalculator.calculateTodoForResponse(response);

		assertThat(calculatedTodo, equalTo(2));
	}

}
