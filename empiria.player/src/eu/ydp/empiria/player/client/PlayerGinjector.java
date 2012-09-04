package eu.ydp.empiria.player.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.DefaultMediaProcessorExtension;
import eu.ydp.empiria.player.client.controller.flow.MainFlowProcessor;
import eu.ydp.empiria.player.client.controller.multiview.MultiPageView;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.view.ViewEngine;
import eu.ydp.empiria.player.client.view.player.PageControllerCache;
import eu.ydp.empiria.player.client.view.player.PageViewCache;

@GinModules(PlayerGinModule.class)
public interface PlayerGinjector extends Ginjector {
	PlayerGinjector INSTANCE  = GWT.create(PlayerGinjector.class);

	ViewEngine getViewEngine();
	DeliveryEngine getDeliveryEngine();
	EventsBus getEventsBus();
	DefaultMediaProcessorExtension getDefaultMediaExtension();
	MultiPageView getMultiPageView();
	PageViewCache getPageViewCache();
	PageControllerCache getPageControllerCache();
	StyleNameConstants getStyleNameConstants();
	MainFlowProcessor getMainFlowProcessor();
	Page getPage();
}
