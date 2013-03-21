package eu.ydp.empiria.player.client.controller.variables.processor.module;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.thirdparty.guava.common.collect.Lists;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.controller.variables.objects.response.DtoProcessedResponse;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponsesMapBuilder;
import eu.ydp.empiria.player.client.controller.variables.processor.results.InitialProcessingResultFactory;
import eu.ydp.empiria.player.client.controller.variables.processor.results.ModulesProcessingResults;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;

@RunWith(MockitoJUnitRunner.class)
public class ResponseChangesFinderJUnitTest {

	private ResponseChangesFinder responseChangesFinder;
	private Map<String, Response> responses;
	private ModulesProcessingResults processingResults;

	@Mock
	private ResponseDifferenceFinder responseDifferenceFinder;

	@Before
	public void setUp() throws Exception {
		responses = new HashMap<String, Response>();
		processingResults = new ModulesProcessingResults(new InitialProcessingResultFactory());
		responseChangesFinder = new ResponseChangesFinder(responseDifferenceFinder);
	}

	@Test
	public void shouldBuildProcessedResponseDtoWithAnswerChanges() throws Exception {
		String responseId = "responseId";

		List<String> previousAnswers = Lists.newArrayList("previousAnswer");
		List<String> currentAnswers = Lists.newArrayList("currentAnswer");
		DtoModuleProcessingResult processingResult = createProcessingResultForIdAndWithPreviousAnswers(responseId, previousAnswers);

		Response response = new ResponseBuilder().withValues(currentAnswers)
				.withIdentifier(responseId)
				.build();
		responses = new ResponsesMapBuilder().buildResponsesMap(response);


		LastAnswersChanges answerChanges = Mockito.mock(LastAnswersChanges.class);
		when(responseDifferenceFinder.findChangesOfAnswers(previousAnswers, currentAnswers))
			.thenReturn(answerChanges);

		List<DtoProcessedResponse> processedResponses = responseChangesFinder.findChangesOfAnswers(processingResults, responses);
		
		assertEquals(1, processedResponses.size());
		DtoProcessedResponse dtoProcessedResponse = processedResponses.get(0);
		assertEquals(response, dtoProcessedResponse.getCurrentResponse());
		assertEquals(answerChanges, dtoProcessedResponse.getLastAnswersChanges());
		assertEquals(processingResult, dtoProcessedResponse.getPreviousProcessingResult());
	}

	private DtoModuleProcessingResult createProcessingResultForIdAndWithPreviousAnswers(String responseId, List<String> previousAnswers) {
		DtoModuleProcessingResult processingResult = processingResults.getProcessingResultsForResponseId(responseId);
		processingResult.getGeneralVariables().setAnswers(previousAnswers);
		return processingResult;
	}

}
