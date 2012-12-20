package eu.ydp.empiria.player.client.module.selection.controller;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.gin.factory.SelectionModuleFactory;
import eu.ydp.empiria.player.client.module.selection.SelectionModuleModel;
import eu.ydp.empiria.player.client.module.selection.handlers.ChoiceButtonClickHandler;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenter;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionInteractionBean;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionItemBean;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionSimpleChoiceBean;
import eu.ydp.empiria.player.client.module.selection.view.SelectionModuleView;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class SelectionModuleViewBuildingController {

	private SelectionModuleView selectionModuleView;
	private StyleNameConstants styleNameConstants;
	private SelectionModuleFactory selectionModuleFactory;
	private SelectionModulePresenter selectionModulePresenter;
	private SelectionModuleModel model;
	private SelectionInteractionBean bean;
	
	
	@Inject
	public SelectionModuleViewBuildingController(
			StyleNameConstants styleNameConstants,
			SelectionModuleFactory selectionModuleFactory, 
			@Assisted SelectionModuleView selectionModuleView, 
			@Assisted SelectionModulePresenter selectionModulePresenter, 
			@Assisted SelectionModuleModel model,
			@Assisted SelectionInteractionBean bean) {
		this.selectionModuleView = selectionModuleView;
		this.styleNameConstants = styleNameConstants;
		this.selectionModuleFactory = selectionModuleFactory;
		this.selectionModulePresenter = selectionModulePresenter;
		this.model = model;
		this.bean = bean;
	}

	public void fillFirstColumnWithItems(List<SelectionItemBean> items) {
		for(int itemNumber=0; itemNumber<items.size(); itemNumber++){
			SelectionItemBean selectionItemBean = items.get(itemNumber);
			XMLContent xmlContent = selectionItemBean.getXmlContent();
			selectionModuleView.setItemDisplayedName(xmlContent, itemNumber);
		}
	}

	public void fillFirstRowWithChoices(List<SelectionSimpleChoiceBean> simpleChoices) {
		for (int rowNumber=0; rowNumber<simpleChoices.size(); rowNumber++) {
			SelectionSimpleChoiceBean selectionSimpleChoiceBean = simpleChoices.get(rowNumber);
			XMLContent xmlContent = selectionSimpleChoiceBean.getXmlContent();
			selectionModuleView.setChoiceOptionDisplayedName(xmlContent, rowNumber);
		}
	}
	
	public List<GroupAnswersController> fillGridWithButtons(List<SelectionItemBean> items, List<SelectionSimpleChoiceBean> simpleChoices) {
		List<GroupAnswersController> groupAnswersControllers = new ArrayList<GroupAnswersController>();
		for(int rowNumber=0; rowNumber<items.size(); rowNumber++){
			SelectionItemBean itemBean = items.get(rowNumber);
			GroupAnswersController groupAnswersController = fillRowWithGroupedItems(rowNumber, itemBean, simpleChoices);
			groupAnswersControllers.add(groupAnswersController);
		}
		
		return groupAnswersControllers;
	}

	private GroupAnswersController fillRowWithGroupedItems(int rowNumber, SelectionItemBean itemBean, List<SelectionSimpleChoiceBean> simpleChoices) {
		boolean multi = bean.isMulti();
		int matchMax = itemBean.getMatchMax();
		
		GroupAnswersController groupAnswerController = selectionModuleFactory.createGroupAnswerController(multi, matchMax, model);
		String itemIdentifier = itemBean.getIdentifier();
		String moduleStyleName = getModuleStyleName(multi);
		
		for(int choiceNumber=0; choiceNumber<simpleChoices.size(); choiceNumber++){
			SelectionSimpleChoiceBean selectionSimpleChoiceBean = simpleChoices.get(choiceNumber);
			String buttonIdentifier = buildPairedIdentifier(itemIdentifier, selectionSimpleChoiceBean.getIdentifier());
			
			selectionModuleView.createButtonForItemChoicePair(rowNumber, choiceNumber, moduleStyleName);
			
			ChoiceButtonClickHandler clickHandler = selectionModuleFactory.createChoiceButtonClickHandler(groupAnswerController, buttonIdentifier, selectionModulePresenter);
			selectionModuleView.addClickHandlerToButton(rowNumber, choiceNumber, clickHandler);
			
			SelectionAnswerDto selectionAnswerDto = selectionModuleFactory.createSelectionAnswerDto(buttonIdentifier);
			groupAnswerController.addSelectionAnswer(selectionAnswerDto);
		}
		
		return groupAnswerController;
	}
	
	private String getModuleStyleName(boolean multi) {
		if(multi)
			return styleNameConstants.SELECTION_MULTI();
		else
			return styleNameConstants.SELECTION();
	}

	private String buildPairedIdentifier(String itemIdentifier, String choiceIdentifier) {
		return itemIdentifier+" "+choiceIdentifier;
	}
	
}
