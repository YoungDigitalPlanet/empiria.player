package eu.ydp.empiria.player.client.controller.variables.processor.module;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.DtoProcessedResponse;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.controller.variables.processor.ProcessingMode;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ModulesVariablesProcessorJUnitTest {

    private ModulesVariablesProcessor modulesVariablesProcessor;

    @Mock
    private ResponseChangesFinder responseChangesFinder;
    @Mock
    private ModulesConstantVariablesInitializer constantVariablesInitializer;
    @Mock
    private ResponseVariablesProcessor responseVariablesProcessor;
    @Mock
    private ModulesProcessingResults processingResults;

    @Mock
    private ItemResponseManager responseManager;
    private ProcessingMode processingMode = ProcessingMode.USER_INTERACT;

    @Before
    public void setUp() throws Exception {
        modulesVariablesProcessor = new ModulesVariablesProcessor(responseChangesFinder, constantVariablesInitializer, responseVariablesProcessor,
                processingResults);
    }

    @After
    public void tearDown() {
        Mockito.verifyNoMoreInteractions(processingResults, responseVariablesProcessor, constantVariablesInitializer, responseChangesFinder);
    }

    @Test
    public void shouldInitializeConstantVariables() throws Exception {
        modulesVariablesProcessor.initialize(responseManager);
        verify(constantVariablesInitializer).initializeTodoVariables(responseManager, processingResults);
    }

    @Test
    public void shouldProcessResponseWithChangesToPreviousState() throws Exception {
        DtoProcessedResponse processedResponse = createProcessedResponseWithChanges();
        List<DtoProcessedResponse> changedResponses = Lists.newArrayList(processedResponse);

        when(responseChangesFinder.findChangesOfAnswers(processingResults, responseManager)).thenReturn(changedResponses);

        modulesVariablesProcessor.processVariablesForResponses(responseManager, processingMode);

        verify(responseChangesFinder).findChangesOfAnswers(processingResults, responseManager);
        verify(responseVariablesProcessor).processChangedResponse(processedResponse, processingMode);
    }

    @Test
    public void shouldProcessResponseWithoutAnyChangesToPreviousState() throws Exception {
        DtoProcessedResponse processedResponse = createProcessedResponseWithoutChanges();
        List<DtoProcessedResponse> changedResponses = Lists.newArrayList(processedResponse);

        when(responseChangesFinder.findChangesOfAnswers(processingResults, responseManager)).thenReturn(changedResponses);

        modulesVariablesProcessor.processVariablesForResponses(responseManager, processingMode);

        verify(responseChangesFinder).findChangesOfAnswers(processingResults, responseManager);
        verify(responseVariablesProcessor).resetLastUserInteractionVariables(processedResponse.getPreviousProcessingResult().getUserInteractionVariables());
    }

    private DtoProcessedResponse createProcessedResponseWithoutChanges() {
        List<String> changes = Lists.newArrayList(); // no changes
        return createSampleProcessedResponseWithChangesToPreviousState(changes);
    }

    private DtoProcessedResponse createProcessedResponseWithChanges() {
        List<String> changes = Lists.newArrayList("change");
        return createSampleProcessedResponseWithChangesToPreviousState(changes);
    }

    private DtoProcessedResponse createSampleProcessedResponseWithChangesToPreviousState(List<String> changes) {
        Response currentResponse = new ResponseBuilder().build();
        DtoModuleProcessingResult previousProcessingResult = DtoModuleProcessingResult.fromDefaultVariables();
        LastAnswersChanges lastAnswerChanges = new LastAnswersChanges(changes, changes);
        DtoProcessedResponse dtoProcessedResponse = new DtoProcessedResponse(currentResponse, previousProcessingResult, lastAnswerChanges);
        return dtoProcessedResponse;
    }
}
