/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.controller.extensions.internal.sound.factory;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.media.client.MediaBase;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.AbstractHTML5MediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.HTML5AudioMediaExecutor;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.HTML5VideoMediaExecutor;
import eu.ydp.empiria.player.client.gin.factory.MediaWrapperFactory;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.module.object.impl.Media;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Matchers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class HTML5MediaExecutorFactoryJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

    private class CustomGinModule implements Module {
        @Override
        public void configure(Binder binder) {
            binder.install(new FactoryModuleBuilder().build(MediaWrapperFactory.class));
            binder.bind(HTML5MediaWrapperFactory.class).toInstance(spy(new HTML5MediaWrapperFactory()));
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

    HTML5MediaExecutorFactory instance;
    HTML5MediaWrapperFactory mediaWrapperFactory;

    @Before
    public void before() {
        setUpAndOverrideMainModule(new GuiceModuleConfiguration(), new CustomGinModule());
        instance = injector.getInstance(HTML5MediaExecutorFactory.class);
        mediaWrapperFactory = injector.getInstance(HTML5MediaWrapperFactory.class);
    }

    @Test
    public void createMediaExecutorVideoTest() {
        Media media = mock(Media.class);
        doReturn(mock(MediaBase.class)).when(media).getMedia();
        AbstractHTML5MediaExecutor executor = instance.createMediaExecutor(media, MediaType.VIDEO);
        assertNotNull(executor);
        verify(media).setEventBusSourceObject(Matchers.any(MediaWrapper.class));
        assertTrue(executor instanceof HTML5VideoMediaExecutor);
    }

    @Test
    public void createMediaExecutorAudioTest() {
        Media media = mock(Media.class);
        doReturn(mock(MediaBase.class)).when(media).getMedia();
        AbstractHTML5MediaExecutor executor = instance.createMediaExecutor(media, MediaType.AUDIO);
        assertNotNull(executor);
        verify(media).setEventBusSourceObject(Matchers.any(MediaWrapper.class));
        assertTrue(executor instanceof HTML5AudioMediaExecutor);
    }

    @Test
    public void createMediaExecutorTest() {
        AbstractHTML5MediaExecutor executor = instance.createMediaExecutor(null, MediaType.AUDIO);
        assertNull(executor);
    }
}
