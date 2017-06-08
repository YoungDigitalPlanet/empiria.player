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

package eu.ydp.empiria.player.client.module;

import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.module.core.base.IUniqueModule;
import eu.ydp.empiria.player.client.module.core.flow.Activity;
import eu.ydp.empiria.player.client.module.core.flow.Lockable;
import eu.ydp.empiria.player.client.module.core.flow.Resetable;

public class ModuleJsSocketFactory {

    public static JavaScriptObject createSocketObject(Object module) {
        JavaScriptObject jso = JavaScriptObject.createObject();

        jso = addObjectFunctions(jso, module.getClass().getName());

        if (module instanceof Lockable) {
            jso = addLockableFunctions(jso, module);
        }

        if (module instanceof Resetable) {
            jso = addResetableFunctions(jso, module);
        }

        if (module instanceof Activity) {
            jso = addActivityFunctions(jso, module);
        }

        if (module instanceof IUniqueModule) {
            jso = addUniqueFunctions(jso, module);
        }

        return jso;
    }

    private static native JavaScriptObject addObjectFunctions(JavaScriptObject jso, String className)/*-{
        var instanceClassName = className;
        jso.getClassName = function () {
            return instanceClassName;
        }
        return jso;
    }-*/;

    private static native JavaScriptObject addLockableFunctions(JavaScriptObject jso, Object moduleInstance)/*-{
        var instance = moduleInstance;
        jso.lock = function () {
            instance.@eu.ydp.empiria.player.client.module.core.flow.Lockable::lock(Z)(true);
        }
        jso.unlock = function () {
            instance.@eu.ydp.empiria.player.client.module.core.flow.Lockable::lock(Z)(false);
        }
        return jso;
    }-*/;

    private static native JavaScriptObject addResetableFunctions(JavaScriptObject jso, Object moduleInstance)/*-{
        var instance = moduleInstance;
        jso.reset = function () {
            instance.@eu.ydp.empiria.player.client.module.core.flow.Resetable::reset()();
        }
        return jso;
    }-*/;

    private static native JavaScriptObject addActivityFunctions(JavaScriptObject jso, Object moduleInstance)/*-{
        var instance = moduleInstance;
        jso.showAnswers = function (value) {
            instance.@eu.ydp.empiria.player.client.module.core.flow.Activity::showCorrectAnswers(Z)(true);
            instance.@eu.ydp.empiria.player.client.module.core.flow.Activity::lock(Z)(true);
        }
        jso.check = function () {
            instance.@eu.ydp.empiria.player.client.module.core.flow.Activity::markAnswers(Z)(true);
            instance.@eu.ydp.empiria.player.client.module.core.flow.Activity::lock(Z)(true);
        }
        jso.continue1 = function () {
            instance.@eu.ydp.empiria.player.client.module.core.flow.Activity::markAnswers(Z)(false);
            instance.@eu.ydp.empiria.player.client.module.core.flow.Activity::showCorrectAnswers(Z)(false);
            instance.@eu.ydp.empiria.player.client.module.core.flow.Activity::lock(Z)(false);
        }
        return jso;
    }-*/;

    private static native JavaScriptObject addUniqueFunctions(JavaScriptObject jso, Object moduleInstance)/*-{
        var instance = moduleInstance;
        jso.getIdentifier = function () {
            return instance.@eu.ydp.empiria.player.client.module.core.base.IInteractionModule::getIdentifier();
        }
        return jso;
    }-*/;
}
