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

package eu.ydp.empiria.player.client.module.img.explorable;

import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractExplorableImgWindowBase extends Composite implements ExplorableImgWindow {

    private double originalImageWidth, originalImageHeight;
    private double originalAspectRatio;
    private int windowWidth, windowHeight;
    private double scale = ExplorableImageConst.ZOOM_SCALE;
    private double scaleMin = ExplorableImageConst.ZOOM_SCALE_MIN;
    private double zoomMax = ExplorableImageConst.ZOOM_SCALE_MAX;
    private double scaleStep = ExplorableImageConst.ZOOM_SCALE_STEP;

    protected void findScaleMinAndOriginalAspectRatio() {
        originalAspectRatio = originalImageWidth / originalImageHeight;
        if (windowHeight / originalImageHeight < windowWidth / originalImageWidth) {
            scaleMin = (originalAspectRatio * windowHeight) / windowWidth;
        }
    }

    protected double getOriginalAspectRatio() {
        return originalAspectRatio;
    }

    protected double getScaleMin() {
        return scaleMin;
    }

    protected double getOriginalImageWidth() {
        return originalImageWidth;
    }

    protected void setOriginalImageWidth(double originalImageWidth) {
        this.originalImageWidth = originalImageWidth;
    }

    protected double getOriginalImageHeight() {
        return originalImageHeight;
    }

    protected void setOriginalImageHeight(double originalImageHeight) {
        this.originalImageHeight = originalImageHeight;
    }

    protected int getWindowWidth() {
        return windowWidth;
    }

    protected void
    setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    protected int getWindowHeight() {
        return windowHeight;
    }

    protected void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    protected double getScale() {
        return scale;
    }

    protected void setScale(double scale) {
        this.scale = scale;
    }

    protected double getZoom() {
        return windowWidth / originalImageWidth * (scale);
    }

    protected double getZoom(double newScale) {
        return windowWidth / originalImageWidth * (newScale);
    }

    public double getScaleStep() {
        return scaleStep;
    }

    public void setScaleStep(double scaleStep) {
        this.scaleStep = scaleStep;
    }

    public double getZoomMax() {
        return zoomMax;
    }

    public void setZoomMax(double zoomMax) {
        this.zoomMax = zoomMax;
    }

}
