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

import com.google.gwt.dom.client.AudioElement;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.FlowPanel;
import eu.ydp.empiria.player.client.media.Audio;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class ReAttachAudioUtilTest {

    private ReAttachAudioUtil util;
    private Audio audioMock;

    @Before
    public void setUp() throws Exception {
        util = new ReAttachAudioUtilMock();
        audioMock = mock(Audio.class);
        when(audioMock.getAudioElement()).thenReturn(mock(AudioElement.class));
        when(audioMock.getParent()).thenReturn(mock(FlowPanel.class));
    }

    @Test
    public void testParentPanelAndRemoveAudioElement() {
        // when
        FlowPanel parentPanel = util.getParentPanelAndRemoveAudioElement(audioMock);

        // then
        verify(parentPanel).remove(audioMock);
    }

    @Test
    public void createNewAudioAndAddToFlowPanel() {
        // given
        FlowPanel parentPanel = mock(FlowPanel.class);

        // when
        Audio newAudio = util.createNewAudioAndAddToFlowPanel(parentPanel);

        // then
        verify(newAudio).addToParent(parentPanel);
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

    private class ReAttachAudioUtilMock extends ReAttachAudioUtil {
        @Override
        protected Audio createAudio() {
            return mock(Audio.class);
        }
    }
}
