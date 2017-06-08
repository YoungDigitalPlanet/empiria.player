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

package eu.ydp.empiria.player.client.controller.delivery;

public class EngineModeManager {

    private EngineMode state;

    private boolean finished = false;

    EngineModeManager() {
        state = EngineMode.NONE;
    }

    public boolean canBeginAssessmentLoading() {
        return (state == EngineMode.NONE);
    }

    public void beginAssessmentLoading() {
        state = EngineMode.ASSESSMENT_LOADING;
    }

    public boolean canEndAssessmentLoading() {
        return (state == EngineMode.ASSESSMENT_LOADING);
    }

    public void endAssessmentLoading() {
        state = EngineMode.ASSESSMENT_LOADED;
    }

    public boolean canBeginItemLoading() {
        return (state == EngineMode.ASSESSMENT_LOADED || state == EngineMode.RUNNING || state == EngineMode.FINISHED);
    }

    public void beginItemLoading() {
        state = EngineMode.ITEM_LOADING;
    }

    public boolean canEndItemLoading() {
        return (state == EngineMode.ITEM_LOADING);
    }

    public void endItemLoading() {
        state = EngineMode.ITEM_LOADED;
    }

    public boolean canRun() {
        return (state == EngineMode.ITEM_LOADED && !finished);
    }

    public void run() {
        state = EngineMode.RUNNING;
    }

    public boolean canPreview() {
        return (state == EngineMode.ITEM_LOADED && finished);
    }

    public boolean canFinish() {
        return (state == EngineMode.RUNNING && !finished);
    }

    public void finish() {
        state = EngineMode.FINISHED;
        finished = true;
    }

    public boolean canSummary() {
        return (finished);
    }

    public void summary() {
        state = EngineMode.FINISHED;
    }

    public boolean canContinueAssessment() {
        return (state == EngineMode.FINISHED);
    }

    public void continueAssessment() {
        finished = false;
    }

    public boolean isAssessmentLoaded() {
        return (state == EngineMode.ASSESSMENT_LOADED || state == EngineMode.ITEM_LOADING || state == EngineMode.ITEM_LOADED || state == EngineMode.FINISHED || state == EngineMode.RUNNING);

    }

    public boolean canNavigate() {
        return (state == EngineMode.RUNNING || state == EngineMode.FINISHED);
    }

    @Override
    public String toString() {
        return state.toString();
    }

}
