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

package eu.ydp.empiria.player.client.module.labelling.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets.ForIsWidget;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

public class LabellingChildViewImpl extends Composite implements LabellingChildView {

    private static LabellingChildContainerUiBinder uiBinder = GWT.create(LabellingChildContainerUiBinder.class);

    interface LabellingChildContainerUiBinder extends UiBinder<Widget, LabellingChildViewImpl> {
    }

    @UiField
    FlowPanel container;

    public LabellingChildViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public IsWidget getView() {
        return this;
    }

    @Override
    public ForIsWidget getContainer() {
        return container;
    }

    @Override
    public void setPosition(int left, int top) {
        Style style = container.getElement().getStyle();
        style.setLeft(left, Unit.PX);
        style.setTop(top, Unit.PX);
    }

}
