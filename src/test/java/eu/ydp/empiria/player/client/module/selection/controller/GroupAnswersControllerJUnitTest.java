package eu.ydp.empiria.player.client.module.selection.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.controller.answers.SelectionAnswerQueueFactory;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import java.util.*;
import org.junit.*;

@SuppressWarnings("PMD")
public class GroupAnswersControllerJUnitTest {

    private GroupAnswersController testObj;
    private boolean isMulti = true;
    private int maxSelected = 1;
    private SelectionModuleModel responseModel = mock(SelectionModuleModel.class);
    private SelectionAnswerQueueFactory answerQueueFactory = mock(SelectionAnswerQueueFactory.class);
    private NoAnswerPriorityComparator noAnswerPriorityComparator = mock(NoAnswerPriorityComparator.class);

    @Before
    public void setUp() throws Exception {
        when(answerQueueFactory.createAnswerQueue(isMulti, maxSelected)).thenReturn(new PriorityQueue<>(maxSelected, noAnswerPriorityComparator));
        testObj = new GroupAnswersController(isMulti, maxSelected, responseModel, answerQueueFactory);
    }

    @Test
    public void shouldCreateAnswerQueue_whenInit() throws Exception {
        verify(answerQueueFactory).createAnswerQueue(isMulti, maxSelected);
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
        String answerId = "answerId";
        SelectionAnswerDto answer = new SelectionAnswerDto(answerId);
        testObj.addSelectionAnswer(answer);

        // when
        testObj.selectToggleAnswer(answerId);

        // then
        verify(responseModel).addAnswer(answerId);
        assertEquals(true, answer.isSelected());

        List<SelectionAnswerDto> selectedButtons = testObj.getSelectedAnswers();
        assertTrue(selectedButtons.contains(answer));
    }

    @Test
    public void shouldDeselectAnswer_whenLimitReached() throws Exception {
        // given
        String answerId = "answerId";
        String answerId2 = "answerId2";

        SelectionAnswerDto answer = new SelectionAnswerDto(answerId);
        SelectionAnswerDto answer2 = new SelectionAnswerDto(answerId2);

        testObj.addSelectionAnswer(answer);
        testObj.addSelectionAnswer(answer2);
        testObj.selectToggleAnswer(answerId);

        // when
        testObj.selectToggleAnswer(answerId2);

        // then
        verify(responseModel).removeAnswer(answerId);
        verify(responseModel).addAnswer(answerId2);
        assertEquals(true, answer2.isSelected());

        List<SelectionAnswerDto> selectedButtons = testObj.getSelectedAnswers();
        assertEquals(1, selectedButtons.size());
        assertTrue(selectedButtons.contains(answer2));
    }

    @Test
    public void shouldUnselectAnswer() throws Exception {
        // given
        String answerId = "answerId";
        SelectionAnswerDto selectionAnswerDto = createAnswer(answerId, true);
        SelectionAnswerDto otherAnswer = createAnswer("otherId", true);

        testObj.addSelectionAnswer(otherAnswer);
        testObj.addSelectionAnswer(selectionAnswerDto);

        // when
        testObj.selectToggleAnswer(answerId);

        // then
        assertEquals(false, selectionAnswerDto.isSelected());
        assertEquals(true, otherAnswer.isSelected());
        verify(responseModel).removeAnswer(answerId);
        assertTrue(!testObj.getSelectedAnswers().contains(selectionAnswerDto));
    }

    @Test
    public void shouldSelectAnswer() throws Exception {
        // given
        String answerId = "answerId";
        SelectionAnswerDto selectionAnswerDto = createAnswer(answerId, false);
        SelectionAnswerDto otherAnswer = createAnswer("otherId", false);

        testObj.addSelectionAnswer(otherAnswer);
        testObj.addSelectionAnswer(selectionAnswerDto);

        // when
        testObj.selectToggleAnswer(answerId);

        // then
        assertEquals(true, selectionAnswerDto.isSelected());
        assertEquals(false, otherAnswer.isSelected());
        verify(responseModel).addAnswer(answerId);
        assertTrue(testObj.getSelectedAnswers().contains(selectionAnswerDto));
    }

    @Test
    public void testSelectToggleAnswer_notRelatedButton() throws Exception {
        // given
        String answerId = "answerId";
        SelectionAnswerDto otherAnswer = new SelectionAnswerDto("otherId");
        otherAnswer.setSelected(false);
        testObj.addSelectionAnswer(otherAnswer);

        // when
        testObj.selectToggleAnswer(answerId);

        // then
        verifyZeroInteractions(responseModel);
    }

    @Test
    public void shouldResetAnswers() throws Exception {
        // given
        SelectionAnswerDto answer = createAnswer("id1", true);
        SelectionAnswerDto answer2 = createAnswer("id2", true);

        testObj.addSelectionAnswer(answer);
        testObj.addSelectionAnswer(answer2);
        testObj.selectToggleAnswer("id1");
        testObj.selectToggleAnswer("id2");

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

        SelectionAnswerDto answerToSelect = createAnswer(answerToSelectId, false);
        SelectionAnswerDto answerAlreadySelected = createAnswer(answerAlreadySelectedId, true);
        SelectionAnswerDto answerToDeselect = createAnswer("answerToDeselectId", true);

        testObj.addSelectionAnswer(answerToSelect);
        testObj.addSelectionAnswer(answerAlreadySelected);
        testObj.addSelectionAnswer(answerToDeselect);

        testObj.selectToggleAnswer("answerToDeselectId");
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
        SelectionAnswerDto selectedAnswer = new SelectionAnswerDto("id1");
        SelectionAnswerDto notSelectedAnswer = new SelectionAnswerDto("id2");

        testObj.addSelectionAnswer(selectedAnswer);
        testObj.addSelectionAnswer(notSelectedAnswer);

        // when
        testObj.selectToggleAnswer("id1");

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

