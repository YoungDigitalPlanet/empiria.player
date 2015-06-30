package eu.ydp.empiria.player.client.controller.multiview.animation;

import com.google.gwt.user.client.ui.FlowPanel;

public interface Animation {

    void goTo(FlowPanel toAnimate, int xPosition, double duration);

    void addAnimationEndCallback(AnimationEndCallback endCallback);

    void removeAnimationEndCallback(AnimationEndCallback endCallback);

    double getPositionX();

    boolean isRunning();

}
