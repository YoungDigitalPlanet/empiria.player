package eu.ydp.empiria.player.client.controller.multiview.animation;

import com.google.gwt.animation.client.Animation;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;
import eu.ydp.gwtutil.client.NumberUtils;

public class PageSwitchAnimation extends Animation {

    private final Style style;
    private final float from;
    private final float to; // NOPMD
    private final float animationLength;
    private double currentPosition = 0;

    public PageSwitchAnimation(FlowPanel element, int to) { // NOPMD
        this.style = element.getElement().getStyle();
        this.from = NumberUtils.tryParseFloat(style.getLeft().replaceAll("[a-z%]+$", ""));
        this.to = to;
        this.animationLength = Math.abs(from - to);
    }

    private void setPosition(double position) {
        style.setLeft(position, Unit.PCT);
    }

    @Override
    protected void onUpdate(double progress) {
        if (progress <= 1) {
            if (from < to) {
                currentPosition = from + (animationLength * progress);
                setPosition(currentPosition);
            } else if (from > to) {
                currentPosition = from - (animationLength * progress);
                setPosition(currentPosition);
            }
        }

    }

    @Override
    protected double interpolate(double progress) {
        return (1 + Math.cos(Math.PI + progress * Math.PI)) / 2;
    }

    @Override
    protected void onComplete() {
        setPosition(to);
    }

}
