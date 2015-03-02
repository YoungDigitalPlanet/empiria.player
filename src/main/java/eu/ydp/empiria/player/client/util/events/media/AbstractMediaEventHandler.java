package eu.ydp.empiria.player.client.util.events.media;

import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.scope.EventScope;

public abstract class AbstractMediaEventHandler implements MediaEventHandler {
	// @Override
	public EventScope<CurrentPageScope> getScope() {
		return new CurrentPageScope();
	}
}
