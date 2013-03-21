package eu.ydp.empiria.player.client.controller.variables.processor.results;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.Matchers.*;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.Assert.*;

import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import static org.mockito.Mockito.when;


public class ModulesProcessingResultsJUnitTest {

	private ModulesProcessingResults modulesProcessingResults;
	
	@Before
	public void setUp() throws Exception {
		modulesProcessingResults = new ModulesProcessingResults(new InitialProcessingResultFactory());
	}
	
	@Test
	public void shouldCreateNewResultsWhenPreviousNotCached() throws Exception {
		InitialProcessingResultFactory processingResultFactory = Mockito.mock(InitialProcessingResultFactory.class);
		modulesProcessingResults = new ModulesProcessingResults(processingResultFactory);
		
		DtoModuleProcessingResult processingResults = Mockito.mock(DtoModuleProcessingResult.class);
		when(processingResultFactory.createProcessingResultWithInitialValues())
			.thenReturn(processingResults);
		
		DtoModuleProcessingResult returnedProcessingResults = modulesProcessingResults.getProcessingResultsForResponseId("responseId");
		
		assertEquals(processingResults, returnedProcessingResults);
	}

	@Test
	public void shouldReturnPreviousCachedResults() throws Exception {
		String responseId = "responseId";
		DtoModuleProcessingResult firstProcessingResults = modulesProcessingResults.getProcessingResultsForResponseId(responseId);
		DtoModuleProcessingResult secondProcessingResults = modulesProcessingResults.getProcessingResultsForResponseId(responseId);
		
		assertEquals(firstProcessingResults, secondProcessingResults);
	}
	
	@Test
	public void shouldReturnIdsOfAllProcessedResponses() throws Exception {
		createProcessingResult("id1");
		createProcessingResult("id2");
		
		Set<String> ids = modulesProcessingResults.getIdsOfProcessedResponses();
		
		assertThat(ids, hasItems("id1", "id2"));
	}
	
	@Test
	public void shouldReturnCollectionOfProcessingResults() throws Exception {
		DtoModuleProcessingResult processingResult = createProcessingResult("id1");
		DtoModuleProcessingResult otherProcessingResult = createProcessingResult("id2");
		
		List<DtoModuleProcessingResult> listOfProcessingResults = modulesProcessingResults.getListOfProcessingResults();
		
		assertThat(listOfProcessingResults, hasItems(processingResult, otherProcessingResult));
	}

	private DtoModuleProcessingResult createProcessingResult(String responseId) {
		return modulesProcessingResults.getProcessingResultsForResponseId(responseId);
	}
	
}