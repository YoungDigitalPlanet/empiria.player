package eu.ydp.empiria.player.client.module.selection.controller;

import java.util.List;

import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.view.SelectionModuleView;

public class SelectionViewUpdater {

	public void updateView(SelectionModuleView selectionModuleView, GroupAnswersController groupChoicesController, int itemNumber) {
		List<SelectionAnswerDto> allAnswers = groupChoicesController.getAllAnswers();
		
		for(int choiceNumber=0; choiceNumber<allAnswers.size(); choiceNumber++){
			SelectionAnswerDto selectionAnswerDto = allAnswers.get(choiceNumber);
			
			if(selectionAnswerDto.isStateChanged()){
				updateSingleAnswer(selectionAnswerDto, itemNumber, choiceNumber, selectionModuleView);
				selectionAnswerDto.setStateChanged(false);
			}
		}
	}

	private void updateSingleAnswer(SelectionAnswerDto selectionAnswerDto, int itemNumber, int choiceNumber, SelectionModuleView selectionModuleView) {
		if(selectionAnswerDto.isSelected()){
			selectionModuleView.selectButton(itemNumber, choiceNumber);
		}else{
			selectionModuleView.unselectButton(itemNumber, choiceNumber);
		}
		
		selectionModuleView.lockButton(selectionAnswerDto.isLocked(), itemNumber, choiceNumber);
		selectionModuleView.updateButtonStyle(itemNumber, choiceNumber, selectionAnswerDto.getSelectionAnswerType());
	}
}
