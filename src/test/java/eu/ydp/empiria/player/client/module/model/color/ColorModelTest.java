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

package eu.ydp.empiria.player.client.module.model.color;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ColorModelTest {

    private ColorModel colorModel;

    @Test
    public void intsToStringRgb() {
        // when
        colorModel = ColorModel.createFromRgba(20, 30, 40, 50);

        // then
        assertThat(colorModel.toStringRgb(), equalTo("141e28"));
    }

    @Test
    public void intsToStringRgba() {
        // when
        colorModel = ColorModel.createFromRgba(20, 30, 40, 50);

        // then
        assertThat(colorModel.toStringRgba(), equalTo("141e2832"));
    }

    @Test
    public void stringToStringRgb() {
        // when
        colorModel = ColorModel.createFromRgbString("141e28");

        // then
        assertThat(colorModel.toStringRgb(), equalTo("141e28"));
    }

    @Test
    public void stringToStringRgba() {
        // when
        colorModel = ColorModel.createFromRgbString("141e28");

        // then
        assertThat(colorModel.toStringRgba(), equalTo("141e28ff"));
    }

    @Test
    public void eraser() {
        // when
        colorModel = ColorModel.createEraser();

        // then
        assertThat(colorModel.toStringRgba(), equalTo("00000000"));
    }

}
