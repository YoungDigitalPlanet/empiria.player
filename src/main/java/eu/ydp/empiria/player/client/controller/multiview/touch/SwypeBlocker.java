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

package eu.ydp.empiria.player.client.controller.multiview.touch;

import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBoxOpenCloseListener;
import eu.ydp.gwtutil.client.components.exlistbox.IsExListBox;

public class SwypeBlocker {

    private final TouchController touchController;

    @Inject
    public SwypeBlocker(TouchController touchController) {
        this.touchController = touchController;
    }

    public void addBlockOnOpenCloseHandler(IsExListBox exListBox) {
        exListBox.addOpenCloseListener(new OpenCloseListener());
    }

    class OpenCloseListener implements ExListBoxOpenCloseListener {

        @Override
        public void onClose(CloseEvent<PopupPanel> event) {
            touchController.setSwypeLock(false);

        }

        @Override
        public void onOpen() {
            touchController.setSwypeLock(true);

        }

    }

}
