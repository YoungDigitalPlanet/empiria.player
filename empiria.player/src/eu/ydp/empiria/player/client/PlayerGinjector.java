package eu.ydp.empiria.player.client;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.view.ViewEngine;

@GinModules(PlayerGinModule.class)
public interface PlayerGinjector extends Ginjector {
	ViewEngine getViewEngine();
	DeliveryEngine getDeliveryEngine();
}
