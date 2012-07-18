package eu.ydp.empiria.player.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.DefaultMediaProcessorExtension;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.view.ViewEngine;

@GinModules(PlayerGinModule.class)
public interface PlayerGinjector extends Ginjector {
	PlayerGinjector INSTANCE  = GWT.create(PlayerGinjector.class);

	ViewEngine getViewEngine();
	DeliveryEngine getDeliveryEngine();
	EventsBus getEventsBus();
	DefaultMediaProcessorExtension getDefaultMediaExtension();
	StyleNameConstants getStyleNameConstants();
}
