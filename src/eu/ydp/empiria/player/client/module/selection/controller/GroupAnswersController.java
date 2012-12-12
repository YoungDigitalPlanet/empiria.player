package eu.ydp.empiria.player.client.module.selection.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;

public class GroupAnswersController {

	private List<SelectionAnswerDto> allSelectionAnswers = new ArrayList<SelectionAnswerDto>();
	private Queue<SelectionAnswerDto> selectedAnswers;
	private int maxSelected;
	private AbstractResponseModel<?> responseModel;

	@Inject
	public GroupAnswersController(
			@Assisted boolean isMulti, 
			@Assisted int maxSelected,			
			@Assisted AbstractResponseModel<?> responseModel,
			NoAnswerPriorityComparator noPriorityComparator){
		this.maxSelected = maxSelected;
		this.responseModel = responseModel;
		
		if(isMulti){
			selectedAnswers = new PriorityQueue<SelectionAnswerDto>(maxSelected, noPriorityComparator);
		}else{
			selectedAnswers = new PriorityQueue<SelectionAnswerDto>(1, noPriorityComparator);
		}
	}
	
	public void addSelectionAnswer(SelectionAnswerDto button){
		allSelectionAnswers.add(button);
	}
	
	void selectAnswer(SelectionAnswerDto selectionAnswer){
		if(!allSelectionAnswers.contains(selectionAnswer)){
			throw new RuntimeException("SelectButton method called from GroupChoicesController with button as argument, that is not connected with this controller!");
		}

		if(selectedAnswers.size() >= maxSelected){
			unselectFirstlySelectedAnswer();
		}
		
		selectedAnswers.add(selectionAnswer);
		
		selectionAnswer.setSelected(true);
		responseModel.addAnswer(selectionAnswer.getId());
	}
	
	private void unselectFirstlySelectedAnswer() {
		SelectionAnswerDto selectionAnswer = selectedAnswers.poll();
		selectionAnswer.setSelected(false);
		responseModel.removeAnswer(selectionAnswer.getId());
	}

	void unselectAnswer(SelectionAnswerDto selectionAnswer){
		selectedAnswers.remove(selectionAnswer);
		selectionAnswer.setSelected(false);
		responseModel.removeAnswer(selectionAnswer.getId());
	}
	
	public void selectToggleAnswer(String selectionAnswerId) {
		SelectionAnswerDto selectionAnswer = findSelectionAnswerById(selectionAnswerId);
		
		if(selectionAnswer.isSelected())
			unselectAnswer(selectionAnswer);
		else
			selectAnswer(selectionAnswer);
	}
	
	private SelectionAnswerDto findSelectionAnswerById(String answerId){
		for(SelectionAnswerDto selectionAnswer : allSelectionAnswers){
			if(answerId.equals(selectionAnswer.getId())){
				return selectionAnswer;
			}
		}
		throw new RuntimeException("Cannot find SelectionAnswerDto with id: "+answerId);
	}
	
	public void reset(){
		for (SelectionAnswerDto button : selectedAnswers) {
			button.setSelected(false);
		}
		selectedAnswers.clear();
	}
	
	public void selectOnlyAnswersMatchingIds(Collection<String> ids){
		List<SelectionAnswerDto> buttonsToSelect = findAnswersWithIds(ids, allSelectionAnswers);
		List<SelectionAnswerDto> alreadySelectedMatchingIdsButtons = findAnswersWithIds(ids, selectedAnswers);
		
		selectedAnswers.removeAll(alreadySelectedMatchingIdsButtons);
		for(SelectionAnswerDto answerToUnselect : selectedAnswers){
			answerToUnselect.setSelected(false);
		}
		selectedAnswers.clear();
		
		selectedAnswers.addAll(alreadySelectedMatchingIdsButtons);
		
		for(SelectionAnswerDto answerToSelect : buttonsToSelect){
			if( !alreadySelectedMatchingIdsButtons.contains(answerToSelect)){
				answerToSelect.setSelected(true);
				selectedAnswers.add(answerToSelect);
			}
		}
	}
	
	private List<SelectionAnswerDto> findAnswersWithIds(Collection<String> ids, Collection<SelectionAnswerDto> buttonsToSearch){
		List<SelectionAnswerDto> buttons = new ArrayList<SelectionAnswerDto>();
		for(SelectionAnswerDto button : buttonsToSearch){
			String id = button.getId();
			if(ids.contains(id)){
				buttons.add(button);
			}
		}
		return buttons;
	}
	
	public void setLockedAllAnswers(boolean locked){
		for(SelectionAnswerDto answer : allSelectionAnswers){
			answer.setLocked(locked);
		}
	}
	
	public List<SelectionAnswerDto> getSelectedAnswers(){
		return new ArrayList<SelectionAnswerDto>(selectedAnswers);
	}
	
	public List<SelectionAnswerDto> getNotSelectedAnswers(){
		List<SelectionAnswerDto> notSelectedButtons = new ArrayList<SelectionAnswerDto>(allSelectionAnswers);
		notSelectedButtons.removeAll(selectedAnswers);
		return notSelectedButtons;
	}

	public List<SelectionAnswerDto> getAllAnswers() {
		return new ArrayList<SelectionAnswerDto>(allSelectionAnswers);
	}
	
}
