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

package eu.ydp.empiria.player.client.components;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import eu.ydp.empiria.player.client.module.listener.ITouchEventsListener;

public class TouchablePanel extends AbsolutePanel {

    public TouchablePanel(ITouchEventsListener tel) {
        super();
        touchEventsListener = tel;
        registerTouchEvents(this.getElement());
    }

    private ITouchEventsListener touchEventsListener;

    private native void registerTouchEvents(Element element)/*-{
        var instance = this;
        element.ontouchstart = function (e) {
            e.preventDefault();
            if (e.touches.length == 1) {
                var touch = e.touches[0];
                instance.@eu.ydp.empiria.player.client.components.TouchablePanel::processOnTouchStartEvent(II)(touch.pageX, touch.pageY);
            }
        }

        element.ontouchmove = function (e) {
            e.preventDefault();
            if (e.touches.length == 1) {
                var touch = e.touches[0];
                instance.@eu.ydp.empiria.player.client.components.TouchablePanel::processOnTouchMoveEvent(II)(touch.pageX, touch.pageY);
            }
        }

        element.ontouchend = function (e) {
            e.preventDefault();
            instance.@eu.ydp.empiria.player.client.components.TouchablePanel::processOnTouchEndEvent()();
        }

    }-*/;

    private void processOnTouchStartEvent(int x, int y) {
        touchEventsListener.onTouchStart(this.getElement(), x, y);
    }

    private void processOnTouchMoveEvent(int x, int y) {
        touchEventsListener.onTouchMove(this.getElement(), x, y);
    }

    private void processOnTouchEndEvent() {
        touchEventsListener.onTouchEnd(this.getElement());
    }

}
