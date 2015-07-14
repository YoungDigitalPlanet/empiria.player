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
