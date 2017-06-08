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

package eu.ydp.empiria.player.client.module.menu.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public class MenuViewImpl extends Composite implements MenuView {

    @UiTemplate("MenuView.ui.xml")
    interface ReportViewUiBinder extends UiBinder<FlowPanel, MenuViewImpl> {
    }

    private static ReportViewUiBinder uiBinder = GWT.create(ReportViewUiBinder.class);

    @UiField
    FlowPanel menuWrapper;
    @UiField
    FlowPanel tableContainer;
    @UiField
    CustomPushButton button;

    public MenuViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setTable(FlexTable table) {
        tableContainer.clear();
        tableContainer.add(table);
    }

    @Override
    public void addClickHandler(ClickHandler clickHandler) {
        button.addClickHandler(clickHandler);
    }
}
