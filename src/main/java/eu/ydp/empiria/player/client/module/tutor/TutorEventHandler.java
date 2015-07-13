package eu.ydp.empiria.player.client.module.tutor;

import eu.ydp.gwtutil.client.event.EventHandler;

public interface TutorEventHandler extends EventHandler {
    void onTutorChanged(TutorEvent event);
}
