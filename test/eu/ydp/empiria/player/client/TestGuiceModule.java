package eu.ydp.empiria.player.client;

import static org.mockito.Mockito.mock;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import eu.ydp.empiria.player.client.controller.multiview.PanelCache;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.gin.factory.VideoTextTrackElementFactory;
import eu.ydp.empiria.player.client.media.texttrack.VideoTextTrackElementPresenter;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactory;
import eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenHelper;
import eu.ydp.empiria.player.client.module.object.template.ObjectTemplateParser;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.SchedulerImpl;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.bus.PlayerEventsBus;
import eu.ydp.empiria.player.client.util.scheduler.Scheduler;
import eu.ydp.empiria.player.client.util.style.NativeStyleHelper;
import eu.ydp.gwtutil.client.ui.GWTPanelFactory;
import eu.ydp.gwtutil.junit.mock.GWTConstantsMock;
import eu.ydp.gwtutil.test.AbstractTestModule;
import eu.ydp.gwtutil.test.mock.GwtPanelFactoryMock;

public class TestGuiceModule extends AbstractTestModule {
	public TestGuiceModule() {
	}

	public TestGuiceModule(Class<?>... classToOmit) {
		super(classToOmit);
	}

	@Override
	public void configure() {
		bind(Scheduler.class).to(SchedulerImpl.class).in(Singleton.class);
		bind(EventsBus.class).to(PlayerEventsBus.class).in(Singleton.class);
		bind(VideoFullScreenHelper.class).in(Singleton.class);
		bind(PanelCache.class);
		install(new FactoryModuleBuilder().build(VideoTextTrackElementFactory.class));
		install(new FactoryModuleBuilder().build(PageScopeFactory.class));
	}

	@Provides
	@Singleton
	public StyleNameConstants getNameConstants() {
		return GWTConstantsMock.mockAllStringMethods(mock(StyleNameConstants.class),StyleNameConstants.class);
	}

	@Provides
	public MediaControllerFactory getMediaControllerFactory() {
		MediaControllerFactory factory = mock(MediaControllerFactory.class);
		return factory;
	}

	@Provides
	@Singleton
	public VideoTextTrackElementPresenter getVideoTextTrackElementPresenter(){
		return mock(VideoTextTrackElementPresenter.class);
	}


	@Provides
	public ObjectTemplateParser getObjectTemplateParser(MediaControllerFactory factory) {
		ObjectTemplateParser<?> parser = new ObjectTemplateParser();
		parser.setFactory(factory);
		return parser;
	}

	@Provides
	public GWTPanelFactory gwtPanelFactory(){
		return new GwtPanelFactoryMock();
	}

	@Provides
	@Singleton
	public NativeStyleHelper getNativeStyleHelper(){
		return mock(NativeStyleHelper.class);
	}

}
