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

import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolType;
import eu.ydp.empiria.player.client.module.drawing.toolbox.model.ToolboxModelImpl;
import eu.ydp.empiria.player.client.module.drawing.view.DrawCanvas;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ToolFactoryTest {

    @InjectMocks
    private ToolFactory factory;

    @Mock
    private DrawCanvas drawCanvas;

    @Test
    public void shouldCreatePencilTool() throws Exception {
        // given
        ToolboxModelImpl toolModel = new ToolboxModelImpl();
        toolModel.setToolType(ToolType.PENCIL);

        // when
        Tool tool = factory.createTool(toolModel);

        // then
        assertTrue(tool instanceof PencilTool);
    }

    @Test
    public void shouldCreateEraserTool() throws Exception {
        // given
        ToolboxModelImpl toolModel = new ToolboxModelImpl();
        toolModel.setToolType(ToolType.ERASER);

        // when
        Tool tool = factory.createTool(toolModel);

        // then
        assertTrue(tool instanceof EraserTool);
    }
}
