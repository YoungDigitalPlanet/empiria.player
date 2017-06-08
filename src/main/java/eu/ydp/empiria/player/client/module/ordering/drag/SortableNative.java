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

package eu.ydp.empiria.player.client.module.ordering.drag;

import com.google.inject.Singleton;

@Singleton
public class SortableNative {

    public native void init(String selector, String dragAxis, SortCallback callback) /*-{
        $wnd
            .jQuery(selector)
            .sortable(
            {

                containment: ".qp-player",
                axis: dragAxis,
                start: function (event, ui) {
                    this.from = ui.item.index();
                    callback.@eu.ydp.empiria.player.client.module.ordering.drag.SortCallback::setSwypeLock(Z)(true);
                },
                stop: function (event, ui) {
                    var to = ui.item.index();
                    if (to != this.from) {
                        callback.@eu.ydp.empiria.player.client.module.ordering.drag.SortCallback::sortStoped(II)(this.from, to);
                    }
                    callback.@eu.ydp.empiria.player.client.module.ordering.drag.SortCallback::setSwypeLock(Z)(false);
                }
            });
        $wnd.jQuery(selector).disableSelection();
    }-*/;

    public native void enable(String selector) /*-{
        $wnd.jQuery(selector).sortable("enable");
    }-*/;

    public native void disable(String selector) /*-{
        $wnd.jQuery(selector).sortable("disable");
    }-*/;
}
