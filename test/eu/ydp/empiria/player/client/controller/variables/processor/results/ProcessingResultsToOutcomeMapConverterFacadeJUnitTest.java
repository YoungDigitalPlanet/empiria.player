package eu.ydp.empiria.player.client.controller.variables.processor.results;

import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Maps;

import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;

@RunWith(MockitoJUnitRunner.class)
public class ProcessingResultsToOutcomeMapConverterFacadeJUnitTest {

	@Mock
	private ProcessingResultsToOutcomeMapConverterFactory converterFactory;
	@InjectMocks
	private ProcessingResultsToOutcomeMapConverterFacade converterFacade;
	
	@Before
	public void setUp() {
	}
	
	@Test
	public void convert() {
		// given
		HashMap<String, Outcome> outcomesMap = Maps.<String, Outcome>newHashMap();
		ModulesProcessingResults results = mock(ModulesProcessingResults.class);
		GlobalVariables globalVariables = mock(GlobalVariables.class);
		ProcessingResultsToOutcomeMapConverter converter = mock(ProcessingResultsToOutcomeMapConverter.class);
		when(converterFactory.createConverter(anyMap())).thenReturn(converter);
		
		// when
		converterFacade.convert(outcomesMap, results, globalVariables);
		
		// then
		verify(converterFactory).createConverter(outcomesMap);
		verify(converter).updateOutcomeMapByModulesProcessingResults(results);
		verify(converter).updateOutcomeMapWithGlobalVariables(globalVariables);
	}

}
