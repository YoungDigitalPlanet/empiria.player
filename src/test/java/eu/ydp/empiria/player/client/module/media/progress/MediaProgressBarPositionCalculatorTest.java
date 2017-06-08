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

package eu.ydp.empiria.player.client.module.media.progress;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.empiria.player.client.style.ComputedStyle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class MediaProgressBarPositionCalculatorTest {
    @Mock
    private MediaProgressBar mediaProgressBar;
    @Mock
    private ComputedStyle computedStyle;
    @Mock
    private MediaWrapper<?> mediaWrapper;
    @InjectMocks
    private MediaProgressBarPositionCalculator instance;

    @Before
    public void before() {
        doReturn("rtl").when(computedStyle).getDirectionFromBody();
    }

    @Test
    public void isRTL() {
        assertThat(instance.isRTL()).isTrue();
        doReturn(" ").when(computedStyle).getDirectionFromBody();
        assertThat(instance.isRTL()).isFalse();
    }

    @Test
    public void calculateCurrentPositionRTL() throws Exception {
        // given
        int scrollWidth = 200;
        doReturn(scrollWidth).when(mediaProgressBar).getScrollWidth();
        doReturn(mediaWrapper).when(mediaProgressBar).getMediaWrapper();
        double duration = 400d;
        doReturn(duration).when(mediaWrapper).getDuration();
        double steep = duration / scrollWidth;

        // test
        for (int position : Lists.newArrayList(14, 28, 96, 200)) {
            double calculated = instance.calculateCurrentPosistion(position);
            assertThat(calculated).isEqualTo(steep * (scrollWidth - position));
        }

        double calculated = instance.calculateCurrentPosistion(-2);
        assertThat(calculated).isEqualTo(duration);

        calculated = instance.calculateCurrentPosistion(900);
        assertThat(calculated).isEqualTo(0);

    }

    @Test
    public void calculateCurrentPositionLTR() throws Exception {
        // given
        int scrollWidth = 200;
        doReturn(" ").when(computedStyle).getDirectionFromBody();
        doReturn(scrollWidth).when(mediaProgressBar).getScrollWidth();
        doReturn(mediaWrapper).when(mediaProgressBar).getMediaWrapper();
        double duration = 400d;
        doReturn(duration).when(mediaWrapper).getDuration();
        double steep = duration / scrollWidth;

        // test
        for (int position : Lists.newArrayList(14, 28, 96, 200)) {
            double calculated = instance.calculateCurrentPosistion(position);
            assertThat(calculated).isEqualTo(steep * position);
        }

        double calculated = instance.calculateCurrentPosistion(-2);
        assertThat(calculated).isEqualTo(0);

        calculated = instance.calculateCurrentPosistion(900);
        assertThat(calculated).isEqualTo(duration);

    }

    @Test
    public void calculateCurrentPosistionForScrollRTL() throws Exception {
        // given
        int scrollWidth = 200;
        doReturn(scrollWidth).when(mediaProgressBar).getScrollWidth();
        doReturn(mediaWrapper).when(mediaProgressBar).getMediaWrapper();
        double duration = 400d;
        doReturn(duration).when(mediaWrapper).getDuration();

        // test
        for (int position : Lists.newArrayList(14, 28, 96, 200)) {
            int calculated = instance.calculateCurrentPosistionForScroll(position);
            assertThat(calculated).isEqualTo(scrollWidth - position);
        }

        int calculated = instance.calculateCurrentPosistionForScroll(-2);
        assertThat(calculated).isEqualTo(scrollWidth);

        calculated = instance.calculateCurrentPosistionForScroll(900);
        assertThat(calculated).isEqualTo(0);

    }

    @Test
    public void calculateCurrentPosistionForScrollLTR() throws Exception {
        // given
        doReturn(" ").when(computedStyle).getDirectionFromBody();
        int scrollWidth = 200;
        doReturn(scrollWidth).when(mediaProgressBar).getScrollWidth();
        doReturn(mediaWrapper).when(mediaProgressBar).getMediaWrapper();
        double duration = 400d;
        doReturn(duration).when(mediaWrapper).getDuration();

        // test
        for (int position : Lists.newArrayList(14, 28, 96, 200)) {
            int calculated = instance.calculateCurrentPosistionForScroll(position);
            assertThat(calculated).isEqualTo(position);
        }

        int calculated = instance.calculateCurrentPosistionForScroll(-2);
        assertThat(calculated).isEqualTo(0);

        calculated = instance.calculateCurrentPosistionForScroll(900);
        assertThat(calculated).isEqualTo(scrollWidth);
    }

    @Test
    public void getLeftPositionForAfterProgressElementRTL() throws Exception {
        String positionPropertyForAfterProgressElement = instance.getPositionPropertyForAfterProgressElement();
        assertThat(positionPropertyForAfterProgressElement).isEqualTo("left");
    }

    @Test
    public void getLeftPositionForAfterProgressElementLTR() throws Exception {
        doReturn(" ").when(computedStyle).getDirectionFromBody();
        String positionPropertyForAfterProgressElement = instance.getPositionPropertyForAfterProgressElement();
        assertThat(positionPropertyForAfterProgressElement).isEqualTo("right");
    }

    @Test
    public void getLeftPositionForBeforeProgressElementLTR() throws Exception {
        doReturn(" ").when(computedStyle).getDirectionFromBody();
        String positionPropertyForBeforeProgressElement = instance.getPositionPropertyForBeforeProgressElement();
        assertThat(positionPropertyForBeforeProgressElement).isEqualTo("left");

    }

    @Test
    public void getLeftPositionForBeforeProgressElementRTL() throws Exception {
        String positionPropertyForBeforeProgressElement = instance.getPositionPropertyForBeforeProgressElement();
        assertThat(positionPropertyForBeforeProgressElement).isEqualTo("right");

    }

    @Test
    public void getWidthForAfterProgressElementLTR() throws Exception {
        doReturn(" ").when(computedStyle).getDirectionFromBody();
        int buttonWidth = 20;
        doReturn(buttonWidth).when(mediaProgressBar).getButtonWidth();
        int scrollWidth = 200;
        doReturn(scrollWidth).when(mediaProgressBar).getScrollWidth();

        int leftPosition = instance.getWidthForAfterProgressElement(40);
        assertThat(leftPosition).isEqualTo(scrollWidth - 40 + buttonWidth / 2);

    }

    @Test
    public void getWidthForAfterProgressElementRTL() throws Exception {
        int buttonWidth = 20;
        doReturn(buttonWidth).when(mediaProgressBar).getButtonWidth();
        int scrollWidth = 200;
        doReturn(scrollWidth).when(mediaProgressBar).getScrollWidth();

        int leftPosition = instance.getWidthForAfterProgressElement(40);
        assertThat(leftPosition).isEqualTo(scrollWidth - 40 + buttonWidth / 2);
    }

}
