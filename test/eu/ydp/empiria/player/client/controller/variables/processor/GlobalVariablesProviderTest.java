package eu.ydp.empiria.player.client.controller.variables.processor;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.global.GlobalVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import eu.ydp.empiria.player.client.module.IIgnored;
import eu.ydp.empiria.player.client.module.IUniqueModule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
	public void shouldReturnCalculatedResponses_whenSenderIsNotIgnorable() {
		// given
		GlobalVariables globalVariables = mock(GlobalVariables.class);

		when(modulesProcessingResults.getMapOfProcessingResults()).thenReturn(mapOfProcessingResults);
		when(globalVariablesProcessor.calculateGlobalVariables(mapOfProcessingResults, responses)).thenReturn(globalVariables);

		IUniqueModule sender = mock(IUniqueModule.class);

		// when
		GlobalVariables actual = testObj.retrieveGlobalVariables(modulesProcessingResults, responses, sender);

		// then
		assertThat(actual).isEqualTo(globalVariables);
	}

	@Test
	public void shouldReturnEmptyResponses_whenSenderIsIgnored() {
		// given
		IUniqueModule sender = mock(IUniqueModule.class, withSettings().extraInterfaces(IIgnored.class));
		when(((IIgnored) sender).isIgnored()).thenReturn(true);

		// when
		GlobalVariables actual = testObj.retrieveGlobalVariables(modulesProcessingResults, responses, sender);

		// then
		assertThat(actual).isEqualTo(GlobalVariables.createEmpty());
	}

	@Test
	public void shouldReturnCalculatedResponses_whenSenderIsNotIgnored() {
		// given
		GlobalVariables globalVariables = mock(GlobalVariables.class);

		when(modulesProcessingResults.getMapOfProcessingResults()).thenReturn(mapOfProcessingResults);
		when(globalVariablesProcessor.calculateGlobalVariables(mapOfProcessingResults, responses)).thenReturn(globalVariables);

		IUniqueModule sender = mock(IUniqueModule.class, withSettings().extraInterfaces(IIgnored.class));
		when(((IIgnored) sender).isIgnored()).thenReturn(false);

		// when
		GlobalVariables actual = testObj.retrieveGlobalVariables(modulesProcessingResults, responses, sender);

		// then
		assertThat(actual).isEqualTo(globalVariables);
	}
}
