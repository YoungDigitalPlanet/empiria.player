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

package eu.ydp.empiria.player.client.module.identification.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.identification.IdentificationStyleNameConstants;

public class SelectableChoiceViewImpl extends Composite implements SelectableChoiceView {

    private static SelectableChoiceUiBinder uiBinder = GWT.create(SelectableChoiceUiBinder.class);

    @UiTemplate("SelectableChoiceView.ui.xml")
    interface SelectableChoiceUiBinder extends UiBinder<Widget, SelectableChoiceViewImpl> {
    }

    @UiField
    AbsolutePanel cover;

    @UiField
    FlowPanel panel;

    @UiField
    FlowPanel optionPanel;

    @UiField(provided = true)
    Widget contentWidget;

    private final IdentificationStyleNameConstants styleNameConstants;

    @Inject
    public SelectableChoiceViewImpl(@Assisted Widget contentWidget, IdentificationStyleNameConstants styleNameConstants) {
        this.contentWidget = contentWidget;
        this.styleNameConstants = styleNameConstants;
        uiBinder.createAndBindUi(this);
        initWidget(panel);
    }

    @Override
    public void setCoverId(String coverId) {
        cover.getElement().setId(coverId);
    }

    @Override
    public void markNotSelectedAnswerCorrect() {
        optionPanel.setStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_NOTSELECTED_CORRECT());
    }

    @Override
    public void markNotSelectedAnswerWrong() {
        optionPanel.setStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_NOTSELECTED_WRONG());
    }

    @Override
    public void markSelectedAnswerCorrect() {
        optionPanel.setStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_SELECTED_CORRECT());
    }

    @Override
    public void markSelectedAnswerWrong() {
        optionPanel.setStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_SELECTED_WRONG());
    }

    @Override
    public void markSelectedOption() {
        optionPanel.setStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_SELECTED());
    }

    @Override
    public void unmarkSelectedOption() {
        optionPanel.setStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION());
    }

    @Override
    public void lock() {
        optionPanel.addStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_LOCKED());
    }

    @Override
    public void unlock() {
        optionPanel.removeStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_LOCKED());
    }
}
