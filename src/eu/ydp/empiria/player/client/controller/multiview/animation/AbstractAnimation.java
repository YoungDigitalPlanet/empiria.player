package eu.ydp.empiria.player.client.controller.multiview.animation;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.FlowPanel;

public abstract class AbstractAnimation implements Animation {

	protected static final String TRANSITION = "Transition";
	protected static final String TRANSFORM = "Transform";
	protected static final String TRANSITION_DURATION = "TransitionDuration";
	protected static final String ANIMATION_NAME = "AnimationName";
	protected static final String ANIMATION = "Animation";
	protected static final String ANIMATION_DURATION = "AnimationDuration";
	protected static final String ANIMATION_FILL_MODE = "AnimationFillMode";

	protected final Set<AnimationEndCallback> callbacks = new HashSet<AnimationEndCallback>();
	private int positionX = 0;
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

	public void onComplate(final int position) {
		for (AnimationEndCallback callback : callbacks) {
			callback.onComplate(position);
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
