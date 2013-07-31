package eu.ydp.empiria.player.client.module.selection.controller;

import java.util.List;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.model.SelectionGridElementPosition;
import eu.ydp.empiria.player.client.module.selection.view.SelectionElementPositionGenerator;
import eu.ydp.empiria.player.client.module.selection.view.SelectionModuleView;

public class SelectionViewUpdater {

	private SelectionElementPositionGenerator elementPositionGenerator;
	
	@Inject
	public SelectionViewUpdater(SelectionElementPositionGenerator elementPositionGenerator) {
		this.elementPositionGenerator = elementPositionGenerator;
	}
	
	public void updateView(SelectionModuleView selectionModuleView, GroupAnswersController groupChoicesController, int itemNumber) {
		List<SelectionAnswerDto> allAnswers = groupChoicesController.getAllAnswers();
		
		for(int choiceNumber=0; choiceNumber<allAnswers.size(); choiceNumber++){
			SelectionAnswerDto selectionAnswerDto = allAnswers.get(choiceNumber);
			
			if(selectionAnswerDto.isStateChanged()){
				SelectionGridElementPosition position = elementPositionGenerator.getButtonElementPositionFor(itemNumber, choiceNumber);
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
