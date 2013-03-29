package eu.ydp.empiria.player.client.module.selection.view;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.SelectionModuleFactory;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.event.factory.EventHandlerProxy;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class SelectionModuleViewImpl implements SelectionModuleView{

	public static final int ROWS_RESERVED_FOR_COLUMN_HEADER = 1;
	public static final int COLUMNS_RESERVED_FOR_ROW_HEADER = 1;

	@Inject
	private StyleNameConstants styleNameConstants;
	@Inject
	private SelectionModuleFactory selectionModuleFactory;
	@Inject
	private UserInteractionHandlerFactory userInteractionHandlerFactory;

	private List<List<SelectionChoiceButton>> choiceButtons;
	private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;

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
	public void initialize(int amountOfItems, int amountOfChoices, InlineBodyGeneratorSocket inlineBodyGeneratorSocket){
		this.inlineBodyGeneratorSocket = inlineBodyGeneratorSocket;
		SelectionModuleUiBinder uiBinder = GWT.create(SelectionModuleUiBinder.class);
		uiBinder.createAndBindUi(this);
		selectionGrid.resize(amountOfItems+1, amountOfChoices+1);
		choiceButtons = new ArrayList<List<SelectionChoiceButton>>();
	}

	@Override
	public void setItemDisplayedName(XMLContent itemName, int itemNumber){
		Widget itemTextLabel = inlineBodyGeneratorSocket.generateInlineBody(itemName.getValue());

		itemTextLabel.setStyleName(styleNameConstants.QP_SELECTION_ITEM_LABEL());
		itemTextLabel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_INACTIVE());
		Panel itemContainer = new FlowPanel();
		itemContainer.setStyleName(styleNameConstants.QP_SELECTION_ITEM());
		itemContainer.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_INACTIVE());
		itemContainer.add(itemTextLabel);

		selectionGrid.setWidget(itemNumber + ROWS_RESERVED_FOR_COLUMN_HEADER, 0, itemContainer);
	}

	@Override
	public void setChoiceOptionDisplayedName(XMLContent choiceName, int choiceNumber){
		Widget choiseTextWidget = inlineBodyGeneratorSocket.generateInlineBody(choiceName.getValue());
		choiseTextWidget.setStyleName(styleNameConstants.QP_SELECTION_CHOICE());

		selectionGrid.setWidget(0, choiceNumber+COLUMNS_RESERVED_FOR_ROW_HEADER, choiseTextWidget);
	}

	@Override
	public void createButtonForItemChoicePair(int itemNumber, int choiceNumber, String moduleStyleName){
		SelectionChoiceButton choiceButton = selectionModuleFactory.createSelectionChoiceButton(moduleStyleName);
		Panel buttonPanel = new FlowPanel();
		buttonPanel.setStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_INACTIVE());
		buttonPanel.add(choiceButton);

		selectionGrid.setWidget(
				itemNumber+ROWS_RESERVED_FOR_COLUMN_HEADER,
				choiceNumber+COLUMNS_RESERVED_FOR_ROW_HEADER,
				buttonPanel);

		addNewChoiceButton(choiceButton, itemNumber);

		addMouseOverHandler(choiceButton);
	}

	private void addMouseOverHandler(final SelectionChoiceButton button) {
		EventHandlerProxy userOverHandler = userInteractionHandlerFactory.createUserOverHandler(new ChoiceButtonMouseInteractionCommand(button, true));
		userOverHandler.apply(button);

		EventHandlerProxy userOutHandler = userInteractionHandlerFactory.createUserOutHandler(new ChoiceButtonMouseInteractionCommand(button, false));
		userOutHandler.apply(button);
	}

	private void addNewChoiceButton(SelectionChoiceButton choiceButton, int itemNumber) {
		List<SelectionChoiceButton> buttonsOfItem;
		if(buttonsOfItemNotExists(itemNumber)){
			buttonsOfItem = new ArrayList<SelectionChoiceButton>();
			choiceButtons.add(buttonsOfItem);
		}else{
			buttonsOfItem = choiceButtons.get(itemNumber);
		}

		buttonsOfItem.add(choiceButton);
	}

	private boolean buttonsOfItemNotExists(int itemNumber) {
		return choiceButtons.isEmpty() || itemNumber >= choiceButtons.size();
	}

	@Override
	public void addClickHandlerToButton(int itemNumber, int choiceNumber, ClickHandler clickHandler){
		final SelectionChoiceButton button = getButton(itemNumber, choiceNumber);
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				button.setSelected(!button.isSelected());
				button.updateStyle();
			}
		});
		button.addClickHandler(clickHandler);
	}

	@Override
	public void selectButton(int itemNumber, int choiceNumber){
		SelectionChoiceButton selectionChoiceButton = getButton(itemNumber, choiceNumber);
		selectionChoiceButton.select();
	}

	@Override
	public void unselectButton(int itemNumber, int choiceNumber) {
		SelectionChoiceButton selectionChoiceButton = getButton(itemNumber, choiceNumber);
		selectionChoiceButton.unselect();
	}

	private SelectionChoiceButton getButton(int itemNumber, int choiceNumber) {
		List<SelectionChoiceButton> buttonsOfItem;
		String exceptionMessage = "Cannot select button of itemNumber: "+itemNumber+", choiceNumber:"+choiceNumber+" that is not existing!";

		try{
			buttonsOfItem = choiceButtons.get(itemNumber);
		}catch(ArrayIndexOutOfBoundsException e){
			throw new RuntimeException(exceptionMessage);
		}

		SelectionChoiceButton selectionChoiceButton;
		try{
			selectionChoiceButton = buttonsOfItem.get(choiceNumber);
		}catch(ArrayIndexOutOfBoundsException e){
			throw new RuntimeException(exceptionMessage);
		}

		return selectionChoiceButton;
	}

	@Override
	public void lockButton(boolean lock, int itemNumber, int choiceNumber){
		SelectionChoiceButton button = getButton(itemNumber, choiceNumber);
		button.setButtonEnabled(!lock);
	}

	@Override
	public void updateButtonStyle(int itemNumber, int choiceNumber, UserAnswerType styleState){
		SelectionChoiceButton button = getButton(itemNumber, choiceNumber);
		Widget parentPanel = button.getParent();

		switch (styleState) {
			case CORRECT:
				parentPanel.setStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_CORRECT());
				break;
			case WRONG:
				parentPanel.setStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_WRONG());
				break;
			case DEFAULT:
				parentPanel.setStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_INACTIVE());
				break;
			case NONE:
				parentPanel.setStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_NONE());
				break;
			default:
				break;
		}

		button.updateStyle();
	}

	@Override
	public Widget asWidget() {
		return mainPanel;
	}
}
