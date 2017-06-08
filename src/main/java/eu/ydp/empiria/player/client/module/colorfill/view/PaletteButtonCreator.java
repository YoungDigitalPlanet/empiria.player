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

package eu.ydp.empiria.player.client.module.colorfill.view;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class PaletteButtonCreator {

    @Inject
    private Provider<PaletteButton> buttonProvider;

    @Inject
    private UserInteractionHandlerFactory userInteractionHandlerFactory;

    public PaletteButton createButton(ColorModel color, Command command, String description) {
        PaletteButton button = produceButton(color, description);
        applyEventHandler(command, button);
        return button;
    }

    private void applyEventHandler(Command command, IsWidget widget) {
        userInteractionHandlerFactory.applyUserClickHandler(command, widget);
    }

    private PaletteButton produceButton(ColorModel color, String description) {
        PaletteButton button = buttonProvider.get();
        button.setColor(color);
        button.setDescription(description);
        return button;
    }
}
