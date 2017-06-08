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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.drawing.DrawingStyleNameConstants;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxButtonCreator;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.List;

public class ToolboxPalette extends Composite {

    @Inject
    @ModuleScoped
    private ToolboxButtonCreator buttonCreator;
    @Inject
    private DrawingStyleNameConstants styleNames;

    @UiField
    FlowPanel container;
    @UiField
    FlowPanel palette;

    private static ToolboxPaletteUiBinder uiBinder = GWT.create(ToolboxPaletteUiBinder.class);

    interface ToolboxPaletteUiBinder extends UiBinder<Widget, ToolboxPalette> {
    }

    @Inject
    public ToolboxPalette() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    public void hide() {
        container.addStyleName(styleNames.QP_DRAWING_TOOLBOX_PALETTE_HIDDEN());
    }

    public void show() {
        container.removeStyleName(styleNames.QP_DRAWING_TOOLBOX_PALETTE_HIDDEN());
    }

    public void init(List<ColorModel> colorsModel) {
        for (ColorModel colorModel : colorsModel) {
            ToolboxButton button = buttonCreator.createPaletteButton(colorModel);
            palette.add(button);
        }
    }
}
