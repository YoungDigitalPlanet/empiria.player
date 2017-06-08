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

package eu.ydp.empiria.player.client.module.drawing.toolbox.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.drawing.DrawingStyleNameConstants;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public class ToolboxButton extends Composite implements HasClickHandlers {

    @UiField
    CustomPushButton button;
    @Inject
    private DrawingStyleNameConstants styleNames;
    private String currentColorStyle;

    private static ToolboxButtonUiBinder uiBinder = GWT.create(ToolboxButtonUiBinder.class);

    interface ToolboxButtonUiBinder extends UiBinder<Widget, ToolboxButton> {
    }

    @Inject
    public ToolboxButton() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void select() {
        button.addStyleName(styleNames.QP_DRAWING_TOOLBOX_TOOL_SELECTED());
    }

    public void unselect() {
        button.removeStyleName(styleNames.QP_DRAWING_TOOLBOX_TOOL_SELECTED());
    }

    public void setColor(ColorModel colorModel) {
        button.removeStyleDependentName(currentColorStyle);
        currentColorStyle = colorModel.toStringRgba();
        button.addStyleDependentName(currentColorStyle);
    }

    @Override
    public HandlerRegistration addClickHandler(ClickHandler handler) {
        return button.addClickHandler(handler);
    }
}
