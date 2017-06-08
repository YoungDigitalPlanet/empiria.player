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
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEvent;
import eu.ydp.empiria.player.client.controller.events.delivery.DeliveryEventType;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallback;
import eu.ydp.empiria.player.client.controller.events.interaction.MediaInteractionSoundEventCallforward;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionType;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.external.ExternalMediaProcessor;
import eu.ydp.empiria.player.client.controller.extensions.types.DeliveryEventsListenerExtension;
import eu.ydp.empiria.player.client.controller.extensions.types.MediaProcessorExtension;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.SourceUtil;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;

import java.util.HashMap;
import java.util.Map;

/**
 * @deprecated Use {@link ExternalMediaProcessor} instead.
 */
@Deprecated
public class JsMediaProcessorExtension extends AbstractJsExtension implements MediaProcessorExtension, PlayerEventHandler,
        DeliveryEventsListenerExtension, MediaEventHandler {

    private final EventsBus eventsBus;
    private boolean playing;
    private JavaScriptObject socketJs;
    private MediaInteractionSoundEventCallback currCallback;
    private Map<MediaWrapper<Widget>, String> sources = new HashMap<>();

    @Inject
    public JsMediaProcessorExtension(EventsBus eventsBus) {
        this.eventsBus = eventsBus;
    }

    @Override
    public void onDeliveryEvent(DeliveryEvent deliveryEvent) {
        if (deliveryEvent.getType() == DeliveryEventType.PAGE_UNLOADING && playing) {
            soundStop();
        }
        if (deliveryEvent.getType() == DeliveryEventType.FEEDBACK_SOUND || deliveryEvent.getType() == DeliveryEventType.MEDIA_SOUND_PLAY) {

            if (deliveryEvent.getParams().containsKey("url") && deliveryEvent.getParams().get("url") instanceof String) {

                MediaInteractionSoundEventCallback callback = null;
                if (deliveryEvent.getParams().containsKey("callback")
                        && deliveryEvent.getParams().get("callback") instanceof MediaInteractionSoundEventCallback) {
                    callback = ((MediaInteractionSoundEventCallback) deliveryEvent.getParams().get("callback"));
                }

                if (playing) {
                    soundStop();
                }

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
        if (currCallback != null) {
            currCallback.onStop();
        }
    }

    @Override
    public ExtensionType getType() {
        return ExtensionType.EXTENSION_PROCESSOR_MEDIA;
    }

    @Override
    public void init() {
        // do nothing
    }

    @Override
    public void initMediaProcessor() {
        socketJs = createMediaProcessorSocketJs();
        setMediaProcessorSocketJs(extensionJsObject, socketJs);
        eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.CREATE_MEDIA_WRAPPER), this);
        for (MediaEventTypes type : MediaEventTypes.values()) {
            eventsBus.addHandler(MediaEvent.getType(type), this);
        }
    }

    @Override
    @SuppressWarnings(value = {"all"})
    public void onMediaEvent(MediaEvent event) {
        // emulujemy play poprzez deliveryevent

		/*
         * if (event.getAssociatedType().getType() == MediaEventTypes.PLAY) { Map<String, Object> params = new HashMap<String, Object>(); params.put("url",
		 * sources.get(event.getSource())); DeliveryEvent dEvent = new DeliveryEvent(DeliveryEventType.MEDIA_SOUND_PLAY, params); onDeliveryEvent(dEvent); }
		 */
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        if (event.getType() == PlayerEventTypes.CREATE_MEDIA_WRAPPER) {
            if (event.getValue() instanceof BaseMediaConfiguration) {// NOPMD
                BaseMediaConfiguration bmc = (BaseMediaConfiguration) event.getValue();
                JsMediaWrapper mediaWrapper = new JsMediaWrapper(getMediaAvailableOptionsJs(extensionJsObject));
                sources.put(mediaWrapper, SourceUtil.getMpegSource(bmc.getSources()));
                if (event.getSource() instanceof CallbackReceiver) {// NOPMD
                    ((CallbackReceiver) event.getSource()).setCallbackReturnObject(mediaWrapper);
                }
            }
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

    private native JavaScriptObject getMediaAvailableOptionsJs(JavaScriptObject extenstionObject)/*-{
        if (typeof extenstionObject.getMediaAvailableOptions == 'function') {
            return extenstionObject.getMediaAvailableOptions();
        }
        return null;
    }-*/;

    private native JavaScriptObject createMediaProcessorSocketJs()/*-{
        var instance = this;
        var socket = [];
        socket.onSoundFinished = function () {
            return instance.@eu.ydp.empiria.player.client.controller.extensions.jswrappers.JsMediaProcessorExtension::onSoundFinished()();
        }
        return socket;
    }-*/;

    private native void setMediaProcessorSocketJs(JavaScriptObject extension, JavaScriptObject socket)/*-{
        if (typeof extension.setSoundProcessorSocket == 'function') {
            extension.setSoundProcessorSocket(socket);
        }
    }-*/;

}
