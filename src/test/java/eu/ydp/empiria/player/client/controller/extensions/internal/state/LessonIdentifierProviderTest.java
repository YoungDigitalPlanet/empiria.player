package eu.ydp.empiria.player.client.controller.extensions.internal.state;

import eu.ydp.empiria.player.client.controller.data.AssessmentDataSourceManager;
import eu.ydp.gwtutil.client.Base64Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LessonIdentifierProviderTest {

    @InjectMocks
    private LessonIdentifierProvider testObj;
    @Mock
    private AssessmentDataSourceManager assessmentDataSourceManager;
    @Mock
    private Base64Util base64Util;

    @Test
    public void shouldReturnAssessmentTitleEncodedWithBase64() throws Exception {
        // given
        String title = "some title";
        String base64Title = "base64FromTitle";
        when(assessmentDataSourceManager.getAssessmentTitle()).thenReturn(title);
        when(base64Util.encode(title)).thenReturn(base64Title);

        // when
        String lessonIdentifier = testObj.getLessonIdentifier();

        // then
        assertThat(lessonIdentifier).isEqualTo(base64Title);
    }
}