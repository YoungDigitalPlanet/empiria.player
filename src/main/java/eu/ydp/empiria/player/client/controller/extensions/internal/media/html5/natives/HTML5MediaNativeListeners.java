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

package eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.natives;

import com.google.gwt.dom.client.Element;
import eu.ydp.empiria.player.client.util.events.internal.html5.HTML5MediaEventsType;

public class HTML5MediaNativeListeners {

    private HTML5OnMediaEventHandler html5OnMediaEventHandler;

    public void setCallbackListener(HTML5OnMediaEventHandler html5OnMediaEventHandler) {
        this.html5OnMediaEventHandler = html5OnMediaEventHandler;
    }

    public native void addListener(Element audioElement, String eventType)/*-{
        var instance = this;
        audioElement.addEventListener(eventType, function () {
                instance.@eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.natives.HTML5MediaNativeListeners::html5EndedEvent(Ljava/lang/String;)(eventType);
            }
        );
    }-*/;

    private void html5EndedEvent(String eventType) {
        HTML5MediaEventsType html5MediaEvent = HTML5MediaEventsType.valueOf(eventType);
        html5OnMediaEventHandler.onHtml5MediaEvent(html5MediaEvent);
    }
}
