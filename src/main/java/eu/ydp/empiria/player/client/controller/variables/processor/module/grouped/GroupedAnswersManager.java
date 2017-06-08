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

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

import java.util.Collection;
import java.util.Map;

public class GroupedAnswersManager {

    private Map<String, ResponseAnswerGrouper> groupNameToResponseAnswerGrouperMap;
    private GroupedResponseAnswersMapBuilder responseAnswersMapBuilder;

    @Inject
    public GroupedAnswersManager(GroupedResponseAnswersMapBuilder responseAnswersMapBuilder) {
        this.responseAnswersMapBuilder = responseAnswersMapBuilder;
    }

    public void initialize(ItemResponseManager responseManager) {
        responseAnswersMapBuilder.initialize(responseManager);
        this.groupNameToResponseAnswerGrouperMap = responseAnswersMapBuilder.createResponseAnswerGroupersMap();
    }

    public boolean isAnswerCorrectInAnyOfGroups(String answer, Response response, Collection<String> groups) {
        for (String groupName : groups) {
            boolean isAnswerCorrect = isAnswerCorrectInGroup(answer, response, groupName);
            if (isAnswerCorrect) {
                return true;
            }
        }

        return false;
    }

    private boolean isAnswerCorrectInGroup(String answer, Response response, String groupName) {
        ResponseAnswerGrouper responseAnswerGrouper = groupNameToResponseAnswerGrouperMap.get(groupName);
        boolean isAnswerCorrect = responseAnswerGrouper.isAnswerCorrect(answer, response);
        return isAnswerCorrect;
    }
}
