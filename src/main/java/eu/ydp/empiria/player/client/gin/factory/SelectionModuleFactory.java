package eu.ydp.empiria.player.client.gin.factory;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.controller.GroupAnswersController;
import eu.ydp.empiria.player.client.module.selection.controller.answers.AnswerQueueFactory;
import eu.ydp.empiria.player.client.module.selection.handlers.ChoiceButtonClickHandler;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenter;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class SelectionModuleFactory {

    private SelectionModuleModel responseModel;
    private AnswerQueueFactory answerQueueFactory;

    @Inject
    public SelectionModuleFactory(@ModuleScoped SelectionModuleModel responseModel, AnswerQueueFactory answerQueueFactory) {
        this.responseModel = responseModel;
        this.answerQueueFactory = answerQueueFactory;
    }

    public SelectionAnswerDto createSelectionAnswerDto(String id) {
        return new SelectionAnswerDto(id);
    }

    public GroupAnswersController createGroupAnswerController(boolean isMulti, int maxSelected) {
        return new GroupAnswersController(isMulti, maxSelected, responseModel, answerQueueFactory);
    }

    public ChoiceButtonClickHandler createChoiceButtonClickHandler(GroupAnswersController groupAnswerController, String buttonId,
            SelectionModulePresenter selectionModulePresenter) {
        return new ChoiceButtonClickHandler(groupAnswerController, buttonId, selectionModulePresenter);
    }
}
