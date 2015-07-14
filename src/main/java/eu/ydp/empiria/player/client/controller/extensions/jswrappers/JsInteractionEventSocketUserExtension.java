package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.InteractionEventSocketUserExtension;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEventTypes;

public class JsInteractionEventSocketUserExtension extends AbstractJsExtension implements InteractionEventSocketUserExtension {
    private final EventsBus eventsBus = PlayerGinjectorFactory.getPlayerGinjector().getEventsBus();
    protected JavaScriptObject interactionSocketJs;
    protected InteractionEventsListener interactionEventsListener;

    @Override
    public ExtensionType getType() {
        return ExtensionType.EXTENSION_SOCKET_USER_INTERACTION_EVENT;
    }

    @Override
    public void init() {
        interactionSocketJs = createInteractionRequestSocketJs();
        setInteractionRequestSocketJs(extensionJsObject, interactionSocketJs);
    }

    @Override
    public void setInteractionEventsListener(InteractionEventsListener listener) {
        this.interactionEventsListener = listener;
    }

    protected void dispatchInteractionEvent(JavaScriptObject requestJs) {
        // FIXME state change
        InteractionEvent event = InteractionEvent.fromJsObject(requestJs);
        if (event instanceof StateChangedInteractionEvent) {
            StateChangedInteractionEvent scie = (StateChangedInteractionEvent) event;
            eventsBus.fireEvent(new StateChangeEvent(StateChangeEventTypes.STATE_CHANGED, scie), new CurrentPageScope());
        }
        if (event instanceof FeedbackInteractionEvent) {
            interactionEventsListener.onFeedbackSound((FeedbackInteractionEvent) event);
        }
    }

    private native JavaScriptObject createInteractionRequestSocketJs()/*-{
        var instance = this;
        var socket = [];
        socket.dispatchInteractionEvent = function (request) {
            instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsInteractionEventSocketUserExtension::dispatchInteractionEvent(Lcom/google/gwt/core/client/JavaScriptObject;)(request);
        }
        return socket;
    }-*/;

    private native void setInteractionRequestSocketJs(JavaScriptObject extension, JavaScriptObject socket)/*-{
        if (typeof extension.setInteractionRequestsSocket == 'function') {
            extension.setInteractionRequestsSocket(socket);
        }
    }-*/;

}
