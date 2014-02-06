package eu.ydp.empiria.player.client.module.selection.controller;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.model.SelectionGridElementPosition;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.empiria.player.client.module.selection.view.SelectionElementPositionGenerator;
import eu.ydp.empiria.player.client.module.selection.view.SelectionModuleView;

@RunWith(MockitoJUnitRunner.class)
public class SelectionViewUpdaterJUnitTest {

	private SelectionViewUpdater viewUpdater;
	private SelectionModuleView selectionModuleView;

	@Mock
	private SelectionElementPositionGenerator elementPositionGenerator;

	@Before
	public void setUp() throws Exception {
		viewUpdater = new SelectionViewUpdater(elementPositionGenerator);
		selectionModuleView = mock(SelectionModuleView.class);
	}

	@After
	public void tearDown() throws Exception {
		Mockito.verifyNoMoreInteractions(selectionModuleView);
	}

	@Test
	public void testUpdateView() {
		GroupAnswersController groupController1 = mock(GroupAnswersController.class);
		GroupAnswersController groupController2 = mock(GroupAnswersController.class);
		List<GroupAnswersController> groupChoicesControllers = Lists.newArrayList(groupController1, groupController2);

		List<SelectionAnswerDto> answers1 = Lists.newArrayList(createAnswer("id1", false, true), // should be unselected
				createAnswer("id2", true, false) // should be ignored - not state changed
				);
		when(groupController1.getAllAnswers()).thenReturn(answers1);

		List<SelectionAnswerDto> answers2 = Lists.newArrayList(createAnswer("id3", true, true) // should be selected
				);
		when(groupController2.getAllAnswers()).thenReturn(answers2);

		SelectionGridElementPosition firstUpdatedPostion = new SelectionGridElementPosition(1, 1);
		SelectionGridElementPosition secondUpdatedPostion = new SelectionGridElementPosition(2, 1);
		when(elementPositionGenerator.getButtonElementPositionFor(0, 0)).thenReturn(firstUpdatedPostion);
		when(elementPositionGenerator.getButtonElementPositionFor(1, 0)).thenReturn(secondUpdatedPostion);
		when(elementPositionGenerator.getButtonElementPositionFor(1, 1)).thenReturn(firstUpdatedPostion);
		when(elementPositionGenerator.getButtonElementPositionFor(2, 1)).thenReturn(secondUpdatedPostion);

		// then
		for (GroupAnswersController groupCtrl : groupChoicesControllers) {
			viewUpdater.updateView(selectionModuleView, groupCtrl, groupChoicesControllers.indexOf(groupCtrl));
		}

		SelectionGridElementPosition firstPosition = new SelectionGridElementPosition(1, 1);
		verify(selectionModuleView).unselectButton(firstPosition);
		verify(selectionModuleView).lockButton(firstPosition, false);
		verify(selectionModuleView).updateButtonStyle(firstPosition, UserAnswerType.CORRECT);

		SelectionGridElementPosition secondPosition = new SelectionGridElementPosition(2, 1);
		verify(selectionModuleView).selectButton(secondPosition);
		verify(selectionModuleView).lockButton(secondPosition, false);
		verify(selectionModuleView).updateButtonStyle(secondPosition, UserAnswerType.CORRECT);
	}

	private SelectionAnswerDto createAnswer(String id, boolean isSelected, boolean stateChanged) {
		SelectionAnswerDto answerDto = new SelectionAnswerDto(id);
		answerDto.setSelected(isSelected);
		answerDto.setLocked(false);
		answerDto.setSelectionAnswerType(UserAnswerType.CORRECT);
		answerDto.setStateChanged(stateChanged);
		return answerDto;
	}

}
