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

import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.debug.logger.Debug;
import eu.ydp.gwtutil.client.util.events.animation.AnimationEndEvent;
import eu.ydp.gwtutil.client.util.events.animation.AnimationEndHandler;
import eu.ydp.gwtutil.client.util.events.animation.TransitionEndEvent;
import eu.ydp.gwtutil.client.util.events.animation.TransitionEndHandler;

/**
 * <code>
     -webkit-transition: -webkit-transform 2s linear;</br>
     -webkit-transform: translateX(-1920px);</code>
 *
 */
public class AnimationWebkitAndroid extends AbstractAnimation implements TransitionEndHandler, AnimationEndHandler {
	private final static String SUFFIX = "webkit";

	private HandlerRegistration transitionEndRegistration;
	protected Set<String> keyframesAnimations = new HashSet<String>();
	private final static String ANIMATE_TEMPLATE = "@-webkit-keyframes $name { 0% {-webkit-transform:translate($x,0)} 100% {left:$left%} }";

	private String createKeyFrames(int xPosition,Unit unit,int left) {
		String name = "kf" + xPosition+"-"+left;
		if (!keyframesAnimations.contains(name)) {
			String template = ANIMATE_TEMPLATE.replaceAll("\\$name", name).replaceAll("\\$x", xPosition+unit.getType()).replaceAll("\\$left", left+"");
			StyleElement style = StyleElement.as(DOM.createElement("style"));
			Text text = Text.as(Document.get().createTextNode(template));
			style.appendChild(text);
			Document.get().getElementsByTagName("head").getItem(0).appendChild(style);
			keyframesAnimations.add(name);
		}
		return name;

	}

	@Override
	public void goTo(FlowPanel toAnimate, int xPosition, double duration) {
		setRunning(true);
		setToAnimate(toAnimate);
		setPositionX(xPosition);
		transitionEndRegistration = toAnimate.addDomHandler(this, AnimationEndEvent.getType());
		float currentPosition = NumberUtils.tryParseFloat(toAnimate.getElement().getStyle().getLeft().replaceAll("[a-z%]+$", ""));
		double animationLength = toAnimate.getOffsetWidth() * ((Math.abs(currentPosition) - Math.abs(xPosition)) / 100);
		setProperty(toAnimate, SUFFIX+ANIMATION, createKeyFrames((int) animationLength,Unit.PX,xPosition)+" "+duration+"ms"+" forwards");

	}

	@Override
	public void onTransitionEnd(TransitionEndEvent event) {
		transitionEndRegistration.removeHandler();
		setProperty(getToAnimate(), SUFFIX + TRANSITION, "-webkit-transform linear");
		setProperty(getToAnimate(), SUFFIX + TRANSFORM, "translate3d(" + 0 + "px,0,0)");
		setProperty(getToAnimate(), SUFFIX + TRANSITION_DURATION, 0 + "ms");
		getToAnimate().getElement().getStyle().setLeft(getPositionX(), Unit.PCT);
		onComplate(0);
	}

	@Override
	public void onAnimationEnd(AnimationEndEvent event) {
		Debug.log("animation end");
		if(transitionEndRegistration!=null) {
			transitionEndRegistration.removeHandler();
		}
		setProperty(getToAnimate(), SUFFIX+ANIMATION,"none");
		getToAnimate().getElement().getStyle().setLeft(getPositionX(), Unit.PCT);
		onComplate(0);
	}

}
