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

package eu.ydp.empiria.player.client.util.config;

import eu.ydp.empiria.player.client.controller.communication.ActivityMode;
import eu.ydp.empiria.player.client.controller.communication.FlowOptions;
import eu.ydp.empiria.player.client.controller.communication.PageItemsDisplayMode;
import eu.ydp.empiria.player.client.controller.messages.OperationMessage;
import eu.ydp.empiria.player.client.controller.messages.OperationMessagePoint;
import eu.ydp.empiria.player.client.controller.messages.OperationMessageType;
import eu.ydp.empiria.player.client.util.localisation.LocalePublisher;
import eu.ydp.empiria.player.client.util.localisation.LocaleVariable;

@Deprecated
public class OptionsReader {

    public static FlowOptions getFlowOptions() {

        FlowOptions fo = new FlowOptions();

        try {
            if (checkVariable("SHOW_TOC"))
                fo.showToC = getValueBoolean("SHOW_TOC");
            if (checkVariable("SHOW_SUMMARY"))
                fo.showSummary = getValueBoolean("SHOW_SUMMARY");
            if (checkVariable("ITEMS_DISPLAY_MODE"))
                fo.itemsDisplayMode = (getValueString("ITEMS_DISPLAY_MODE").equals("ALL")) ? PageItemsDisplayMode.ALL : PageItemsDisplayMode.ONE;
            if (checkVariable("ACTIVITY_MODE"))
                try {
                    fo.activityMode = ActivityMode.valueOf(getValueString("ACTIVITY_MODE"));
                } catch (Exception e) {
                }
        } catch (Exception e) {
            OperationMessage om = new OperationMessage(LocalePublisher.getText(LocaleVariable.MESSAGE_READOPTIONS_ERROR), OperationMessageType.ERROR, 5000,
                    true);
            OperationMessagePoint.showMessage(om);
        }

        return fo;
    }

    private static native boolean checkVariable(String varName)/*-{
        if (typeof $wnd.QtiPlayer !== 'undefined' && $wnd.QtiPlayer != null) {
            if (typeof $wnd.QtiPlayer.flowoptions !== 'undefined' && $wnd.QtiPlayer.flowoptions != null) {
                if (typeof $wnd.QtiPlayer.flowoptions[varName] !== 'undefined' && $wnd.QtiPlayer.flowoptions[varName] != null) {
                    return true;
                }
            }
        }
        return false;
    }-*/;

    private static native String getValueString(String varName)/*-{
        return $wnd.QtiPlayer.flowoptions[varName];
    }-*/;

    private static native boolean getValueBoolean(String varName)/*-{
        return $wnd.QtiPlayer.flowoptions[varName];
    }-*/;
}
