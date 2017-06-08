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

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.colorfill.ColorfillStyleNameConstants;
import eu.ydp.empiria.player.client.module.colorfill.structure.Image;

import javax.annotation.PostConstruct;

public class ColorfillCorrectImage implements IsWidget {

    private final FlowPanel image;
    private final ColorfillStyleNameConstants styleNameConstants;

    @Inject
    public ColorfillCorrectImage(ColorfillStyleNameConstants styleNameConstants) {
        this.styleNameConstants = styleNameConstants;
        image = new FlowPanel();
    }

    @PostConstruct
    public void initializeView() {
        hide();
        image.setStyleName(styleNameConstants.QP_COLORFILL_CORRECT_IMG());
    }

    public void setImageUrl(Image correctImage) {
        Style style = image.getElement().getStyle();
        style.setBackgroundImage("url(" + correctImage.getSrc() + ")");

        String px = Unit.PX.toString().toLowerCase();
        String width = correctImage.getWidth() + px;
        String height = correctImage.getHeight() + px;
        image.setSize(width, height);
    }

    @Override
    public Widget asWidget() {
        return image;
    }

    public void hide() {
        image.setVisible(false);
    }

    public void show() {
        image.setVisible(true);
    }

}
