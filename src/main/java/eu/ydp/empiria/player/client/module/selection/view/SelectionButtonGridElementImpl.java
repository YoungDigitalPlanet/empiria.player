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
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;
import eu.ydp.empiria.player.client.module.core.answer.MarkAnswersStyleNameConstants;
import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;

public class SelectionButtonGridElementImpl extends FlowPanel implements SelectionButtonGridElement {

    private MarkAnswersStyleNameConstants styleNameConstants;

    public SelectionButtonGridElementImpl(MarkAnswersStyleNameConstants styleNameConstants) {
        this.styleNameConstants = styleNameConstants;
    }

    @Override
    public void addClickHandler(ClickHandler clickHandler) {
        getButton().addClickHandler(clickHandler);
    }

    @Override
    public void select() {
        getButton().select();
    }

    @Override
    public void unselect() {
        getButton().unselect();
    }

    @Override
    public void setButtonEnabled(boolean b) {
        getButton().setButtonEnabled(b);
    }

    @Override
    public void updateStyle() {
        getButton().updateStyle();
    }

    @Override
    public void updateStyle(UserAnswerType styleState) {
        this.setStyleName(getButtonStyleNameForState(styleState));
        updateStyle();
    }

    @Override
    public Widget asWidget() {
        return this;
    }

    private SelectionChoiceButton getButton() {
        WidgetCollection children = getChildren();
        // each grid element panel should contain only one child ex. button
        if (children.size() != 1) {
            throw new RuntimeException("SelectionGridElement panel contains " + children.size() + " elements when 1 was expected!");
        }
        SelectionChoiceButton button = (SelectionChoiceButton) children.get(0);
        return button;
    }

    private String getButtonStyleNameForState(UserAnswerType styleState) {
        switch (styleState) {
            case CORRECT:
                return styleNameConstants.QP_MARKANSWERS_BUTTON_CORRECT();
            case WRONG:
                return styleNameConstants.QP_MARKANSWERS_BUTTON_WRONG();
            case DEFAULT:
                return styleNameConstants.QP_MARKANSWERS_BUTTON_INACTIVE();
            case NONE:
                return styleNameConstants.QP_MARKANSWERS_BUTTON_NONE();
            default:
                return "";
        }
    }
}
