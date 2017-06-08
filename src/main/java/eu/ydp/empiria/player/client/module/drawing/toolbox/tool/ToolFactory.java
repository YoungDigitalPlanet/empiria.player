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

package eu.ydp.empiria.player.client.module.drawing.toolbox.tool;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolType;
import eu.ydp.empiria.player.client.module.drawing.toolbox.model.ToolboxModel;
import eu.ydp.empiria.player.client.module.drawing.view.DrawCanvas;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ToolFactory {

    @Inject
    @ModuleScoped
    private DrawCanvas canvas;

    public Tool createTool(ToolboxModel model) {
        ToolType toolType = model.getToolType();
        ColorModel colorModel = model.getColorModel();

        switch (toolType) {
            case ERASER:
                return new EraserTool(canvas);
            case PENCIL:
                return new PencilTool(colorModel, canvas);
            default:
                throw new UnknownToolTypeException(toolType + " is unknown tool type.");
        }
    }
}
