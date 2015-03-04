package eu.ydp.empiria.player.client.module.selection.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Queue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceButtonBase;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.test.utils.ReflectionsUtils;

@SuppressWarnings("PMD")
public class GroupAnswersControllerJUnitTest {

	private ReflectionsUtils reflectionsUtils = new ReflectionsUtils();

	private GroupAnswersController groupAnswerController;
	private boolean isMulti = true;
	private int maxSelected = 1;
	private SelectionModuleModel responseModel;
	private NoAnswerPriorityComparator noAnswerPriorityComparator = new NoAnswerPriorityComparator();

	@Before
	public void setUp() throws Exception {
		responseModel = mock(SelectionModuleModel.class);

		groupAnswerController = createGroupChoicesController(isMulti, maxSelected, responseModel);
	}

	private GroupAnswersController createGroupChoicesController(boolean isMulti, int maxSelected, SelectionModuleModel responseModel) {
		return new GroupAnswersController(isMulti, maxSelected, responseModel, noAnswerPriorityComparator);
	}

	@After
	public void tearDown() throws Exception {
		Mockito.verifyNoMoreInteractions(responseModel);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCorrectQueueSetUp() throws Exception {
		groupAnswerController = createGroupChoicesController(true, 10, responseModel);
		Queue<SelectionAnswerDto> selectedAnswersQueue = (Queue<SelectionAnswerDto>) reflectionsUtils.getValueFromFiledInObject("selectedAnswers",
				groupAnswerController);
		Object[] queueArray = (Object[]) reflectionsUtils.getValueFromFiledInObject("queue", selectedAnswersQueue);
		assertEquals(10, queueArray.length);

		groupAnswerController = createGroupChoicesController(false, 10, responseModel);
		selectedAnswersQueue = (Queue<SelectionAnswerDto>) reflectionsUtils.getValueFromFiledInObject("selectedAnswers", groupAnswerController);
		queueArray = (Object[]) reflectionsUtils.getValueFromFiledInObject("queue", selectedAnswersQueue);
		assertEquals(1, queueArray.length);
	}

	@Test
	public void testAddSelectionAnswer() {
		SelectionAnswerDto answer = new SelectionAnswerDto();
		SelectionAnswerDto answer2 = new SelectionAnswerDto();

		assertEquals(0, groupAnswerController.getAllAnswers().size());

		groupAnswerController.addSelectionAnswer(answer);
		assertEquals(1, groupAnswerController.getAllAnswers().size());
		assertTrue(groupAnswerController.getAllAnswers().contains(answer));

		groupAnswerController.addSelectionAnswer(answer2);
		assertEquals(2, groupAnswerController.getAllAnswers().size());
		assertTrue(groupAnswerController.getAllAnswers().contains(answer));
		assertTrue(groupAnswerController.getAllAnswers().contains(answer2));
	}

	@Test
	public void testSelectAnswer_selectedLimitNotReached() {
		String answerId = "answerId";
		SelectionAnswerDto answer = new SelectionAnswerDto(answerId);
		groupAnswerController.addSelectionAnswer(answer);

		// when
		groupAnswerController.selectAnswer(answer);

		// then
		verify(responseModel).addAnswer(answerId);
		assertEquals(true, answer.isSelected());

		List<SelectionAnswerDto> selectedButtons = groupAnswerController.getSelectedAnswers();
		assertTrue(selectedButtons.contains(answer));
	}

	@Test
	public void testSelectAnswer_selectedLimitReached() throws Exception {
		String answerId = "answerId";
		SelectionAnswerDto answer = new SelectionAnswerDto(answerId);

		String answerId2 = "answerId2";
		SelectionAnswerDto answer2 = new SelectionAnswerDto(answerId2);

		groupAnswerController.addSelectionAnswer(answer);
		groupAnswerController.addSelectionAnswer(answer2);

		addAnswerToSelectedQueue(answer);

		// when
		groupAnswerController.selectAnswer(answer2);

		// then
		verify(responseModel).removeAnswer(answerId);
		verify(responseModel).addAnswer(answerId2);
		assertEquals(true, answer2.isSelected());

		List<SelectionAnswerDto> selectedButtons = groupAnswerController.getSelectedAnswers();
		assertEquals(1, selectedButtons.size());
		assertTrue(selectedButtons.contains(answer2));
	}

	private void addAnswerToSelectedQueue(SelectionAnswerDto... answers) throws Exception {

		try {
			Queue<SelectionAnswerDto> selectedAnswers = (Queue<SelectionAnswerDto>) reflectionsUtils.getValueFromFiledInObject("selectedAnswers",
					groupAnswerController);
			for (SelectionAnswerDto selectionAnswerDto : answers) {
				selectedAnswers.add(selectionAnswerDto);
			}
		} catch (Exception e) {
			throw new RuntimeException("Reflection failed - cannot find field: 'selectedAnswers' in class: " + GroupAnswersController.class.getName());
		}
	}

	@Test
	public void testSelectAnswer_selectedNotRelatedButton() {
		String answerId = "answerId";
		SelectionAnswerDto answer = new SelectionAnswerDto(answerId);

		// when
		groupAnswerController.selectAnswer(answer);
	}

	private void setQueueObjectInController(Queue<ChoiceButtonBase> queue) {
		try {
			reflectionsUtils.setValueInObjectOnField("selectedAnswers", groupAnswerController, queue);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testUnselectAnswer() throws Exception {
		String answerId = "answerId";
		SelectionAnswerDto selectionAnswer = new SelectionAnswerDto(answerId);
		selectionAnswer.setSelected(true);

		addAnswerToSelectedQueue(selectionAnswer);
		assertEquals(1, groupAnswerController.getSelectedAnswers().size());

		groupAnswerController.unselectAnswer(selectionAnswer);

		assertEquals(false, selectionAnswer.isSelected());
		assertEquals(0, groupAnswerController.getSelectedAnswers().size());
		verify(responseModel).removeAnswer(answerId);
	}

	@Test
	public void testSelectToggleAnswer_unselect() throws Exception {
		String answerId = "answerId";
		SelectionAnswerDto selectionAnswerDto = new SelectionAnswerDto(answerId);
		selectionAnswerDto.setSelected(true);

		SelectionAnswerDto otherAnswer = new SelectionAnswerDto("otherId");
		otherAnswer.setSelected(true);

		groupAnswerController.addSelectionAnswer(otherAnswer);
		groupAnswerController.addSelectionAnswer(selectionAnswerDto);

		groupAnswerController.selectToggleAnswer(answerId);

		assertEquals(false, selectionAnswerDto.isSelected());
		assertEquals(true, otherAnswer.isSelected());
		verify(responseModel).removeAnswer(answerId);
		assertTrue(!groupAnswerController.getSelectedAnswers().contains(selectionAnswerDto));
	}

	@Test
	public void testSelectToggleAnswer_select() throws Exception {
		String answerId = "answerId";
		SelectionAnswerDto selectionAnswerDto = new SelectionAnswerDto(answerId);
		selectionAnswerDto.setSelected(false);

		SelectionAnswerDto otherAnswer = new SelectionAnswerDto("otherId");
		otherAnswer.setSelected(false);

		groupAnswerController.addSelectionAnswer(otherAnswer);
		groupAnswerController.addSelectionAnswer(selectionAnswerDto);

		groupAnswerController.selectToggleAnswer(answerId);

		assertEquals(true, selectionAnswerDto.isSelected());
		assertEquals(false, otherAnswer.isSelected());
		verify(responseModel).addAnswer(answerId);
		assertTrue(groupAnswerController.getSelectedAnswers().contains(selectionAnswerDto));
	}

	@Test
	public void testSelectToggleAnswer_notRelatedButton() throws Exception {
		String answerId = "answerId";
		SelectionAnswerDto otherAnswer = new SelectionAnswerDto("otherId");
		otherAnswer.setSelected(false);
		groupAnswerController.addSelectionAnswer(otherAnswer);

		groupAnswerController.selectToggleAnswer(answerId);
	}

	@Test
	public void testReset() throws Exception {
		SelectionAnswerDto answer = new SelectionAnswerDto("id1");
		answer.setSelected(true);

		SelectionAnswerDto answer2 = new SelectionAnswerDto("id2");
		answer2.setSelected(true);

		addAnswerToSelectedQueue(answer);
		addAnswerToSelectedQueue(answer2);

		groupAnswerController.reset();

		assertEquals(false, answer.isSelected());
		assertEquals(false, answer2.isSelected());
		assertEquals(0, groupAnswerController.getSelectedAnswers().size());
	}

	@Test
	public void testSelectOnlyAnswersMatchingIds() throws Exception {
		String answerToSelectId = "answerToSelectId";
		String answerAlreadySelectedId = "answerSelectedId";

		SelectionAnswerDto answerToSelect = createAnswer(answerToSelectId, false);
		SelectionAnswerDto answerAlreadySelected = createAnswer(answerAlreadySelectedId, true);
		SelectionAnswerDto answerToDeselect = createAnswer("answerToDeselectId", true);

		groupAnswerController.addSelectionAnswer(answerToSelect);
		groupAnswerController.addSelectionAnswer(answerAlreadySelected);
		groupAnswerController.addSelectionAnswer(answerToDeselect);

		addAnswerToSelectedQueue(answerAlreadySelected, answerToDeselect);

		List<String> idsOfAnswersToSelect = Lists.newArrayList(answerToSelectId, answerAlreadySelectedId);
		groupAnswerController.selectOnlyAnswersMatchingIds(idsOfAnswersToSelect);

		assertEquals(2, groupAnswerController.getSelectedAnswers().size());
		assertTrue(groupAnswerController.getSelectedAnswers().contains(answerToSelect));
		assertTrue(groupAnswerController.getSelectedAnswers().contains(answerAlreadySelected));

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

		groupAnswerController.addSelectionAnswer(answer);
		groupAnswerController.addSelectionAnswer(answer2);

		groupAnswerController.setLockedAllAnswers(true);

		assertEquals(true, answer.isLocked());
		assertEquals(true, answer2.isLocked());
	}

	@Test
	public void testGetNotSelectedAnswers() throws Exception {
		SelectionAnswerDto selectedAnswer = new SelectionAnswerDto();
		SelectionAnswerDto notSelectedAnswer = new SelectionAnswerDto();

		addAnswerToSelectedQueue(selectedAnswer);

		groupAnswerController.addSelectionAnswer(selectedAnswer);
		groupAnswerController.addSelectionAnswer(notSelectedAnswer);

		List<SelectionAnswerDto> notSelectedAnswers = groupAnswerController.getNotSelectedAnswers();
		assertEquals(1, notSelectedAnswers.size());
		assertTrue(notSelectedAnswers.contains(notSelectedAnswer));
	}

}
