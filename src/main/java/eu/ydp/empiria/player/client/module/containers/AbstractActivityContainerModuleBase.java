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

package eu.ydp.empiria.player.client.module.containers;

import eu.ydp.empiria.player.client.controller.feedback.counter.event.FeedbackCounterEvent;
import eu.ydp.empiria.player.client.module.core.base.ContainerModuleBase;
import eu.ydp.empiria.player.client.module.core.flow.Activity;

import static eu.ydp.empiria.player.client.controller.feedback.counter.event.FeedbackCounterEventTypes.*;

public abstract class AbstractActivityContainerModuleBase extends ContainerModuleBase implements Activity {

    private final FeedbackCounterEvent feedbackCounterResetEvent = new FeedbackCounterEvent(RESET_COUNTER, this);
    private final ModulesActivitiesController modulesActivitiesController;

    private boolean markingAnswers = false;
    private boolean showingAnswers = false;

    public AbstractActivityContainerModuleBase() {
        modulesActivitiesController = new ModulesActivitiesController();
    }

    @Override
    public void markAnswers(boolean mark) {
        if (!mark && markingAnswers || mark && !markingAnswers) {
            showCorrectAnswers(false);
            doMarkAnswers(mark);
        }
    }

    @Override
    public void showCorrectAnswers(boolean show) {
        if (!show && showingAnswers || show && !showingAnswers) {
            markAnswers(false);
            doShowCorrectAnswers(show);
        }
    }

    @Override
    public void lock(boolean state) {
        modulesActivitiesController.lock(getChildren(), state);
    }

    @Override
    public void reset() {
        getEventsBus().fireEvent(feedbackCounterResetEvent);
        showCorrectAnswers(false);
        markAnswers(false);
        modulesActivitiesController.reset(getChildren());
    }

    void doMarkAnswers(boolean mark) {
        modulesActivitiesController.markAnswers(getChildren(), mark);
        markingAnswers = mark;
    }

    void doShowCorrectAnswers(boolean show) {
        modulesActivitiesController.showCorrectAnswers(getChildren(), show);
        showingAnswers = show;
    }

}
