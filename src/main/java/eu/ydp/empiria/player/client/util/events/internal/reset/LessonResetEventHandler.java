package eu.ydp.empiria.player.client.util.events.internal.reset;

import eu.ydp.empiria.player.client.util.events.internal.EventHandler;

public interface LessonResetEventHandler extends EventHandler {
    public void onLessonReset(LessonResetEvent event);
}
