package eu.ydp.empiria.player.client.module.img;

import com.google.gwt.user.client.ui.Composite;

public abstract class ExplorableImgWindowBase extends Composite implements ExplorableImgWindow {

	private double originalImageWidth, originalImageHeight;
	private double originalAspectRatio;
	private int windowWidth, windowHeight;
	private double scale = 2.0d;
	private double scaleMin = 1.0d;
	protected final double ZOOM_MAX = 8;
	protected final double SCALE_STEP = 1.2d;
	
	protected void findScaleMinAndOriginalAspectRatio(){
		if (windowHeight/originalImageHeight  <  windowWidth/originalImageWidth)
			scaleMin = 1.0d * (double)originalImageWidth / (double)originalImageHeight;
		originalAspectRatio = (double)originalImageWidth / (double)originalImageHeight;
	}
	
	protected double getOriginalAspectRatio(){
		return originalAspectRatio;
	}
	
	protected double getScaleMin(){
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


	protected void setWindowWidth(int windowWidth) {
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


	protected double getZoom(){
		return (double)windowWidth/ (double)originalImageWidth * (scale) ;
	}

}
