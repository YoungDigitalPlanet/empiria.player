package eu.ydp.empiria.player.client.controller;

import com.google.common.base.Optional;
import eu.ydp.empiria.player.client.controller.data.AssessmentDataSourceManager;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.inject.ScriptInjectorWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GtmLoaderTest {

    @InjectMocks
    GtmLoader testObj;
    @Mock
    EmpiriaPaths empiriaPaths;
    @Mock
    ScriptInjectorWrapper scriptInjectorWrapper;
    @Mock
    AssessmentDataSourceManager assessmentDataSourceManager;

    @Test
    public void shouldLoadGtm_whenAttributeIsPresent() {
        // given
        String expectedURL = "http://example.com/commons/gtm.js";
        String attributeValue = "anything";
        when(assessmentDataSourceManager.getAssessmentGtm()).thenReturn(Optional.of(attributeValue));

        String gtmFileName = "gtm.js";
        when(empiriaPaths.getCommonsFilePath(gtmFileName)).thenReturn(expectedURL);

        // when
        testObj.loadGtm();

        // then
        verify(scriptInjectorWrapper).fromUrl(expectedURL);
    }

    @Test
    public void shouldNotLoadGtm_whenAttributeIsAbsent() {
        // given
        Optional<String> missingAttribute = Optional.absent();
        when(assessmentDataSourceManager.getAssessmentGtm()).thenReturn(missingAttribute);

        // when
        testObj.loadGtm();

        // then
        verifyZeroInteractions(scriptInjectorWrapper);
    }
}
