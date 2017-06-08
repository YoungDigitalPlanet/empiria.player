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

package eu.ydp.empiria.player.client.module.connection.presenter.translation;

import eu.ydp.empiria.player.client.module.connection.presenter.ConnectionItems;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SurfaceRectangleFinderTest {

    @InjectMocks
    private SurfaceRectangleFinder testObj;

    @Mock
    private SurfacesOffsetsCalculator surfacesOffsetsUtils;

    @Test
    public void shouldReturnMinLeftOffsetOfAllItems() throws Exception {
        // given
        ConnectionItems items = Mockito.mock(ConnectionItems.class);

        int minLeftOffset = 123;
        when(surfacesOffsetsUtils.findMinOffsetLeft(items)).thenReturn(minLeftOffset);

        // when
        int result = testObj.findOffsetLeft(items);

        // then
        assertThat(result).isEqualTo(minLeftOffset);
    }

    @Test
    public void shouldReturnMinTopOffsetOfAllItems() throws Exception {
        // given
        ConnectionItems items = Mockito.mock(ConnectionItems.class);

        int minTopOffset = 1232342;
        when(surfacesOffsetsUtils.findMinOffsetTop(items)).thenReturn(minTopOffset);

        // when
        int result = testObj.findOffsetTop(items);

        // then
        assertThat(result).isEqualTo(minTopOffset);
    }

    @Test
    public void shouldReturnWidthOfAllItems() throws Exception {
        // given
        ConnectionItems items = mock(ConnectionItems.class);

        int minLeftOffset = 100;
        int maxRightOffset = 1000;
        when(surfacesOffsetsUtils.findMinOffsetLeft(items)).thenReturn(minLeftOffset);
        when(surfacesOffsetsUtils.findMaxOffsetRight(items)).thenReturn(maxRightOffset);

        // when
        int result = testObj.findWidth(items);

        // then
        assertThat(result).isEqualTo(900);
    }

    @Test
    public void shouldReturnHeightOfAllItems() throws Exception {
        // given
        ConnectionItems items = mock(ConnectionItems.class);

        int minTopOffset = 100;
        int maxBottomOffset = 1000;

        when(surfacesOffsetsUtils.findMinOffsetTop(items)).thenReturn(minTopOffset);
        when(surfacesOffsetsUtils.findMaxOffsetBottom(items)).thenReturn(maxBottomOffset);

        // when
        int result = testObj.findHeight(items);

        // then
        assertThat(result).isEqualTo(900);
    }
}
