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
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.util.BrowserNativeInterface;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.regex.Pattern;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class VideoFullScreenHelperTest extends AbstractTestBaseWithoutAutoInjectorInit {

    public static final String FIREFOX_WINDOWS = "Mozilla/5.0 (Windows NT 6.1; rv:15.0) Gecko/20120716 Firefox/15.0a2";
    public static final String FIREFOX_ANDROID = "Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30";
    public static final String IE_9 = "Mozilla/5.0 (Windows; U; MSIE 9.0; WIndows NT 9.0; Trident; en-US))";
    public static final String SAFARI = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.22 (KHTML, like Gecko) Chrome/25.0.1364.172 Safari/537.22";

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
            binder.bind(VideoFullScreenView.class).toInstance(mock(VideoFullScreenViewImpl.class));
        }
    }

    public void before(String userAgent) {
        BrowserNativeInterface nativeInterface = mock(BrowserNativeInterface.class);
        when(nativeInterface.getUserAgentStrting()).thenReturn(userAgent);
        when(nativeInterface.isUserAgent(anyString(), anyString())).then(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                Pattern pattern = Pattern.compile(String.valueOf(args[0]));
                return pattern.matcher(String.valueOf(args[1])).find();
            }
        });

        UserAgentChecker.setNativeInterface(nativeInterface);
        setUp(new Class[0], new Class[0], new Class[]{EventsBus.class}, new CustomGuiceModule());
        fullScreenHelper = injector.getInstance(NativeHTML5FullScreenHelper.class);
        instance = injector.getInstance(VideoFullScreenHelper.class);
        instance.postConstruct(); // nie dziala z automatu na mokach
        eventsBus = injector.getInstance(EventsBus.class);

    }

    @Test
    public void initTest() {
        before(FIREFOX_WINDOWS);
        verify(fullScreenHelper).addFullScreenEventHandler(Matchers.any(VideoFullScreenEventHandler.class));
    }

    @Test
    public void openFullScreenDesktopTest() {
        before(FIREFOX_WINDOWS);
        doNothing().when(instance)
                .openFullScreenDesktop(Matchers.any(MediaWrapper.class), Matchers.any(Element.class));
        instance.openFullScreen(mock(MediaWrapper.class), mock(MediaWrapper.class), mock(Element.class));
        verify(instance).openFullScreenDesktop(Matchers.any(MediaWrapper.class), Matchers.any(Element.class));
    }

    @Test
    public void openFullScreenMobileTest() {
        before(FIREFOX_ANDROID);
        doNothing().when(instance)
                .openFullScreenMobile(Matchers.any(MediaWrapper.class));
        instance.openFullScreen(mock(MediaWrapper.class), mock(MediaWrapper.class), mock(Element.class));
        verify(instance).openFullScreenMobile(Matchers.any(MediaWrapper.class));
    }

    @Test
    public void openFullScreenSafariTest() {
        before(SAFARI);
        doNothing().when(instance)
                .openFullScreenMobile(Matchers.any(MediaWrapper.class));
        instance.openFullScreen(mock(MediaWrapper.class), mock(MediaWrapper.class), mock(Element.class));
        verify(instance).openFullScreenMobile(Matchers.any(MediaWrapper.class));
    }

    @Test
    public void openFullScreenIETest() {
        before(IE_9);
        doNothing().when(instance)
                .openFullscreenIE(Matchers.any(MediaWrapper.class), Matchers.any(Element.class));
        instance.openFullScreen(mock(MediaWrapper.class), mock(MediaWrapper.class), mock(Element.class));
        verify(instance).openFullscreenIE(Matchers.any(MediaWrapper.class), Matchers.any(Element.class));
    }

    @Test
    public void closeFullScreenTest() {
        before(FIREFOX_WINDOWS);
        doNothing().when(instance)
                .clearFullScreenView();
        instance.closeFullScreen();
        verify(fullScreenHelper).exitFullScreen();
        verify(instance).closeFullScreen();
        verify(eventsBus).fireEventFromSource(Matchers.any(MediaEvent.class), Matchers.any(MediaWrapper.class), Matchers.any(CurrentPageScope.class));
    }

}
