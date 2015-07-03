package eu.ydp.empiria.player.client.module.selection.view;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.selection.model.SelectionGridElementPosition;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.event.factory.EventHandlerProxy;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class SelectionGridElementGeneratorImpl implements SelectionElementPositionGenerator, SelectionElementGenerator {

    public static final int ROWS_RESERVED_FOR_COLUMN_HEADER = 1;
    public static final int COLUMNS_RESERVED_FOR_ROW_HEADER = 1;

    private StyleNameConstants styleNameConstants;
    private UserInteractionHandlerFactory userInteractionHandlerFactory;
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;

    @Override
    public void setInlineBodyGenerator(InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        this.inlineBodyGeneratorSocket = inlineBodyGeneratorSocket;
    }

    @Inject
    public SelectionGridElementGeneratorImpl(StyleNameConstants styleNameConstants, UserInteractionHandlerFactory userInteractionHandlerFactory) {
        this.styleNameConstants = styleNameConstants;
        this.userInteractionHandlerFactory = userInteractionHandlerFactory;
    }

    @Override
    public SelectionButtonGridElementImpl createChoiceButtonElement(String styleName) {
        SelectionButtonGridElementImpl choiceButtonGridElement = new SelectionButtonGridElementImpl(styleNameConstants);
        SelectionChoiceButton choiceButton = new SelectionChoiceButton(styleName);
        addMouseOverHandler(choiceButton);

        choiceButtonGridElement.setStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_INACTIVE());
        choiceButtonGridElement.add(choiceButton);
        return choiceButtonGridElement;
    }

    @Override
    public SelectionItemGridElementImpl createItemDisplayElement(Element item) {
        SelectionItemGridElementImpl itemLabelElement = new SelectionItemGridElementImpl();
        Widget itemTextLabel = inlineBodyGeneratorSocket.generateInlineBody(item, true);

        itemTextLabel.setStyleName(styleNameConstants.QP_SELECTION_ITEM_LABEL());
        itemTextLabel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_INACTIVE());

        itemLabelElement.setStyleName(styleNameConstants.QP_SELECTION_ITEM());
        itemLabelElement.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_INACTIVE());
        itemLabelElement.add(itemTextLabel);
        return itemLabelElement;
    }

    @Override
    public SelectionChoiceGridElementImpl createChoiceDisplayElement(Element item) {
        SelectionChoiceGridElementImpl choiceLabelElement = new SelectionChoiceGridElementImpl();
        Widget choiceTextLabel = inlineBodyGeneratorSocket.generateInlineBody(item);
        choiceTextLabel.setStyleName(styleNameConstants.QP_SELECTION_CHOICE());

        choiceLabelElement.add(choiceTextLabel);
        return choiceLabelElement;
    }

    @Override
    public SelectionGridElementPosition getButtonElementPositionFor(int itemIndex, int choiceIndex) {
        return new SelectionGridElementPosition(choiceIndex + COLUMNS_RESERVED_FOR_ROW_HEADER, itemIndex + ROWS_RESERVED_FOR_COLUMN_HEADER);
    }

    @Override
    public SelectionGridElementPosition getChoiceLabelElementPosition(int choiceIndex) {
        return new SelectionGridElementPosition(choiceIndex + COLUMNS_RESERVED_FOR_ROW_HEADER, 0);
    }

    @Override
    public SelectionGridElementPosition getItemLabelElementPosition(int itemIndex) {
        return new SelectionGridElementPosition(0, itemIndex + COLUMNS_RESERVED_FOR_ROW_HEADER);
    }

    private void addMouseOverHandler(final SelectionChoiceButton button) {
        EventHandlerProxy userOverHandler = userInteractionHandlerFactory.createUserOverHandler(new ChoiceButtonMouseInteractionCommand(button, true));
        userOverHandler.apply(button);

        EventHandlerProxy userOutHandler = userInteractionHandlerFactory.createUserOutHandler(new ChoiceButtonMouseInteractionCommand(button, false));
        userOutHandler.apply(button);
    }
}
