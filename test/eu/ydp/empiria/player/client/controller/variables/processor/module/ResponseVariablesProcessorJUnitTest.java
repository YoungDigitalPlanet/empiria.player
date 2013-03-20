package eu.ydp.empiria.player.client.controller.variables.processor.module;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.response.DtoProcessedResponse;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.controller.variables.processor.ProcessingMode;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.GeneralVariables;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.UserInteractionVariables;

@RunWith(MockitoJUnitRunner.class)
public class ResponseVariablesProcessorJUnitTest {

	private ResponseVariablesProcessor responseVariablesProcessor;

	@Mock
	private VariableProcessorFactory variableProcessorFactory;
	@Mock
	private VariableProcessor variableProcessor;

	private Cardinality cardinality = Cardinality.SINGLE;
	private boolean hasGroups = false;
	private List<String> newUserAnswers = Lists.newArrayList("newUserAnswer");
	private Response currentResponse = new ResponseBuilder().withValues(newUserAnswers)
			.withCardinality(cardinality)
			.build();
	private int previousMistakes = 1;
	private List<Boolean> userAnswersEvaluation = Lists.newArrayList(true, true);


	@Before
	public void setUp() throws Exception {
		responseVariablesProcessor = new ResponseVariablesProcessor(variableProcessorFactory);
	}

	@Test
	public void shouldProcessVariablesInNotUserInteractionMode() throws Exception {
		DtoProcessedResponse processedResponse = prepareInitialProcessedResponse();

		when(variableProcessorFactory.findAppropriateProcessor(cardinality, hasGroups))
			.thenReturn(variableProcessor);

		setUpVariableProcessorCalls();
		
		responseVariablesProcessor.processChangedResponse(processedResponse, ProcessingMode.NOT_USER_INTERACT);

		assertThatGeneralVariablesAreCalculated(processedResponse);
		assertThatUserInteractionVariablesAreReset(processedResponse);
	}
	
	@Test
	public void shouldProcessVariablesWhenIsUserInteractionMode() throws Exception {
		DtoProcessedResponse processedResponse = prepareInitialProcessedResponse();
		
		when(variableProcessorFactory.findAppropriateProcessor(cardinality, hasGroups))
		.thenReturn(variableProcessor);
		
		setUpVariableProcessorCalls();
		
		responseVariablesProcessor.processChangedResponse(processedResponse, ProcessingMode.USER_INTERACT);
		
		assertThatGeneralVariablesAreCalculated(processedResponse);
		assertThatUserInteractionVariablesAreCalculated(processedResponse);
	}

	private void assertThatUserInteractionVariablesAreCalculated(DtoProcessedResponse processedResponse) {
		DtoModuleProcessingResult processingResult = processedResponse.getPreviousProcessingResult();
		UserInteractionVariables userInteractionVariables = processingResult.getUserInteractionVariables();
		
		assertEquals(true, userInteractionVariables.isLastmistaken());
		assertEquals(previousMistakes+1, userInteractionVariables.getMistakes());
	}

	private void assertThatUserInteractionVariablesAreReset(DtoProcessedResponse processedResponse) {
		DtoModuleProcessingResult processingResult = processedResponse.getPreviousProcessingResult();
		UserInteractionVariables userInteractionVariables = processingResult.getUserInteractionVariables();
		
		assertEquals(false, userInteractionVariables.isLastmistaken());
		assertEquals(false, userInteractionVariables.getLastAnswerChanges().containChanges());
		assertEquals(previousMistakes, userInteractionVariables.getMistakes());   //mistakes cannot be reset, but cannot change from previous mistakes
	}

	private void assertThatGeneralVariablesAreCalculated(DtoProcessedResponse processedResponse) {
		DtoModuleProcessingResult processingResult = processedResponse.getPreviousProcessingResult();
		GeneralVariables generalVariables = processingResult.getGeneralVariables();
		
		assertEquals(1, generalVariables.getDone());
		assertEquals(1, generalVariables.getErrors());
		assertEquals(newUserAnswers, generalVariables.getAnswers());
		assertEquals(userAnswersEvaluation, generalVariables.getAnswersEvaluation());
	}

	private void setUpVariableProcessorCalls() {
		when(variableProcessor.calculateDone(currentResponse))
			.thenReturn(1);
		
		when(variableProcessor.calculateErrors(currentResponse))
			.thenReturn(1);
		
		when(variableProcessor.calculateMistakes(true, previousMistakes))
			.thenReturn(previousMistakes+1);
		
		when(variableProcessor.checkLastmistaken(eq(currentResponse), Mockito.any(LastAnswersChanges.class)))
			.thenReturn(true);
		
		when(variableProcessor.evaluateAnswers(currentResponse))
			.thenReturn(userAnswersEvaluation );
	}

	private DtoProcessedResponse prepareInitialProcessedResponse() {
		DtoModuleProcessingResult previousProcessingResult = DtoModuleProcessingResult.fromDefaultVariables();
		previousProcessingResult.getUserInteractionVariables().setMistakes(previousMistakes);
		LastAnswersChanges lastAnswerChanges = new LastAnswersChanges(new ArrayList<String>(), new ArrayList<String>());
		DtoProcessedResponse processedResponse = new DtoProcessedResponse(currentResponse, previousProcessingResult, lastAnswerChanges);
		return processedResponse;
	}

}
