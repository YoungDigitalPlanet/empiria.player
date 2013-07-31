package eu.ydp.empiria.player.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

import eu.ydp.empiria.player.client.controller.AssessmentControllerFactory;
import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.body.ModuleHandlerManager;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionsManager;
import eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport.AssessmentJsonReportExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiesProcessorExtension;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackRegistry;
import eu.ydp.empiria.player.client.controller.feedback.ModuleFeedbackProcessor;
import eu.ydp.empiria.player.client.controller.flow.MainFlowProcessor;
import eu.ydp.empiria.player.client.controller.multiview.MultiPageController;
import eu.ydp.empiria.player.client.controller.multiview.PanelCache;
import eu.ydp.empiria.player.client.controller.multiview.touch.TouchController;
import eu.ydp.empiria.player.client.controller.report.AssessmentReportFactory;
import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.ModuleProviderFactory;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.gin.factory.SingleModuleInstanceProvider;
import eu.ydp.empiria.player.client.gin.factory.TextTrackFactory;
import eu.ydp.empiria.player.client.gin.module.ChoiceGinModule;
import eu.ydp.empiria.player.client.gin.module.ColorfillGinModule;
import eu.ydp.empiria.player.client.gin.module.ConnectionGinModule;
import eu.ydp.empiria.player.client.gin.module.DragGapGinModule;
import eu.ydp.empiria.player.client.gin.module.ModuleScopedModule;
import eu.ydp.empiria.player.client.gin.module.OrderingGinModule;
import eu.ydp.empiria.player.client.gin.module.PageScopedModule;
import eu.ydp.empiria.player.client.gin.module.PlayerGinModule;
import eu.ydp.empiria.player.client.gin.module.SelectionGinModule;
import eu.ydp.empiria.player.client.gin.module.SimulationGinModule;
import eu.ydp.empiria.player.client.gin.module.SlideshowGinModule;
import eu.ydp.empiria.player.client.gin.module.SourceListGinModule;
import eu.ydp.empiria.player.client.gin.module.TextEntryGinModule;
import eu.ydp.empiria.player.client.gin.module.tutor.TutorGinModule;
import eu.ydp.empiria.player.client.module.info.VariableInterpreterFactory;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactory;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import eu.ydp.empiria.player.client.view.ViewEngine;
import eu.ydp.empiria.player.client.view.player.PageControllerCache;
import eu.ydp.gwtutil.client.dom.DOMTreeWalker;
import eu.ydp.gwtutil.client.ui.GWTPanelFactory;

@GinModules(value = {
		PlayerGinModule.class, ChoiceGinModule.class, ConnectionGinModule.class,
		SourceListGinModule.class, TextEntryGinModule.class, SelectionGinModule.class,
		SimulationGinModule.class, PageScopedModule.class, SlideshowGinModule.class,
		OrderingGinModule.class, ModuleScopedModule.class, ColorfillGinModule.class,
		DragGapGinModule.class, TutorGinModule.class})

public interface PlayerGinjector extends Ginjector {

	ViewEngine getViewEngine();

	DeliveryEngine getDeliveryEngine();

	EventsBus getEventsBus();

	MultiPageController getMultiPage();

	PageControllerCache getPageControllerCache();

	StyleNameConstants getStyleNameConstants();

	MainFlowProcessor getMainFlowProcessor();

	Page getPage();

	DOMTreeWalker getDomTreeWalker();

	PanelCache getPanelCache();

	GWTPanelFactory getPanelFactory();

	MediaControllerFactory getControllerFactory();

	PageScopeFactory getPageScopeFactory();

	PositionHelper getPositionHelper();

	ModuleHandlerManager getModuleHandlerManager();

	TextTrackFactory getTextTrackFactory();

	ModuleFactory getModuleFactory();

	ModuleProviderFactory getModuleProviderFactory();

	SingleModuleInstanceProvider getSingleModuleInstanceProvider();

	FeedbackRegistry getFeedbackRegistry();

	ModuleFeedbackProcessor getModuleFeedbackProcessor();

	AssessmentControllerFactory getAssessmentControllerFactory();

	VariableInterpreterFactory getVariableInterpreterFactory();

	AssessmentReportFactory getAssessmentReportFactory();

	ExtensionsManager getExtensionsManager();

	BookmarkProcessorExtension getBookmarkProcessorExtension();

	StickiesProcessorExtension getStickiesProcessorExtension();

	IPlayerContainersAccessor getPlayerContainersAccessor();

	AssessmentJsonReportExtension getAssessmentJsonReportExtension();

	TouchController getTouchController();

	StyleSocket getStyleSocket();
}
