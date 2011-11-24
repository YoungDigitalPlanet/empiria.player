package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackSoundInteractionEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEvent;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.InteractionEventSocketUserExtension;

public class JsInteractionEventSocketUserExtension extends JsExtension implements
		InteractionEventSocketUserExtension {

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
		InteractionEvent event = InteractionEvent.fromJsObject(requestJs);
		if (event instanceof StateChangedInteractionEvent)
			interactionEventsListener.onStateChanged( (StateChangedInteractionEvent)event);
		if (event instanceof FeedbackSoundInteractionEvent)
			interactionEventsListener.onFeedback( (FeedbackSoundInteractionEvent)event);
	}
	
	private native JavaScriptObject createInteractionRequestSocketJs()/*-{
		var instance = this;
		var socket = [];
		socket.invokeInteractionRequest = function(request){
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
