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

package eu.ydp.empiria.player.client.controller.feedback.structure;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackActionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackConditionBean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "feedback")
public class FeedbackBean implements Feedback {

    @XmlElement(name = "action")
    FeedbackActionBean action;

    @XmlElement(name = "condition")
    FeedbackConditionBean condition;

    public FeedbackActionBean getAction() {
        return action;
    }

    public void setActions(FeedbackActionBean action) {
        this.action = action;
    }

    public void setConditionElement(FeedbackConditionBean condition) {
        this.condition = condition;
    }

    @Override
    public List<FeedbackAction> getActions() {
        return action.getAllActions();
    }

    @Override
    public FeedbackCondition getCondition() {
        return (condition.getAllConditions().isEmpty()) ? null : condition.getAllConditions().get(0);
    }
}
