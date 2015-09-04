package eu.ydp.empiria.player.client.gin.factory;

import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.controller.GroupAnswersController;
import eu.ydp.empiria.player.client.module.selection.handlers.ChoiceButtonClickHandler;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenter;

public interface SelectionModuleFactory {

    public SelectionAnswerDto getSelectionAnswerDto(String id);

    public GroupAnswersController getGroupAnswerController(int maxSelected, SelectionModuleModel selectionModuleModel);

    public ChoiceButtonClickHandler getChoiceButtonClickHandler(GroupAnswersController groupAnswerController, String buttonId,
            SelectionModulePresenter selectionModulePresenter);
}
