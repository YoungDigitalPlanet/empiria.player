package eu.ydp.empiria.player.client.controller.variables.processor;

import com.google.gwt.thirdparty.guava.common.collect.Maps;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.OutcomeController;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class MistakesInitializerTest {

    private MistakesInitializer mistakesInitializer;

    @Mock
    private ModulesProcessingResults processingResults;
    @Mock
    private OutcomeController outcomeController;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        mistakesInitializer = new MistakesInitializer(processingResults, outcomeController);
    }

    @After
    public void after() {
        verifyNoMoreInteractions(processingResults, outcomeController);
    }

    @Test
    public void initializeTest() {
        // given
        Map<String, Outcome> outcomes = Maps.newHashMap();

        Integer value1 = 1;
        Integer value2 = 2;
        Integer value3 = 3;
        String id1 = "id1";
        String id2 = "id2";
        String id3 = "id3";

        Map<String, Integer> mistakeResponses = Maps.newHashMap();
        mistakeResponses.put(id1, value1);
        mistakeResponses.put(id2, value2);
        mistakeResponses.put(id3, value3);

        DtoModuleProcessingResult dtoModuleProcessingResult1 = DtoModuleProcessingResult.fromDefaultVariables();
        DtoModuleProcessingResult dtoModuleProcessingResult2 = DtoModuleProcessingResult.fromDefaultVariables();
        DtoModuleProcessingResult dtoModuleProcessingResult3 = DtoModuleProcessingResult.fromDefaultVariables();

        when(outcomeController.getAllMistakes(outcomes)).thenReturn(mistakeResponses);
        when(processingResults.getProcessingResultsForResponseId(id1)).thenReturn(dtoModuleProcessingResult1);
        when(processingResults.getProcessingResultsForResponseId(id2)).thenReturn(dtoModuleProcessingResult2);
        when(processingResults.getProcessingResultsForResponseId(id3)).thenReturn(dtoModuleProcessingResult3);

        // when
        mistakesInitializer.initialize(outcomes);

        // then
        InOrder inOrder = inOrder(processingResults, outcomeController);
        inOrder.verify(outcomeController).getAllMistakes(outcomes);
        verify(processingResults).getProcessingResultsForResponseId(id1);
        verify(processingResults).getProcessingResultsForResponseId(id2);
        verify(processingResults).getProcessingResultsForResponseId(id3);

        assertEquals(value1, new Integer(dtoModuleProcessingResult1.getUserInteractionVariables().getMistakes()));
        assertEquals(value2, new Integer(dtoModuleProcessingResult2.getUserInteractionVariables().getMistakes()));
        assertEquals(value3, new Integer(dtoModuleProcessingResult3.getUserInteractionVariables().getMistakes()));
    }
}
