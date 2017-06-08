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

package eu.ydp.empiria.player.client.module.dictionary.external.components;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.user.client.ui.PushButton;

public class PushButtonWithIndex extends PushButton {

    public PushButtonWithIndex(String text) {
        super(text);
        this.getElement().getElementsByTagName("input").getItem(0).getStyle().setPosition(Position.RELATIVE);
    }

    protected int index = -1;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
