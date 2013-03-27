package eu.ydp.empiria.player.client.module.selection.handlers;

import com.google.gwt.dom.client.NativeEvent;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.selection.controller.GroupAnswersController;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenter;
import eu.ydp.gwtutil.client.event.factory.Command;

public class ChoiceButtonClickHandler implements Command{

	private GroupAnswersController groupAnswerController;
	private String buttonId;
	private final SelectionModulePresenter selectionModulePresenter;
	
	@Inject
	public ChoiceButtonClickHandler(
			@Assisted GroupAnswersController groupAnswerController,
			@Assisted String buttonId,
			@Assisted SelectionModulePresenter selectionModulePresenter) {
		this.groupAnswerController = groupAnswerController;
		this.buttonId = buttonId;
		this.selectionModulePresenter = selectionModulePresenter;
	}

	@Override
	public void execute(NativeEvent event) {
		groupAnswerController.selectToggleAnswer(buttonId);
		selectionModulePresenter.updateView();
	}
}
