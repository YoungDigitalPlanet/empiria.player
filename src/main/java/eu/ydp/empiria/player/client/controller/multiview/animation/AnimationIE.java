package eu.ydp.empiria.player.client.controller.multiview.animation;

import com.google.gwt.user.client.ui.FlowPanel;

public class AnimationIE extends AbstractAnimation {

    @Override
    public void goTo(FlowPanel toAnimate, int xPosition, double duration) {
        setPositionX(xPosition);
    }

}
