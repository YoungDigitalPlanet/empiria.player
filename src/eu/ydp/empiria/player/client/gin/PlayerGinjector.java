package eu.ydp.empiria.player.client.gin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.controller.body.ModuleHandlerManager;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.DefaultMediaProcessorExtension;
import eu.ydp.empiria.player.client.controller.flow.MainFlowProcessor;
import eu.ydp.empiria.player.client.controller.multiview.MultiPageController;
import eu.ydp.empiria.player.client.controller.multiview.PageEventsHandler;
import eu.ydp.empiria.player.client.controller.multiview.PanelCache;
import eu.ydp.empiria.player.client.gin.factory.ConnectionItemFactory;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactory;
import eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenHelper;
import eu.ydp.empiria.player.client.module.object.template.ObjectTemplateParser;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import eu.ydp.empiria.player.client.view.ViewEngine;
import eu.ydp.empiria.player.client.view.player.PageControllerCache;
import eu.ydp.empiria.player.client.view.player.PageViewCache;
import eu.ydp.gwtutil.client.dom.DOMTreeWalker;
import eu.ydp.gwtutil.client.ui.GWTPanelFactory;

@GinModules(PlayerGinModule.class)
public interface PlayerGinjector extends Ginjector {
	PlayerGinjector INSTANCE  = GWT.create(PlayerGinjector.class);

	ViewEngine getViewEngine();
	DeliveryEngine getDeliveryEngine();
	EventsBus getEventsBus();
	DefaultMediaProcessorExtension getDefaultMediaExtension();
	MultiPageController getMultiPage();
	PageViewCache getPageViewCache();
	PageControllerCache getPageControllerCache();
	StyleNameConstants getStyleNameConstants();
	MainFlowProcessor getMainFlowProcessor();
	Page getPage();
	//HTML5FullScreenHelper getHtml5FullScreenHelper();
	DOMTreeWalker getDomTreeWalker();
	PanelCache getPanelCache();
	GWTPanelFactory getPanelFactory();
	PageEventsHandler getPageEvents();
	ObjectTemplateParser<?>  getObjectTemplateParser();
	VideoFullScreenHelper getVideoFullScreenHelper();
	MediaControllerFactory getControllerFactory();
	PageScopeFactory getPageScopeFactory();
	PositionHelper getPositionHelper();
	ModuleHandlerManager getModuleHandlerManager();
//	ConnectionItemFactory getConnectionItemFactory();
//	TextTrackFactory getTextTrackFactory();
//	ModuleFactory getModuleFactory();
}
