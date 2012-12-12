package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.controller.GroupAnswersController;
import eu.ydp.empiria.player.client.module.selection.controller.SelectionModuleViewBuildingController;
import eu.ydp.empiria.player.client.module.selection.handlers.ChoiceButtonClickHandler;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenter;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionInteractionBean;
import eu.ydp.empiria.player.client.module.selection.view.SelectionChoiceButton;
import eu.ydp.empiria.player.client.module.selection.view.SelectionModuleView;

public interface SelectionModuleFactory {
	
	SelectionModuleModel createSelectionModuleModel(Response response, ResponseModelChangeListener modelChangeListener);
	
	SelectionChoiceButton createSelectionChoiceButton(@Assisted("moduleStyleNamePart") String moduleStyleNamePart);
	
	SelectionAnswerDto createSelectionAnswerDto(String id);
	
	GroupAnswersController createGroupAnswerController(boolean isMulti, int maxSelected, AbstractResponseModel<?> responseModel);

	SelectionModuleViewBuildingController  createViewBuildingController(
			SelectionModuleView selectionModuleView, 
			SelectionModulePresenter selectionModulePresenter,
			SelectionModuleModel model,
			SelectionInteractionBean bean);
	
	ChoiceButtonClickHandler createChoiceButtonClickHandler(
			GroupAnswersController groupAnswerController,
			String buttonId,
			SelectionModulePresenter selectionModulePresenter);
}
