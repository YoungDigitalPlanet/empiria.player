package eu.ydp.empiria.player.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.delivery.DeliveryEngine;
import eu.ydp.empiria.player.client.controller.extensions.ExtensionsManager;
import eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkProcessorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiesProcessorExtension;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackRegistry;
import eu.ydp.empiria.player.client.controller.flow.MainFlowProcessor;
import eu.ydp.empiria.player.client.controller.multiview.MultiPageController;
import eu.ydp.empiria.player.client.controller.multiview.PanelCache;
import eu.ydp.empiria.player.client.controller.multiview.touch.TouchController;
import eu.ydp.empiria.player.client.controller.report.AssessmentReportFactory;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.gin.factory.TextTrackFactory;
import eu.ydp.empiria.player.client.gin.module.*;
import eu.ydp.empiria.player.client.gin.module.tutor.TutorGinModule;
import eu.ydp.empiria.player.client.module.img.events.handlers.TouchHandlerOnImageProvider;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.scripts.ScriptsLoader;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.external.ExternalEventDispatcher;
import eu.ydp.empiria.player.client.util.position.PositionHelper;
import eu.ydp.empiria.player.client.view.ViewEngine;
import eu.ydp.gwtutil.client.debug.log.Logger;
import eu.ydp.gwtutil.client.gin.module.AnimationGinModule;
import eu.ydp.gwtutil.client.gin.module.UtilGinModule;

@GinModules(value = { PlayerGinModule.class, UtilGinModule.class, ChoiceGinModule.class, ConnectionGinModule.class, SourceListGinModule.class,
		SelectionGinModule.class, SimulationGinModule.class, PageScopedModule.class, SlideshowGinModule.class,
		OrderingGinModule.class, ModuleScopedModule.class, ColorfillGinModule.class, DragGapGinModule.class, TutorGinModule.class, ButtonGinModule.class,
		AnimationGinModule.class, DrawingGinModule.class, BonusGinModule.class, ProgressBonusGinModule.class, VideoGinModule.class, DictionaryGinModule.class,
		TextEditorGinModule.class, TestGinModule.class, SpeechScoreGinModule.class, ScriptInjectorModule.class, IdentificationGinModule.class,
		ExternalGinModule.class, PicturePlayerModule.class })
public interface PlayerGinjector extends Ginjector {

	ViewEngine getViewEngine();

	DeliveryEngine getDeliveryEngine();

	EventsBus getEventsBus();

	MultiPageController getMultiPage();

	StyleNameConstants getStyleNameConstants();

	MainFlowProcessor getMainFlowProcessor();

	Page getPage();

	PanelCache getPanelCache();

	PageScopeFactory getPageScopeFactory();

	PositionHelper getPositionHelper();

	TextTrackFactory getTextTrackFactory();

	FeedbackRegistry getFeedbackRegistry();

	AssessmentReportFactory getAssessmentReportFactory();

	ExtensionsManager getExtensionsManager();

	BookmarkProcessorExtension getBookmarkProcessorExtension();

	StickiesProcessorExtension getStickiesProcessorExtension();

	IPlayerContainersAccessor getPlayerContainersAccessor();

	TouchController getTouchController();

	StyleSocket getStyleSocket();

	Logger getLogger();

	TouchHandlerOnImageProvider getTouchHandlerOnImageProvider();

	ScriptsLoader getScriptsLoader();

	ExternalEventDispatcher getEventDispatcher();

}
