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

package eu.ydp.empiria.player.client.module.drawing.view;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.drawing.toolbox.tool.Tool;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.util.geom.Size;

public class CanvasPresenter {

    private final CanvasView canvasView;
    private Tool currentTool;
    private Point previousPoint;
    private boolean mouseOutWhileActive = false;

    @Inject
    public CanvasPresenter(@ModuleScoped CanvasView canvasView) {
        this.canvasView = canvasView;
        this.canvasView.initializeInteractionHandlers(this);
    }

    public void mouseDown(Point point) {
        currentTool.start(point);
        this.previousPoint = point;
    }

    public void mouseMove(Point point) {
        if (cameBackToScreen()) {
            overridePreviousPosition(point);
        }

        if (isActive()) {
            moveTool(point);
        }
    }

    private void overridePreviousPosition(Point point) {
        previousPoint = point;
        mouseOutWhileActive = false;
    }

    private boolean cameBackToScreen() {
        return mouseOutWhileActive && isActive();
    }

    private void moveTool(Point point) {
        currentTool.move(previousPoint, point);
        previousPoint = point;
    }

    private boolean isActive() {
        return previousPoint != null;
    }

    public void mouseUp() {
        previousPoint = null;
    }

    public void mouseOut() {
        if (isActive()) {
            this.mouseOutWhileActive = true;
        }
    }

    public void setTool(Tool tool) {
        this.currentTool = tool;
        tool.setUp();
    }

    public void setImage(String url, Size size) {
        canvasView.setSize(size);
        canvasView.setBackground(url);
    }

    public CanvasView getView() {
        return canvasView;
    }
}
