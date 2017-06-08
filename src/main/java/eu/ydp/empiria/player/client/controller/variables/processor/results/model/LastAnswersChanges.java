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

package eu.ydp.empiria.player.client.controller.variables.processor.results.model;

import com.google.common.collect.Lists;

import java.util.List;

public class LastAnswersChanges {

    private List<String> addedAnswers;
    private List<String> removedAnswers;

    public LastAnswersChanges() {
        addedAnswers = Lists.newArrayList();
        removedAnswers = Lists.newArrayList();
    }

    public LastAnswersChanges(List<String> addedAnswers, List<String> removedAnswers) {
        this.addedAnswers = addedAnswers;
        this.removedAnswers = removedAnswers;
    }

    public List<String> getAddedAnswers() {
        return addedAnswers;
    }

    public void setAddedAnswers(List<String> addedAnswers) {
        this.addedAnswers = addedAnswers;
    }

    public List<String> getRemovedAnswers() {
        return removedAnswers;
    }

    public void setRemovedAnswers(List<String> removedAnswers) {
        this.removedAnswers = removedAnswers;
    }

    public boolean containChanges() {
        return (!addedAnswers.isEmpty()) || (!removedAnswers.isEmpty());
    }
}
