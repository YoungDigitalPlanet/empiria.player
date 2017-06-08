/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.controller.multiview.animation;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.FlowPanel;

import java.util.HashSet;
import java.util.Set;

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
