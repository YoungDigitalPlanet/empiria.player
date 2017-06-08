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

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import eu.ydp.empiria.player.client.module.drawing.model.ColorBean;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class ColorBeanConverter {

    private final Function<ColorBean, ColorModel> colorBeanTransformation = new Function<ColorBean, ColorModel>() {

        @Override
        public ColorModel apply(ColorBean color) {
            return ColorModel.createFromRgbString(color.getRgb());
        }
    };

    public List<ColorModel> convert(List<ColorBean> colorBeans) {
        return newArrayList(Iterables.transform(colorBeans, colorBeanTransformation));
    }

}
