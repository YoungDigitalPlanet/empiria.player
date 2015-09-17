package eu.ydp.empiria.player.client.util.events.internal.page;

import eu.ydp.empiria.player.client.util.events.internal.EventHandler;

public interface PageEventHandler extends EventHandler {
    public void onPageEvent(PageEvent event);
}
