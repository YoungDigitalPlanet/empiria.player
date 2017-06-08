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

package eu.ydp.empiria.player.client.module.ordering.model;

import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;

public class OrderingItem {

    private final String itemId;
    private final String answerValue;
    private boolean selected = false;
    private UserAnswerType answerType = UserAnswerType.DEFAULT;
    private boolean locked = false;

    public OrderingItem(String itemId, String answerValue) {
        this.itemId = itemId;
        this.answerValue = answerValue;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public UserAnswerType getAnswerType() {
        return answerType;
    }

    public void setAnswerType(UserAnswerType answerType) {
        this.answerType = answerType;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getId() {
        return itemId;
    }

    public String getAnswerValue() {
        return answerValue;
    }
}
