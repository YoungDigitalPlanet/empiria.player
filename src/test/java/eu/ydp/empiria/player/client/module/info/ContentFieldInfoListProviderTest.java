package eu.ydp.empiria.player.client.module.info;

import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasockets.AssessmentSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.ResultExtractorsFactory;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.module.info.handler.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContentFieldInfoListProviderTest {

    @InjectMocks
    private ContentFieldInfoListProvider testObj;
    @Mock
    private DataSourceDataSupplier dataSourceDataSupplier;
    @Mock
    private SessionDataSupplier sessionDataSupplier;
    @Mock
    private FieldValueHandlerFactory handlerFactory;
    @Mock
    private ContentFieldInfoFactory contentFieldInfoFactory;
    @Mock
    private AssessmentSessionDataSocket assessmentSessionDataSocket;
    @Mock
    private VariableProviderSocket variableProviderSocket;
    @Mock
    private ResultExtractorsFactory resultExtractorsFactory;
    @Mock
    private ResultForPageIndexProvider resultForPageIndexProvider;
    @Mock
    private ContentFieldInfo itemTodoContentFieldInfo;
    @Mock
    private ContentFieldInfo itemDoneContentFieldInfo;
    @Mock
    private ContentFieldInfo itemChecksContentFieldInfo;
    @Mock
    private ContentFieldInfo itemMistakesContentFieldInfo;
    @Mock
    private ContentFieldInfo itemShowAnswersContentFieldInfo;
    @Mock
    private ContentFieldInfo itemResetContentFieldInfo;
    @Mock
    private ContentFieldInfo itemTitleContentFieldInfo;
    @Mock
    private ContentFieldInfo itemIndexContentFieldInfo;
    @Mock
    private ContentFieldInfo itemPageNumContentFieldInfo;
    @Mock
    private ContentFieldInfo itemPageCountContentFieldInfo;
    @Mock
    private ContentFieldInfo testTodoContentFieldInfo;
    @Mock
    private ContentFieldInfo testDoneContentFieldInfo;
    @Mock
    private ContentFieldInfo testChecksContentFieldInfo;
    @Mock
    private ContentFieldInfo testMistakesContentFieldInfo;
    @Mock
    private ContentFieldInfo testShowAnswersContentFieldInfo;
    @Mock
    private ContentFieldInfo testResetContentFieldInfo;
    @Mock
    private ContentFieldInfo testTitleContentFieldInfo;
    @Mock
    private ContentFieldInfo testResultContentFieldInfo;
    @Mock
    private ContentFieldInfo itemFeedbackContentFieldInfo;

    @Test
    public void testGet() {
        // given
        prepareMocks();
        // when
        List<ContentFieldInfo> result = testObj.get();
        // then
        assertEquals(20, result.size());
        assertTrue(result.contains(itemTodoContentFieldInfo));
        assertTrue(result.contains(itemDoneContentFieldInfo));
        assertTrue(result.contains(itemChecksContentFieldInfo));
        assertTrue(result.contains(itemMistakesContentFieldInfo));
        assertTrue(result.contains(itemShowAnswersContentFieldInfo));
        assertTrue(result.contains(itemTitleContentFieldInfo));
        assertTrue(result.contains(itemIndexContentFieldInfo));
        assertTrue(result.contains(itemPageNumContentFieldInfo));
        assertTrue(result.contains(itemResetContentFieldInfo));
        assertTrue(result.contains(testTodoContentFieldInfo));
        assertTrue(result.contains(testDoneContentFieldInfo));
        assertTrue(result.contains(testChecksContentFieldInfo));
        assertTrue(result.contains(testMistakesContentFieldInfo));
        assertTrue(result.contains(testShowAnswersContentFieldInfo));
        assertTrue(result.contains(testResetContentFieldInfo));
        assertTrue(result.contains(testTitleContentFieldInfo));
        assertTrue(result.contains(testResultContentFieldInfo));
        assertTrue(result.contains(itemFeedbackContentFieldInfo));

    }

    private void prepareMocks() {
        when(sessionDataSupplier.getAssessmentSessionDataSocket()).thenReturn(assessmentSessionDataSocket);
        when(assessmentSessionDataSocket.getVariableProviderSocket()).thenReturn(variableProviderSocket);

        ProviderValueHandler itemValueHandler = mock(ProviderValueHandler.class);
        ProviderAssessmentValueHandler assessmentValueHandler = mock(ProviderAssessmentValueHandler.class);
        TitleValueHandler titleValueHandler = mock(TitleValueHandler.class);
        ItemIndexValueHandler itemIndexValueHandler = mock(ItemIndexValueHandler.class);
        PageCountValueHandler pageCountValueHandler = mock(PageCountValueHandler.class);
        AssessmentResultValueHandler assessmentResultValueHandler = mock(AssessmentResultValueHandler.class);
        ResultValueHandler resultValueHandler = mock(ResultValueHandler.class);
        FeedbackValueHandler feedbackValueHandler = mock(FeedbackValueHandler.class);

        when(handlerFactory.getProviderValueHandler(sessionDataSupplier)).thenReturn(itemValueHandler);
        when(handlerFactory.getProviderAssessmentValueHandler(variableProviderSocket)).thenReturn(assessmentValueHandler);
        when(handlerFactory.getTitleValueHandler(dataSourceDataSupplier)).thenReturn(titleValueHandler);
        when(handlerFactory.getItemIndexValueHandler()).thenReturn(itemIndexValueHandler);
        when(handlerFactory.getPageCountValueHandler(dataSourceDataSupplier)).thenReturn(pageCountValueHandler);
        when(handlerFactory.getAssessmentResultValueHandler(variableProviderSocket)).thenReturn(assessmentResultValueHandler);
        when(handlerFactory.getResultValueHandler(sessionDataSupplier)).thenReturn(resultValueHandler);
        when(resultExtractorsFactory.createResultForPageIndexProvider(sessionDataSupplier)).thenReturn(resultForPageIndexProvider);
        when(handlerFactory.getFeedbackValueHandler(resultForPageIndexProvider, dataSourceDataSupplier)).thenReturn(feedbackValueHandler);

        when(contentFieldInfoFactory.create("item.todo", itemValueHandler)).thenReturn(itemTodoContentFieldInfo);
        when(contentFieldInfoFactory.create("item.done", itemValueHandler)).thenReturn(itemDoneContentFieldInfo);
        when(contentFieldInfoFactory.create("item.checks", itemValueHandler)).thenReturn(itemChecksContentFieldInfo);
        when(contentFieldInfoFactory.create("item.mistakes", itemValueHandler)).thenReturn(itemMistakesContentFieldInfo);
        when(contentFieldInfoFactory.create("item.show_answers", itemValueHandler)).thenReturn(itemShowAnswersContentFieldInfo);
        when(contentFieldInfoFactory.create("item.reset", itemValueHandler)).thenReturn(itemResetContentFieldInfo);
        when(contentFieldInfoFactory.create("item.title", titleValueHandler)).thenReturn(itemTitleContentFieldInfo);
        when(contentFieldInfoFactory.create("item.index", itemIndexValueHandler)).thenReturn(itemIndexContentFieldInfo);
        when(contentFieldInfoFactory.create("item.page_num", itemIndexValueHandler)).thenReturn(itemPageNumContentFieldInfo);
        when(contentFieldInfoFactory.create("item.page_count", pageCountValueHandler)).thenReturn(itemPageCountContentFieldInfo);
        when(contentFieldInfoFactory.create("item.result", resultValueHandler)).thenReturn(itemResetContentFieldInfo);
        when(contentFieldInfoFactory.create("test.todo", assessmentValueHandler)).thenReturn(testTodoContentFieldInfo);
        when(contentFieldInfoFactory.create("test.done", assessmentValueHandler)).thenReturn(testDoneContentFieldInfo);
        when(contentFieldInfoFactory.create("test.checks", assessmentValueHandler)).thenReturn(testChecksContentFieldInfo);
        when(contentFieldInfoFactory.create("test.mistakes", assessmentValueHandler)).thenReturn(testMistakesContentFieldInfo);
        when(contentFieldInfoFactory.create("test.show_answers", assessmentValueHandler)).thenReturn(testShowAnswersContentFieldInfo);
        when(contentFieldInfoFactory.create("test.reset", assessmentValueHandler)).thenReturn(testResetContentFieldInfo);
        when(contentFieldInfoFactory.create("test.title", titleValueHandler)).thenReturn(testTitleContentFieldInfo);
        when(contentFieldInfoFactory.create("test.result", assessmentResultValueHandler)).thenReturn(testResultContentFieldInfo);
        when(contentFieldInfoFactory.create("item.feedback", feedbackValueHandler)).thenReturn(itemFeedbackContentFieldInfo);
    }

}
