package eu.ydp.empiria.player.client.gin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

import eu.ydp.empiria.player.client.controller.AssessmentControllerFactory;
import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.body.ModuleHandlerManager;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.DefaultMediaProcessorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiesProcessorExtension;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackRegistry;
import eu.ydp.empiria.player.client.controller.feedback.ModuleFeedbackProcessor;
import eu.ydp.empiria.player.client.controller.flow.MainFlowProcessor;
import eu.ydp.empiria.player.client.controller.multiview.PanelCache;
import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.ModuleProviderFactory;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.gin.factory.TextTrackFactory;
import eu.ydp.empiria.player.client.gin.module.ChoiceGinModule;
import eu.ydp.empiria.player.client.gin.module.ConnectionGinModule;
import eu.ydp.empiria.player.client.gin.module.PlayerGinModule;
import eu.ydp.empiria.player.client.gin.module.SourceListGinModule;
import eu.ydp.empiria.player.client.gin.module.TextEntryGinModule;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactory;
import eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenHelper;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import eu.ydp.empiria.player.client.view.ViewEngine;
import eu.ydp.empiria.player.client.view.player.PageControllerCache;
import eu.ydp.gwtutil.client.dom.DOMTreeWalker;
import eu.ydp.gwtutil.client.ui.GWTPanelFactory;

@GinModules(value={PlayerGinModule.class, ChoiceGinModule.class, ConnectionGinModule.class, SourceListGinModule.class, TextEntryGinModule.class})
public interface PlayerGinjector extends Ginjector {
	PlayerGinjector INSTANCE  = GWT.create(PlayerGinjector.class);

	ViewEngine getViewEngine();
	DeliveryEngine getDeliveryEngine();
	EventsBus getEventsBus();
	DefaultMediaProcessorExtension getDefaultMediaExtension();
//	MultiPageController getMultiPage();
//	PageViewCache getPageViewCache();
	PageControllerCache getPageControllerCache();
	StyleNameConstants getStyleNameConstants();
	MainFlowProcessor getMainFlowProcessor();
	Page getPage();
	//HTML5FullScreenHelper getHtml5FullScreenHelper();
	DOMTreeWalker getDomTreeWalker();
	PanelCache getPanelCache();
	GWTPanelFactory getPanelFactory();
	VideoFullScreenHelper getVideoFullScreenHelper();
	MediaControllerFactory getControllerFactory();
	PageScopeFactory getPageScopeFactory();
	PositionHelper getPositionHelper();
	ModuleHandlerManager getModuleHandlerManager();
	TextTrackFactory getTextTrackFactory();
	ModuleFactory getModuleFactory();
	ModuleProviderFactory getModuleProviderFactory();
	FeedbackRegistry getFeedbackRegistry();
	ModuleFeedbackProcessor getModuleFeedbackProcessor();
	AssessmentControllerFactory getAssessmentControllerFactory();

	BookmarkProcessorExtension getBookmarkProcessorExtension();
	StickiesProcessorExtension getStickiesProcessorExtension();
	IPlayerContainersAccessor getPlayerContainersAccessor();
}
