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

package eu.ydp.empiria.player.client.module.drawing.toolbox;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.module.drawing.model.ColorBean;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import org.junit.Test;

import java.util.List;

import static eu.ydp.empiria.player.client.module.model.color.ColorModel.createFromRgbString;
import static org.fest.assertions.api.Assertions.assertThat;

public class ColorBeanConverterTest {

    private final ColorBeanConverter converter = new ColorBeanConverter();

    @Test
    public void convert() {
        // given
        List<ColorBean> colorBeans = Lists.newArrayList(createColorBean("00FF00"), createColorBean("00FFFF"), createColorBean("000DAF"));

        // when
        List<ColorModel> models = converter.convert(colorBeans);

        // then
        assertThat(models).containsExactly(createFromRgbString("00FF00"), createFromRgbString("00FFFF"), createFromRgbString("000DAF"));
    }

    private static ColorBean createColorBean(String rgb) {
        ColorBean color2 = new ColorBean();
        color2.setRgb(rgb);
        return color2;
    }

}
