package eu.ydp.empiria.player.client.controller.multiview.animation;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.user.client.ui.FlowPanel;


public class AnimationBase implements Animation {
	protected final Set<AnimationEndCallback> callbacks = new HashSet<AnimationEndCallback>();
	private int xPosition;

	@Override
	public void addAnimationEndCallback(AnimationEndCallback endCallback) {
		callbacks.add(endCallback);

	}

	@Override
	public void goTo(FlowPanel toAnimate, int xPosition, double duration) {
		this.xPosition = xPosition;
		PageSwitchAnimation pageAnimation = new PageSwitchAnimation(toAnimate, xPosition) {
			@Override
			protected void onComplete() {
				super.onComplete();
				for (AnimationEndCallback callback : callbacks) {
					callback.onComplate();
				}
			}
		};
		pageAnimation.run((int) duration);

	}

	@Override
	public void removeAnimationEndCallback(AnimationEndCallback endCallback) {
		callbacks.remove(endCallback);
	}

	@Override
	public double getPositionX() {
		return xPosition;
	}

}
