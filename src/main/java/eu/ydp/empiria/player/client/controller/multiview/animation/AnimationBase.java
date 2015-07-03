package eu.ydp.empiria.player.client.controller.multiview.animation;

import com.google.gwt.user.client.ui.FlowPanel;

import java.util.HashSet;
import java.util.Set;

public class AnimationBase implements Animation {
    protected final Set<AnimationEndCallback> callbacks = new HashSet<AnimationEndCallback>();
    private int xPosition;
    private boolean running = false;

    @Override
    public void addAnimationEndCallback(AnimationEndCallback endCallback) {
        callbacks.add(endCallback);

    }

    @Override
    public void goTo(FlowPanel toAnimate, final int xPosition, double duration) {
        running = true;
        this.xPosition = xPosition;
        PageSwitchAnimation pageAnimation = new PageSwitchAnimation(toAnimate, xPosition) {
            @Override
            protected void onComplete() {
                super.onComplete();
                for (AnimationEndCallback callback : callbacks) {
                    callback.onComplate(xPosition);
                }
                running = false;
            }
        };
        pageAnimation.run((int) duration);

    }

    @Override
    public void removeAnimationEndCallback(AnimationEndCallback endCallback) {
        callbacks.remove(endCallback);
    }

    @Override
    public double getPositionX() {
        return xPosition;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

}
