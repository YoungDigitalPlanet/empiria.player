package eu.ydp.empiria.player.client.module.selection.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.controller.answers.AnswerQueueFactory;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import java.util.*;
import org.junit.*;

@SuppressWarnings("PMD")
public class GroupAnswersControllerJUnitTest {

    private GroupAnswersController testObj;
    private boolean isMulti = true;
    private int maxSelected = 1;
    private SelectionModuleModel responseModel;
    private AnswerQueueFactory answerQueueFactory = mock(AnswerQueueFactory.class);
    private NoAnswerPriorityComparator noAnswerPriorityComparator = mock(NoAnswerPriorityComparator.class);

    @Before
    public void setUp() throws Exception {
        responseModel = mock(SelectionModuleModel.class);
        when(answerQueueFactory.createAnswerQueue(isMulti, maxSelected)).thenReturn(new PriorityQueue<>(maxSelected, noAnswerPriorityComparator));
        testObj = createGroupChoicesController(isMulti, maxSelected, responseModel);
    }

    private GroupAnswersController createGroupChoicesController(boolean isMulti, int maxSelected, SelectionModuleModel responseModel) {
        return new GroupAnswersController(isMulti, maxSelected, responseModel, answerQueueFactory);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testCorrectQueueSetUp() throws Exception {
        testObj = createGroupChoicesController(true, 10, responseModel);

        verify(answerQueueFactory).createAnswerQueue(true, 10);
    }

    @Test
    public void testAddSelectionAnswer() {
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
    public void testSelectAnswer_selectedLimitNotReached() {
        String answerId = "answerId";
        SelectionAnswerDto answer = new SelectionAnswerDto(answerId);
        testObj.addSelectionAnswer(answer);

        // when
        testObj.selectAnswer(answer);

        // then
        verify(responseModel).addAnswer(answerId);
        assertEquals(true, answer.isSelected());

        List<SelectionAnswerDto> selectedButtons = testObj.getSelectedAnswers();
        assertTrue(selectedButtons.contains(answer));
    }

    @Test
    public void testSelectAnswer_selectedLimitReached() throws Exception {
        String answerId = "answerId";
        SelectionAnswerDto answer = new SelectionAnswerDto(answerId);

        String answerId2 = "answerId2";
        SelectionAnswerDto answer2 = new SelectionAnswerDto(answerId2);

        testObj.addSelectionAnswer(answer);
        testObj.addSelectionAnswer(answer2);

        testObj.selectAnswer(answer);

        // when
        testObj.selectAnswer(answer2);

        // then
        verify(responseModel).removeAnswer(answerId);
        verify(responseModel).addAnswer(answerId2);
        assertEquals(true, answer2.isSelected());

        List<SelectionAnswerDto> selectedButtons = testObj.getSelectedAnswers();
        assertEquals(1, selectedButtons.size());
        assertTrue(selectedButtons.contains(answer2));
    }

    @Test
    public void testSelectAnswer_selectedNotRelatedButton() {
        String answerId = "answerId";
        SelectionAnswerDto answer = new SelectionAnswerDto(answerId);

        // when
        testObj.selectAnswer(answer);
    }

    @Test
    public void testUnselectAnswer() throws Exception {
        String answerId = "answerId";
        SelectionAnswerDto selectionAnswer = new SelectionAnswerDto(answerId);
        selectionAnswer.setSelected(true);

        testObj.addSelectionAnswer(selectionAnswer);
        testObj.selectAnswer(selectionAnswer);
        assertEquals(1, testObj.getSelectedAnswers().size());

        testObj.unselectAnswer(selectionAnswer);

        assertEquals(false, selectionAnswer.isSelected());
        assertEquals(0, testObj.getSelectedAnswers().size());
        verify(responseModel).removeAnswer(answerId);
    }

    @Test
    public void testSelectToggleAnswer_unselect() throws Exception {
        String answerId = "answerId";
        SelectionAnswerDto selectionAnswerDto = new SelectionAnswerDto(answerId);
        selectionAnswerDto.setSelected(true);

        SelectionAnswerDto otherAnswer = new SelectionAnswerDto("otherId");
        otherAnswer.setSelected(true);

        testObj.addSelectionAnswer(otherAnswer);
        testObj.addSelectionAnswer(selectionAnswerDto);

        testObj.selectToggleAnswer(answerId);

        assertEquals(false, selectionAnswerDto.isSelected());
        assertEquals(true, otherAnswer.isSelected());
        verify(responseModel).removeAnswer(answerId);
        assertTrue(!testObj.getSelectedAnswers().contains(selectionAnswerDto));
    }

    @Test
    public void testSelectToggleAnswer_select() throws Exception {
        String answerId = "answerId";
        SelectionAnswerDto selectionAnswerDto = new SelectionAnswerDto(answerId);
        selectionAnswerDto.setSelected(false);

        SelectionAnswerDto otherAnswer = new SelectionAnswerDto("otherId");
        otherAnswer.setSelected(false);

        testObj.addSelectionAnswer(otherAnswer);
        testObj.addSelectionAnswer(selectionAnswerDto);

        testObj.selectToggleAnswer(answerId);

        assertEquals(true, selectionAnswerDto.isSelected());
        assertEquals(false, otherAnswer.isSelected());
        verify(responseModel).addAnswer(answerId);
        assertTrue(testObj.getSelectedAnswers().contains(selectionAnswerDto));
    }

    @Test
    public void testSelectToggleAnswer_notRelatedButton() throws Exception {
        String answerId = "answerId";
        SelectionAnswerDto otherAnswer = new SelectionAnswerDto("otherId");
        otherAnswer.setSelected(false);
        testObj.addSelectionAnswer(otherAnswer);

        testObj.selectToggleAnswer(answerId);
    }

    @Test
    public void testReset() throws Exception {
        SelectionAnswerDto answer = new SelectionAnswerDto("id1");
        answer.setSelected(true);

        SelectionAnswerDto answer2 = new SelectionAnswerDto("id2");
        answer2.setSelected(true);

        testObj.addSelectionAnswer(answer);
        testObj.addSelectionAnswer(answer2);
        testObj.selectAnswer(answer);
        testObj.selectAnswer(answer2);

        testObj.reset();

        assertEquals(false, answer.isSelected());
        assertEquals(false, answer2.isSelected());
        assertEquals(0, testObj.getSelectedAnswers().size());
    }

    @Test
    public void testSelectOnlyAnswersMatchingIds() throws Exception {
        String answerToSelectId = "answerToSelectId";
        String answerAlreadySelectedId = "answerSelectedId";

        SelectionAnswerDto answerToSelect = createAnswer(answerToSelectId, false);
        SelectionAnswerDto answerAlreadySelected = createAnswer(answerAlreadySelectedId, true);
        SelectionAnswerDto answerToDeselect = createAnswer("answerToDeselectId", true);

        testObj.addSelectionAnswer(answerToSelect);
        testObj.addSelectionAnswer(answerAlreadySelected);
        testObj.addSelectionAnswer(answerToDeselect);

        testObj.selectAnswer(answerToDeselect);
        testObj.selectAnswer(answerToSelect);

        List<String> idsOfAnswersToSelect = Lists.newArrayList(answerToSelectId, answerAlreadySelectedId);
        testObj.selectOnlyAnswersMatchingIds(idsOfAnswersToSelect);

        assertEquals(2, testObj.getSelectedAnswers().size());
        assertTrue(testObj.getSelectedAnswers().contains(answerToSelect));
        assertTrue(testObj.getSelectedAnswers().contains(answerAlreadySelected));

        assertEquals(true, answerToSelect.isSelected());
        assertEquals(true, answerAlreadySelected.isSelected());
        assertEquals(false, answerToDeselect.isSelected());
    }

    private SelectionAnswerDto createAnswer(String id, boolean selected) {
        SelectionAnswerDto answer = new SelectionAnswerDto();
        answer.setId(id);
        answer.setSelected(selected);
        return answer;
    }

    @Test
    public void testSetLockedAllAnswers() {
        SelectionAnswerDto answer = new SelectionAnswerDto();
        answer.setLocked(false);

        SelectionAnswerDto answer2 = new SelectionAnswerDto();
        answer2.setLocked(false);

        testObj.addSelectionAnswer(answer);
        testObj.addSelectionAnswer(answer2);

        testObj.setLockedAllAnswers(true);

        assertEquals(true, answer.isLocked());
        assertEquals(true, answer2.isLocked());
    }

    @Test
    public void testGetNotSelectedAnswers() throws Exception {
        SelectionAnswerDto selectedAnswer = new SelectionAnswerDto();
        SelectionAnswerDto notSelectedAnswer = new SelectionAnswerDto();

        testObj.addSelectionAnswer(selectedAnswer);
        testObj.addSelectionAnswer(notSelectedAnswer);

        testObj.selectAnswer(selectedAnswer);

        List<SelectionAnswerDto> notSelectedAnswers = testObj.getNotSelectedAnswers();
        assertEquals(1, notSelectedAnswers.size());

        assertTrue(notSelectedAnswers.contains(notSelectedAnswer));
    }
}

