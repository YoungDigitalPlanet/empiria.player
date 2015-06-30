package eu.ydp.empiria.player.client.controller.events.interaction;

import java.util.HashMap;
import java.util.Map;

public class FeedbackInteractionSoundEvent extends FeedbackInteractionEvent {

    protected String url;

    public FeedbackInteractionSoundEvent(String path) {
        this.url = path;
    }

    public String getPath() {
        return url;
    }

    @Override
    public InteractionEventType getType() {
        return InteractionEventType.FEEDBACK_SOUND;
    }

    @Override
    public Map<String, Object> getParams() {
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("url", url);
        return p;
    }
}
