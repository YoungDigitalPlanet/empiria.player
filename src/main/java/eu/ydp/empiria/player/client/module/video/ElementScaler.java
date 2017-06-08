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

package eu.ydp.empiria.player.client.module.video;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style;

public class ElementScaler {

    private final String MAX_WIDTH_PROPERTY = "maxWidth";

    private final Element element;
    private final double initialRatio;

    public ElementScaler(Element element) {
        this.element = element;
        initialRatio = calculateRatio();
    }

    private double calculateRatio() {
        int width = element.getClientWidth();
        int height = element.getClientHeight();
        return 100.0 * height / width;
    }

    public void setRatio() {
        Style style = element.getStyle();

        style.clearWidth();
        style.clearHeight();

        style.setPaddingTop(initialRatio, Style.Unit.PCT);
    }

    public void clearRatio() {
        Style style = element.getStyle();
        style.clearPaddingTop();
    }

    public void setMaxWidth(int width) {
        Style style = element.getParentElement().getStyle();
        style.setProperty(MAX_WIDTH_PROPERTY, width, Style.Unit.PX);
    }

    public void clearMaxWidth() {
        Style style = element.getParentElement().getStyle();
        style.clearProperty(MAX_WIDTH_PROPERTY);
    }
}
