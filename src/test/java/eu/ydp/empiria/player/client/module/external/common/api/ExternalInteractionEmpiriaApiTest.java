package eu.ydp.empiria.player.client.module.external.common.api;

import eu.ydp.empiria.player.client.module.external.interaction.ExternalInteractionResponseModel;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalInteractionEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalInteractionStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ExternalInteractionEmpiriaApiTest {

    @InjectMocks
    private ExternalInteractionEmpiriaApi testObj;
    @Mock
    private ExternalInteractionResponseModel responseModel;
    @Captor
    private ArgumentCaptor<String> answersCaptor;

    @Test
    public void shouldReplaceExternalState() {
        // given
        int done = 5;
        int errors = 3;
        ExternalInteractionStatus status = mock(ExternalInteractionStatus.class);
        when(status.getDone()).thenReturn(done);
        when(status.getErrors()).thenReturn(errors);

        List<String> expectedAnswers = Arrays.asList("1", "2", "3", "4", "5", "-1", "-2", "-3");
        int answersSize = expectedAnswers.size();

        // when
        testObj.onResultChange(status);

        // then
        verify(responseModel, times(answersSize)).addAnswer(answersCaptor.capture());
        List<String> resultList = answersCaptor.getAllValues();
        assertThat(resultList).containsAll(expectedAnswers);
    }
}
