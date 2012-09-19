package eu.ydp.empiria.player.client;

import static org.mockito.Mockito.mock;

import com.google.inject.Provides;
import com.google.inject.Singleton;

import eu.ydp.empiria.player.client.controller.multiview.PanelCache;
import eu.ydp.empiria.player.client.module.media.MediaControllerFactory;
import eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenHelper;
import eu.ydp.empiria.player.client.module.object.template.ObjectTemplateParser;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.SchedulerImpl;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.bus.PlayerEventsBus;
import eu.ydp.empiria.player.client.util.scheduler.Scheduler;
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
	//	bind(GWTPanelFactory.class).to(GwtPanelFactoryMock.class).in(Singleton.class);
		bind(VideoFullScreenHelper.class).in(Singleton.class);
		bind(PanelCache.class);
	}

	@Provides
	public StyleNameConstants getNameConstants() {
		return GWTConstantsMock.mockAllStringMethods(mock(StyleNameConstants.class));
	}

	@Provides
	public MediaControllerFactory getMediaControllerFactory() {
		MediaControllerFactory factory = mock(MediaControllerFactory.class);
		return factory;
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

}
