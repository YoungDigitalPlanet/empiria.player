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

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.FlowPanel;

import java.util.HashSet;
import java.util.Set;

public class NoAnimation implements Animation {
    protected final Set<AnimationEndCallback> callbacks = new HashSet<AnimationEndCallback>();
    private int xPosition;

    @Override
    public void goTo(FlowPanel toAnimate, int xPosition, double duration) {
        this.xPosition = xPosition;
        setPosition(toAnimate, xPosition);
        callEndCallbacks();
    }

    private void callEndCallbacks() {
        for (AnimationEndCallback endCallback : callbacks) {
            endCallback.onComplate(xPosition);
        }
    }

    @Override
    public void addAnimationEndCallback(AnimationEndCallback endCallback) {
        callbacks.add(endCallback);

    }

    private void setPosition(FlowPanel toAnimate, double position) {
        toAnimate.getElement().getStyle().setLeft(position, Unit.PCT);
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
        return false;
    }

}
