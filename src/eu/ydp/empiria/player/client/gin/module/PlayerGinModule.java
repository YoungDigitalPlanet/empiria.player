package eu.ydp.empiria.player.client.gin.module;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.inject.client.assistedinject.GinFactoryModuleBuilder;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import eu.ydp.empiria.player.client.controller.AssessmentControllerFactory;
import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.controller.body.IPlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.body.ModuleHandlerManager;
import eu.ydp.empiria.player.client.controller.body.PlayerContainersAccessor;
import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.controller.data.StyleDataSourceManager;
import eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.BookmarkPopup;
import eu.ydp.empiria.player.client.controller.extensions.internal.bookmark.IBookmarkPopupView;
import eu.ydp.empiria.player.client.controller.extensions.internal.sound.DefaultMediaProcessorExtension;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.IStickieProperties;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.IStickieView;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickieProperties;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickieView;
import eu.ydp.empiria.player.client.controller.feedback.FeedbackRegistry;
import eu.ydp.empiria.player.client.controller.feedback.matcher.MatcherRegistry;
import eu.ydp.empiria.player.client.controller.feedback.matcher.MatcherRegistryFactory;
import eu.ydp.empiria.player.client.controller.flow.MainFlowProcessor;
import eu.ydp.empiria.player.client.controller.multiview.MultiPageController;
import eu.ydp.empiria.player.client.controller.multiview.PanelCache;
import eu.ydp.empiria.player.client.gin.factory.AssessmentFactory;
import eu.ydp.empiria.player.client.gin.factory.DragDropObjectFactory;
import eu.ydp.empiria.player.client.gin.factory.ModuleFactory;
import eu.ydp.empiria.player.client.gin.factory.ModuleProviderFactory;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.gin.factory.TextTrackFactory;
import eu.ydp.empiria.player.client.gin.factory.TouchRecognitionFactory;
import eu.ydp.empiria.player.client.gin.factory.VideoTextTrackElementFactory;
import eu.ydp.empiria.player.client.media.texttrack.VideoTextTrackElementPresenter;
import eu.ydp.empiria.player.client.media.texttrack.VideoTextTrackElementView;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactory;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactoryImpl;
import eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenHelper;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropHelper;
import eu.ydp.empiria.player.client.util.dom.drag.DragDropHelperImpl;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.bus.PlayerEventsBus;
import eu.ydp.empiria.player.client.util.events.dom.emulate.HasTouchHandlers;
import eu.ydp.empiria.player.client.util.events.dom.emulate.TouchRecognition;
import eu.ydp.empiria.player.client.util.scheduler.Scheduler;
import eu.ydp.empiria.player.client.util.scheduler.SchedulerImpl;
import eu.ydp.empiria.player.client.util.style.NativeStyleHelper;
import eu.ydp.empiria.player.client.util.style.NativeStyleHelperImpl;
import eu.ydp.empiria.player.client.util.style.StyleHelper;
import eu.ydp.empiria.player.client.view.player.PageControllerCache;
import eu.ydp.empiria.player.client.view.player.PageViewCache;
import eu.ydp.empiria.player.client.view.player.PlayerContentView;
import eu.ydp.empiria.player.client.view.player.PlayerViewSocket;
import eu.ydp.gwtutil.client.dom.DOMTreeWalker;
import eu.ydp.gwtutil.client.ui.GWTPanelFactory;
import eu.ydp.gwtutil.client.ui.GWTPanelFactoryImpl;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class PlayerGinModule extends AbstractGinModule {

	@SuppressWarnings("unchecked")
	@Override
	protected void configure() {
		bind(StyleDataSourceManager.class).in(Singleton.class);
		bind(StyleSocket.class).to(StyleDataSourceManager.class).in(Singleton.class);
		bind(PlayerViewSocket.class).to(PlayerContentView.class).in(Singleton.class);
		bind(PlayerContentView.class).in(Singleton.class);

		// this is unnecessary, but left for clarity - if GIN can't find a
		// binding for a class, it falls back to calling GWT.create() on that
		// class
		bind(DataSourceManager.class);
		bind(EventsBus.class).to(PlayerEventsBus.class).in(Singleton.class);
		bind(DefaultMediaProcessorExtension.class).in(Singleton.class);
		bind(MultiPageController.class).in(Singleton.class);
		bind(PageViewCache.class).in(Singleton.class);
		bind(PageControllerCache.class).in(Singleton.class);
		bind(StyleNameConstants.class).in(Singleton.class);
		bind(MainFlowProcessor.class).in(Singleton.class);
		bind(Scheduler.class).to(SchedulerImpl.class).in(Singleton.class);
		bind(Page.class).in(Singleton.class);
		bind(PanelCache.class).in(Singleton.class);
		// bind(HTML5FullScreenHelper.class).in(Singleton.class);
		bind(DOMTreeWalker.class);
		bind(GWTPanelFactory.class).to(GWTPanelFactoryImpl.class).in(Singleton.class);
		bind(MediaControllerFactory.class).to(MediaControllerFactoryImpl.class).in(Singleton.class);
		bind(VideoFullScreenHelper.class).in(Singleton.class);
		bind(VideoTextTrackElementPresenter.class).to(VideoTextTrackElementView.class);
		bind(NativeStyleHelper.class).to(NativeStyleHelperImpl.class);
		bind(StyleHelper.class);
		bind(ModuleHandlerManager.class).in(Singleton.class);
		bind(IBookmarkPopupView.class).to(BookmarkPopup.class);
		bind(XMLParser.class);
		bind(FeedbackRegistry.class).in(Singleton.class);
		bind(MatcherRegistry.class).in(Singleton.class);
		bind(IStickieView.class) .to(StickieView.class);
		bind(IPlayerContainersAccessor.class).to(PlayerContainersAccessor.class).in(Singleton.class);
		bind(DragDropHelper.class).to(DragDropHelperImpl.class).in(Singleton.class);
		//bind(OverlayTypesParser.class).in(Singleton.class);
		install(new GinFactoryModuleBuilder(). build(VideoTextTrackElementFactory.class));
		install(new GinFactoryModuleBuilder().build(PageScopeFactory.class));
		install(new GinFactoryModuleBuilder().build(TextTrackFactory.class));
		install(new GinFactoryModuleBuilder().build(AssessmentFactory.class));
		install(new GinFactoryModuleBuilder(). build(ModuleFactory.class));
		bind(ModuleProviderFactory.class).in(Singleton.class);
		install(new GinFactoryModuleBuilder().build(AssessmentControllerFactory.class));
		install(new GinFactoryModuleBuilder().build(DragDropObjectFactory.class));
		install(new GinFactoryModuleBuilder().build(MatcherRegistryFactory.class));
		install(new GinFactoryModuleBuilder().implement(HasTouchHandlers.class, TouchRecognition.class).build(TouchRecognitionFactory.class));

	}

	@Provides
	public IStickieProperties provideStickieProperties() {
		return StickieProperties.newInstance();
	}
}
