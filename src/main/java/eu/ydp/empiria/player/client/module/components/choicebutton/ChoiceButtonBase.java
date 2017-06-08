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

package eu.ydp.empiria.player.client.module.components.choicebutton;

import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public abstract class ChoiceButtonBase extends CustomPushButton implements ISelectableButton {

    protected boolean selected;
    protected boolean over;
    protected String moduleStyleNamePart;

    public ChoiceButtonBase(String moduleStyleNamePart) {
        selected = false;
        over = false;
        this.moduleStyleNamePart = moduleStyleNamePart;
    }

    public void setButtonEnabled(boolean value) {
        setEnabled(value);
        updateStyle();
    }

    public void setSelected(boolean value) {
        selected = value;
        updateStyle();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setMouseOver(boolean o) {
        over = o;
        updateStyle();
    }

    @Override
    public void select() {
        setSelected(true);
    }

    @Override
    public void unselect() {
        setSelected(false);
    }

    protected void updateStyle() {
        String styleName = findStyleName();
        setStyleName(styleName);
    }

    protected String findStyleName() {
        String styleName = "qp-" + moduleStyleNamePart + "-button";
        if (selected) {
            styleName += "-selected";
        } else {
            styleName += "-notselected";
        }
        if (!isEnabled()) {
            styleName += "-disabled";
        }
        if (over) {
            styleName += "-over";
        }
        return styleName;
    }
}
