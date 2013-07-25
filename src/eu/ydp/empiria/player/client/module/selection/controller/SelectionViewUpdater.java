package eu.ydp.empiria.player.client.module.selection.controller;

import java.util.List;

import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.model.SelectionGridElementPosition;
import eu.ydp.empiria.player.client.module.selection.view.SelectionModuleView;

public class SelectionViewUpdater {

	public void updateView(SelectionModuleView selectionModuleView, GroupAnswersController groupChoicesController, int itemNumber) {
		List<SelectionAnswerDto> allAnswers = groupChoicesController.getAllAnswers();
		
		for(int choiceNumber=0; choiceNumber<allAnswers.size(); choiceNumber++){
			SelectionAnswerDto selectionAnswerDto = allAnswers.get(choiceNumber);
			
			if(selectionAnswerDto.isStateChanged()){
				SelectionGridElementPosition position = new SelectionGridElementPosition(choiceNumber + 1, itemNumber + 1);
				updateSingleAnswer(selectionAnswerDto, position, selectionModuleView);
				selectionAnswerDto.setStateChanged(false);
			}
		}
	}

	private void updateSingleAnswer(SelectionAnswerDto selectionAnswerDto, SelectionGridElementPosition position, SelectionModuleView selectionModuleView) {
		if(selectionAnswerDto.isSelected()){
			selectionModuleView.selectButton(position);
		}else{
			selectionModuleView.unselectButton(position);
		}
		
		selectionModuleView.lockButton(position, selectionAnswerDto.isLocked());
		selectionModuleView.updateButtonStyle(position, selectionAnswerDto.getSelectionAnswerType());
	}
}
