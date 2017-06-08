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

package eu.ydp.empiria.player.client.module.gap;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.UIObject;

public abstract class GapModulePesenterBase implements GapModulePresenter {

    public abstract UIObject getComponent();

    @Override
    public void setWidth(double value, Unit unit) {
        getComponent().setWidth(value + unit.getType());
    }

    @Override
    public int getOffsetWidth() {
        return getComponent().getOffsetWidth();
    }

    @Override
    public void setHeight(double value, Unit unit) {
        getComponent().setHeight(value + unit.getType());
    }

    @Override
    public int getOffsetHeight() {
        return getComponent().getOffsetHeight();
    }

    @Override
    public void setFontSize(double value, Unit unit) {
        getComponent().getElement().getStyle().setFontSize(value, unit);
    }

    @Override
    public int getFontSize() {
        return Integer.parseInt(getComponent().getElement().getStyle().getFontSize().replace("px", ""));
    }
}
