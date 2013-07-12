package eu.ydp.empiria.player.client.animation.js;

import static java.lang.Math.floor;
import static java.lang.Math.min;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.animation.AnimationConfig;
import eu.ydp.empiria.player.client.animation.AnimationEndHandler;
import eu.ydp.empiria.player.client.animation.AnimationRuntimeConfig;
import eu.ydp.empiria.player.client.animation.AnimationWithRuntimeConfig;
import eu.ydp.empiria.player.client.animation.holder.AnimationHolder;
import eu.ydp.empiria.player.client.util.geom.Size;

public class JsAnimation implements AnimationWithRuntimeConfig {

	private final FrameworkAnimationListener frameworkAnimationListener = new FrameworkAnimationListener() {

		@Override
		public void onUpdate(double progress) {
			onAnimationUpdate(progress);
		}
	};

	private static final double PROGRESS_MAX = 1.0;
	private final FrameworkAnimation frameworkAnimation;

	private AnimationEndHandler handler;
	private AnimationRuntimeConfig animationRuntimeConfig;

	@Inject
	public JsAnimation(FrameworkAnimation frameworkAnimation) {
		this.frameworkAnimation = frameworkAnimation;
		frameworkAnimation.setListener(frameworkAnimationListener);
	}


	@Override
	public void start(AnimationEndHandler handler) {
		this.handler = handler;
		play();
	}

	private void play() {
		initHolder();
		runAnimation();
	}

	private void initHolder() {
		String src = getAnimateConfig().getSource();
		Size size = animationRuntimeConfig.getImageSize();
		
		getAnimationHolder().setAnimationImage(src, size);
		getAnimationHolder().setAnimationLeft(0);
	}


	private AnimationHolder getAnimationHolder() {
		return animationRuntimeConfig.getAnimationHolder();
	}


	private AnimationConfig getAnimateConfig() {
		return animationRuntimeConfig.getAnimationConfig();
	}

	private void runAnimation() {
		int duration = findAnimationDuration();
		frameworkAnimation.run(duration);
	}

	private int findAnimationDuration(){
		return animationRuntimeConfig.getFramesCount() * getAnimateConfig().getIntervalMs();
	}

	private void onAnimationUpdate(double progress) {
		updateAnimation(progress);
		checkAnimationEnd(progress);
	}

	private void checkAnimationEnd(double progress) {
		if (progress == PROGRESS_MAX){
			handler.onEnd();
		}
	}

	private void updateAnimation(double progress) {
		int framesCount = animationRuntimeConfig.getFramesCount();
		int currentFrame = min( (int) floor( framesCount * progress) , framesCount - 1);
		int animationLeft = -1 * getAnimateConfig().getFrameSize().getWidth() * currentFrame;
		getAnimationHolder().setAnimationLeft(animationLeft);
	}

	@Override
	public void terminate() {
		frameworkAnimation.cancel();
	}

	@Override
	public void setRuntimeConfiguration(AnimationRuntimeConfig animationRuntimeConfig) {
		this.animationRuntimeConfig = animationRuntimeConfig;
	}
}
