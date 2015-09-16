package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionEventListener;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.internal.state.StateChangeEventTypes;

public class JsInteractionEventSocketUserExtension extends AbstractJsExtension {
    private final EventsBus eventsBus;
    protected JavaScriptObject interactionSocketJs;
    private final PageScopeFactory pageScopeFactory;
    private final FeedbackInteractionEventListener feedbackInteractionEventListener;

    @Inject
    public JsInteractionEventSocketUserExtension(EventsBus eventsBus, PageScopeFactory pageScopeFactory, FeedbackInteractionEventListener feedbackInteractionEventListener) {
        this.eventsBus = eventsBus;
        this.pageScopeFactory = pageScopeFactory;
        this.feedbackInteractionEventListener = feedbackInteractionEventListener;
    }

    @Override
    public ExtensionType getType() {
        return ExtensionType.EXTENSION_SOCKET_USER_INTERACTION_EVENT;
    }

    @Override
    public void init() {
        interactionSocketJs = createInteractionRequestSocketJs();
        setInteractionRequestSocketJs(extensionJsObject, interactionSocketJs);
    }

    protected void dispatchInteractionEvent(JavaScriptObject requestJs) {
        // FIXME state change
        InteractionEvent event = InteractionEvent.fromJsObject(requestJs);
        if (event instanceof StateChangedInteractionEvent) {
            StateChangedInteractionEvent scie = (StateChangedInteractionEvent) event;
            CurrentPageScope eventScope = pageScopeFactory.getCurrentPageScope();
            eventsBus.fireEvent(new StateChangeEvent(StateChangeEventTypes.STATE_CHANGED, scie), eventScope);
        }
        if (event instanceof FeedbackInteractionEvent) {
            feedbackInteractionEventListener.onFeedbackSound((FeedbackInteractionEvent) event);
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
