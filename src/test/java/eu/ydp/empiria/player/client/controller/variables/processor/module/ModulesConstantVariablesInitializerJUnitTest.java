package eu.ydp.empiria.player.client.controller.variables.processor.module;

import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponsesMapBuilder;
import eu.ydp.empiria.player.client.controller.variables.processor.results.InitialProcessingResultFactory;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ModulesConstantVariablesInitializerJUnitTest {

    private ModulesConstantVariablesInitializer constantVariablesInitializer;
    private ModulesProcessingResults modulesProcessingResults;

    @Mock
    private ModuleTodoCalculator moduleTodoCalculator;

    @Before
    public void setUp() throws Exception {
        modulesProcessingResults = new ModulesProcessingResults(new InitialProcessingResultFactory());
        constantVariablesInitializer = new ModulesConstantVariablesInitializer(moduleTodoCalculator);
    }

    @Test
    public void shouldInitializeTodoRelatedToResponse() throws Exception {

        Response response = new ResponseBuilder().withIdentifier("responseId").build();
        ItemResponseManager responses = new ResponsesMapBuilder().buildResponseManager(response);

        int todo = 123;
        when(moduleTodoCalculator.calculateTodoForResponse(response)).thenReturn(todo);

        constantVariablesInitializer.initializeTodoVariables(responses, modulesProcessingResults);

        assertThatTodoForResponseIsInitialized(todo, response);
    }

    private void assertThatTodoForResponseIsInitialized(int todo, Response response) {
        DtoModuleProcessingResult processingResult = modulesProcessingResults.getProcessingResultsForResponseId(response.identifier);
        int currentTodo = processingResult.getConstantVariables().getTodo();
        assertThat(currentTodo, equalTo(todo));
    }

}
