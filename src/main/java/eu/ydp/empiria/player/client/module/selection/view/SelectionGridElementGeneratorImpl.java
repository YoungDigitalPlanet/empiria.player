/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.module.selection.view;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersStyleNameConstants;
import eu.ydp.empiria.player.client.module.selection.SelectionStyleNameConstants;
import eu.ydp.empiria.player.client.module.selection.model.SelectionGridElementPosition;
import eu.ydp.gwtutil.client.event.factory.EventHandlerProxy;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class SelectionGridElementGeneratorImpl implements SelectionElementPositionGenerator, SelectionElementGenerator {

    public static final int ROWS_RESERVED_FOR_COLUMN_HEADER = 1;
    public static final int COLUMNS_RESERVED_FOR_ROW_HEADER = 1;

    private MarkAnswersStyleNameConstants styleNameConstants;
    private SelectionStyleNameConstants selectionStyleNameConstants;
    private UserInteractionHandlerFactory userInteractionHandlerFactory;
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;

    @Override
    public void setInlineBodyGenerator(InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        this.inlineBodyGeneratorSocket = inlineBodyGeneratorSocket;
    }

    @Inject
    public SelectionGridElementGeneratorImpl(MarkAnswersStyleNameConstants styleNameConstants, UserInteractionHandlerFactory userInteractionHandlerFactory, SelectionStyleNameConstants selectionStyleNameConstants) {
        this.styleNameConstants = styleNameConstants;
        this.userInteractionHandlerFactory = userInteractionHandlerFactory;
        this.selectionStyleNameConstants = selectionStyleNameConstants;
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

        itemTextLabel.setStyleName(selectionStyleNameConstants.QP_SELECTION_ITEM_LABEL());
        itemTextLabel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_INACTIVE());

        itemLabelElement.setStyleName(selectionStyleNameConstants.QP_SELECTION_ITEM());
        itemLabelElement.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_INACTIVE());
        itemLabelElement.add(itemTextLabel);
        return itemLabelElement;
    }

    @Override
    public SelectionChoiceGridElementImpl createChoiceDisplayElement(Element item) {
        SelectionChoiceGridElementImpl choiceLabelElement = new SelectionChoiceGridElementImpl();
        Widget choiceTextLabel = inlineBodyGeneratorSocket.generateInlineBody(item);
        choiceTextLabel.setStyleName(selectionStyleNameConstants.QP_SELECTION_CHOICE());

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
