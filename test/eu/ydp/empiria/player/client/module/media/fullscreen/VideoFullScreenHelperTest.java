package eu.ydp.empiria.player.client.module.media.fullscreen;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.google.gwt.dom.client.Style;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.xml.client.Element;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.HTML5FullScreenHelper;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.fullscreen.VideoFullScreenEventHandler;
import eu.ydp.empiria.player.client.util.events.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.scope.PageScope;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.junit.mock.UserAgentCheckerNativeInterfaceMock;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;
//import eu.ydp.gwtutil.junit.mock.ElementMock;

@SuppressWarnings("PMD")
@RunWith(ExMockRunner.class)
@PrepareForTest({ HTML5FullScreenHelper.class,Style.class, com.google.gwt.user.client.Element.class,com.google.gwt.dom.client.Element.class, FlowPanel.class})
public class VideoFullScreenHelperTest extends AbstractTestBaseWithoutAutoInjectorInit {
	private VideoFullScreenHelper instance = null;
	private EventsBus eventsBus = null;
	private HTML5FullScreenHelper fullScreenHelper;
	private static class CustomGuiceModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(HTML5FullScreenHelper.class).toInstance(mock(HTML5FullScreenHelper.class));
			binder.bind(VideoFullScreenHelper.class).toInstance(spy(new VideoFullScreenHelper()));

		}
	}
	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}


	public void before(String userAgent) {
		UserAgentChecker.setNativeInterface(UserAgentCheckerNativeInterfaceMock.getNativeInterfaceMock(userAgent));
		setUp(new Class[0], new Class[0], new Class[] { EventsBus.class }, new CustomGuiceModule());
		fullScreenHelper  = injector.getInstance(HTML5FullScreenHelper.class);
		instance = injector.getInstance(VideoFullScreenHelper.class);
		instance.postConstruct(); //nie dziala z automatu na mokach
		eventsBus = injector.getInstance(EventsBus.class);

	}

	@Test
	public void initTest(){
		before(UserAgentCheckerNativeInterfaceMock.FIREFOX_WINDOWS);
		verify(fullScreenHelper).addFullScreenEventHandler(Mockito.any(VideoFullScreenEventHandler.class));
	}

	@Test
	public void openFullScreenDesktopTest(){
		before(UserAgentCheckerNativeInterfaceMock.FIREFOX_WINDOWS);
		doNothing().when(instance).openFullScreenDesktop(Mockito.any(MediaWrapper.class), Mockito.any(Element.class));
		instance.openFullScreen(mock(MediaWrapper.class), mock(MediaWrapper.class), mock(Element.class));
		verify(instance).openFullScreenDesktop(Mockito.any(MediaWrapper.class), Mockito.any(Element.class));
	}
	@Test
	public void openFullScreenMobileTest(){
		before(UserAgentCheckerNativeInterfaceMock.FIREFOX_ANDROID);
		doNothing().when(instance).openFullScreenMobile(Mockito.any(MediaWrapper.class), Mockito.any(MediaWrapper.class));
		instance.openFullScreen(mock(MediaWrapper.class), mock(MediaWrapper.class), mock(Element.class));
		verify(instance).openFullScreenMobile(Mockito.any(MediaWrapper.class), Mockito.any(MediaWrapper.class));
	}
	@Test
	public void openFullScreenIETest(){
		before(UserAgentCheckerNativeInterfaceMock.IE_9);
		doNothing().when(instance).openFullscreenIE(Mockito.any(MediaWrapper.class), Mockito.any(Element.class));
		instance.openFullScreen(mock(MediaWrapper.class), mock(MediaWrapper.class), mock(Element.class));
		verify(instance).openFullscreenIE(Mockito.any(MediaWrapper.class), Mockito.any(Element.class));
	}

	@Test
	public void closeFullScreenTest(){
		before(UserAgentCheckerNativeInterfaceMock.FIREFOX_WINDOWS);
		doNothing().when(instance).clearFullScreenView();
		instance.closeFullScreen();
		verify(fullScreenHelper).exitFullScreen();
		verify(instance).closeFullScreen();
		verify(eventsBus).fireEventFromSource(Mockito.any(MediaEvent.class), Mockito.any(MediaWrapper.class),Mockito.any(PageScope.class));
	}

}
