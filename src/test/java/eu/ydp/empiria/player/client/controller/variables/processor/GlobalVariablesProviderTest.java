package eu.ydp.empiria.player.client.controller.variables.processor;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.global.GlobalVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;

@RunWith(MockitoJUnitRunner.class)
public class GlobalVariablesProviderTest {

	@InjectMocks
	private GlobalVariablesProvider testObj;

	@Mock
	private GlobalVariablesProcessor globalVariablesProcessor;
	@Mock
	private Map<String, Response> responses;
	@Mock
	private ModulesProcessingResults modulesProcessingResults;
	@Mock
	private Map<String, DtoModuleProcessingResult> mapOfProcessingResults;

	@Test
	public void shouldReturnCalculatedResponses() {
		// given
		GlobalVariables globalVariables = mock(GlobalVariables.class);

		when(modulesProcessingResults.getMapOfProcessingResults()).thenReturn(mapOfProcessingResults);
		when(globalVariablesProcessor.calculateGlobalVariables(mapOfProcessingResults, responses)).thenReturn(globalVariables);

		// when
		GlobalVariables actual = testObj.retrieveGlobalVariables(modulesProcessingResults, responses);

		// then
		assertThat(actual).isEqualTo(globalVariables);
	}
}
