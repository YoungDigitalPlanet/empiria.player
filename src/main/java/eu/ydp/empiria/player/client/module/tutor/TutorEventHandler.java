package eu.ydp.empiria.player.client.module.tutor;

import eu.ydp.empiria.player.client.util.events.internal.EventHandler;

public interface TutorEventHandler extends EventHandler {
    void onTutorChanged(TutorEvent event);
}
