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

package eu.ydp.empiria.player.client.module.choice.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

public class ChoiceModuleViewImpl implements ChoiceModuleView {

    private static ChoiceModuleViewUiBinder uiBinder = GWT.create(ChoiceModuleViewUiBinder.class);

    @UiTemplate("ChoiceModuleView.ui.xml")
    interface ChoiceModuleViewUiBinder extends UiBinder<Widget, ChoiceModuleViewImpl> {
    }

    @UiField
    Panel mainPanel;

    @UiField
    Widget promptWidget;

    @UiField
    Panel choicesPanel;

    private final Widget widget;

    public ChoiceModuleViewImpl() {
        widget = uiBinder.createAndBindUi(this);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public void clear() {
        choicesPanel.clear();
    }

    @Override
    public void addChoice(Widget widget) {
        choicesPanel.add(widget);
    }

    @Override
    public Element getPrompt() {
        return promptWidget.getElement();
    }
}
