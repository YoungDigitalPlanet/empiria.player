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

package eu.ydp.empiria.player.client.module.object;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class ObjectModuleView extends Composite {

    private static ObjectModuleViewUiBinder uiBinder = GWT.create(ObjectModuleViewUiBinder.class);

    interface ObjectModuleViewUiBinder extends UiBinder<Widget, ObjectModuleView> {
    }

    @UiField
    protected FlowPanel containerPanel;

    @UiField
    protected FlowPanel contentPanel;

    @UiField
    protected FlowPanel titlePanel;

    @UiField
    protected FlowPanel descriptionPanel;

    public ObjectModuleView() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public FlowPanel getContainerPanel() {
        return containerPanel;
    }

    public FlowPanel getContentPanel() {
        return contentPanel;
    }

    public FlowPanel getDescriptionPanel() {
        return descriptionPanel;
    }

    public void setTitleWidget(IsWidget w) {
        titlePanel.clear();
        titlePanel.add(w);
    }
}
