package eu.ydp.empiria.player.client.module.media.fullscreen;

import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.inject.Binder;
import com.google.inject.Module;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.NativeHTML5FullScreenHelper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.fullscreen.VideoFullScreenEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.scope.PageScope;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.junit.mock.UserAgentCheckerNativeInterfaceMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class VideoFullScreenHelperTest extends AbstractTestBaseWithoutAutoInjectorInit {
    private VideoFullScreenHelper instance = null;
    private EventsBus eventsBus = null;
    private NativeHTML5FullScreenHelper fullScreenHelper;

    private static class CustomGuiceModule implements Module {
        @Override
        public void configure(Binder binder) {
            binder.bind(NativeHTML5FullScreenHelper.class)
                    .toInstance(mock(NativeHTML5FullScreenHelper.class));
            binder.bind(VideoFullScreenHelper.class)
                    .toInstance(spy(new VideoFullScreenHelper()));

        }
    }

    public void before(String userAgent) {
        UserAgentChecker.setNativeInterface(UserAgentCheckerNativeInterfaceMock.getNativeInterfaceMock(userAgent));
        setUp(new Class[0], new Class[0], new Class[]{EventsBus.class}, new CustomGuiceModule());
        fullScreenHelper = injector.getInstance(NativeHTML5FullScreenHelper.class);
        instance = injector.getInstance(VideoFullScreenHelper.class);
        instance.postConstruct(); // nie dziala z automatu na mokach
        eventsBus = injector.getInstance(EventsBus.class);

    }

    @Test
    public void initTest() {
        before(UserAgentCheckerNativeInterfaceMock.FIREFOX_WINDOWS);
        verify(fullScreenHelper).addFullScreenEventHandler(Matchers.any(VideoFullScreenEventHandler.class));
    }

    @Test
    public void openFullScreenDesktopTest() {
        before(UserAgentCheckerNativeInterfaceMock.FIREFOX_WINDOWS);
        doNothing().when(instance)
                .openFullScreenDesktop(Matchers.any(MediaWrapper.class), Matchers.any(Element.class));
        instance.openFullScreen(mock(MediaWrapper.class), mock(MediaWrapper.class), mock(Element.class));
        verify(instance).openFullScreenDesktop(Matchers.any(MediaWrapper.class), Matchers.any(Element.class));
    }

    @Test
    public void openFullScreenMobileTest() {
        before(UserAgentCheckerNativeInterfaceMock.FIREFOX_ANDROID);
        doNothing().when(instance)
                .openFullScreenMobile(Matchers.any(MediaWrapper.class));
        instance.openFullScreen(mock(MediaWrapper.class), mock(MediaWrapper.class), mock(Element.class));
        verify(instance).openFullScreenMobile(Matchers.any(MediaWrapper.class));
    }

    @Test
    public void openFullScreenSafariTest() {
        before(UserAgentCheckerNativeInterfaceMock.SAFARI);
        doNothing().when(instance)
                .openFullScreenMobile(Matchers.any(MediaWrapper.class));
        instance.openFullScreen(mock(MediaWrapper.class), mock(MediaWrapper.class), mock(Element.class));
        verify(instance).openFullScreenMobile(Matchers.any(MediaWrapper.class));
    }

    @Test
    public void openFullScreenIETest() {
        before(UserAgentCheckerNativeInterfaceMock.IE_9);
        doNothing().when(instance)
                .openFullscreenIE(Matchers.any(MediaWrapper.class), Matchers.any(Element.class));
        instance.openFullScreen(mock(MediaWrapper.class), mock(MediaWrapper.class), mock(Element.class));
        verify(instance).openFullscreenIE(Matchers.any(MediaWrapper.class), Matchers.any(Element.class));
    }

    @Test
    public void closeFullScreenTest() {
        before(UserAgentCheckerNativeInterfaceMock.FIREFOX_WINDOWS);
        doNothing().when(instance)
                .clearFullScreenView();
        instance.closeFullScreen();
        verify(fullScreenHelper).exitFullScreen();
        verify(instance).closeFullScreen();
        verify(eventsBus).fireEventFromSource(Matchers.any(MediaEvent.class), Matchers.any(MediaWrapper.class), Matchers.any(PageScope.class));
    }

}
