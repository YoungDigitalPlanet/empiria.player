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

package eu.ydp.empiria.player.client.controller.extensions.internal.sound;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.inject.Binder;
import com.google.inject.Module;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.controller.extensions.internal.media.html5.HTML5AudioMediaExecutor;
import eu.ydp.empiria.player.client.media.Audio;
import eu.ydp.empiria.player.client.module.media.html5.HTML5AudioMediaWrapper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ReCreateAudioHackTest extends AbstractTestBaseWithoutAutoInjectorInit {

    private ReCreateAudioHack hack;
    private HTML5AudioMediaWrapper wrapper;
    private HTML5AudioMediaExecutor executor;
    private ReAttachAudioUtil reattachUtil;
    private Audio newAudio;
    private Audio oldAudio;

    @Before
    public void setUpTest() throws Exception {
        setUpGin();
        setUpInjectors();
        mockObjects();
    }

    @Test
    public void testApply() {
        // when
        hack.apply(wrapper, executor);

        // then
        verify(wrapper).setMediaObject(newAudio);
        verify(executor).setMedia(newAudio);
        verify(executor).init();
    }

    private void setUpInjectors() {
        hack = injector.getInstance(ReCreateAudioHack.class);
        reattachUtil = injector.getInstance(ReAttachAudioUtil.class);
    }

    private void mockObjects() {
        oldAudio = mock(Audio.class);
        newAudio = mock(Audio.class);
        wrapper = mock(HTML5AudioMediaWrapper.class);
        executor = mock(HTML5AudioMediaExecutor.class);
        when(wrapper.getMediaObject()).thenReturn(oldAudio);
        when(reattachUtil.reAttachAudio(oldAudio)).thenReturn(newAudio);
    }

    private void setUpGin() {
        setUpAndOverrideMainModule(new GuiceModuleConfiguration(), new CustomGinModule());
    }

    private class CustomGinModule implements Module {
        @Override
        public void configure(Binder binder) {
            binder.bind(ReAttachAudioUtil.class).toInstance(mock(ReAttachAudioUtil.class));
        }
    }

    @BeforeClass
    public static void prepareTestEnviroment() {
        /**
         * disable GWT.create() behavior for pure JUnit testing
         */
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void restoreEnviroment() {
        /**
         * restore GWT.create() behavior
         */
        GWTMockUtilities.restore();
    }

}
