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

import java.util.HashMap;
import java.util.Map;

public class SelectionModuleViewImpl implements SelectionModuleView {

    public SelectionModuleViewImpl() {
    }

    ;

    private SelectionElementGenerator gridElementGenerator;

    private final Map<SelectionGridElementPosition, SelectionButtonGridElement> buttonsGridMap = new HashMap<SelectionGridElementPosition, SelectionButtonGridElement>();

    @UiField
    Panel mainPanel;

    @UiField
    Widget promptWidget;

    @UiField
    Grid selectionGrid;

    @Inject
    public SelectionModuleViewImpl(SelectionElementGenerator gridElementGenerator) {
        this.gridElementGenerator = gridElementGenerator;
    }

    @UiTemplate("SelectionModuleView.ui.xml")
    interface SelectionModuleUiBinder extends UiBinder<Widget, SelectionModuleViewImpl> {
    }

    @Override
    public void initialize(InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        SelectionModuleUiBinder uiBinder = GWT.create(SelectionModuleUiBinder.class);
        uiBinder.createAndBindUi(this);
        gridElementGenerator.setInlineBodyGenerator(inlineBodyGeneratorSocket);
    }

    @Override
    public void setGridSize(int amountOfItems, int amountOfChoices) {
        selectionGrid.resize(amountOfItems + 1, amountOfChoices + 1);
    }

    @Override
    public void setItemDisplayedName(XMLContent itemName, SelectionGridElementPosition position) {
        SelectionItemGridElement itemTextGridElement = gridElementGenerator.createItemDisplayElement(itemName.getValue());
        addToGrid(itemTextGridElement, position);
    }

    @Override
    public void setChoiceOptionDisplayedName(XMLContent choiceName, SelectionGridElementPosition position) {
        SelectionChoiceGridElement choiseTextGridElement = gridElementGenerator.createChoiceDisplayElement(choiceName.getValue());
        addToGrid(choiseTextGridElement, position);
    }

    @Override
    public void createButtonForItemChoicePair(SelectionGridElementPosition position, String moduleStyleName) {
        SelectionButtonGridElement choiceButtonGridElement = gridElementGenerator.createChoiceButtonElement(moduleStyleName);
        addToGrid(choiceButtonGridElement, position);
        buttonsGridMap.put(position, choiceButtonGridElement);
    }

    @Override
    public void addClickHandlerToButton(SelectionGridElementPosition position, ClickHandler clickHandler) {
        final SelectionButtonGridElement gridElement = buttonsGridMap.get(position);
        gridElement.addClickHandler(clickHandler);
    }

    @Override
    public void selectButton(SelectionGridElementPosition position) {
        SelectionButtonGridElement gridElement = buttonsGridMap.get(position);
        gridElement.select();
    }

    @Override
    public void unselectButton(SelectionGridElementPosition position) {
        SelectionButtonGridElement gridElement = buttonsGridMap.get(position);
        gridElement.unselect();
    }

    @Override
    public void lockButton(SelectionGridElementPosition position, boolean lock) {
        SelectionButtonGridElement gridElement = buttonsGridMap.get(position);
        gridElement.setButtonEnabled(!lock);
    }

    @Override
    public void updateButtonStyle(SelectionGridElementPosition position, UserAnswerType styleState) {
        SelectionButtonGridElement gridElement = buttonsGridMap.get(position);
        gridElement.updateStyle(styleState);
    }

    @Override
    public Widget asWidget() {
        return mainPanel;
    }

    private <V extends SelectionGridElement> void addToGrid(V gridElement, SelectionGridElementPosition position) {
        selectionGrid.setWidget(position.getRowNumber(), position.getColumnNumber(), gridElement.asWidget());
    }
}
