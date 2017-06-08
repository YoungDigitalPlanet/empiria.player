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

package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.controller;

import com.google.common.base.Optional;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.*;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.StickieViewPositionFinder;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.ContainerDimensions;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.size.MaximizedStickieSizeStorage;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.size.StickieSize;
import eu.ydp.gwtutil.client.geom.Point;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StickieMinimizeMaximizeControllerJUnitTest {

    @InjectMocks
    private StickieMinimizeMaximizeController testObj;

    @Mock
    private MaximizedStickieSizeStorage maximizedStickieSizeStorage;
    @Mock
    private StickieViewPositionFinder positionFinder;
    @Mock
    private Point<Integer> previousMinimizedPosition;
    @Mock
    private IStickieProperties stickieProperties;

    private final int absoluteLeft = 1;
    private final int absoluteTop = 2;
    private final int width = 3;
    private final int height = 4;
    private int expectedColorIndex = 0;
    private ContainerDimensions stickieDimensions;

    @Before
    public void setUp() throws Exception {
        stickieDimensions = new ContainerDimensions.Builder().absoluteLeft(absoluteLeft).absoluteTop(absoluteTop).width(width).height(height).build();
    }

    @Test
    public void shouldUpdateMaximizeStickieSizeWhenMinimizingStickie() throws Exception {
        // given
        when(stickieProperties.getColorIndex()).thenReturn(expectedColorIndex);

        // when
        testObj.positionMinimizedStickie(stickieDimensions);

        // then
        ArgumentCaptor<Integer> colorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<StickieSize> stickieSizeCaptor = ArgumentCaptor.forClass(StickieSize.class);
        verify(maximizedStickieSizeStorage).updateIfBiggerThanExisting(colorCaptor.capture(), stickieSizeCaptor.capture());

        assertThat(colorCaptor.getValue(), equalTo(expectedColorIndex));

        StickieSize stickieSize = stickieSizeCaptor.getValue();
        assertThat(stickieSize.getWidth(), equalTo(width));
        assertThat(stickieSize.getHeight(), equalTo(height));
    }

    @Test
    public void shouldUseCurrentPositionIfNoPreviousMinimizedPositionIsStored() throws Exception {
        // given
        Point<Integer> expectedPosition = new Point<>(1, 1);
        when(stickieProperties.getPosition()).thenReturn(expectedPosition);

        // when
        Point<Integer> result = testObj.positionMinimizedStickie(stickieDimensions);

        // then
        assertEquals(result, expectedPosition);
    }

    @Test
    public void shouldUsePreviousMinimizedPositionIfStored() throws Exception {
        // given
        Point<Integer> expectedPosition = new Point<>(1, 1);
        when(stickieProperties.getPosition()).thenReturn(expectedPosition);

        // when
        Point<Integer> result = testObj.positionMinimizedStickie(stickieDimensions);

        // then
        assertEquals(result, expectedPosition);
        verify(stickieProperties).getPosition();
    }

    @Test
    public void shouldResetPreviousMinimizedPosition() throws Exception {
        // given
        Point<Integer> previousMinimizedPosition = new Point<>(1, 1);
        when(stickieProperties.getPosition()).thenReturn(previousMinimizedPosition);
        testObj.positionMinimizedStickie(stickieDimensions);

        // when
        testObj.resetCachedMinimizedPosition();
        testObj.positionMinimizedStickie(stickieDimensions);

        // then
        verify(stickieProperties, times(2)).getPosition();
    }

    @Test
    public void shouldSaveCurrentPositionAsPreviousMinimizedPositionWhenMaximizing() throws Exception {
        // given
        Point<Integer> expectedPosition = new Point<>(1, 1);
        Optional<StickieSize> optionalStickieSize = Optional.absent();
        when(maximizedStickieSizeStorage.getSizeOfMaximizedStickie(stickieProperties.getColorIndex())).thenReturn(optionalStickieSize);
        when(stickieProperties.getPosition()).thenReturn(expectedPosition);

        // when
        testObj.positionMaximizedStickie(stickieDimensions, null);
        Point<Integer> previousMinimizedPosition = testObj.positionMinimizedStickie(stickieDimensions);

        // then
        assertEquals(expectedPosition, previousMinimizedPosition);
    }

    @Test
    public void shouldNotOverridePreviousMinimizedPositionWhenMaximizing() throws Exception {
        // given
        Point<Integer> expectedPreviousMinimizedPosition = new Point<Integer>(1, 1);
        when(stickieProperties.getPosition()).thenReturn(expectedPreviousMinimizedPosition);

        Optional<StickieSize> optionalStickieSize = Optional.absent();
        when(maximizedStickieSizeStorage.getSizeOfMaximizedStickie(stickieProperties.getColorIndex())).thenReturn(optionalStickieSize);
        testObj.positionMinimizedStickie(stickieDimensions);

        // when
        testObj.positionMaximizedStickie(stickieDimensions, null);
        Point<Integer> result = testObj.positionMinimizedStickie(stickieDimensions);

        //then
        assertEquals(expectedPreviousMinimizedPosition, result);
    }

    @Test
    public void shouldUpdateMaximizeStickieSizeWhenMaximizingStickie() throws Exception {
        // given
        ContainerDimensions parentDimensions = new ContainerDimensions.Builder().build();

        Optional<StickieSize> optionalStickieSize = Optional.absent();
        when(maximizedStickieSizeStorage.getSizeOfMaximizedStickie(stickieProperties.getColorIndex())).thenReturn(optionalStickieSize);

        // when
        testObj.positionMaximizedStickie(stickieDimensions, parentDimensions);

        // then
        ArgumentCaptor<Integer> colorCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<StickieSize> stickieSizeCaptor = ArgumentCaptor.forClass(StickieSize.class);
        verify(maximizedStickieSizeStorage).updateIfBiggerThanExisting(colorCaptor.capture(), stickieSizeCaptor.capture());

        assertThat(colorCaptor.getValue(), equalTo(stickieProperties.getColorIndex()));

        StickieSize stickieSize = stickieSizeCaptor.getValue();
        assertThat(stickieSize.getWidth(), equalTo(width));
        assertThat(stickieSize.getHeight(), equalTo(height));
    }

    @Test
    public void shouldUseGivenStickieDimensions_whenMaximizingAndNoOtherAreStored() throws Exception {
        // given
        ContainerDimensions parentDimensions = new ContainerDimensions.Builder().build();

        Optional<StickieSize> optionalStickieSize = Optional.absent();
        when(maximizedStickieSizeStorage.getSizeOfMaximizedStickie(stickieProperties.getColorIndex())).thenReturn(optionalStickieSize);

        Point<Integer> expectedPosition = new Point<Integer>(1, 1);
        when(positionFinder.refinePosition(stickieProperties.getPosition(), stickieDimensions, parentDimensions)).thenReturn(expectedPosition);

        // when
        Point<Integer> positionMaximizedStickie = testObj.positionMaximizedStickie(stickieDimensions, parentDimensions);

        // then
        verify(positionFinder).refinePosition(stickieProperties.getPosition(), stickieDimensions, parentDimensions);
        assertEquals(expectedPosition, positionMaximizedStickie);
    }

    @Test
    public void shouldUseStoredStickieDimensionsWhenMaximizing() throws Exception {
        // given
        ContainerDimensions parentDimensions = new ContainerDimensions.Builder().build();

        StickieSize maximizedStickieSize = new StickieSize(666, 777);
        Optional<StickieSize> optionalStickieSize = Optional.fromNullable(maximizedStickieSize);
        when(maximizedStickieSizeStorage.getSizeOfMaximizedStickie(stickieProperties.getColorIndex())).thenReturn(optionalStickieSize);

        Point<Integer> newPosition = new Point<Integer>(1, 1);
        when(positionFinder.refinePosition(eq(stickieProperties.getPosition()), any(ContainerDimensions.class), eq(parentDimensions))).thenReturn(
                newPosition);

        // when
        Point<Integer> positionMaximizedStickie = testObj.positionMaximizedStickie(stickieDimensions, parentDimensions);

        // then
        assertEquals(newPosition, positionMaximizedStickie);

        ArgumentCaptor<ContainerDimensions> stickieContainerDimensionsCaptor = ArgumentCaptor.forClass(ContainerDimensions.class);
        verify(positionFinder).refinePosition(eq(stickieProperties.getPosition()), stickieContainerDimensionsCaptor.capture(), eq(parentDimensions));

        ContainerDimensions capturedDimensions = stickieContainerDimensionsCaptor.getValue();
        assertEquals(stickieDimensions.getAbsoluteLeft(), capturedDimensions.getAbsoluteLeft());
        assertEquals(stickieDimensions.getAbsoluteTop(), capturedDimensions.getAbsoluteTop());
        assertEquals(maximizedStickieSize.getWidth(), capturedDimensions.getWidth());
        assertEquals(maximizedStickieSize.getHeight(), capturedDimensions.getHeight());
    }
}
