package eu.ydp.empiria.player.client.module.img.explorable.view;

import eu.ydp.empiria.player.client.module.img.explorable.ExplorableImageConst;

public class ImageProperties {

    private int windowWidth = ExplorableImageConst.WINDOW_WIDTH;
    private int windowHeight = ExplorableImageConst.WINDOW_HEIGHT;
    private double scale = ExplorableImageConst.ZOOM_SCALE;
    private double scaleStep = ExplorableImageConst.ZOOM_SCALE_STEP;
    private double zoomMax = ExplorableImageConst.ZOOM_SCALE_MAX;

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
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
