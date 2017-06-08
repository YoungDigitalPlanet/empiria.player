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

package eu.ydp.empiria.player.client.controller.variables.processor.module.grouped;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupedResponseAnswersMapBuilder {

    private Map<String, List<GroupedAnswer>> groupNameToGroupedAnswersMap;

    public Map<String, ResponseAnswerGrouper> createResponseAnswerGroupersMap() {
        Map<String, ResponseAnswerGrouper> responseAnswerGrouperMap = new HashMap<String, ResponseAnswerGrouper>();
        for (String groupName : groupNameToGroupedAnswersMap.keySet()) {
            List<GroupedAnswer> groupedAnswers = groupNameToGroupedAnswersMap.get(groupName);
            responseAnswerGrouperMap.put(groupName, new ResponseAnswerGrouper(groupedAnswers));
        }

        return responseAnswerGrouperMap;
    }

    public void initialize(ItemResponseManager responseManager) {
        groupNameToGroupedAnswersMap = new HashMap<>();
        assignAnswersFromResponsesToCorrectGroup(responseManager);
    }

    private void assignAnswersFromResponsesToCorrectGroup(ItemResponseManager responseManager) {
        for (Response response : responseManager.getResponses()) {
            List<String> allCorrectAnswers = response.correctAnswers.getAllAnswers();
            List<String> groups = response.groups;

            addAnswersToGroups(allCorrectAnswers, groups);
        }
    }

    private void addAnswersToGroups(List<String> correctAnswers, List<String> groups) {
        for (String groupName : groups) {
            List<GroupedAnswer> existingGroupedAnswers = getExistingGroupedAnswers(groupName);
            List<GroupedAnswer> currentGroupedAnswers = createGroupedAnswersFromStrings(correctAnswers);

            existingGroupedAnswers.addAll(currentGroupedAnswers);
        }
    }

    private List<GroupedAnswer> getExistingGroupedAnswers(String groupName) {
        List<GroupedAnswer> existingGroupedAnswers = groupNameToGroupedAnswersMap.get(groupName);
        if (existingGroupedAnswers == null) {
            existingGroupedAnswers = Lists.newArrayList();
            groupNameToGroupedAnswersMap.put(groupName, existingGroupedAnswers);
        }
        return existingGroupedAnswers;
    }

    private List<GroupedAnswer> createGroupedAnswersFromStrings(List<String> correctAnswers) {
        List<GroupedAnswer> currentGroupedAnswers = Lists.newArrayList();
        for (String correctAnswer : correctAnswers) {
            GroupedAnswer groupedAnswer = new GroupedAnswer(correctAnswer);
            currentGroupedAnswers.add(groupedAnswer);
        }

        return currentGroupedAnswers;
    }
}
