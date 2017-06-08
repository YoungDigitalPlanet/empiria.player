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

package eu.ydp.empiria.player.client.controller.feedback.player;

import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.media.html5.HTML5AudioMediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SingleFeedbackSoundPlayerWithHTML5MediaWrapperJUnitTest {

    private SingleFeedbackSoundPlayer testObj;
    @Mock
    private EventsBus eventsBus;
    @Mock
    private HTML5AudioMediaWrapper mediaWrapper;
    @Mock
    private Provider<HTML5MediaForFeedbacksAvailableOptions> optionsProvider;

    @Mock
    private HTML5MediaForFeedbacksAvailableOptions options;

    @Before
    public void before() {
        when(optionsProvider.get()).thenReturn(options);
        testObj = new SingleFeedbackSoundPlayer(mediaWrapper, eventsBus, optionsProvider);
    }

    @Test
    public void shouldOverrideWrapperOptions() {
        verify(mediaWrapper).setMediaAvailableOptions(options);
    }
}
