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

import com.google.gwt.json.client.JSONArray;
import eu.ydp.empiria.player.client.json.JSONStateSerializer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerWorkModeStateTest {

    @InjectMocks
    private PlayerWorkModeState testObj;
    @Mock
    private PlayerWorkModeService playerWorkModeService;
    @Mock
    private JSONStateSerializer jsonStateUtil;

    @Test
    public void testShouldUpdateModeOnService() {
        // given
        JSONArray state = mock(JSONArray.class);
        when(jsonStateUtil.extractString(state)).thenReturn("FULL");

        // when
        testObj.setState(state);

        // then
        verify(playerWorkModeService).tryToUpdateWorkMode(PlayerWorkMode.FULL);
    }

    @Test
    public void shouldReturnStateWithCurrentWorkMode() {
        // given
        final JSONArray EXPECTED = mock(JSONArray.class);

        when(playerWorkModeService.getCurrentWorkMode()).thenReturn(PlayerWorkMode.FULL);
        when(jsonStateUtil.createWithString("FULL")).thenReturn(EXPECTED);

        // when
        JSONArray actual = testObj.getState();

        // then
        assertThat(actual).isEqualTo(EXPECTED);
    }
}
