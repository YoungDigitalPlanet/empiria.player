package eu.ydp.empiria.player.client.module.selection.presenter;

import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.controller.GroupAnswersController;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionInteractionBean;

public interface SelectionModulePresenter extends ActivityPresenter<SelectionModuleModel, SelectionInteractionBean> {

	void updateGroupAnswerView(GroupAnswersController groupChoicesController);

}
