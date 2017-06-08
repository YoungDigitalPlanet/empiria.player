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

package eu.ydp.empiria.player.client.module.selection.model;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.components.choicebutton.Identifiable;

public class SelectionAnswerDto implements Identifiable {

    private String id;
    private boolean selected;
    private boolean locked;
    private UserAnswerType selectionAnswerType;
    private boolean stateChanged;

    public SelectionAnswerDto() {
    }

    @Inject
    public SelectionAnswerDto(@Assisted String id) {
        this.id = id;
        this.selected = false;
        this.locked = false;
        this.selectionAnswerType = UserAnswerType.DEFAULT;
        this.stateChanged = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        if (this.selected != selected) {
            stateChanged = true;
        }

        this.selected = selected;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        if (this.locked != locked) {
            stateChanged = true;
        }

        this.locked = locked;
    }

    public UserAnswerType getSelectionAnswerType() {
        return selectionAnswerType;
    }

    public void setSelectionAnswerType(UserAnswerType selectionAnswerType) {
        if (this.selectionAnswerType != selectionAnswerType) {
            stateChanged = true;
        }

        this.selectionAnswerType = selectionAnswerType;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStateChanged() {
        return stateChanged;
    }

    public void setStateChanged(boolean stateChanged) {
        this.stateChanged = stateChanged;
    }
}
