package eu.ydp.empiria.player.client.module.selection.view;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.event.factory.EventHandlerProxy;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class SelectionGridElementGenerator {

	private StyleNameConstants styleNameConstants;
	private UserInteractionHandlerFactory userInteractionHandlerFactory;
	private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;
	
	public void setInlineBodyGenerator(InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
		this.inlineBodyGeneratorSocket = inlineBodyGeneratorSocket;
	}
	
	@Inject
	public SelectionGridElementGenerator(
			StyleNameConstants styleNameConstants,
			UserInteractionHandlerFactory userInteractionHandlerFactory) {
		this.styleNameConstants = styleNameConstants;
		this.userInteractionHandlerFactory = userInteractionHandlerFactory;
	}
	
	public SelectionButtonGridElement createChoiceButtonElement(String styleName) {
		SelectionButtonGridElement choiceButtonGridElement = new SelectionButtonGridElement(styleNameConstants);
		SelectionChoiceButton choiceButton = new SelectionChoiceButton(styleName);
		addMouseOverHandler(choiceButton);
		
		choiceButtonGridElement.setStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_INACTIVE());
		choiceButtonGridElement.add(choiceButton);
		return choiceButtonGridElement;
	}
	
	public SelectionItemLabelGridElement createItemDisplayElement(Element item) {
		SelectionItemLabelGridElement itemLabelElement = new SelectionItemLabelGridElement();
		Widget itemTextLabel = inlineBodyGeneratorSocket.generateInlineBody(item, true);

		itemTextLabel.setStyleName(styleNameConstants.QP_SELECTION_ITEM_LABEL());
		itemTextLabel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_INACTIVE());
		
		itemLabelElement.setStyleName(styleNameConstants.QP_SELECTION_ITEM());
		itemLabelElement.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_INACTIVE());
		itemLabelElement.add(itemTextLabel);
		return itemLabelElement;
	}
	
	public SelectionChoiceLabelGridElement createChoiceDisplayElement(Element item) {
		SelectionChoiceLabelGridElement choiceLabelElement = new SelectionChoiceLabelGridElement();
		Widget choiceTextLabel = inlineBodyGeneratorSocket.generateInlineBody(item);
		choiceTextLabel.setStyleName(styleNameConstants.QP_SELECTION_CHOICE());

		// TODO choiceTextLabel wasn't placed inside flowPanel
		choiceLabelElement.add(choiceTextLabel);
		return choiceLabelElement;
	}
	
	private void addMouseOverHandler(final SelectionChoiceButton button) {
		EventHandlerProxy userOverHandler = userInteractionHandlerFactory.createUserOverHandler(new ChoiceButtonMouseInteractionCommand(button, true));
		userOverHandler.apply(button);

		EventHandlerProxy userOutHandler = userInteractionHandlerFactory.createUserOutHandler(new ChoiceButtonMouseInteractionCommand(button, false));
		userOutHandler.apply(button);
	}
}
