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

package eu.ydp.empiria.player.client.components.hidden;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class HiddenContainerWidget extends Composite {

    private static HiddenContainerWidgetUiBinder uiBinder = GWT.create(HiddenContainerWidgetUiBinder.class);

    interface HiddenContainerWidgetUiBinder extends UiBinder<Widget, HiddenContainerWidget> {
    }

    @UiField
    FlowPanel container;

    public HiddenContainerWidget() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void addWidgetToContainer(IsWidget widget) {
        container.add(widget);
    }

}
