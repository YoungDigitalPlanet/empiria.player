package eu.ydp.empiria.player.client.module.info.handler;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.report.table.extraction.PagesRangeExtractor;
import eu.ydp.empiria.player.client.controller.variables.storage.assessment.AssessmentVariableStorage;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;
import eu.ydp.empiria.player.client.style.ModuleStyle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_INFO_TEST_INCLUDE;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AssessmentResultValueHandlerTest {

    private AssessmentResultValueHandler testObj;
    @Mock
    private ModuleStyle moduleStyle;
    @Mock
    private AssessmentVariableStorage assessmentVariableStorage;
    @Mock
    private PagesRangeExtractor pagesRangeExtractor;
    @Captor
    private ArgumentCaptor<List<Integer>> pagesCaptor;

    @Test
    public void shouldReturnPercentValueFromAllItems_whenStyleDoesNotExist() {
        // given
        when(moduleStyle.containsKey(EMPIRIA_INFO_TEST_INCLUDE)).thenReturn(false);
        testObj = new AssessmentResultValueHandler(moduleStyle, assessmentVariableStorage, pagesRangeExtractor);

        int todo = 5;
        int done = 3;
        String percentExpected = "60";
        when(assessmentVariableStorage.getVariableIntValue("TODO")).thenReturn(todo);
        when(assessmentVariableStorage.getVariableIntValue("DONE")).thenReturn(done);

        // when
        String result = testObj.getValue(mock(ContentFieldInfo.class), 0);

        // then
        assertThat(result).isEqualTo(percentExpected);
    }

    @Test
    public void shouldReturnPercentValueFromGivenPages_whenStyleExists() {
        // given
        String range = "2:5";
        Integer[] pages = {2, 3, 4, 5};
        List<Integer> pagesList = Lists.newArrayList(pages);
        when(moduleStyle.containsKey(EMPIRIA_INFO_TEST_INCLUDE)).thenReturn(true);
        when(pagesRangeExtractor.parseRange(range)).thenReturn(pagesList);
        when(moduleStyle.get(EMPIRIA_INFO_TEST_INCLUDE)).thenReturn(range);

        testObj = new AssessmentResultValueHandler(moduleStyle, assessmentVariableStorage, pagesRangeExtractor);

        int todo = 5;
        int done = 3;
        String percentExpected = "60";
        when(assessmentVariableStorage.getVariableIntValue(eq("TODO"), pagesCaptor.capture())).thenReturn(todo);
        when(assessmentVariableStorage.getVariableIntValue(eq("DONE"), pagesCaptor.capture())).thenReturn(done);

        // when
        String result = testObj.getValue(mock(ContentFieldInfo.class), 0);

        // then
        assertThat(result).isEqualTo(percentExpected);
        List<List<Integer>> capturedPagesList = pagesCaptor.getAllValues();
        assertThat(capturedPagesList.get(0)).containsExactly(pages);
        assertThat(capturedPagesList.get(1)).containsExactly(pages);
    }
}