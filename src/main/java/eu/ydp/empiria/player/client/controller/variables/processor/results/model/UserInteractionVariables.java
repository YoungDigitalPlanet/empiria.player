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

public class UserInteractionVariables {

    private LastAnswersChanges lastAnswerChanges;
    private LastMistaken lastmistaken;
    private int mistakes;

    public UserInteractionVariables() {
        lastAnswerChanges = new LastAnswersChanges();
        lastmistaken = LastMistaken.NONE;
        mistakes = 0;
    }

    public UserInteractionVariables(LastAnswersChanges lastAnswerChanges, LastMistaken lastmistaken, int mistakes) {
        this.lastAnswerChanges = lastAnswerChanges;
        this.lastmistaken = lastmistaken;
        this.mistakes = mistakes;
    }

    public int getMistakes() {
        return mistakes;
    }

    public void setMistakes(int mistakes) {
        this.mistakes = mistakes;
    }

    public LastAnswersChanges getLastAnswerChanges() {
        return lastAnswerChanges;
    }

    public void setLastAnswerChanges(LastAnswersChanges lastAnswerChanges) {
        this.lastAnswerChanges = lastAnswerChanges;
    }

    public LastMistaken getLastmistaken() {
        return lastmistaken;
    }

    public void setLastmistaken(LastMistaken lastmistaken) {
        this.lastmistaken = lastmistaken;
    }
}
