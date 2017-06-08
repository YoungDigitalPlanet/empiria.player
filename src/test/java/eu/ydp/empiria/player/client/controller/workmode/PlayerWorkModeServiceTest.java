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

package eu.ydp.empiria.player.client.controller.workmode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerWorkModeServiceTest {

    @InjectMocks
    private PlayerWorkModeService testObj;

    @Mock(extraInterfaces = {WorkModePreviewClient.class, WorkModeTestClient.class})
    private WorkModeClientType moduleToDisableAndEnable;
    @Mock(extraInterfaces = WorkModeTestClient.class)
    private WorkModeClientType moduleToDisable;
    @Mock(extraInterfaces = WorkModePreviewClient.class)
    private WorkModeClientType moduleToEnable;
    @Mock
    private WorkModeClientType moduleWithoutInterfaces;

    @Test
    public void shouldSetModeOnModuleDuringRegistration_disablingAndEnabling() {
        // given
        PlayerWorkMode previousWorkMode = PlayerWorkMode.TEST;
        PlayerWorkMode currentWorkMode = PlayerWorkMode.PREVIEW;

        testObj.tryToUpdateWorkMode(previousWorkMode);
        testObj.tryToUpdateWorkMode(currentWorkMode);

        // when
        testObj.registerModule(moduleToDisableAndEnable);

        // then
        verify((WorkModeTestClient) moduleToDisableAndEnable).disableTestMode();
        verify((WorkModePreviewClient) moduleToDisableAndEnable).enablePreviewMode();
        verifyNoMoreInteractions(moduleToDisableAndEnable);
    }

    @Test
    public void shouldSetModeOnModuleDuringRegistration_disabling() {
        // given
        PlayerWorkMode previousWorkMode = PlayerWorkMode.TEST;
        PlayerWorkMode currentWorkMode = PlayerWorkMode.PREVIEW;

        testObj.tryToUpdateWorkMode(previousWorkMode);
        testObj.tryToUpdateWorkMode(currentWorkMode);

        // when
        testObj.registerModule(moduleToDisable);

        // then
        verify((WorkModeTestClient) moduleToDisable).disableTestMode();
        verifyNoMoreInteractions(moduleToDisable);
    }

    @Test
    public void shouldSetModeOnModuleDuringRegistration_enabling() {
        // given
        PlayerWorkMode previousWorkMode = PlayerWorkMode.TEST;
        PlayerWorkMode currentWorkMode = PlayerWorkMode.PREVIEW;

        testObj.tryToUpdateWorkMode(previousWorkMode);
        testObj.tryToUpdateWorkMode(currentWorkMode);

        // when
        testObj.registerModule(moduleToEnable);

        // then
        verify((WorkModePreviewClient) moduleToEnable).enablePreviewMode();
        verifyNoMoreInteractions(moduleToEnable);
    }

    @Test
    public void shouldSetModeOnModuleDuringRegistration_() {
        // given
        PlayerWorkMode previousWorkMode = PlayerWorkMode.TEST;
        PlayerWorkMode currentWorkMode = PlayerWorkMode.PREVIEW;

        testObj.tryToUpdateWorkMode(previousWorkMode);
        testObj.tryToUpdateWorkMode(currentWorkMode);

        // when
        testObj.registerModule(moduleWithoutInterfaces);

        // then
        verifyZeroInteractions(moduleWithoutInterfaces);
    }

    @Test
    public void shouldNotifyModule_ifTransitionIsValid() {
        // given
        PlayerWorkMode validTransition = PlayerWorkMode.PREVIEW;
        testObj.registerModule(moduleToEnable);

        // when
        testObj.tryToUpdateWorkMode(validTransition);

        // then
        verify((WorkModePreviewClient) moduleToEnable).enablePreviewMode();
    }

    @Test
    public void shouldNotNotifyModule_ifTransitionIsInvalid() {
        // given
        PlayerWorkMode invalidTransition = PlayerWorkMode.FULL;

        PlayerWorkMode initialState = PlayerWorkMode.TEST;
        testObj.tryToUpdateWorkMode(initialState);
        testObj.registerModule(moduleToDisableAndEnable);

        // when
        testObj.tryToUpdateWorkMode(invalidTransition);

        // then
        verify((WorkModeTestClient) moduleToDisableAndEnable, never()).disableTestMode();
    }
}
