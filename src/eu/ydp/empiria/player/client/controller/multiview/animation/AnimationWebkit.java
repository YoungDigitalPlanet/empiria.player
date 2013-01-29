package eu.ydp.empiria.player.client.controller.multiview.animation;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;

import eu.ydp.empiria.player.client.util.events.animation.TransitionEndEvent;
import eu.ydp.empiria.player.client.util.events.animation.TransitionEndHandler;

/**
 * <code>
     -webkit-transition: -webkit-transform 2s linear;</br>
     -webkit-transform: translateX(-1920px);</code>
 *
 */
public class AnimationWebkit extends AbstractAnimation implements TransitionEndHandler {
	private final static String SUFFIX = "webkit";

	private HandlerRegistration transitionEndRegistration;

	int animationStartAt = 0;

	@Override
	public void goTo(FlowPanel toAnimate, int xPosition, double duration) {
		if (animationStartAt != xPosition) {
			setRunning(true);
			animationStartAt = xPosition;
			setToAnimate(toAnimate);
			setPositionX(xPosition);
			setProperty(toAnimate, SUFFIX + TRANSITION, "all " + duration + "ms ease-out");
			setProperty(toAnimate, SUFFIX + TRANSFORM, "translate(" + xPosition + "px,0)");
			transitionEndRegistration = toAnimate.addDomHandler(this, TransitionEndEvent.getType());
		}else{
			onComplate(xPosition);
		}
	}

	@Override
	public void onTransitionEnd(TransitionEndEvent event) {
		transitionEndRegistration.removeHandler();
		onComplate(animationStartAt);
	}

}
