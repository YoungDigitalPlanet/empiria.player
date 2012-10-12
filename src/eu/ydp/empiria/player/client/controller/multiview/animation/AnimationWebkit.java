package eu.ydp.empiria.player.client.controller.multiview.animation;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;

import eu.ydp.empiria.player.client.util.events.animation.TransitionEndEvent;
import eu.ydp.empiria.player.client.util.events.animation.TransitionEndHandler;
import eu.ydp.gwtutil.client.NumberUtils;

/**
 * <code>
     -webkit-transition: -webkit-transform 2s linear;</br>
     -webkit-transform: translateX(-1920px);</code>
 *
 */
public class AnimationWebkit extends AbstractAnimation implements TransitionEndHandler {
	private final static String SUFFIX = "webkit";

	private HandlerRegistration transitionEndRegistration;

	@Override
	public void goTo(FlowPanel toAnimate, int xPosition, double duration) {
		setRunning(true);
		setToAnimate(toAnimate);
		setPositionX(xPosition);
		float currentPosition = NumberUtils.tryParseFloat(toAnimate.getElement().getStyle().getLeft().replaceAll("[a-z%]+$", ""));
		double animationLength = Math.abs(currentPosition) - Math.abs(xPosition);
		setProperty(toAnimate, SUFFIX + TRANSITION, "all " + duration + "ms ease-in-out");
		setProperty(toAnimate, SUFFIX + TRANSFORM, "translate(" + animationLength + "%,0)");
		transitionEndRegistration = toAnimate.addDomHandler(this, TransitionEndEvent.getType());
	}

	@Override
	public void onTransitionEnd(TransitionEndEvent event) {
		transitionEndRegistration.removeHandler();
		setProperty(getToAnimate(), SUFFIX + TRANSITION, "-webkit-transform linear");
		setProperty(getToAnimate(), SUFFIX + TRANSFORM, "translate(" + 0 + "%,0)");
		setProperty(getToAnimate(), SUFFIX + TRANSITION_DURATION, 0 + "ms");
		getToAnimate().getElement().getStyle().setLeft(getPositionX(), Unit.PCT);
		onComplate();
	}

}
