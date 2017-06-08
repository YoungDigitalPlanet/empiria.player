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

package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.junit.GWTMockUtilities;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEvent;
import eu.ydp.empiria.player.client.util.events.internal.media.MediaEventTypes;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayPauseMediaButtonJunitTest {

    private PlayPauseMediaButton button;

    @Before
    public void setUp() throws Exception {
        button = mock(PlayPauseMediaButton.class, Mockito.CALLS_REAL_METHODS);
    }

    @Test
    public void pauseExpected() {
        when(button.isActive()).thenReturn(true);

        MediaEvent firedEvent = button.createMediaEvent();

        assertThat(firedEvent.getType(), equalTo(MediaEventTypes.PAUSE));
    }

    @Test
    public void playExpected() {
        when(button.isActive()).thenReturn(false);

        MediaEvent firedEvent = button.createMediaEvent();

        assertThat(firedEvent.getType(), equalTo(MediaEventTypes.PLAY));
    }

    @BeforeClass
    public static void disarm() {
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void restore() {
        GWTMockUtilities.restore();
    }
}
