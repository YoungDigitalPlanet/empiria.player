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

package eu.ydp.empiria.player.client.controller.feedback.structure.condition;

import com.google.common.collect.Lists;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.NONE)
public class FeedbackConditionBase implements FeedbackCondition {

    @XmlElement(name = "propertyCondition")
    private List<PropertyConditionBean> propertyConditions = Lists.newArrayList();

    @XmlElement(name = "countCondition")
    private List<CountConditionBean> countConditions = Lists.newArrayList();

    @XmlElement(name = "and")
    private List<AndConditionBean> andConditions = Lists.newArrayList();

    @XmlElement(name = "or")
    private List<OrConditionBean> orConditions = Lists.newArrayList();

    @XmlElement(name = "not")
    private List<NotConditionBean> notConditions = Lists.newArrayList();

    public List<PropertyConditionBean> getPropertyConditions() {
        return propertyConditions;
    }

    public void setPropertyConditions(List<PropertyConditionBean> propertyCondition) {
        this.propertyConditions = propertyCondition;
    }

    public List<CountConditionBean> getCountConditions() {
        return countConditions;
    }

    public void setCountConditions(List<CountConditionBean> countCondition) {
        this.countConditions = countCondition;
    }

    public List<AndConditionBean> getAndConditions() {
        return andConditions;
    }

    public void setAndConditions(List<AndConditionBean> andConditions) {
        this.andConditions = andConditions;
    }

    public List<OrConditionBean> getOrConditions() {
        return orConditions;
    }

    public void setOrConditions(List<OrConditionBean> orConditions) {
        this.orConditions = orConditions;
    }

    public List<NotConditionBean> getNotConditions() {
        return notConditions;
    }

    public void setNotConditions(List<NotConditionBean> notConditions) {
        this.notConditions = notConditions;
    }

    public List<FeedbackCondition> getAllConditions() {
        List<FeedbackCondition> allConditions = new ArrayList<FeedbackCondition>();
        allConditions.addAll(propertyConditions);
        allConditions.addAll(countConditions);
        allConditions.addAll(andConditions);
        allConditions.addAll(orConditions);
        allConditions.addAll(notConditions);

        return allConditions;
    }
}
