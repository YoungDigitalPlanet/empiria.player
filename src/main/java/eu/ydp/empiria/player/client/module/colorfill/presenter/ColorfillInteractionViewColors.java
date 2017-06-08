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

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.colorfill.structure.Area;
import eu.ydp.empiria.player.client.module.colorfill.view.ColorfillInteractionView;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.List;
import java.util.Map;

public class ColorfillInteractionViewColors {

    @Inject
    @ModuleScoped
    private ColorfillInteractionView interactionView;

    public Map<Area, ColorModel> getColors(List<Area> areas) {
        Map<Area, ColorModel> colors = Maps.newHashMap();
        for (Area area : areas) {
            ColorModel color = interactionView.getColor(area);
            colors.put(area, color);
        }
        return colors;
    }
}
