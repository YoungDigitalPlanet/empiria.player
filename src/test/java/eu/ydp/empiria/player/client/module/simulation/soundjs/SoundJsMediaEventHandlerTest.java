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

package eu.ydp.empiria.player.client.module.simulation.soundjs;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockito;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import eu.ydp.gwtutil.client.debug.log.Logger;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class SoundJsMediaEventHandlerTest {

    private final static String FILE = "file.mp3";

    @InjectMocks
    private SoundJsMediaEventHandler testObj;

    @Mock
    private SoundJsNative soundJsNative;
    @Mock
    private Logger logger;

    @Mock
    private MediaEvent mediaEvent;
    @Mock
    private MediaWrapper<Widget> mediaWrapper;

    @Before
    public void setUp() {
        GwtMockito.initMocks(this);
        MediaBase mediaBase = mock(MediaBase.class);

        when(mediaEvent.getMediaWrapper()).thenReturn((MediaWrapper) mediaWrapper);
        when(mediaWrapper.getMediaObject()).thenReturn(mediaBase);
        when(mediaBase.getCurrentSrc()).thenReturn(FILE);
    }

    @Test
    public void shouldProcess_ifOnEnd() {
        // given
        when(mediaEvent.getType()).thenReturn(MediaEventTypes.ON_END);

        // when
        testObj.onMediaEvent(mediaEvent);

        // then
        verify(soundJsNative).onComplete(FILE);
    }

    @Test
    @Parameters
    public void shouldNotProcess(MediaEventTypes type) {
        // given
        when(mediaEvent.getType()).thenReturn(type);

        // when
        testObj.onMediaEvent(mediaEvent);

        // then
        verifyZeroInteractions(soundJsNative, logger);
    }

    public Object[] parametersForShouldNotProcess() {
        final List<MediaEventTypes> mappedTypes = Lists.newArrayList(MediaEventTypes.ON_END);
        List<MediaEventTypes> allTypes = Lists.newArrayList(MediaEventTypes.values());
        List<MediaEventTypes> typesNotToProcess = FluentIterable.from(allTypes).filter(new Predicate<MediaEventTypes>() {
            @Override
            public boolean apply(MediaEventTypes input) {
                return !mappedTypes.contains(input);
            }
        }).toList();

        return typesNotToProcess.toArray();
    }

    @Test
    public void shouldNotProceed_ifMediaObjectIsNotMediaBase() {
        // given
        when(mediaWrapper.getMediaObject()).thenReturn(mock(Widget.class));

        // when
        testObj.onMediaEvent(mediaEvent);

        // then
        verify(logger).error("MediaObject does not extend a MediaBase");
        verifyZeroInteractions(soundJsNative, logger);
    }

}
