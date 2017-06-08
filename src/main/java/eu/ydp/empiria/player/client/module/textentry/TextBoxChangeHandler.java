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

package eu.ydp.empiria.player.client.module.textentry;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.event.dom.client.DropHandler;
import com.google.gwt.user.client.ui.TextBox;
import eu.ydp.empiria.player.client.module.gap.GapBase.PresenterHandler;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;

public class TextBoxChangeHandler implements BlurHandler, DropHandler {

    private PresenterHandler presenterHandler;

    public void bind(DroppableObject<TextBox> listBox, PresenterHandler presenterHandler) {
        if (presenterHandler != null) {
            this.presenterHandler = presenterHandler;
            listBox.getOriginalWidget().addBlurHandler(this);
            listBox.addDropHandler(this);
        }
    }

    @Override
    public void onDrop(DropEvent event) {
        BlurEvent noopBlurEvent = new BlurEvent() {
        };
        callBlurHandler(noopBlurEvent);
    }

    @Override
    public void onBlur(BlurEvent event) {
        callBlurHandler(event);
    }

    private void callBlurHandler(BlurEvent event) {
        presenterHandler.onBlur(event);
    }

}
