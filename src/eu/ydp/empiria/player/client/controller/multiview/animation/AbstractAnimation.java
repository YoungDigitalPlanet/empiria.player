package eu.ydp.empiria.player.client.controller.multiview.animation;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.FlowPanel;

public abstract class AbstractAnimation implements Animation {

	protected final static String TRANSITION = "Transition";
	protected final static String TRANSFORM = "Transform";
	protected final static String TRANSITION_DURATION = "TransitionDuration";
	protected final static String ANIMATION_NAME = "AnimationName";
	protected final static String ANIMATION = "Animation";
	protected final static String ANIMATION_DURATION = "AnimationDuration";
	protected final static String ANIMATION_FILL_MODE = "AnimationFillMode";

	protected final Set<AnimationEndCallback> callbacks = new HashSet<AnimationEndCallback>();
	protected int positionX = 0;
	private boolean running = false;
	private FlowPanel toAnimate;

	@Override
	public void addAnimationEndCallback(AnimationEndCallback endCallback) {
		callbacks.add(endCallback);
	}

	@Override
	public void removeAnimationEndCallback(AnimationEndCallback endCallback) {
		callbacks.remove(endCallback);
	}

	protected void setToAnimate(FlowPanel toAnimate) {
		this.toAnimate = toAnimate;
	}

	public FlowPanel getToAnimate() {
		return toAnimate;
	}

	public void onComplate() {
		for (AnimationEndCallback callback : callbacks) {
			callback.onComplate();
		}
		running = false;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	protected void setProperty(FlowPanel widget, String propertyName, String propertyValue) {
		if (widget != null) {
			Style style = widget.getElement().getStyle();
			style.setProperty(propertyName, propertyValue);
		}
	}

	@Override
	public double getPositionX() {
		return positionX;
	}

	@Override
	public boolean isRunning() {
		return running;
	}

}
