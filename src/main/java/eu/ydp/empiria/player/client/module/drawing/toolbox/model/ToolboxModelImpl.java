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

package eu.ydp.empiria.player.client.module.drawing.toolbox.model;

import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolType;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;

public class ToolboxModelImpl implements ToolboxModel {
    private ColorModel colorModel;
    private ToolType toolType;

    @Override
    public int getLineThickness() {
        return 0;
    }

    @Override
    public ColorModel getColorModel() {
        return colorModel;
    }

    public void setColorModel(ColorModel colorModel) {
        this.colorModel = colorModel;
    }

    @Override
    public ToolType getToolType() {
        return toolType;
    }

    public void setToolType(ToolType toolType) {
        this.toolType = toolType;
    }
}
