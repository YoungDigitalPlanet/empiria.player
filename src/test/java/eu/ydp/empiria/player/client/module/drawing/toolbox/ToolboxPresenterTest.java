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
import eu.ydp.empiria.player.client.module.drawing.command.DrawCommand;
import eu.ydp.empiria.player.client.module.drawing.command.DrawCommandFactory;
import eu.ydp.empiria.player.client.module.drawing.toolbox.model.ToolboxModelImpl;
import eu.ydp.empiria.player.client.module.drawing.toolbox.tool.Tool;
import eu.ydp.empiria.player.client.module.drawing.toolbox.tool.ToolFactory;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasPresenter;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static eu.ydp.empiria.player.client.module.drawing.command.DrawCommandType.CLEAR_ALL;
import static eu.ydp.empiria.player.client.module.drawing.toolbox.ToolType.PENCIL;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ToolboxPresenterTest {

    @InjectMocks
    private ToolboxPresenter testObj;
    @Mock
    private ToolboxView view;
    @Mock
    private ToolboxButtonCreator buttonCreator;
    @Mock
    private ToolboxModelImpl model;
    @Mock
    private ToolFactory toolFactory;
    @Mock
    private CanvasPresenter canvasPresenter;
    @Mock
    private DrawCommandFactory drawCommandFactory;
    @Mock
    private PaletteColorsProvider paletteColorsProvider;
    @Mock
    private Tool tool;

    @Test
    public void shouldInitializePaletteOnInit() {
        // given

        ColorModel primaryColor = ColorModel.createFromRgbString("000000");
        ColorModel secondColor = ColorModel.createFromRgbString("0000FF");
        ColorModel thirdColor = ColorModel.createFromRgbString("00FFFF");
        List<ColorModel> colorModels = Lists.newArrayList(primaryColor, secondColor, thirdColor);
        when(paletteColorsProvider.getColors()).thenReturn(colorModels);

        // when
        testObj.init();

        // then
        verify(view).setPalette(colorModels);
        verify(view).setPaletteColor(primaryColor);
        verify(model).setColorModel(primaryColor);
    }

    @Test
    public void shouldInitializeToolOnInit() {
        // given

        ColorModel primaryColor = ColorModel.createFromRgbString("000000");
        ColorModel secondColor = ColorModel.createFromRgbString("0000FF");
        ColorModel thirdColor = ColorModel.createFromRgbString("00FFFF");
        List<ColorModel> colorModels = Lists.newArrayList(primaryColor, secondColor, thirdColor);
        when(paletteColorsProvider.getColors()).thenReturn(colorModels);

        // when
        testObj.init();

        // then
        verify(view).selectPencil();
        verify(model).setToolType(PENCIL);
    }

    @Test
    public void shouldHidePaletteAndSetColorOnColorClicked() {
        // given
        ColorModel colorModel = ColorModel.createFromRgbString("FFAADD");
        when(toolFactory.createTool(model)).thenReturn(tool);
        testObj.paletteClicked();

        // when
        testObj.colorClicked(colorModel);

        // then
        verify(view).hidePalette();
        verify(view).setPaletteColor(colorModel);
        verify(model).setColorModel(colorModel);

        verify(canvasPresenter).setTool(tool);
    }

    @Test
    public void shouldTogglePaletteVisibilityState() {
        // when
        testObj.paletteClicked();
        testObj.paletteClicked();
        testObj.paletteClicked();

        // then
        InOrder order = inOrder(view);
        order.verify(view).showPalette();
        order.verify(view).hidePalette();
        order.verify(view).showPalette();
    }

    @Test
    public void shouldHudePaletteOnColorSelection() {
        // when
        ColorModel colorModel = ColorModel.createFromRgbString("FFAADD");
        testObj.paletteClicked();
        testObj.colorClicked(colorModel);

        // then
        InOrder order = inOrder(view);
        order.verify(view).showPalette();
        order.verify(view).hidePalette();
    }

    @Test
    public void shouldSelectPencilOnClick() {
        // given
        when(toolFactory.createTool(model)).thenReturn(tool);

        // when
        testObj.pencilClicked();

        // then
        verify(view).selectPencil();
        verify(model).setToolType(ToolType.PENCIL);

        verify(canvasPresenter).setTool(tool);
    }

    @Test
    public void shouldSelectEraserOnClicked() {
        // given
        when(toolFactory.createTool(model)).thenReturn(tool);

        // when
        testObj.eraserClicked();

        // then
        verify(view).selectEraser();
        verify(model).setToolType(ToolType.ERASER);

        verify(canvasPresenter).setTool(tool);
    }

    @Test
    public void shouldCreateAndExecuteClearAllCommand() {
        // given
        DrawCommand command = mock(DrawCommand.class);
        when(drawCommandFactory.createCommand(CLEAR_ALL)).thenReturn(command);

        // when
        testObj.clearAllClicked();

        // then
        verify(command).execute();
    }

    @Test
    public void getView() {
        // when
        ToolboxView result = testObj.getView();

        // then
        assertThat(view).isSameAs(result);
    }
}
