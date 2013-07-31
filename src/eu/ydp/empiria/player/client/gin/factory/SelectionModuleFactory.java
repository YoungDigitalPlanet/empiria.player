package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.controller.GroupAnswersController;
import eu.ydp.empiria.player.client.module.selection.controller.NoAnswerPriorityComparator;
import eu.ydp.empiria.player.client.module.selection.handlers.ChoiceButtonClickHandler;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenter;
import eu.ydp.empiria.player.client.module.selection.view.SelectionModuleView;

public class SelectionModuleFactory {
	
	private SelectionModuleModel responseModel;
	private NoAnswerPriorityComparator noPriorityComparator;
	
	@Inject
	public SelectionModuleFactory(
			@ModuleScoped SelectionModuleModel responseModel,
			@ModuleScoped SelectionModuleView selectionModuleView,
			NoAnswerPriorityComparator noPriorityComparator) {
		this.responseModel = responseModel;
		this.noPriorityComparator = noPriorityComparator;
	}
	
	public SelectionAnswerDto createSelectionAnswerDto(String id) {
		return new SelectionAnswerDto(id);
	}
	
	public GroupAnswersController createGroupAnswerController(boolean isMulti, int maxSelected) {
		return new GroupAnswersController(isMulti, maxSelected, responseModel, noPriorityComparator);
	}

	public ChoiceButtonClickHandler createChoiceButtonClickHandler(
			GroupAnswersController groupAnswerController,
			String buttonId,
			SelectionModulePresenter selectionModulePresenter) {
		return new ChoiceButtonClickHandler(groupAnswerController, buttonId, selectionModulePresenter);
	}
}
