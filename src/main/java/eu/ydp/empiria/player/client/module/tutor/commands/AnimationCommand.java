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

package eu.ydp.empiria.player.client.module.tutor.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.tutor.TutorCommand;
import eu.ydp.empiria.player.client.module.tutor.TutorEndHandler;
import eu.ydp.gwtutil.client.animation.Animation;
import eu.ydp.gwtutil.client.animation.AnimationEndHandler;

public class AnimationCommand implements TutorCommand {

    private final Animation animation;
    private final TutorEndHandler endHandler;
    private boolean finished = false;
    protected final AnimationEndHandler animationHandler = new AnimationEndHandler() {

        @Override
        public void onEnd() {
            finished = true;
            endHandler.onEndWithDefaultAction();
        }
    };

    @Inject
    public AnimationCommand(@Assisted Animation animation, @Assisted TutorEndHandler handler) {
        this.animation = animation;
        this.endHandler = handler;
    }

    @Override
    public void execute() {
        animation.start(animationHandler);
    }

    @Override
    public void terminate() {
        animation.terminate();
        finished = true;
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

}
