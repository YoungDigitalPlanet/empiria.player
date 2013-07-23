package eu.ydp.empiria.player.client.module.selection.presenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.MarkAnswersMode;
import eu.ydp.empiria.player.client.module.MarkAnswersType;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ShowAnswersType;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.controller.GroupAnswersController;
import eu.ydp.empiria.player.client.module.selection.controller.IdentifiableAnswersByTypeFinder;
import eu.ydp.empiria.player.client.module.selection.controller.SelectionViewBuilder;
import eu.ydp.empiria.player.client.module.selection.controller.SelectionViewUpdater;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionInteractionBean;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionItemBean;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionSimpleChoiceBean;
import eu.ydp.empiria.player.client.module.selection.view.SelectionModuleView;

public class SelectionModulePresenterImpl implements SelectionModulePresenter{

	private SelectionModuleModel model;
	private SelectionInteractionBean bean;
	private ModuleSocket moduleSocket;
	private List<GroupAnswersController> groupChoicesControllers;

	private SelectionModuleView selectionModuleView;
	private IdentifiableAnswersByTypeFinder identifiableAnswersByTypeFinder;
	private SelectionViewUpdater viewUpdater;
	private SelectionViewBuilder viewBuilder;
	
	@Inject
	public SelectionModulePresenterImpl(
			IdentifiableAnswersByTypeFinder identifiableAnswersByTypeFinder,
			SelectionViewUpdater selectionViewUpdater,
			@ModuleScoped SelectionModuleView selectionModuleView,
			@ModuleScoped SelectionModuleModel selectionModuleModel,
			@ModuleScoped SelectionViewBuilder selectionViewBuilder) {
		this.selectionModuleView = selectionModuleView;
		this.identifiableAnswersByTypeFinder = identifiableAnswersByTypeFinder;
		this.viewUpdater = selectionViewUpdater;
		this.model = selectionModuleModel;
		this.viewBuilder = selectionViewBuilder;
	}

	@Override
	public void bindView() {
		InlineBodyGeneratorSocket inlineBodyGeneratorSocket = moduleSocket.getInlineBodyGeneratorSocket();
		
		selectionModuleView.initialize(inlineBodyGeneratorSocket);

		viewBuilder.bindView(this, bean);
		
		fillSelectionGrid();
	}

	private void fillSelectionGrid() {
		List<SelectionItemBean> items = bean.getItems();
		List<SelectionSimpleChoiceBean> simpleChoices = bean.getSimpleChoices();
		
		viewBuilder.setGridSize(items.size(), simpleChoices.size());
		
		this.groupChoicesControllers = viewBuilder.fillGridWithButtons(items, simpleChoices);
	}

	@Override
	public void reset() {
		for (GroupAnswersController groupChoicesController : groupChoicesControllers) {
			groupChoicesController.reset();
		}
	}

	@Override
	public void setModel(SelectionModuleModel model) {
		// SelectionModuleModel is now being injected in moduleScope 
	}

	@Override
	public void setModuleSocket(ModuleSocket socket) {
		this.moduleSocket = socket;
	}

	@Override
	public void setBean(SelectionInteractionBean bean) {
		this.bean = bean;
	}

	@Override
	public void setLocked(boolean locked) {
		for (GroupAnswersController groupChoicesController : groupChoicesControllers) {
			groupChoicesController.setLockedAllAnswers(locked);

			updateGroupAnswerView(groupChoicesController);
		}
	}

	@Override
	public void updateGroupAnswerView(GroupAnswersController groupChoicesController) {
		int itemNumber = groupChoicesControllers.indexOf(groupChoicesController);
		viewUpdater.updateView(selectionModuleView, groupChoicesController, itemNumber);
	}
	
	@Override
	public void markAnswers(MarkAnswersType type, MarkAnswersMode mode) {
		for(GroupAnswersController groupChoicesController : groupChoicesControllers){
			markAnswersOnButtonsFromController(type, mode, groupChoicesController);

			updateGroupAnswerView(groupChoicesController);
		}
	}

	private void markAnswersOnButtonsFromController(MarkAnswersType type, MarkAnswersMode mode, GroupAnswersController groupChoicesController) {
		List<SelectionAnswerDto> selectedButtons = groupChoicesController.getSelectedAnswers();
		List<SelectionAnswerDto> buttonsToMark = identifiableAnswersByTypeFinder.findAnswersObjectsOfGivenType(type, selectedButtons, model);
		List<SelectionAnswerDto> notSelectedButtons = groupChoicesController.getNotSelectedAnswers();
		
		if(MarkAnswersMode.MARK.equals(mode)){
			if(MarkAnswersType.CORRECT.equals(type)){
				applyAnswerTypeToCollection(UserAnswerType.CORRECT, buttonsToMark);
			}else{
				applyAnswerTypeToCollection(UserAnswerType.WRONG, buttonsToMark);
			}
			
			applyAnswerTypeToCollection(UserAnswerType.NONE, notSelectedButtons);
			
		}else if(MarkAnswersMode.UNMARK.equals(mode)){
			List<SelectionAnswerDto> buttonsToMarkDefaultState = new ArrayList<SelectionAnswerDto>(buttonsToMark);
			buttonsToMarkDefaultState.addAll(notSelectedButtons);
			applyAnswerTypeToCollection(UserAnswerType.DEFAULT, buttonsToMarkDefaultState);
		}
	}

	private void applyAnswerTypeToCollection(UserAnswerType userAnswerType, Collection<SelectionAnswerDto> answers){
		for (SelectionAnswerDto selectionAnswerDto : answers) {
			selectionAnswerDto.setSelectionAnswerType(userAnswerType);
		}
	}
	
	@Override
	public void showAnswers(ShowAnswersType mode) {
		List<String> answersToSelect;
		if(ShowAnswersType.CORRECT.equals(mode))
			answersToSelect = model.getCorrectAnswers();
		else
			answersToSelect = model.getCurrentAnswers();
		
		for (GroupAnswersController groupChoicesController : groupChoicesControllers) {
			groupChoicesController.selectOnlyAnswersMatchingIds(answersToSelect);
			
			updateGroupAnswerView(groupChoicesController);
		}
	}

	@Override
	public Widget asWidget() {
		return selectionModuleView.asWidget();
	}
}
