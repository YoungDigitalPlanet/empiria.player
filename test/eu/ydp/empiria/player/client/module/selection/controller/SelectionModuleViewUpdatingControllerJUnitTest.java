package eu.ydp.empiria.player.client.module.selection.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.empiria.player.client.module.selection.view.SelectionModuleView;

public class SelectionModuleViewUpdatingControllerJUnitTest {

	private SelectionModuleViewUpdatingController viewUpdatingController;
	private SelectionModuleView selectionModuleView;
	
	@Before
	public void setUp() throws Exception {
		viewUpdatingController = new SelectionModuleViewUpdatingController();
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
		
		List<SelectionAnswerDto> answers1 = Lists.newArrayList(
				createAnswer("id1", false, true), //should be unselected
				createAnswer("id2", true, false) //should be ignored - not state changed
				);
		when(groupController1.getAllAnswers())
			.thenReturn(answers1 );
		
		
		List<SelectionAnswerDto> answers2 = Lists.newArrayList(
				createAnswer("id3", true, true) //should be selected
				);
		when(groupController2.getAllAnswers())
			.thenReturn(answers2 );
		
		//then
		viewUpdatingController.updateView(selectionModuleView, groupChoicesControllers);
		
		verify(selectionModuleView).unselectButton(0, 0);
		verify(selectionModuleView).lockButton(false, 0, 0);
		verify(selectionModuleView).updateButtonStyle(0, 0, UserAnswerType.CORRECT);
		
		verify(selectionModuleView).selectButton(1, 0);
		verify(selectionModuleView).lockButton(false, 1, 0);
		verify(selectionModuleView).updateButtonStyle(1, 0, UserAnswerType.CORRECT);
	}
	
	private SelectionAnswerDto createAnswer(String id, boolean isSelected, boolean stateChanged){
		SelectionAnswerDto answerDto = new SelectionAnswerDto(id);
		answerDto.setSelected(isSelected);
		answerDto.setLocked(false);
		answerDto.setSelectionAnswerType(UserAnswerType.CORRECT);
		answerDto.setStateChanged(stateChanged);
		return answerDto;
	}

}
