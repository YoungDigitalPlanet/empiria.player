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

package eu.ydp.empiria.player.client.module.colorfill.presenter;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class ResponseUserAnswersConverterTest {

    private final ResponseUserAnswersConverter responseUserAnswersConverter = new ResponseUserAnswersConverter();

    @Test
    public void shouldParseUserAnswersToAreaColorMap() throws Exception {
        List<String> currentAnswers = Lists.newArrayList("2,3 ff00ff", "10,30 00ff00");

        Map<Area, ColorModel> areaColorMap = responseUserAnswersConverter.convertResponseAnswersToAreaColorMap(currentAnswers);

        assertEquals(2, areaColorMap.size());

        ColorModel colorModel = areaColorMap.get(new Area(2, 3));
        assertEquals(ColorModel.createFromRgbString("ff00ff"), colorModel);

        ColorModel secondColorModel = areaColorMap.get(new Area(10, 30));
        assertEquals(ColorModel.createFromRgbString("00ff00"), secondColorModel);
    }

    @Test
    public void shouldBuildResponseAnswerFromColorAndArea() throws Exception {
        Area area = new Area(123, 654);
        ColorModel colorModel = ColorModel.createFromRgbString("00ff00");

        String responseAnswer = responseUserAnswersConverter.buildResponseAnswerFromAreaAndColor(area, colorModel);

        assertThat(responseAnswer).isEqualTo("123,654 00ff00");
    }

    @Test
    public void convertResponseAnswersToAreaList() {
        List<String> currentAnswers = Lists.newArrayList("2,3 ff00ff", "10,30 00ff00");
        List<Area> areaList = responseUserAnswersConverter.convertResponseAnswersToAreaList(currentAnswers);
        assertThat(areaList).hasSize(2).contains(new Area(2, 3), new Area(10, 30));
    }
}
