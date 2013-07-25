package eu.ydp.empiria.player.client.module.selection.view;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.selection.model.SelectionGridElementPosition;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;

public class SelectionModuleViewImpl implements SelectionModuleView{

	@Inject
	private SelectionGridElementGenerator gridElementGenerator;

	private Map<SelectionGridElementPosition, SelectionGridElement> gridMap;

	@UiField
	Panel mainPanel;

	@UiField
	Widget promptWidget;

	@UiField
	Grid selectionGrid;

	@UiTemplate("SelectionModuleView.ui.xml")
	interface SelectionModuleUiBinder extends UiBinder<Widget, SelectionModuleViewImpl> {
	};

	@Override
	public void initialize(InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
		SelectionModuleUiBinder uiBinder = GWT.create(SelectionModuleUiBinder.class);
		uiBinder.createAndBindUi(this);
		gridElementGenerator.setInlineBodyGenerator(inlineBodyGeneratorSocket);
		
		gridMap = new HashMap<SelectionGridElementPosition, SelectionGridElement>();
	}

	@Override
	public void setGridSize(int amountOfItems, int amountOfChoices) {
		selectionGrid.resize(amountOfItems + 1, amountOfChoices + 1);
	}
	
	@Override
	public void setItemDisplayedName(XMLContent itemName, SelectionGridElementPosition position){
		SelectionGridElement itemTextGridElement = gridElementGenerator.createItemDisplayElement(itemName.getValue());
		addNewGridElement(itemTextGridElement, position);
	}

	@Override
	public void setChoiceOptionDisplayedName(XMLContent choiceName, SelectionGridElementPosition position){
		SelectionGridElement choiseTextGridElement = gridElementGenerator.createChoiceDisplayElement(choiceName.getValue());
		addNewGridElement(choiseTextGridElement, position);
	}

	@Override
	public void createButtonForItemChoicePair(SelectionGridElementPosition position, String moduleStyleName){
		SelectionGridElement choiceButtonGridElement = gridElementGenerator.createChoiceButtonElement(moduleStyleName);
		addNewGridElement(choiceButtonGridElement, position);
	}
	
	@Override
	public void addClickHandlerToButton(SelectionGridElementPosition position, ClickHandler clickHandler){
		final SelectionGridElement gridElement = gridMap.get(position);
		gridElement.addClickHandler(clickHandler);
	}

	@Override
	public void selectButton(SelectionGridElementPosition position){
		SelectionGridElement gridElement = gridMap.get(position);
		gridElement.select();
	}

	@Override
	public void unselectButton(SelectionGridElementPosition position) {
		SelectionGridElement gridElement = gridMap.get(position);
		gridElement.unselect();
	}

	@Override
	public void lockButton(SelectionGridElementPosition position, boolean lock){
		SelectionGridElement gridElement = gridMap.get(position);
		gridElement.setButtonEnabled(!lock);
	}

	@Override
	public void updateButtonStyle(SelectionGridElementPosition position, UserAnswerType styleState){
		SelectionGridElement gridElement = gridMap.get(position);
		gridElement.updateStyle(styleState);
	}

	@Override
	public Widget asWidget() {
		return mainPanel;
	}
	
	private void addNewGridElement(SelectionGridElement gridElement, SelectionGridElementPosition position) {
		selectionGrid.setWidget(position.getRowNumber(), position.getColumnNumber(), gridElement.asWidget());
		if(gridMap.get(position) == null) {
			gridMap.put(position, gridElement);
		}
	}
}
