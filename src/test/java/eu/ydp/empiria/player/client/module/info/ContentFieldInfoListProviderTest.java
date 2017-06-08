/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.module.info;

import eu.ydp.empiria.player.client.module.info.handler.*;
import org.junit.Before;
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
    private ContentFieldInfoFactory contentFieldInfoFactory;
    @Mock
    private ItemValueHandler itemValueHandler;
    @Mock
    private ProviderAssessmentValueHandler assessmentValueHandler;
    @Mock
    private TitleValueHandler titleValueHandler;
    @Mock
    private ItemIndexValueHandler itemIndexValueHandler;
    @Mock
    private PageCountValueHandler pageCountValueHandler;
    @Mock
    private AssessmentResultValueHandler assessmentResultValueHandler;
    @Mock
    private ResultValueHandler resultValueHandler;
    @Mock
    private FeedbackValueHandler feedbackValueHandler;
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

    @Before
    public void prepareMocks() {
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

    @Test
    public void testGet() {
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

}
