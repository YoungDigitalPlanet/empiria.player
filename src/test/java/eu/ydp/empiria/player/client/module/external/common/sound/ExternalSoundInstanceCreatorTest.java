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

package eu.ydp.empiria.player.client.module.external.common.sound;

import com.google.common.base.Optional;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.gin.factory.ExternalInteractionModuleFactory;
import eu.ydp.empiria.player.client.media.MediaWrapperCreator;
import eu.ydp.empiria.player.client.module.external.common.ExternalPaths;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.callback.CallbackReceiver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExternalSoundInstanceCreatorTest {

    @InjectMocks
    private ExternalSoundInstanceCreator testObj;
    @Mock
    private ExternalPaths paths;
    @Mock
    private MediaWrapperCreator mediaWrapperCreator;
    @Mock
    private ExternalInteractionModuleFactory moduleFactory;
    @Mock
    private ExternalSoundInstanceCallback callback;
    @Mock
    private MediaWrapper<Widget> audioWrapper;
    @Mock
    private ExternalSoundInstance soundInstance;
    @Captor
    private ArgumentCaptor<CallbackReceiver<MediaWrapper<Widget>>> argumentCaptor;
    private Optional<OnEndCallback> onEndCallback = Optional.absent();
    private Optional<OnPauseCallback> onPauseCallback = Optional.absent();

    @Before
    public void init() {
        when(paths.getExternalFilePath("ok.mp3")).thenReturn("external/ok.mp3");
        when(moduleFactory.getExternalSoundInstance(audioWrapper, onEndCallback, onPauseCallback)).thenReturn(soundInstance);
    }

    @Test
    public void shouldCreateSound() {
        // given
        String src = "ok.mp3";

        // when
        testObj.createSound(src, callback, onEndCallback, onPauseCallback);
        verify(mediaWrapperCreator).createExternalMediaWrapper(eq("external/ok.mp3"), argumentCaptor.capture());
        CallbackReceiver<MediaWrapper<Widget>> callbackReceiver = argumentCaptor.getValue();
        callbackReceiver.setCallbackReturnObject(audioWrapper);

        // then
        verify(callback).onSoundCreated(soundInstance, src);
    }
}
