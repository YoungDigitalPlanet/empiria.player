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

package eu.ydp.empiria.player.client.components.event;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.IsWidget;

public class InputEventRegistrar {

    public void registerInputHandler(IsWidget widget, InputEventListener listener) {
        InputEventListenerJsWrapper listenerJs = new InputEventListenerJsWrapper(listener);
        registerInputHandler(widget.asWidget().getElement(), listenerJs.getJavaScriptObject());
    }

    private native void registerInputHandler(Element element, JavaScriptObject listenerJs)/*-{
        var self = this;
        element.oninput = function () {
            listenerJs.onInput();
        }
    }-*/;
}
