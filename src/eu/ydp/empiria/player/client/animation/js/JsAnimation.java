package eu.ydp.empiria.player.client.animation.js;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.animation.Animation;
import eu.ydp.empiria.player.client.animation.AnimationConfig;
import eu.ydp.empiria.player.client.animation.AnimationEndHandler;
import eu.ydp.empiria.player.client.animation.holder.AnimationHolder;
import eu.ydp.empiria.player.client.animation.preload.ImagePreloadHandler;
import eu.ydp.empiria.player.client.animation.preload.ImagePreloader;
import eu.ydp.empiria.player.client.util.geom.Size;
import eu.ydp.gwtutil.client.timer.Timer;

public class JsAnimation implements Animation {

	private final AnimationAnalyzer animationAnalyzer;
	private final ImagePreloader preloader;
	private final Timer timer;
	
	@Inject
	public JsAnimation(AnimationAnalyzer animationAnalyzer, ImagePreloader preloader, Timer timer) {
		this.animationAnalyzer = animationAnalyzer;
		this.preloader = preloader;
		this.timer = timer;
	}

	private final Runnable timerAction = new Runnable() {
		
		@Override
		public void run() {
			onTimer();
		}

	};
	
	private AnimationConfig config;
	private AnimationHolder holder;
	private AnimationEndHandler handler;

	private int animationCounter;
	private int animationLeft;

	public void init(AnimationConfig config, AnimationHolder holder){
		this.config = config;
		this.holder = holder;
		
		timer.init(timerAction);
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
		int framesCount = animationAnalyzer.findFramesCount(imageSize, config.getFrameSize());
		if (framesCount > 0){
			play(framesCount);
		} else {
			handler.onEnd();
		}
	}

	private void play(int framesCount) {
		String src = config.getSource();
		holder.setAnimationImage(src);
		holder.setAnimationLeft(0);
		
		animationCounter = framesCount - 1;
		animationLeft = 0;
		
		timer.scheduleRepeating(config.getIntervalMs());
	}

	private void onTimer() {
		updateAnimation();
		checkAnimationEnd();
	}

	private void checkAnimationEnd() {
		if (animationCounter == 0){
			timer.cancel();
			handler.onEnd();
		}
	}

	private void updateAnimation() {
		animationLeft -= config.getFrameSize().getWidth();
		holder.setAnimationLeft(animationLeft);
		animationCounter--;
	}

	@Override
	public void terminate() {
		timer.cancel();
	}
}
