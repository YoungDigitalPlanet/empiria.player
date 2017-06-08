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

package eu.ydp.empiria.player.client.module.selection.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import java.util.List;
import org.junit.*;

@SuppressWarnings("PMD")
public class GroupAnswersControllerJUnitTest {

    private GroupAnswersController testObj;
    private int maxSelected = 1;
    private SelectionModuleModel responseModel = mock(SelectionModuleModel.class);
    private NoAnswerPriorityComparator noAnswerPriorityComparator = mock(NoAnswerPriorityComparator.class);

    private final String ANSWER_ID1 = "id1";
    private final String ANSWER_ID2 = "id2";

    @Before
    public void setUp() throws Exception {
        testObj = new GroupAnswersController(maxSelected, responseModel, noAnswerPriorityComparator);
    }

    @Test
    public void shouldAddAnswersToSelect() {
        SelectionAnswerDto answer = new SelectionAnswerDto();
        SelectionAnswerDto answer2 = new SelectionAnswerDto();

        assertEquals(0, testObj.getAllAnswers().size());

        testObj.addSelectionAnswer(answer);
        assertEquals(1, testObj.getAllAnswers().size());
        assertTrue(testObj.getAllAnswers().contains(answer));

        testObj.addSelectionAnswer(answer2);
        assertEquals(2, testObj.getAllAnswers().size());
        assertTrue(testObj.getAllAnswers().contains(answer));
        assertTrue(testObj.getAllAnswers().contains(answer2));
    }

    @Test
    public void shouldSelectNextAnswer_whenLimitReached() {
        // given
        SelectionAnswerDto answer = new SelectionAnswerDto(ANSWER_ID1);
        testObj.addSelectionAnswer(answer);

        // when
        testObj.selectToggleAnswer(ANSWER_ID1);

        // then
        verify(responseModel).addAnswer(ANSWER_ID1);
        assertEquals(true, answer.isSelected());

        List<SelectionAnswerDto> selectedButtons = testObj.getSelectedAnswers();
        assertTrue(selectedButtons.contains(answer));
    }

    @Test
    public void shouldDeselectAnswer_whenLimitReached() throws Exception {
        // given
        SelectionAnswerDto answer = new SelectionAnswerDto(ANSWER_ID1);
        SelectionAnswerDto answer2 = new SelectionAnswerDto(ANSWER_ID2);

        testObj.addSelectionAnswer(answer);
        testObj.addSelectionAnswer(answer2);
        testObj.selectToggleAnswer(ANSWER_ID1);

        // when
        testObj.selectToggleAnswer(ANSWER_ID2);

        // then
        verify(responseModel).removeAnswer(ANSWER_ID1);
        verify(responseModel).addAnswer(ANSWER_ID2);
        assertEquals(true, answer2.isSelected());

        List<SelectionAnswerDto> selectedButtons = testObj.getSelectedAnswers();
        assertEquals(1, selectedButtons.size());
        assertTrue(selectedButtons.contains(answer2));
    }

    @Test
    public void shouldUnselectAnswer() throws Exception {
        // given
        SelectionAnswerDto selectionAnswerDto = createAnswer(ANSWER_ID1, true);
        SelectionAnswerDto otherAnswer = createAnswer(ANSWER_ID2, true);

        testObj.addSelectionAnswer(otherAnswer);
        testObj.addSelectionAnswer(selectionAnswerDto);

        // when
        testObj.selectToggleAnswer(ANSWER_ID1);

        // then
        assertEquals(false, selectionAnswerDto.isSelected());
        assertEquals(true, otherAnswer.isSelected());
        verify(responseModel).removeAnswer(ANSWER_ID1);
        assertTrue(!testObj.getSelectedAnswers().contains(selectionAnswerDto));
    }

    @Test
    public void shouldSelectAnswer() throws Exception {
        //
        SelectionAnswerDto selectionAnswerDto = createAnswer(ANSWER_ID1, false);
        SelectionAnswerDto otherAnswer = createAnswer(ANSWER_ID2, false);

        testObj.addSelectionAnswer(otherAnswer);
        testObj.addSelectionAnswer(selectionAnswerDto);

        // when
        testObj.selectToggleAnswer(ANSWER_ID1);

        // then
        assertEquals(true, selectionAnswerDto.isSelected());
        assertEquals(false, otherAnswer.isSelected());
        verify(responseModel).addAnswer(ANSWER_ID1);
        assertTrue(testObj.getSelectedAnswers().contains(selectionAnswerDto));
    }

    @Test
    public void testSelectToggleAnswer_notRelatedButton() throws Exception {
        // given
        SelectionAnswerDto otherAnswer = new SelectionAnswerDto(ANSWER_ID2);
        otherAnswer.setSelected(false);
        testObj.addSelectionAnswer(otherAnswer);

        // when
        testObj.selectToggleAnswer(ANSWER_ID1);

        // then
        verifyZeroInteractions(responseModel);
    }

    @Test
    public void shouldResetAnswers() throws Exception {
        // given
        SelectionAnswerDto answer = createAnswer(ANSWER_ID2, true);
        SelectionAnswerDto answer2 = createAnswer(ANSWER_ID1, true);

        testObj.addSelectionAnswer(answer);
        testObj.addSelectionAnswer(answer2);
        testObj.selectToggleAnswer(ANSWER_ID2);
        testObj.selectToggleAnswer(ANSWER_ID1);

        // when
        testObj.reset();

        // then
        assertEquals(false, answer.isSelected());
        assertEquals(false, answer2.isSelected());
        assertEquals(0, testObj.getSelectedAnswers().size());
    }

    @Test
    public void shouldGetAnswers_withGivenIds() throws Exception {
        // given
        String answerToSelectId = "answerToSelectId";
        String answerAlreadySelectedId = "answerSelectedId";
        String deselectedAnswerId = "answerToDeselectId";

        SelectionAnswerDto answerToSelect = createAnswer(answerToSelectId, false);
        SelectionAnswerDto answerAlreadySelected = createAnswer(answerAlreadySelectedId, true);
        SelectionAnswerDto answerToDeselect = createAnswer(deselectedAnswerId, true);

        testObj.addSelectionAnswer(answerToSelect);
        testObj.addSelectionAnswer(answerAlreadySelected);
        testObj.addSelectionAnswer(answerToDeselect);

        testObj.selectToggleAnswer(deselectedAnswerId);
        testObj.selectToggleAnswer(answerToSelectId);

        List<String> idsOfAnswersToSelect = Lists.newArrayList(answerToSelectId, answerAlreadySelectedId);

        // when
        testObj.selectOnlyAnswersMatchingIds(idsOfAnswersToSelect);

        // then
        assertEquals(2, testObj.getSelectedAnswers().size());
        assertTrue(testObj.getSelectedAnswers().contains(answerToSelect));
        assertTrue(testObj.getSelectedAnswers().contains(answerAlreadySelected));

        assertEquals(true, answerToSelect.isSelected());
        assertEquals(true, answerAlreadySelected.isSelected());
        assertEquals(false, answerToDeselect.isSelected());
    }

    @Test
    public void shouldLockedAllAnswers() {
        // given
        SelectionAnswerDto answer = createAnswer("", false);
        SelectionAnswerDto answer2 = createAnswer("", false);

        testObj.addSelectionAnswer(answer);
        testObj.addSelectionAnswer(answer2);

        // when
        testObj.setLockedAllAnswers(true);

        // then
        assertEquals(true, answer.isLocked());
        assertEquals(true, answer2.isLocked());
    }

    @Test
    public void shouldGetNotSelectedAnswers() throws Exception {
        // given
        SelectionAnswerDto selectedAnswer = new SelectionAnswerDto(ANSWER_ID1);
        SelectionAnswerDto notSelectedAnswer = new SelectionAnswerDto(ANSWER_ID2);

        testObj.addSelectionAnswer(selectedAnswer);
        testObj.addSelectionAnswer(notSelectedAnswer);

        // when
        testObj.selectToggleAnswer(ANSWER_ID1);

        // then
        List<SelectionAnswerDto> notSelectedAnswers = testObj.getNotSelectedAnswers();
        assertEquals(1, notSelectedAnswers.size());

        assertTrue(notSelectedAnswers.contains(notSelectedAnswer));
    }

    private SelectionAnswerDto createAnswer(String id, boolean selected) {
        SelectionAnswerDto answer = new SelectionAnswerDto();
        answer.setId(id);
        answer.setSelected(selected);
        return answer;
    }
}

