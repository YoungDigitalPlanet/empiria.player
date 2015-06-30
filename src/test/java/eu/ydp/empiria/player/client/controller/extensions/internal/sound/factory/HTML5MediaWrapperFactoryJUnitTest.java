package eu.ydp.empiria.player.client.controller.extensions.internal.sound.factory;

import com.google.inject.Binder;
import com.google.inject.Module;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.gin.factory.MediaWrapperFactory;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.media.html5.AbstractHTML5MediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.HTML5AudioMediaWrapper;
import eu.ydp.empiria.player.client.module.media.html5.HTML5VideoMediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.Media;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class HTML5MediaWrapperFactoryJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

    private MediaWrapperFactory mediaWrapperFactory;
    private HTML5MediaWrapperFactory instance;

    private class CustomGinModule implements Module {
        @Override
        public void configure(Binder binder) {
            binder.bind(MediaWrapperFactory.class).toInstance(mock(MediaWrapperFactory.class));
        }
    }

    @Before
    public void before() {
        setUpAndOverrideMainModule(new GuiceModuleConfiguration(), new CustomGinModule());
        mediaWrapperFactory = injector.getInstance(MediaWrapperFactory.class);
        when(mediaWrapperFactory.getHtml5VideoMediaWrapper(Matchers.any(Media.class))).thenReturn(mock(HTML5VideoMediaWrapper.class));
        when(mediaWrapperFactory.getHtml5AudioMediaWrapper(Matchers.any(Media.class))).thenReturn(mock(HTML5AudioMediaWrapper.class));
        instance = injector.getInstance(HTML5MediaWrapperFactory.class);
    }

    @Test
    public void createMediaWrapperVideoTest() {
        AbstractHTML5MediaWrapper mediaWrapper = instance.createMediaWrapper(mock(Media.class), MediaType.VIDEO);
        assertNotNull(mediaWrapper);
        verify(mediaWrapperFactory).getHtml5VideoMediaWrapper(Matchers.any(Media.class));
        verifyNoMoreInteractions(mediaWrapperFactory);

    }

    @Test
    public void createMediaWrapperAudioTest() {
        AbstractHTML5MediaWrapper mediaWrapper = instance.createMediaWrapper(mock(Media.class), MediaType.AUDIO);
        assertNotNull(mediaWrapper);
        verify(mediaWrapperFactory).getHtml5AudioMediaWrapper(Matchers.any(Media.class));
        verifyNoMoreInteractions(mediaWrapperFactory);
    }
}
