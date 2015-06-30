package eu.ydp.empiria.player.client.controller.multiview.animation;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.FlowPanel;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.debug.logger.Debug;
import eu.ydp.gwtutil.client.util.events.animation.TransitionEndEvent;
import eu.ydp.gwtutil.client.util.events.animation.TransitionEndHandler;

/**
 * <code>
 * -moz-transition: -moz-transform linear;<br/>
 * -moz-transform: translateX(-1920px);<br/>
 * -moz-transition-duration: 2s;<br/>
 * </code>
 *
 * @author plelakowski
 */
public class AnimationFF extends AbstractAnimation implements TransitionEndHandler {
    private final static String SUFFIX = "moz";

    private HandlerRegistration transitionEndRegistration;

    @Override
    public void goTo(FlowPanel toAnimate, int xPosition, double duration) {
        Debug.log("animation ff");
        setRunning(true);
        setToAnimate(toAnimate);
        setPositionX(xPosition);
        if (transitionEndRegistration != null) {
            transitionEndRegistration.removeHandler();
        }
        toAnimate.addDomHandler(this, TransitionEndEvent.getType());
        float currentPosition = NumberUtils.tryParseFloat(toAnimate.getElement().getStyle().getLeft().replaceAll("[a-z%]+$", ""));
        double animationLength = Math.abs(currentPosition) - Math.abs(xPosition);
        setProperty(toAnimate, SUFFIX + TRANSITION_DURATION, duration + "ms");
        setProperty(toAnimate, SUFFIX + TRANSITION, "all 1s ease");
        setProperty(toAnimate, SUFFIX + TRANSFORM, "translate(" + animationLength + "px,0)");

    }

    @Override
    public void onTransitionEnd(TransitionEndEvent event) {
        Debug.log("complate");
        setProperty(getToAnimate(), SUFFIX + TRANSITION, "-webkit-transform linear");
        setProperty(getToAnimate(), SUFFIX + TRANSFORM, "translate(" + 0 + "%,0)");
        setProperty(getToAnimate(), SUFFIX + TRANSITION_DURATION, 0 + "ms");
        getToAnimate().getElement().getStyle().setLeft(getPositionX(), Unit.PCT);
        onComplate(0);
    }

}
