package eu.ydp.empiria.player.client.animation;

import eu.ydp.empiria.player.client.animation.holder.AnimationHolder;
import eu.ydp.empiria.player.client.util.geom.Size;

public class AnimationRuntimeConfig {
	private Size imageSize;
	private int framesCount;
	private AnimationConfig animationConfig;
	private AnimationHolder animationHolder;

	public AnimationRuntimeConfig(Size imageSize, int framesCount, AnimationConfig animationConfig, AnimationHolder animationHolder) {
		this.imageSize = imageSize;
		this.framesCount = framesCount;
		this.animationConfig = animationConfig;
		this.animationHolder = animationHolder;
	}

	public Size getImageSize() {
		return imageSize;
	}

	public void setImageSize(Size imageSize) {
		this.imageSize = imageSize;
	}

	public int getFramesCount() {
		return framesCount;
	}

	public void setFramesCount(int framesCount) {
		this.framesCount = framesCount;
	}

	public AnimationHolder getAnimationHolder() {
		return animationHolder;
	}

	public void setAnimationHolder(AnimationHolder animationHolder) {
		this.animationHolder = animationHolder;
	}

	public AnimationConfig getAnimationConfig() {
		return animationConfig;
	}

	public void setAnimationConfig(AnimationConfig animationConfig) {
		this.animationConfig = animationConfig;
	}

}
