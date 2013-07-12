package eu.ydp.empiria.player.client.animation.css;

import eu.ydp.empiria.player.client.animation.AnimationConfig;
import eu.ydp.empiria.player.client.util.geom.Size;

public class CssAnimationConfig {

	private final AnimationConfig animationConfig;
	private final String animationStyleName;
	private final Size imgSize;

	public CssAnimationConfig(AnimationConfig animationConfig, Size imgSize) {
		this.animationConfig = animationConfig;
		this.imgSize = imgSize;
		this.animationStyleName = getRawStyleName(animationConfig.getSource());
	}

	private String getRawStyleName(String imgSource) {
		return imgSource.toUpperCase().replaceAll("[\\/.: ]", "_");
	}
	
	public AnimationConfig getAnimationConfig() {
		return animationConfig;
	}

	public String getAnimationStyleName() {
		return animationStyleName;
	}

	public Size getImgSize() {
		return imgSize;
	}

	public int getFramesCount() {
		int framesCount = imgSize.getWidth() / animationConfig.getFrameSize().getWidth();
		return framesCount;
	}
	
	public String getSource() {
		return animationConfig.getSource();
	}
	
	public String getAnimationTime() {
		return String.valueOf(animationConfig.getIntervalMs() * getFramesCount());
	}
}
