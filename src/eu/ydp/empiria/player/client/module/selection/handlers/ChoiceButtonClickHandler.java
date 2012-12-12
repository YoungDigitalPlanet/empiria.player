package eu.ydp.empiria.player.client.module.selection.handlers;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.selection.controller.GroupAnswersController;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenter;

public class ChoiceButtonClickHandler implements ClickHandler{

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
	public void onClick(ClickEvent event) {
		groupAnswerController.selectToggleAnswer(buttonId);
		selectionModulePresenter.updateView();
	}
}
