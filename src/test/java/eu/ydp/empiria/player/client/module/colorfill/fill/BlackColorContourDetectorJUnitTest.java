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

package eu.ydp.empiria.player.client.module.colorfill.fill;

import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
@SuppressWarnings("PMD")
public class BlackColorContourDetectorJUnitTest {

    BlackColorContourDetector instance = new BlackColorContourDetector();

    @Test
    @Parameters({"255,255,255,255,false", "123,123,45,0,false", "0,0,0,0,false", "0,0,0,255,true"})
    public void isContourColor(final int red, final int green, final int blue, final int alpha, final boolean assertResult) throws Exception {
        ColorModel rgba = ColorModel.createFromRgba(red, green, blue, alpha);
        assertThat(instance.isContourColor(rgba)).isEqualTo(assertResult);
    }

}
