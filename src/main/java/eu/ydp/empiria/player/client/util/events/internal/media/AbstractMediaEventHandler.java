package eu.ydp.empiria.player.client.util.events.internal.media;

import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.internal.scope.EventScope;

public abstract class AbstractMediaEventHandler implements MediaEventHandler {
	// @Override
	public EventScope<CurrentPageScope> getScope() {
		return new CurrentPageScope();
	}
}
