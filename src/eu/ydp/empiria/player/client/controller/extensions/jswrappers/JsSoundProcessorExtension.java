package eu.ydp.empiria.player.client.controller.extensions.jswrappers;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallback;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallforward;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.types.SoundProcessorExtension;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.SourceUtil;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.callback.CallbackRecevier;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;

public class JsSoundProcessorExtension extends JsExtension implements SoundProcessorExtension, PlayerEventHandler, MediaEventHandler {

	protected boolean playing;
	protected JavaScriptObject socketJs;
	protected MediaInteractionSoundEventCallback currCallback;
	protected EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
	protected Map<MediaWrapper<Widget>, String> sources = new HashMap<MediaWrapper<Widget>, String>();

	@Override
	public void onDeliveryEvent(DeliveryEvent deliveryEvent) {
		if (deliveryEvent.getType() == DeliveryEventType.PAGE_UNLOADING) {
			if (playing)
				soundStop();
		}
		if (deliveryEvent.getType() == DeliveryEventType.FEEDBACK_SOUND || deliveryEvent.getType() == DeliveryEventType.MEDIA_SOUND_PLAY) {

			if (deliveryEvent.getParams().containsKey("url") && deliveryEvent.getParams().get("url") instanceof String) {

				MediaInteractionSoundEventCallback callback = null;
				if (deliveryEvent.getParams().containsKey("callback") && deliveryEvent.getParams().get("callback") instanceof MediaInteractionSoundEventCallback) {
					callback = ((MediaInteractionSoundEventCallback) deliveryEvent.getParams().get("callback"));
				}

				if (playing)
					soundStop();

				if (callback != null) {
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

	protected void soundStop() {
		soundStopJs(extensionJsObject);
		soundFinished();
	}

	protected void soundFinished() {
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
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.CREATE_MEDIA_WRAPPER), this);
		for (MediaEventTypes type : MediaEventTypes.values()) {
			eventsBus.addHandler(MediaEvent.getType(type), this);
		}
	}

	@Override
	public void onMediaEvent(MediaEvent event) {
		// emulujemy play poprzez deliveryevent
		switch (((MediaEventTypes) event.getAssociatedType().getType())) {
		case PLAY:
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("url", sources.get(event.getSource()));
			DeliveryEvent e = new DeliveryEvent(DeliveryEventType.MEDIA_SOUND_PLAY, params);
			onDeliveryEvent(e);
			break;
		}
	}

	@Override
	public void onPlayerEvent(PlayerEvent event) {
		switch (event.getType()) {
		case CREATE_MEDIA_WRAPPER:
			if (event.getValue() != null && event.getValue() instanceof BaseMediaConfiguration) {
				BaseMediaConfiguration bmc = (BaseMediaConfiguration) event.getValue();
				JsMediaWrapper mediaWrapper = new JsMediaWrapper();
				sources.put(mediaWrapper, SourceUtil.getMpegSource(bmc.getSources()));
				if (event.getSource() instanceof CallbackRecevier) {
					((CallbackRecevier) event.getSource()).setCallbackReturnObject(mediaWrapper);
				}
			}
			break;
		default:
			break;
		}
	}

	private void onSoundFinished() {
		soundFinished();
	}

	private native void soundPlayJs(JavaScriptObject extenstionObject, JavaScriptObject eventJsObject)/*-{
		if (typeof extenstionObject.soundPlay == 'function') {
			extenstionObject.soundPlay(eventJsObject);
		}
	}-*/;

	private native void soundStopJs(JavaScriptObject extenstionObject)/*-{
		if (typeof extenstionObject.soundStop == 'function') {
			extenstionObject.soundStop();
		}
	}-*/;

	private native JavaScriptObject createSoundProcessorSocketJs()/*-{
		var instance = this;
		var socket = [];
		socket.onSoundFinished = function() {
			return instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsSoundProcessorExtension::onSoundFinished()();
		}
		return socket;
	}-*/;

	private native void setSoundProcessorSocketJs(JavaScriptObject extension, JavaScriptObject socket)/*-{
		if (typeof extension.setSoundProcessorSocket == 'function') {
			extension.setSoundProcessorSocket(socket);
		}
	}-*/;

}
