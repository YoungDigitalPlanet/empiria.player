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

package eu.ydp.empiria.player.client.controller.messages;

import com.google.gwt.user.client.Window;

import java.util.Vector;

public class OperationMessageManager implements OperationMessageDisplayEventListener {

    public OperationMessageManager() {
        messages = new Vector<OperationMessage>();
    }

    public Vector<OperationMessage> messages;

    public void showMessage(OperationMessage msg) {
        messages.add(msg);
        msg.show(this);
    }

    public void hideMessage(OperationMessage msg) {
        messages.remove(msg);
        msg.hide();
    }

    @Override
    public void onMessageAttaching(OperationMessage msg) {
        msg.setPopupPosition(0, findNextMessageYPosition() - msg.getOffsetHeight());

    }

    @Override
    public void onMessageHided(OperationMessage msg) {
        messages.remove(msg);
    }

    public int findNextMessageYPosition() {
        int y = Window.getClientHeight();
        for (OperationMessage currMsg : messages) {
            if (currMsg.isAttached()) {
                if (currMsg.getAbsoluteTop() < y)
                    y = currMsg.getAbsoluteTop();
            }
        }
        return y;
    }

}
