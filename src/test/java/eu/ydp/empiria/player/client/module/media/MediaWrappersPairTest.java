package eu.ydp.empiria.player.client.module.media;

import static eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent.getType;
import static eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes.*;
import static eu.ydp.gwtutil.junit.mock.UserAgentCheckerNativeInterfaceMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.web.bindery.event.shared.HandlerRegistration;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.gin.factory.MediaWrappersPairFactory;
import eu.ydp.empiria.player.client.media.Video;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.scope.EventScope;
import eu.ydp.empiria.player.client.util.events.internal.scope.PageScope;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.junit.mock.UserAgentCheckerNativeInterfaceMock;

@SuppressWarnings("PMD")
public class MediaWrappersPairTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private static class CustomGuiceModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.install(new FactoryModuleBuilder().build(MediaWrappersPairFactory.class));
		}
	}

	protected EventsBus eventsBus;
	protected MediaWrappersPair instance;
	protected MediaWrapper<Video> defaultWrapper = mock(MediaWrapper.class);
	protected MediaWrapper<Video> fullScreenWrapper = mock(MediaWrapper.class);
	protected HandlerRegistration handlerRegistration = mock(HandlerRegistration.class);
	protected HandlerRegistration handlerRegistration2 = mock(HandlerRegistration.class);

	public void before(String userAgent) {
		UserAgentChecker.setNativeInterface(UserAgentCheckerNativeInterfaceMock.getNativeInterfaceMock(userAgent));
		setUp(new Class[0], new Class[0], new Class[] { EventsBus.class }, new CustomGuiceModule());
		eventsBus = injector.getInstance(EventsBus.class);
		doReturn(handlerRegistration).when(eventsBus).addHandlerToSource(eq(getType(ON_FULL_SCREEN_OPEN)), eq(fullScreenWrapper), any(MediaEventHandler.class),
				any(PageScope.class));
		doReturn(handlerRegistration2).when(eventsBus).addHandlerToSource(eq(getType(ON_FULL_SCREEN_EXIT)), eq(fullScreenWrapper),
				any(MediaEventHandler.class), any(PageScope.class));

		MediaWrappersPair instance = new MediaWrappersPair(defaultWrapper, fullScreenWrapper);
		injector.injectMembers(instance);
		this.instance = spy(instance);
		Video video = mock(Video.class);
		when(defaultWrapper.getMediaObject()).thenReturn(video);
		when(fullScreenWrapper.getMediaObject()).thenReturn(video);
	}

	@Test
	public void mediaWrappersGetersTest() {
		before(FIREFOX_WINDOWS);
		assertEquals(defaultWrapper, instance.getDefaultMediaWrapper());
		assertEquals(fullScreenWrapper, instance.getFullScreanMediaWrapper());
	}

	@Test
	public void factoryTest() {
		before(FIREFOX_ANDROID);
		instance = injector.getInstance(MediaWrappersPairFactory.class).getMediaWrappersPair(defaultWrapper, fullScreenWrapper);
		assertNotNull(instance);
	}

	@Test
	public void mobileBrowserInitTest() {
		before(FIREFOX_ANDROID);
		Mockito.verifyZeroInteractions(eventsBus);
	}

	@Test
	public void safariBrowserInitTest() {
		before(SAFARI);
		Mockito.verifyZeroInteractions(eventsBus);
	}

	@Test
	public void desktopBrowserInitTest() {
		before(FIREFOX_WINDOWS);
		verify(eventsBus).addHandlerToSource(eq(getType(ON_FULL_SCREEN_OPEN)), eq(fullScreenWrapper), any(MediaEventHandler.class), any(EventScope.class));
		verify(eventsBus).addHandlerToSource(eq(getType(ON_FULL_SCREEN_EXIT)), eq(fullScreenWrapper), any(MediaEventHandler.class), any(EventScope.class));
	}

	@Test
	public void disableFullScreenSynchronizationInitTest() {
		before(FIREFOX_WINDOWS);
		verify(eventsBus).addHandlerToSource(eq(getType(ON_FULL_SCREEN_OPEN)), eq(fullScreenWrapper), any(MediaEventHandler.class), any(EventScope.class));
		verify(eventsBus).addHandlerToSource(eq(getType(ON_FULL_SCREEN_EXIT)), eq(fullScreenWrapper), any(MediaEventHandler.class), any(EventScope.class));
		instance.disableFullScreenSynchronization();
		verify(handlerRegistration, times(1)).removeHandler();
		verify(handlerRegistration2, times(1)).removeHandler();
		instance.disableFullScreenSynchronization();
		verify(handlerRegistration, times(1)).removeHandler();
		verify(handlerRegistration2, times(1)).removeHandler();
	}

	@Test
	public void mobileFullScreenOpenRequestTest() {
		before(FIREFOX_ANDROID);
		MediaEvent mediaEvent = new MediaEvent(ON_FULL_SCREEN_OPEN);
		eventsBus.fireEventFromSource(mediaEvent, fullScreenWrapper);
		verify(instance, times(0)).onMediaEvent(eq(mediaEvent));
	}

	@Test
	public void safariFullScreenOpenRequestTest() {
		before(SAFARI);
		MediaEvent mediaEvent = new MediaEvent(ON_FULL_SCREEN_OPEN);
		eventsBus.fireEventFromSource(mediaEvent, fullScreenWrapper);
		verify(instance, times(0)).onMediaEvent(eq(mediaEvent));
	}

	@Test
	public void desktopFullScreenOpenRequestTest() {
		before(FIREFOX_WINDOWS);
		MediaEvent mediaEvent = new MediaEvent(ON_FULL_SCREEN_OPEN);
		instance.onMediaEvent(mediaEvent);
		verify(instance).setCurrentTimeForMedia(eq(fullScreenWrapper), eq(defaultWrapper));
		verify(eventsBus).fireEventFromSource(any(MediaEvent.class), eq(defaultWrapper));
		verify(eventsBus).fireEventFromSource(any(MediaEvent.class), eq(fullScreenWrapper));
		verify(eventsBus).addHandlerToSource(eq(getType(ON_PLAY)), eq(fullScreenWrapper), any(MediaEventHandler.class), Matchers.any(PageScope.class));
		verify(instance).firePlay(fullScreenWrapper);
	}

	@Test
	public void desktopFullScreenCloseRequestTest() {
		before(FIREFOX_WINDOWS);
		MediaEvent mediaEvent = new MediaEvent(ON_FULL_SCREEN_EXIT);
		instance.onMediaEvent(mediaEvent);
		verify(instance).setCurrentTimeForMedia(eq(defaultWrapper), eq(fullScreenWrapper));
		verify(eventsBus).fireEventFromSource(any(MediaEvent.class), eq(defaultWrapper));
		verify(eventsBus).fireEventFromSource(any(MediaEvent.class), eq(fullScreenWrapper));
		verify(eventsBus).addHandlerToSource(eq(getType(ON_PLAY)), eq(defaultWrapper), any(MediaEventHandler.class), Matchers.any(PageScope.class));
		verify(instance).firePlay(defaultWrapper);
	}

	@Test
	public void synchronizeTimeTest() {
		desktopFullScreenOpenRequestTest(); // preparehandler
		Mockito.reset(eventsBus);
		eventsBus.fireEventFromSource(new MediaEvent(ON_PLAY), fullScreenWrapper);
		verify(instance).setCurrentTimeForMedia(eq(fullScreenWrapper), eq(defaultWrapper));
		verify(eventsBus).fireAsyncEventFromSource(any(MediaEvent.class), eq(fullScreenWrapper));
	}

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}
}
