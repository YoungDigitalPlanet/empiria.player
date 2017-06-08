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

package eu.ydp.empiria.player.client.module.colorfill;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.colorfill.presenter.ColorfillInteractionPresenter;
import eu.ydp.empiria.player.client.module.colorfill.presenter.handlers.AreaClickListener;
import eu.ydp.empiria.player.client.module.colorfill.presenter.handlers.ColorButtonClickListener;
import eu.ydp.empiria.player.client.module.colorfill.structure.*;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.List;

public class ColorfillViewBuilder {

    public static final ColorModel ERASING_COLOR = ColorModel.createFromRgba(0, 0, 0, 0);
    private final ColorfillInteractionView interactionView;

    @Inject
    public ColorfillViewBuilder(@ModuleScoped ColorfillInteractionView interactionView) {
        this.interactionView = interactionView;
    }

    public void buildView(ColorfillInteractionBean bean, ColorfillInteractionPresenter interactionPresenter) {
        createButtons(bean.getButtons());

        setListenersOnView(interactionPresenter);

        Image image = bean.getImage();
        interactionView.setImage(image);

        Image correctlyColoredImage = bean.getCorrectImage();
        if (correctlyColoredImage != null) {
            interactionView.setCorrectImage(correctlyColoredImage);
        }
    }

    private void createButtons(ButtonsContainer buttonsContainer) {
        List<ColorButton> buttons = buttonsContainer.getButtons();
        createColorButtons(buttons);

        EraserButton eraserButton = buttonsContainer.getEraserButton();
        if (eraserButton != null) {
            createEraserButton(eraserButton);
        }
    }

    private void createColorButtons(List<ColorButton> buttons) {
        for (ColorButton colorButton : buttons) {
            ColorModel colorModel = ColorModel.createFromRgbString(colorButton.getRgb());
            interactionView.createButton(colorModel, colorButton.getDescription());
        }
    }

    private void createEraserButton(EraserButton eraserButton) {
        interactionView.createButton(ERASING_COLOR, eraserButton.getDescription());
    }

    private void setListenersOnView(ColorfillInteractionPresenter interactionPresenter) {
        setButtonClickedListener(interactionPresenter);
        setImageColorChangedListener(interactionPresenter);
    }

    private void setImageColorChangedListener(ColorfillInteractionPresenter interactionPresenter) {
        AreaClickListener areaClickListener = new AreaClickListener(interactionPresenter);
        interactionView.setAreaClickListener(areaClickListener);
    }

    private void setButtonClickedListener(ColorfillInteractionPresenter interactionPresenter) {
        ColorButtonClickListener buttonClickListener = new ColorButtonClickListener(interactionPresenter);
        interactionView.setButtonClickListener(buttonClickListener);
    }
}
