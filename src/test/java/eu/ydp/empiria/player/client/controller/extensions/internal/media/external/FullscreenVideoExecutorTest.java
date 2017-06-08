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

package eu.ydp.empiria.player.client.controller.extensions.internal.media.external;

import com.google.common.collect.Maps;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration.MediaType;
import eu.ydp.empiria.player.client.module.media.external.FullscreenVideoMediaWrapper;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(JUnitParamsRunner.class)
public class FullscreenVideoExecutorTest {

    @InjectMocks
    private FullscreenVideoExecutor testObj;
    @Mock
    private ExternalFullscreenVideoConnector connector;
    @Mock
    private EventsBus eventsBus;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void play_timeChanged() {
        // given
        final String ID = "ID";
        final Double TIME = 70d;
        FullscreenVideoMediaWrapper mw = createMediaWrapperMock(ID, TIME);
        testObj.setMediaWrapper(mw);
        BaseMediaConfiguration bmc = createBaseMediaConfiguration();
        testObj.setBaseMediaConfiguration(bmc);

        // when
        testObj.play();

        // then
        verify(connector).openFullscreen(eq(ID), anyCollection(), eq(TIME));
    }

    @Test
    @Parameters({"0", "25", "50", "75", "100"})
    public void setCurrentTime(double timeNew) {
        // given
        final String ID = "ID";
        final Double TIME_OLD = 50d;
        FullscreenVideoMediaWrapper mw = createMediaWrapperMock(ID, TIME_OLD);
        testObj.setMediaWrapper(mw);
        BaseMediaConfiguration bmc = createBaseMediaConfiguration();
        testObj.setBaseMediaConfiguration(bmc);

        // when
        testObj.setCurrentTime(timeNew);

        // then
        verify(mw).setCurrentTime(eq(timeNew));
        ArgumentCaptor<MediaEvent> ac = ArgumentCaptor.forClass(MediaEvent.class);
        verify(eventsBus).fireEventFromSource(ac.capture(), eq(mw));
        assertThat(ac.getValue().getCurrentTime(), equalTo(timeNew));

    }

    @Test
    @Parameters({"0", "25", "50", "75", "100"})
    public void onFullscreenClosed_validId(double timeNew) {
        // given
        final String ID = "ID";
        final Double TIME_OLD = 50d;
        FullscreenVideoMediaWrapper mw = createMediaWrapperMock(ID, TIME_OLD);
        testObj.setMediaWrapper(mw);
        BaseMediaConfiguration bmc = createBaseMediaConfiguration();
        testObj.setBaseMediaConfiguration(bmc);

        // when
        testObj.onFullscreenClosed(ID, timeNew);

        // then
        verify(mw).setCurrentTime(eq(timeNew));
        ArgumentCaptor<MediaEvent> ac = ArgumentCaptor.forClass(MediaEvent.class);
        verify(eventsBus).fireEventFromSource(ac.capture(), eq(mw));
        assertThat(ac.getValue().getCurrentTime(), equalTo(timeNew));
    }

    @Test
    public void onFullscreenClosed_invalidId() {
        // given
        final String ID = "ID";
        final String OTHER_ID = "OTHER_ID";
        final Double TIME_OLD = 50d;
        final double TIME_NEW = 60d;
        FullscreenVideoMediaWrapper mw = createMediaWrapperMock(ID, TIME_OLD);
        testObj.setMediaWrapper(mw);
        BaseMediaConfiguration bmc = createBaseMediaConfiguration();
        testObj.setBaseMediaConfiguration(bmc);

        // when
        testObj.onFullscreenClosed(OTHER_ID, TIME_NEW);

        // then
        verify(mw, never()).setCurrentTime(anyDouble());
        verify(eventsBus, never()).fireEventFromSource(any(MediaEvent.class), anyObject());
    }

    private FullscreenVideoMediaWrapper createMediaWrapperMock(final String id, Double time) {
        FullscreenVideoMediaWrapper mw = mock(FullscreenVideoMediaWrapper.class);
        stub(mw.getMediaUniqId()).toReturn(id);
        stub(mw.getCurrentTime()).toReturn(time);
        return mw;
    }

    private BaseMediaConfiguration createBaseMediaConfiguration() {
        return new BaseMediaConfiguration(Maps.<String, String>newHashMap(), MediaType.VIDEO, "poster.jpg", 480, 640, true, false, "");
    }

}
