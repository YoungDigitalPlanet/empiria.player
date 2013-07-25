package eu.ydp.empiria.player.client.module.selection.presenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.AnswersMarkingTemplate;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.controller.GroupAnswersController;
import eu.ydp.empiria.player.client.module.selection.controller.IdentifiableAnswersByTypeFinder;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;

public class SelectionAnswersMarker extends AnswersMarkingTemplate {

	private IdentifiableAnswersByTypeFinder identifiableAnswersByTypeFinder;
	private SelectionModuleModel model;
	private List<SelectionAnswerDto> selectedButtons;
	private List<SelectionAnswerDto> buttonsToMark;
	private List<SelectionAnswerDto> notSelectedButtons;

	@Inject
	public SelectionAnswersMarker(
			IdentifiableAnswersByTypeFinder identifiableAnswersByTypeFinder,
			@ModuleScoped SelectionModuleModel selectionModuleModel) {
				this.model = selectionModuleModel;
				this.identifiableAnswersByTypeFinder = identifiableAnswersByTypeFinder;
	}

	
	@Override
	public void unmarkWrong() {
		unMark();
	}

	@Override
	public void markWrong() {
		applyAnswerTypeToCollection(UserAnswerType.WRONG, buttonsToMark);
		applyAnswerTypeToCollection(UserAnswerType.NONE, notSelectedButtons);
	}

	@Override
	public void unmarkCorrect() {
		unMark();
	}

	@Override
	public void markCorrect() {
		applyAnswerTypeToCollection(UserAnswerType.CORRECT, buttonsToMark);
		applyAnswerTypeToCollection(UserAnswerType.NONE, notSelectedButtons);
	}

	public void markAnswers(MarkAnswersType type, MarkAnswersMode mode, GroupAnswersController groupChoicesController) {
		getButtonsListFromController(type, groupChoicesController);
		markAnswers(type, mode);
	}
	
	private void unMark() {
		List<SelectionAnswerDto> buttonsToMarkDefaultState = new ArrayList<SelectionAnswerDto>(buttonsToMark);
		buttonsToMarkDefaultState.addAll(notSelectedButtons);
		applyAnswerTypeToCollection(UserAnswerType.DEFAULT, buttonsToMarkDefaultState);
	}

	private void getButtonsListFromController(MarkAnswersType type, GroupAnswersController groupChoicesController) {
		selectedButtons = groupChoicesController.getSelectedAnswers();
		buttonsToMark = identifiableAnswersByTypeFinder.findAnswersObjectsOfGivenType(type, selectedButtons, model);
		notSelectedButtons = groupChoicesController.getNotSelectedAnswers();
	}

	private void applyAnswerTypeToCollection(UserAnswerType userAnswerType, Collection<SelectionAnswerDto> answers){
		for (SelectionAnswerDto selectionAnswerDto : answers) {
			selectionAnswerDto.setSelectionAnswerType(userAnswerType);
		}
	}
}