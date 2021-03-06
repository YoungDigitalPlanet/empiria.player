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

package eu.ydp.empiria.player.client.module.drawing;

import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.drawing.command.DrawCommand;
import eu.ydp.empiria.player.client.module.drawing.command.DrawCommandFactory;
import eu.ydp.empiria.player.client.module.drawing.command.DrawCommandType;
import eu.ydp.empiria.player.client.module.drawing.model.DrawingBean;
import eu.ydp.empiria.player.client.module.drawing.model.ImageBean;
import eu.ydp.empiria.player.client.module.drawing.toolbox.ToolboxPresenter;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasPresenter;
import eu.ydp.gwtutil.client.util.geom.Size;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DrawingModuleTest {

    @InjectMocks
    private DrawingModule drawingModule;

    @Mock
    private DrawingBean bean;
    @Mock
    private DrawingView drawingView;
    @Mock
    private ToolboxPresenter toolboxPresenter;
    @Mock
    private CanvasPresenter canvasPresenter;
    @Mock
    private DrawCommandFactory factory;

    @Test
    public void shouldResetViewOnCanvas() throws Exception {
        // given
        DrawCommand clearAllCommand = Mockito.mock(DrawCommand.class);
        when(factory.createCommand(DrawCommandType.CLEAR_ALL)).thenReturn(clearAllCommand);

        // when
        drawingModule.reset();

        // then
        verify(clearAllCommand).execute();
    }

    @Test
    public void shouldInitializeModule() throws Exception {
        // given
        ImageBean image = getSampleImageBean();
        when(bean.getImage()).thenReturn(image);

        // when
        drawingModule.initModule(mock(Element.class));

        // then
        ArgumentCaptor<Size> sizeCaptor = ArgumentCaptor.forClass(Size.class);
        verify(canvasPresenter).setImage(eq(image.getSrc()), sizeCaptor.capture());

        Size imageSize = sizeCaptor.getValue();
        assertEquals(image.getWidth(), imageSize.getWidth());
        assertEquals(image.getHeight(), imageSize.getHeight());

        verify(toolboxPresenter).init();
    }

    private ImageBean getSampleImageBean() {
        ImageBean image = new ImageBean();
        image.setHeight(10);
        image.setWidth(123);
        image.setSrc("src");
        return image;
    }
}
