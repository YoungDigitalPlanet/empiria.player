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

package eu.ydp.empiria.player.client.module.dictionary.external.controller;

public final class Options {

    private Options() {
    }

    public static ViewType getViewType() {
        if (isViewTypeHalf()) {
            return ViewType.HALF;
        }
        return ViewType.FULL;
    }

    private static native boolean isViewTypeHalf()/*-{
        if (typeof $wnd.mskillsDictionaryOptionsViewTypeHalf == 'boolean') {
            return $wnd.mskillsDictionaryOptionsViewTypeHalf;
        }
        return false;
    }-*/;

}