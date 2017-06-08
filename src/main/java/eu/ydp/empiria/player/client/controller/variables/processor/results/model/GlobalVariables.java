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

public class GlobalVariables implements ResultVariables {

    private final int todo;
    private final int done;
    private final int mistakes;
    private final int errors;
    private final LastMistaken lastMistaken;

    public GlobalVariables(int todo, int done, int errors, int mistakes, LastMistaken lastMistaken) {
        this.todo = todo;
        this.done = done;
        this.mistakes = mistakes;
        this.errors = errors;
        this.lastMistaken = lastMistaken;
    }

    @Override
    public int getTodo() {
        return todo;
    }

    @Override
    public int getDone() {
        return done;
    }

    @Override
    public int getMistakes() {
        return mistakes;
    }

    @Override
    public int getErrors() {
        return errors;
    }

    @Override
    public LastMistaken getLastMistaken() {
        return lastMistaken;
    }

    @Override
    public String toString() {
        return "GlobalVariables [todo=" + todo + ", done=" + done + ", mistakes=" + mistakes + ", errors=" + errors + ", lastMistaken=" + lastMistaken + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + done;
        result = prime * result + errors;
        result = prime * result + ((lastMistaken == null) ? 0 : lastMistaken.hashCode());
        result = prime * result + mistakes;
        result = prime * result + todo;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ResultVariables)) {
            return false;
        }
        ResultVariables other = (ResultVariables) obj;
        if (done != other.getDone()) {
            return false;
        }
        if (errors != other.getErrors()) {
            return false;
        }
        if (lastMistaken != other.getLastMistaken()) {
            return false;
        }
        if (mistakes != other.getMistakes()) {
            return false;
        }
        if (todo != other.getTodo()) {
            return false;
        }
        return true;
    }

    public static GlobalVariables createEmpty() {
        return new GlobalVariables(0, 0, 0, 0, LastMistaken.NONE);
    }
}
