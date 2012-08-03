package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.InteractionEventSocketUserExtension;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEventTypes;

public class JsInteractionEventSocketUserExtension extends JsExtension implements
		InteractionEventSocketUserExtension {
	private final EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
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

	protected void dispatchInteractionEvent(JavaScriptObject requestJs){
		//FIXME state change
		InteractionEvent event = InteractionEvent.fromJsObject(requestJs);
		if (event instanceof StateChangedInteractionEvent) {
			eventsBus.fireEvent(new StateChangeEvent(StateChangeEventTypes.STATE_CHANGED, event), new CurrentPageScope());
		}
		if (event instanceof FeedbackInteractionEvent) {
			interactionEventsListener.onFeedbackSound( (FeedbackInteractionEvent)event);
		}
	}

	private native JavaScriptObject createInteractionRequestSocketJs()/*-{
		var instance = this;
		var socket = [];
		socket.dispatchInteractionEvent = function(request){
			instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsInteractionEventSocketUserExtension::dispatchInteractionEvent(Lcom/google/gwt/core/client/JavaScriptObject;)(request);
		}
		return socket;
	}-*/;

	private native void setInteractionRequestSocketJs(JavaScriptObject extension, JavaScriptObject socket)/*-{
		if (typeof extension.setInteractionRequestsSocket == 'function'){
			extension.setInteractionRequestsSocket(socket);
		}
	}-*/;


}
