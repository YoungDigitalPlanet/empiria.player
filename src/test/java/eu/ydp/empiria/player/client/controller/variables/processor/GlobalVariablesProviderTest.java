package eu.ydp.empiria.player.client.controller.variables.processor;

import eu.ydp.empiria.player.client.controller.variables.manager.VariableManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.global.GlobalVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GlobalVariablesProviderTest {

    @InjectMocks
    private GlobalVariablesProvider testObj;

    @Mock
    private GlobalVariablesProcessor globalVariablesProcessor;
    @Mock
    private VariableManager<Response> responseManager;
    @Mock
    private ModulesProcessingResults modulesProcessingResults;
    @Mock
    private Map<String, DtoModuleProcessingResult> mapOfProcessingResults;

    @Test
    public void shouldReturnCalculatedResponses() {
        // given
        GlobalVariables globalVariables = mock(GlobalVariables.class);

        when(modulesProcessingResults.getMapOfProcessingResults()).thenReturn(mapOfProcessingResults);
        when(globalVariablesProcessor.calculateGlobalVariables(mapOfProcessingResults, responseManager)).thenReturn(globalVariables);

        // when
        GlobalVariables actual = testObj.retrieveGlobalVariables(modulesProcessingResults, responseManager);

        // then
        assertThat(actual).isEqualTo(globalVariables);
    }
}
