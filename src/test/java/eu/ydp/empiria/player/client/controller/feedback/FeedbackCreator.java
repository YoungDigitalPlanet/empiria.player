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

package eu.ydp.empiria.player.client.controller.feedback;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.feedback.structure.Feedback;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ActionType;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.AndConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.PropertyConditionBean;
import eu.ydp.gwtutil.client.operator.MatchOperator;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FeedbackCreator {

    private final String allOkUrl;

    private final String okUrl;

    private final String wrongUrl;

    public FeedbackCreator(String okUrl, String wrongUrl, String allOkUrl) {
        this.okUrl = okUrl;
        this.wrongUrl = wrongUrl;
        this.allOkUrl = allOkUrl;
    }

    public List<Feedback> createFeedbackList() {
        List<Feedback> feedbackList = Lists.newArrayList();

        addSoundFeedback(feedbackList, FeedbackPropertyName.OK, okUrl);
        addSoundFeedback(feedbackList, FeedbackPropertyName.WRONG, wrongUrl);
        addSoundFeedback(feedbackList, FeedbackPropertyName.ALL_OK, allOkUrl);

        return feedbackList;
    }

    private void addSoundFeedback(List<Feedback> feedbacks, FeedbackPropertyName name, String url) {
        if (url != null) {
            feedbacks.add(createSoundFeedback(name, url));
        }
    }

    private Feedback createSoundFeedback(FeedbackPropertyName name, String url) {
        Feedback feedback = mock(Feedback.class);
        List<FeedbackAction> actionList = ActionListBuilder.create().addUrlAction(ActionType.NARRATION, url).getList();
        FeedbackCondition condition = getCondition(name);

        when(feedback.getActions()).thenReturn(actionList);
        when(feedback.getCondition()).thenReturn(condition);

        return feedback;
    }

    private FeedbackCondition getCondition(FeedbackPropertyName name) {
        AndConditionBean andCondition = new AndConditionBean();
        PropertyConditionBean condition = new PropertyConditionBean();
        List<PropertyConditionBean> conditions = Lists.newArrayList();

        condition.setOperator(MatchOperator.EQUAL.getName());
        condition.setProperty(name.getName());
        conditions.add(condition);

        andCondition.setPropertyConditions(conditions);

        return andCondition;
    }
}
