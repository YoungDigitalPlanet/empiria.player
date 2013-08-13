package eu.ydp.empiria.player.client.controller.multiview.animation;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;

public class NoAnimation implements Animation {
	protected final Set<AnimationEndCallback> callbacks = new HashSet<AnimationEndCallback>();
	private int xPosition;

	@Override
	public void goTo(FlowPanel toAnimate, int xPosition, double duration) {
		this.xPosition = xPosition;
		setPosition(toAnimate, xPosition);
		callEndCallbacks();
	}

	private void callEndCallbacks() {
		for(AnimationEndCallback endCallback : callbacks){
			endCallback.onComplate(xPosition);
		}
	}

	@Override
	public void addAnimationEndCallback(AnimationEndCallback endCallback) {
		callbacks.add(endCallback);

	}

	private void setPosition(FlowPanel toAnimate ,double position) {
		toAnimate.getElement().getStyle().setLeft(position, Unit.PCT);
	}

	@Override
	public void removeAnimationEndCallback(AnimationEndCallback endCallback) {
		callbacks.remove(endCallback);

	}

	@Override
	public double getPositionX() {
		return xPosition;
	}

	@Override
	public boolean isRunning() {
		return false;
	}

}
