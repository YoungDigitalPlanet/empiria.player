package eu.ydp.empiria.player.client.animation.js;

import static java.lang.Math.floor;
import static java.lang.Math.min;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.animation.Animation;
import eu.ydp.empiria.player.client.animation.AnimationConfig;
import eu.ydp.empiria.player.client.animation.AnimationEndHandler;
import eu.ydp.empiria.player.client.animation.holder.AnimationHolder;
import eu.ydp.empiria.player.client.animation.preload.ImagePreloadHandler;
import eu.ydp.empiria.player.client.animation.preload.ImagePreloader;
import eu.ydp.empiria.player.client.util.geom.Size;

public class JsAnimation implements Animation {

	private static final double PROGRESS_MAX = 1.0;
	private final AnimationAnalyzer animationAnalyzer;
	private final ImagePreloader preloader;
	private final FrameworkAnimation frameworkAnimation;
	
	@Inject
	public JsAnimation(AnimationAnalyzer animationAnalyzer, ImagePreloader preloader, FrameworkAnimation frameworkAnimation) {
		this.animationAnalyzer = animationAnalyzer;
		this.preloader = preloader;
		this.frameworkAnimation = frameworkAnimation;
		
		frameworkAnimation.setListener(frameworkAnimationListener);
	}

	private final FrameworkAnimationListener frameworkAnimationListener = new FrameworkAnimationListener() {
		
		@Override
		public void onUpdate(double progress) {
			onAnimationUpdate(progress);
		}
	};
	
	private AnimationConfig config;
	private AnimationHolder holder;
	private AnimationEndHandler handler;
	private int framesCount;

//	private int animationCounter;
//	private int animationLeft;

	public void init(AnimationConfig config, AnimationHolder holder){
		this.config = config;
		this.holder = holder;
	}
	
	@Override
	public void start(AnimationEndHandler handler) {
		this.handler = handler;
		preloadAndPlay();
	}

	private void preloadAndPlay() {
		String src = config.getSource();
		preloader.preload(src, new ImagePreloadHandler() {
			
			@Override
			public void onLoad(Size imageSize) {
				computeAndPlay(imageSize);
			}
		});
	}
	
	private void computeAndPlay(Size imageSize) {
		findFramesCount(imageSize);
		if (framesCount > 0){
			play();
		} else {
			handler.onEnd();
		}
	}

	private void findFramesCount(Size imageSize) {
		Size frameSize = config.getFrameSize();
		framesCount = animationAnalyzer.findFramesCount(imageSize, frameSize);
	}

	private void play() {
		initHolder();
		runAnimation();
	}
	
	private void initHolder() {
		String src = config.getSource();
		holder.setAnimationImage(src);
		holder.setAnimationLeft(0);
	}

	private void runAnimation() {
		int duration = findAnimationDuration();
		frameworkAnimation.run(duration);
	}
	
	private int findAnimationDuration(){
		return framesCount * config.getIntervalMs();
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
		int currentFrame = min( (int) floor( framesCount * progress) , framesCount - 1);
		int animationLeft = -1 * config.getFrameSize().getWidth() * currentFrame;
		holder.setAnimationLeft(animationLeft);
	}

	@Override
	public void terminate() {
		frameworkAnimation.cancel();
	}
}
