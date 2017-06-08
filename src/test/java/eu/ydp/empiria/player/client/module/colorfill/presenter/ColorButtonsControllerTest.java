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

package eu.ydp.empiria.player.client.module.colorfill.presenter;

import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.fest.assertions.api.Assertions.assertThat;

public class ColorButtonsControllerTest {

    private ColorButtonsController colorButtonsController;
    private ColorfillInteractionView interactionView;

    @Before
    public void setUp() throws Exception {
        interactionView = Mockito.mock(ColorfillInteractionView.class);
        colorButtonsController = new ColorButtonsController(interactionView);
    }

    @Test
    public void shouldSelectClickedColor() throws Exception {
        ColorModel color = ColorModel.createFromRgbString("00ff00");

        colorButtonsController.colorButtonClicked(color);

        assertThat(colorButtonsController.getCurrentSelectedButtonColor()).isEqualTo(color);
    }

    @Test
    public void shouldSelectAndDeselectButtonWhenDoubleClicked() throws Exception {
        ColorModel color = ColorModel.createFromRgbString("00ff00");

        colorButtonsController.colorButtonClicked(color);
        colorButtonsController.colorButtonClicked(color);

        assertThat(colorButtonsController.getCurrentSelectedButtonColor()).isEqualTo(null);
    }

    @Test
    public void shouldSelectColorAndReplaceItWithNewClicked() throws Exception {
        ColorModel color = ColorModel.createFromRgbString("00ff00");
        ColorModel newColor = ColorModel.createFromRgbString("ff00ff");

        colorButtonsController.colorButtonClicked(color);
        colorButtonsController.colorButtonClicked(newColor);

        assertThat(colorButtonsController.getCurrentSelectedButtonColor()).isEqualTo(newColor);
    }

}
