package eu.ydp.empiria.player.client.controller.variables.processor.results;

import eu.ydp.empiria.player.client.controller.variables.processor.results.model.DtoModuleProcessingResult;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Map;
import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;
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
        when(processingResultFactory.createProcessingResultWithInitialValues()).thenReturn(processingResults);

        DtoModuleProcessingResult returnedProcessingResults = modulesProcessingResults.getProcessingResultsForResponseId("responseId");

        assertThat(returnedProcessingResults).isEqualTo(processingResults);
    }

    @Test
    public void shouldReturnPreviousCachedResults() throws Exception {
        String responseId = "responseId";
        DtoModuleProcessingResult firstProcessingResults = modulesProcessingResults.getProcessingResultsForResponseId(responseId);
        DtoModuleProcessingResult secondProcessingResults = modulesProcessingResults.getProcessingResultsForResponseId(responseId);

        assertThat(secondProcessingResults).isEqualTo(firstProcessingResults);
    }

    @Test
    public void shouldReturnIdsOfAllProcessedResponses() throws Exception {
        createProcessingResult("id1");
        createProcessingResult("id2");

        Set<String> ids = modulesProcessingResults.getIdsOfProcessedResponses();

        assertThat(ids).contains("id1", "id2");
    }

    @Test
    public void shouldReturnCollectionOfProcessingResults() throws Exception {
        DtoModuleProcessingResult processingResult = createProcessingResult("id1");
        DtoModuleProcessingResult otherProcessingResult = createProcessingResult("id2");

        Map<String, DtoModuleProcessingResult> listOfProcessingResults = modulesProcessingResults.getMapOfProcessingResults();


        assertThat(listOfProcessingResults.values()).contains(processingResult, otherProcessingResult);
    }

    private DtoModuleProcessingResult createProcessingResult(String responseId) {
        return modulesProcessingResults.getProcessingResultsForResponseId(responseId);
    }

}
