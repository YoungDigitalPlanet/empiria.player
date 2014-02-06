package eu.ydp.empiria.player.client.module.slideshow;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.slideshow.SlideshowPlayerEvent;
import eu.ydp.empiria.player.client.util.events.slideshow.SlideshowPlayerEventType;
import eu.ydp.gwtutil.client.event.EventImpl.Type;

@SuppressWarnings("PMD")
@RunWith(JUnitParamsRunner.class)
public class SlideshowMediaHandlerJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private SlideshowMediaHandler instance;
	private final SlideshowPlayerModule slideshowPlayerModule = mock(SlideshowPlayerModule.class);
	private final SlideshowPresenter slideshowPresenter = mock(SlideshowPresenter.class);
	private EventsBus eventsBus;

	@Before
	public void before() {
		GuiceModuleConfiguration config = new GuiceModuleConfiguration();
		config.addAllClassToSpy(EventsBus.class);
		setUp(config);
		eventsBus = injector.getInstance(EventsBus.class);
		instance = new SlideshowMediaHandler(slideshowPlayerModule, slideshowPresenter);
		injector.injectMembers(instance);
	}

	@Test
	public void testOnSlideshowPlayerEvent() {
		SlideshowPlayerEvent event = new SlideshowPlayerEvent(SlideshowPlayerEventType.ON_NEXT);
		instance.onSlideshowPlayerEvent(event);
		verify(slideshowPlayerModule).showNextSlide();

		event = new SlideshowPlayerEvent(SlideshowPlayerEventType.ON_PREVIOUS);
		instance.onSlideshowPlayerEvent(event);
		verify(slideshowPlayerModule).showPreviousSlide();

		event = new SlideshowPlayerEvent(SlideshowPlayerEventType.ON_PLAY);
		instance.onSlideshowPlayerEvent(event);
		verify(slideshowPlayerModule).onPlayEvent();

		event = new SlideshowPlayerEvent(SlideshowPlayerEventType.ON_STOP);
		instance.onSlideshowPlayerEvent(event);
		verify(slideshowPlayerModule).onStopEvent();

	}

	@Test
	@Parameters(method = "getTypes")
	public void testAddEventHandler(SlideshowPlayerEventType type) {
		ArgumentCaptor<Type> typeCaptor = ArgumentCaptor.forClass(Type.class);
		instance.addEventHandler(type);
		verify(eventsBus).addHandlerToSource(typeCaptor.capture(), Matchers.eq(slideshowPresenter), eq(instance));
		assertTrue(typeCaptor.getValue().getType() == type);
	}

	public Object[] getTypes() {
		return JUnitParamsRunner.$(SlideshowPlayerEventType.values());
	}
}
