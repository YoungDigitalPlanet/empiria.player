package eu.ydp.empiria.player.client.module.media;

import com.google.common.collect.Sets;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.media.button.AbstractMediaController;
import eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenView;
import eu.ydp.empiria.player.client.module.media.fullscreen.VideoFullScreenViewImpl;
import eu.ydp.empiria.player.client.module.media.progress.MediaProgressBarAndroid;
import eu.ydp.empiria.player.client.module.media.progress.MediaProgressBarImpl;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;
import eu.ydp.gwtutil.client.util.UserAgentUtil;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MediaControllerFactoryImplTest extends AbstractTestBaseWithoutAutoInjectorInit {

    MediaControllerFactoryImpl instance;

    private class CustomGinModule implements Module {
        @Override
        public void configure(Binder binder) {
            binder.bind(MediaProgressBarImpl.class).toProvider(progressBarProvider);
            binder.bind(MediaProgressBarAndroid.class).toProvider(progressBarAndroidProvider);
            binder.bind(UserAgentUtil.class).toInstance(userAgentUtil);
            binder.bind(VideoFullScreenView.class).toInstance(mock(VideoFullScreenViewImpl.class));
        }
    }

    Provider<MediaProgressBarImpl> progressBarProvider = new Provider<MediaProgressBarImpl>() {
        @Override
        public MediaProgressBarImpl get() {
            return mock(MediaProgressBarImpl.class);
        }
    };

    Provider<MediaProgressBarAndroid> progressBarAndroidProvider = new Provider<MediaProgressBarAndroid>() {
        @Override
        public MediaProgressBarAndroid get() {
            return mock(MediaProgressBarAndroid.class);
        }
    };

    UserAgentUtil userAgentUtil = mock(UserAgentUtil.class);
    Set<MobileUserAgent> androidUserAgentsForProgressBar = Sets.newHashSet(MobileUserAgent.ANDROID23, MobileUserAgent.ANDROID3, MobileUserAgent.ANDROID321,
            MobileUserAgent.ANDROID4);

    @Before
    public void before() {
        setUpAndOverrideMainModule(new GuiceModuleConfiguration(), new CustomGinModule());
        instance = injector.getInstance(MediaControllerFactoryImpl.class);
    }

    @BeforeClass
    public static void disarm() {
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void rearm() {
        GWTMockUtilities.restore();
    }

    @Test
    public void testProgressBarGetAndroid() {
        when(userAgentUtil.isMobileUserAgent((MobileUserAgent[]) Matchers.anyVararg())).thenAnswer(new Answer<Boolean>() {
            @Override
            public Boolean answer(InvocationOnMock invocation) throws Throwable {
                Object[] arguments = invocation.getArguments();
                androidUserAgentsForProgressBar.removeAll(Sets.newHashSet(arguments));
                return androidUserAgentsForProgressBar.isEmpty();
            }
        });

        AbstractMediaController abstractMediaController = instance.get(ModuleTagName.MEDIA_PROGRESS_BAR);
        assertThat(abstractMediaController).isNotNull();
        assertThat(abstractMediaController).isInstanceOf(MediaProgressBarAndroid.class);

    }

    @Test
    public void testProgressBarGetDesktop() {
        when(userAgentUtil.isMobileUserAgent((MobileUserAgent[]) Matchers.anyVararg())).thenReturn(false);
        AbstractMediaController abstractMediaController = instance.get(ModuleTagName.MEDIA_PROGRESS_BAR);
        assertThat(abstractMediaController).isNotNull();
        assertThat(abstractMediaController).isInstanceOf(MediaProgressBarImpl.class);

    }

}
