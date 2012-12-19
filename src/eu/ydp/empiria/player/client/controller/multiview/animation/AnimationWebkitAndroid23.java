package eu.ydp.empiria.player.client.controller.multiview.animation;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.dom.client.Text;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
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
public class AnimationWebkitAndroid23 extends AbstractAnimation implements TransitionEndHandler {
	private final static String SUFFIX = "webkit";

	private HandlerRegistration transitionEndRegistration;
	Set<String> keyframesAnimations = new HashSet<String>();
	private final String animateTemplate = "@-webkit-keyframes $name { to {$x} }";

	private String createKeyFrames(String xPosition) {
		String name = "kf" + xPosition.replace('%', ' ');
		if (!keyframesAnimations.contains(name)) {
			String template = animateTemplate.replaceAll("\\$name", name).replaceAll("\\$x", xPosition);
			StyleElement style = StyleElement.as(DOM.createElement("style"));
			Text text = Text.as(Document.get().createTextNode(template));
			style.appendChild(text);
			Document.get().getElementsByTagName("head").getItem(0).appendChild(style);
		}
		return name;

	}

	@Override
	public void goTo(FlowPanel toAnimate, int xPosition, double duration) {
		setRunning(true);
		setToAnimate(toAnimate);
		setPositionX(xPosition);
		float currentPosition = NumberUtils.tryParseFloat(toAnimate.getElement().getStyle().getLeft().replaceAll("[a-z%]+$", ""));
		double animationLength = toAnimate.getOffsetWidth() * ((Math.abs(currentPosition) - Math.abs(xPosition)) / 100);
		setProperty(toAnimate, SUFFIX + TRANSITION, "all " + duration + "ms ease-in-out");
		// procentowa animacja nie dziala na adroidzie 2.3.3
		setProperty(toAnimate, SUFFIX + TRANSFORM, "translate(" + animationLength + "px,0)");
		transitionEndRegistration = toAnimate.addDomHandler(this, TransitionEndEvent.getType());
	}

	@Override
	public void onTransitionEnd(TransitionEndEvent event) {
		transitionEndRegistration.removeHandler();
		setProperty(getToAnimate(), SUFFIX + TRANSITION, "-webkit-transform linear");
		setProperty(getToAnimate(), SUFFIX + TRANSFORM, "translate(" + 0 + "px,0)");
		setProperty(getToAnimate(), SUFFIX + TRANSITION_DURATION, 0 + "ms");
		getToAnimate().getElement().getStyle().setLeft(getPositionX(), Unit.PCT);
		onComplate();
	}

}
