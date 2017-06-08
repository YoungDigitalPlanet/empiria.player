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

package eu.ydp.empiria.player.client.controller.communication;

import com.google.gwt.core.client.JavaScriptObject;

public class FlowOptions {

    public FlowOptions() {
        showToC = false;
        showSummary = false;
        itemsDisplayMode = PageItemsDisplayMode.ONE;
        activityMode = ActivityMode.NORMAL;
    }

    public FlowOptions(boolean toc, boolean summary, PageItemsDisplayMode pidm, ActivityMode activityMode) {
        showToC = toc;
        showSummary = summary;
        itemsDisplayMode = pidm;
        this.activityMode = activityMode;
    }

    public boolean showToC;
    public boolean showSummary;
    public PageItemsDisplayMode itemsDisplayMode;
    public ActivityMode activityMode;

    public JavaScriptObject toJsObject() {
        return createJsObject();
    }

    private native JavaScriptObject createJsObject()/*-{
        var obj = [];
        var instance = this;
        obj.isShowToc = function () {
            return instance.@eu.ydp.empiria.player.client.controller.communication.FlowOptions::showToC;
        }
        obj.isShowSummary = function () {
            return instance.@eu.ydp.empiria.player.client.controller.communication.FlowOptions::showSummary;
        }
        obj.getPageItemsDisplayMode = function () {
            return instance.@eu.ydp.empiria.player.client.controller.communication.FlowOptions::getItemsDisplayModeString()();
        }
        obj.getActivityMode = function () {
            return instance.@eu.ydp.empiria.player.client.controller.communication.FlowOptions::getActivityModeString()();
        }
        return obj;
    }-*/;

    private String getItemsDisplayModeString() {
        if (itemsDisplayMode != null)
            return itemsDisplayMode.toString();
        return PageItemsDisplayMode.ONE.toString();
    }

    private String getActivityModeString() {
        if (activityMode != null)
            return activityMode.toString();
        return ActivityMode.NORMAL.toString();
    }

    public static FlowOptions fromJsObject(JavaScriptObject o) {

        if (o == null) {
            return new FlowOptions();
        }

        FlowOptions fo = new FlowOptions();
        try {
            fo.showToC = decodeFlowOptionsObjectShowToC(o);
        } catch (Exception e) {
        }
        try {
            fo.showSummary = decodeFlowOptionsObjectShowSummary(o);
        } catch (Exception e) {
        }
        try {
            fo.itemsDisplayMode = (decodeFlowOptionsObjectPageItemsDisplayMode(o).compareTo(PageItemsDisplayMode.ALL.toString()) == 0) ? PageItemsDisplayMode.ALL
                    : PageItemsDisplayMode.ONE;
        } catch (Exception e) {
        }
        return fo;
    }

    private native static boolean decodeFlowOptionsObjectShowToC(JavaScriptObject obj)/*-{
        return obj.showToC;
    }-*/;

    private native static boolean decodeFlowOptionsObjectShowSummary(JavaScriptObject obj)/*-{
        return obj.showSummary;
    }-*/;

    private native static String decodeFlowOptionsObjectPageItemsDisplayMode(JavaScriptObject obj)/*-{
        return obj.itemsDisplayMode;
    }-*/;
}
