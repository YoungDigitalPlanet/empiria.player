package eu.ydp.empiria.player.client.module.selection.controller;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.gin.factory.SelectionModuleFactory;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.selection.handlers.ChoiceButtonClickHandler;
import eu.ydp.empiria.player.client.module.selection.model.SelectionAnswerDto;
import eu.ydp.empiria.player.client.module.selection.model.SelectionGridElementPosition;
import eu.ydp.empiria.player.client.module.selection.presenter.SelectionModulePresenter;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionInteractionBean;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionItemBean;
import eu.ydp.empiria.player.client.module.selection.structure.SelectionSimpleChoiceBean;
import eu.ydp.empiria.player.client.module.selection.view.SelectionModuleView;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class SelectionViewBuilder {

	public static final int ROWS_RESERVED_FOR_COLUMN_HEADER = 1;
	public static final int COLUMNS_RESERVED_FOR_ROW_HEADER = 1;
	
	private SelectionModuleView selectionModuleView;
	private SelectionModuleFactory selectionModuleFactory;
	private SelectionModulePresenter selectionModulePresenter;
	private SelectionInteractionBean bean;
	private StyleNameConstants styleNameConstants;
	
	@Inject
	public SelectionViewBuilder(
			SelectionModuleFactory selectionModuleFactory,
			StyleNameConstants styleNameConstants,
			@ModuleScoped SelectionModuleView selectionModuleView) { 
		this.styleNameConstants = styleNameConstants;
		this.selectionModuleView = selectionModuleView;
		this.selectionModuleFactory = selectionModuleFactory;
	}
	
	public void bindView(
			SelectionModulePresenter selectionModulePresenter, 
			SelectionInteractionBean bean) {
		this.selectionModulePresenter = selectionModulePresenter;
		this.bean = bean;
	}

	public void fillFirstColumnWithItems(List<SelectionItemBean> items) {
		for(int rowNumber=0; rowNumber<items.size(); rowNumber++){
			SelectionItemBean selectionItemBean = items.get(rowNumber);
			XMLContent xmlContent = selectionItemBean.getXmlContent();
			SelectionGridElementPosition position = new SelectionGridElementPosition(0, rowNumber + COLUMNS_RESERVED_FOR_ROW_HEADER);
			selectionModuleView.setItemDisplayedName(xmlContent, position);
		}
	}

	public void fillFirstRowWithChoices(List<SelectionSimpleChoiceBean> simpleChoices) {
		for (int columnNumber=0; columnNumber<simpleChoices.size(); columnNumber++) {
			SelectionSimpleChoiceBean selectionSimpleChoiceBean = simpleChoices.get(columnNumber);
			XMLContent xmlContent = selectionSimpleChoiceBean.getXmlContent();
			SelectionGridElementPosition position = new SelectionGridElementPosition(columnNumber + ROWS_RESERVED_FOR_COLUMN_HEADER, 0);
			selectionModuleView.setChoiceOptionDisplayedName(xmlContent, position);
		}
	}
	
	public List<GroupAnswersController> fillGridWithButtons(List<SelectionItemBean> items, List<SelectionSimpleChoiceBean> simpleChoices) {
		fillFirstRowWithChoices(simpleChoices);
		fillFirstColumnWithItems(items);

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
		
		GroupAnswersController groupAnswerController = selectionModuleFactory.createGroupAnswerController(multi, matchMax);
		String moduleStyleName = getModuleStyleName(bean.isMulti());
		String itemIdentifier = itemBean.getIdentifier();
		
		for(int columnNumber = 0; columnNumber < simpleChoices.size(); columnNumber++){
			SelectionSimpleChoiceBean selectionSimpleChoiceBean = simpleChoices.get(columnNumber);
			String buttonIdentifier = buildPairedIdentifier(itemIdentifier, selectionSimpleChoiceBean.getIdentifier());
			
			SelectionGridElementPosition position = new SelectionGridElementPosition(columnNumber + COLUMNS_RESERVED_FOR_ROW_HEADER, rowNumber + ROWS_RESERVED_FOR_COLUMN_HEADER);
			
			selectionModuleView.createButtonForItemChoicePair(position, moduleStyleName);
			
			ChoiceButtonClickHandler clickHandler = selectionModuleFactory.createChoiceButtonClickHandler(groupAnswerController, buttonIdentifier, selectionModulePresenter);
			selectionModuleView.addClickHandlerToButton(position, clickHandler);
			
			SelectionAnswerDto selectionAnswerDto = selectionModuleFactory.createSelectionAnswerDto(buttonIdentifier);
			groupAnswerController.addSelectionAnswer(selectionAnswerDto);
		}
		
		return groupAnswerController;
	}
	
	public String getModuleStyleName(boolean isMulti) {
		if(isMulti) {
			return styleNameConstants.SELECTION_MULTI();
		} else {
			return styleNameConstants.SELECTION();
		}
	}
	
	private String buildPairedIdentifier(String itemIdentifier, String choiceIdentifier) {
		return itemIdentifier+" "+choiceIdentifier;
	}

	public void setGridSize(int amountOfItems, int amountOfChoices) {
		selectionModuleView.setGridSize(amountOfItems, amountOfChoices);
	}
}
