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

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.drawing.command.DrawCommand;
import eu.ydp.empiria.player.client.module.drawing.command.DrawCommandFactory;
import eu.ydp.empiria.player.client.module.drawing.command.DrawCommandType;
import eu.ydp.empiria.player.client.module.drawing.toolbox.model.ToolboxModelImpl;
import eu.ydp.empiria.player.client.module.drawing.toolbox.tool.Tool;
import eu.ydp.empiria.player.client.module.drawing.toolbox.tool.ToolFactory;
import eu.ydp.empiria.player.client.module.drawing.view.CanvasPresenter;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.List;

import static eu.ydp.empiria.player.client.module.drawing.toolbox.ToolType.ERASER;
import static eu.ydp.empiria.player.client.module.drawing.toolbox.ToolType.PENCIL;

public class ToolboxPresenter {
    @Inject
    @ModuleScoped
    private ToolboxView view;
    @Inject
    @ModuleScoped
    private ToolboxButtonCreator buttonCreator;
    @Inject
    private ToolboxModelImpl model;
    @Inject
    private ToolFactory toolFactory;
    @Inject
    @ModuleScoped
    private CanvasPresenter canvasPresenter;
    @Inject
    private DrawCommandFactory drawCommandFactory;
    @Inject
    private PaletteColorsProvider paletteColorsProvider;

    private boolean paletteVisible;

    public void init() {
        initView();
        initTool();
        initPalette();
    }

    private void initView() {
        view.setPresenterAndBind(this);
        buttonCreator.setPresenter(this);
    }

    private void initPalette() {
        List<ColorModel> colorModels = paletteColorsProvider.getColors();
        view.setPalette(colorModels);
        selectColor(colorModels.get(0));
    }

    private void initTool() {
        pencilClicked();
    }

    public void colorClicked(ColorModel colorModel) {
        paletteClicked();
        selectColor(colorModel);
    }

    private void selectColor(ColorModel colorModel) {
        view.setPaletteColor(colorModel);
        model.setColorModel(colorModel);
        update();
    }

    public void paletteClicked() {
        if (paletteVisible) {
            view.hidePalette();
        } else {
            view.showPalette();
        }
        paletteVisible = !paletteVisible;
    }

    public void pencilClicked() {
        view.selectPencil();
        model.setToolType(PENCIL);
        update();
    }

    public void eraserClicked() {
        view.selectEraser();
        model.setToolType(ERASER);
        update();
    }

    public void clearAllClicked() {
        DrawCommand command = drawCommandFactory.createCommand(DrawCommandType.CLEAR_ALL);
        command.execute();
    }

    private void update() {
        Tool tool = toolFactory.createTool(model);
        canvasPresenter.setTool(tool);
    }

    public ToolboxView getView() {
        return view;
    }
}
