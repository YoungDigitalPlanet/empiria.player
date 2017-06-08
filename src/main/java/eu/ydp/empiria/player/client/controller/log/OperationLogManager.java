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

package eu.ydp.empiria.player.client.controller.log;

import eu.ydp.empiria.player.client.controller.messages.OperationMessage;
import eu.ydp.empiria.player.client.controller.messages.OperationMessagePoint;
import eu.ydp.empiria.player.client.controller.messages.OperationMessageType;
import eu.ydp.empiria.player.client.util.localisation.LocaleVariable;

public abstract class OperationLogManager {

    public static void logEvent(OperationLogEvent evt) {

        if (evt == OperationLogEvent.LOADING_STARTED) {
            loadingMsg = new OperationMessage(LocaleVariable.MESSAGE_LOADING, OperationMessageType.INFO, 0, false);
            OperationMessagePoint.showMessage(loadingMsg);
        } else if (evt == OperationLogEvent.LOADING_FINISHED) {
            if (loadingMsg != null) {
                OperationMessagePoint.hideMessage(loadingMsg);
                loadingMsg = null;
            }
            OperationMessagePoint.showMessage(new OperationMessage(LocaleVariable.MESSAGE_LOADED, OperationMessageType.INFO, 3000, true));
        } else if (evt == OperationLogEvent.DISPLAY_PAGE_FAILED) {
            OperationMessagePoint.showMessage(new OperationMessage(LocaleVariable.MESSAGE_ASSESSMENT_ERROR, OperationMessageType.ERROR, 3000, true));
        } else if (evt == OperationLogEvent.DISPLAY_ITEM_FAILED) {
            OperationMessagePoint.showMessage(new OperationMessage(LocaleVariable.MESSAGE_ITEM_ERROR, OperationMessageType.ERROR, 3000, true));
        }

    }

    private static OperationMessage loadingMsg;

}
