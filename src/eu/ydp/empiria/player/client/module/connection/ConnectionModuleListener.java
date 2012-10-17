package eu.ydp.empiria.player.client.module.connection;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.choice.ChoiceModuleEvent;
import eu.ydp.empiria.player.client.util.events.choice.ChoiceModuleEventHandler;
import eu.ydp.empiria.player.client.util.events.choice.ChoiceModuleEventType;
import eu.ydp.empiria.player.client.util.events.connection.ConnectionEvent;
import eu.ydp.empiria.player.client.util.events.connection.ConnectionEventTypes;
import eu.ydp.empiria.player.client.util.events.connection.ConnectionModuleEventHandler;

public class ConnectionModuleListener implements ConnectionModuleEventHandler {
	private final ConnectionModule connectionModule;
	private final EventsBus eventBus;
	private final PageScopeFactory pageScopeFactory;

	@Inject
	public ConnectionModuleListener(EventsBus eventsBus, PageScopeFactory pageScopeFactory, @Assisted ConnectionModule connectionModule) {
		this.connectionModule = connectionModule;
		this.eventBus = eventsBus;
		this.pageScopeFactory = pageScopeFactory;
	}

	public void addEventHandler(ChoiceModuleEventType type) {
		// TODO to be implemented
	}

	@Override
	public void onConnectionModuleEvent(ConnectionEvent event) {
		// TODO to be implemented		
	}

}
