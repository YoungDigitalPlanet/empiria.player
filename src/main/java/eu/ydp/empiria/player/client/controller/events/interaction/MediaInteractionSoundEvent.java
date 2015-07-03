package eu.ydp.empiria.player.client.controller.events.interaction;

import java.util.HashMap;
import java.util.Map;

public class MediaInteractionSoundEvent extends InteractionEvent {

    protected String url;
    protected MediaInteractionSoundEventCallback callback;

    public MediaInteractionSoundEvent(String path, MediaInteractionSoundEventCallback callback) {
        this.url = path;
        this.callback = callback;
    }

    public String getPath() {
        return url;
    }

    public MediaInteractionSoundEventCallback getCallback() {
        return callback;
    }

    @Override
    public InteractionEventType getType() {
        return InteractionEventType.MEDIA_SOUND_PLAY;
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("url", url);
        p.put("callback", callback);
        return p;
    }
}
