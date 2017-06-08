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

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.selection.model.SelectionGridElementPosition;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;

public interface SelectionModuleView extends IsWidget {

    void initialize(InlineBodyGeneratorSocket inlineBodyGeneratorSocket);

    void setItemDisplayedName(XMLContent itemName, SelectionGridElementPosition gridElementPosition);

    void setChoiceOptionDisplayedName(XMLContent choiceName, SelectionGridElementPosition gridElementPosition);

    void createButtonForItemChoicePair(SelectionGridElementPosition gridElementPosition, String moduleStyleName);

    void selectButton(SelectionGridElementPosition gridElementPosition);

    void unselectButton(SelectionGridElementPosition gridElementPosition);

    void lockButton(SelectionGridElementPosition gridElementPosition, boolean lock);

    void updateButtonStyle(SelectionGridElementPosition gridElementPosition, UserAnswerType styleState);

    void addClickHandlerToButton(SelectionGridElementPosition gridElementPosition, ClickHandler clickHandler);

    void setGridSize(int amountOfItems, int amountOfChoices);
}
