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

package eu.ydp.empiria.player.client.module.drawing.toolbox;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.drawing.toolbox.view.ToolboxButton;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;

public class ToolboxButtonCreator {

    @Inject
    private Provider<ToolboxButton> buttonProvider;
    private ToolboxPresenter presenter;

    public void setPresenter(ToolboxPresenter presenter) {
        this.presenter = presenter;
    }

    public ToolboxButton createPaletteButton(ColorModel colorModel) {
        ToolboxButton toolboxButton = buttonProvider.get();
        toolboxButton.setColor(colorModel);
        addClickHandler(colorModel, toolboxButton);
        return toolboxButton;
    }

    private void addClickHandler(ColorModel colorModel, ToolboxButton toolboxButton) {
        ClickHandler handler = createPaletteButtonClickHandler(colorModel);
        toolboxButton.addClickHandler(handler);
    }

    private ClickHandler createPaletteButtonClickHandler(final ColorModel color) {
        return new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (presenter != null) {
                    presenter.colorClicked(color);
                }
            }
        };
    }

}
