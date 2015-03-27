package eu.ydp.empiria.player.client.controller.multiview.animation;

import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.multiview.swipe.SwipeType;
import eu.ydp.empiria.player.client.inject.Instance;

public class SwipeAnimationProvider implements Provider<Animation> {
	@Inject
	private Provider<SwipeType> swipeType;
	@Inject
	private Instance<AnimationBase> animationBase;
	@Inject
	private Instance<NoAnimation> noAnimation;

	@Override
	public Animation get() {
		if (swipeType.get() == SwipeType.DEFAULT) {
			return animationBase.get();
		} else {
			return noAnimation.get();
		}
	}
}
