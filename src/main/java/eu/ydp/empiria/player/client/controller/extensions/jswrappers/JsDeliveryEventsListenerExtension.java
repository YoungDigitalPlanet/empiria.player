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

package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEventsListenerExtension;

public class JsDeliveryEventsListenerExtension extends AbstractJsExtension implements DeliveryEventsListenerExtension {

    @Override
    public ExtensionType getType() {
        return ExtensionType.EXTENSION_LISTENER_DELIVERY_EVENTS;
    }

    @Override
    public void init() {
    }

    @Override
    public void onDeliveryEvent(DeliveryEvent event) {
        onDeliveryEventJs(extensionJsObject, event.toJsObject());
    }

    private native void onDeliveryEventJs(JavaScriptObject extenstionObject, JavaScriptObject eventJsObject)/*-{
        if (typeof extenstionObject.onDeliveryEvent == 'function') {
            extenstionObject.onDeliveryEvent(eventJsObject);
        }
    }-*/;

}
