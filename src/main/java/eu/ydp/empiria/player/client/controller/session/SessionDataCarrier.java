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

package eu.ydp.empiria.player.client.controller.session;

import com.google.gwt.core.client.JavaScriptObject;

public class SessionDataCarrier {

    public SessionDataCarrier() {

    }

    public int[] dones;
    public int[] todos;
    public int[] times;
    public int[] checks;
    public int[] mistakes;
    public boolean[] visiteds;
    public int doneTotal;
    public int todoTotal;
    public int timeTotal;
    public int visitedCount;

    public JavaScriptObject toJsObject() {
        return createJsObject();
    }

    private native JavaScriptObject createJsObject()/*-{
        var obj = [];
        var instance = this;
        obj.getTimeTotal = function () {
            return instance.@eu.ydp.empiria.player.client.controller.session.SessionDataCarrier::timeTotal;
        }
        obj.getVariableTotalValue = function () {
            return instance.@eu.ydp.empiria.player.client.controller.session.SessionDataCarrier::timeTotal;
        }
        obj.getDoneTotal = function () {
            return instance.@eu.ydp.empiria.player.client.controller.session.SessionDataCarrier::doneTotal;
        }
        obj.getTodoTotal = function () {
            return instance.@eu.ydp.empiria.player.client.controller.session.SessionDataCarrier::todoTotal;
        }
        obj.getVisitedCount = function () {
            return instance.@eu.ydp.empiria.player.client.controller.session.SessionDataCarrier::visitedCount;
        }
        return obj;
    }-*/;
}
