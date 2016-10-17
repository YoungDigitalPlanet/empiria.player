package eu.ydp.empiria.player.client.controller.variables.processor;

import com.google.common.collect.ImmutableMap;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ModulesVariablesProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ProcessingResultsToOutcomeMapConverterFacade;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GlobalVariables;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class VariableProcessingAdapterTest {

    @InjectMocks
    private VariableProcessingAdapter testObj;

    @Mock
    private ModulesVariablesProcessor modulesVariablesProcessor;
    @Mock
    private AnswerEvaluationSupplier answerEvaluationProvider;
    @Mock
    private ProcessingResultsToOutcomeMapConverterFacade resultsToOutcomeMapConverterFacade;
    @Mock
    private GlobalVariablesProvider globalVariablesProvider;

    @Mock
    private ModulesProcessingResults modulesProcessingResults;
    @Mock
    private Map<String, Response> responses;
    @Mock
    private Map<String, Outcome> outcomes;

    private final ProcessingMode processingMode = ProcessingMode.USER_INTERACT;

    @Before
    public void setUp() throws Exception {
        when(modulesVariablesProcessor.processVariablesForResponses(null, processingMode)).thenReturn(modulesProcessingResults);
    }

    @Test
    public void shouldProcessResponsesAndReturnResultConvertedToOldMap() throws Exception {
        // given
        final String ID = "ID";

        Map<String, DtoModuleProcessingResult> processingResults = ImmutableMap.of(ID, DtoModuleProcessingResult.fromDefaultVariables());
        when(modulesProcessingResults.getMapOfProcessingResults()).thenReturn(processingResults);

        GlobalVariables globalVariables = mock(GlobalVariables.class);
        when(globalVariablesProvider.retrieveGlobalVariables(modulesProcessingResults, null)).thenReturn(globalVariables);

        // when
        testObj.processResponseVariables(null, null, processingMode);

        // then
        verify(modulesVariablesProcessor).processVariablesForResponses(null, processingMode);
        verify(resultsToOutcomeMapConverterFacade).convert(null, modulesProcessingResults, globalVariables);
    }

    @Test
    public void shouldUpdateAnswerEvaluationProvider() throws Exception {
        // when
        testObj.processResponseVariables(null, null, processingMode);

        // then
        verify(answerEvaluationProvider).updateModulesProcessingResults(modulesProcessingResults);
    }
}
