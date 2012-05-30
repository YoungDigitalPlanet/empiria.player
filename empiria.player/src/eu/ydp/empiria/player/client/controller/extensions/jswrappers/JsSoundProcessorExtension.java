package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallback;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallforward;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEventsListenerExtension;

public class JsSoundProcessorExtension extends JsExtension implements
		DeliveryEventsListenerExtension {

	protected boolean playing;
	protected JavaScriptObject socketJs;
	protected MediaInteractionSoundEventCallback currCallback;
	
	@Override
	public void onDeliveryEvent(DeliveryEvent deliveryEvent) {
		if (deliveryEvent.getType() == DeliveryEventType.PAGE_UNLOADING){
			if (playing)
				soundStop();
		}
		if (deliveryEvent.getType() == DeliveryEventType.FEEDBACK_SOUND  ||  deliveryEvent.getType() == DeliveryEventType.MEDIA_SOUND_PLAY){

			if (deliveryEvent.getParams().containsKey("url")  &&  deliveryEvent.getParams().get("url") instanceof String){

				MediaInteractionSoundEventCallback callback = null;
				if (deliveryEvent.getParams().containsKey("callback")  &&  deliveryEvent.getParams().get("callback") instanceof MediaInteractionSoundEventCallback){
					callback = ((MediaInteractionSoundEventCallback)deliveryEvent.getParams().get("callback"));
				}
				
				if (playing)
					soundStop();				
				
				if (callback != null){
					
					currCallback = callback;	
				
					playing = true;
					callback.onPlay();
					callback.setCallforward(new MediaInteractionSoundEventCallforward() {
						
						@Override
						public void stop() {
							soundStop();
						}
					});
				}
				
				soundPlayJs(extensionJsObject, deliveryEvent.toJsObject());
				
				
			}
			
		}

	}
	
	protected void soundStop(){
		soundStopJs(extensionJsObject);
		soundFinished();
	}
	
	protected void soundFinished(){
		playing = false;
		if (currCallback != null)
			currCallback.onStop();
	}

	@Override
	public ExtensionType getType() {
		return ExtensionType.EXTENSION_PROCESSOR_SOUND;
	}

	@Override
	public void init() {
		socketJs = createSoundProcessorSocketJs();
		setSoundProcessorSocketJs(extensionJsObject, socketJs);
	}
	
	private void onSoundFinished(){
		soundFinished();
	}
	
	private native void soundPlayJs(JavaScriptObject extenstionObject, JavaScriptObject eventJsObject)/*-{
		if (typeof extenstionObject.soundPlay == 'function'){
			extenstionObject.soundPlay(eventJsObject);
		}
	}-*/;
	
	private native void soundStopJs(JavaScriptObject extenstionObject)/*-{
		if (typeof extenstionObject.soundStop == 'function'){
			extenstionObject.soundStop();
		}
	}-*/;
	
	private native JavaScriptObject createSoundProcessorSocketJs()/*-{
		var instance = this;
		var socket = [];
		socket.onSoundFinished = function(){
			return instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsSoundProcessorExtension::onSoundFinished()();
		}
		return socket;
	}-*/;
	

	private native void setSoundProcessorSocketJs(JavaScriptObject extension, JavaScriptObject socket)/*-{
		if (typeof extension.setSoundProcessorSocket == 'function'){
			extension.setSoundProcessorSocket(socket);
		}
	}-*/;

}
