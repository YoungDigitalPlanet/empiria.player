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

import static org.junit.Assert.*;

import eu.ydp.empiria.player.client.controller.item.ItemResponseManager;
import eu.ydp.empiria.player.client.controller.variables.objects.response.*;
import java.util.Map;
import org.junit.*;

public class GroupedResponseAnswersMapBuilderJUnitTest {

    private GroupedResponseAnswersMapBuilder testObj;
    private ResponsesMapBuilder responsesMapBuilder = new ResponsesMapBuilder();

    @Before
    public void setUp() throws Exception {
        testObj = new GroupedResponseAnswersMapBuilder();
    }

    @Test
    public void shouldGroupResponsesWithSameGroup() throws Exception {
        Response firstResponseWithGroup1 = new ResponseBuilder().withIdentifier("id1").withCorrectAnswers("correct1").withGroups("group1").build();
        Response secondResponseWithGroup1 = new ResponseBuilder().withIdentifier("id2").withCorrectAnswers("correct2").withGroups("group1").build();

        Response firstResponseWithGroup2 = new ResponseBuilder().withIdentifier("id3").withCorrectAnswers("correct3").withGroups("group2").build();
        Response secondResponseWithGroup2 = new ResponseBuilder().withIdentifier("id4").withCorrectAnswers("correct4").withGroups("group2").build();

        ItemResponseManager responseManager = responsesMapBuilder.buildResponseManager(firstResponseWithGroup1, secondResponseWithGroup1, firstResponseWithGroup2,
                                                                                secondResponseWithGroup2);
        testObj.initialize(responseManager);

        Map<String, ResponseAnswerGrouper> groupToResponseGrouperMap = testObj.createResponseAnswerGroupersMap();

        ResponseAnswerGrouper responseAnswerGrouper = groupToResponseGrouperMap.get("group1");
        assertTrue(responseAnswerGrouper.isAnswerCorrect("correct1", firstResponseWithGroup1));
        assertTrue(responseAnswerGrouper.isAnswerCorrect("correct2", secondResponseWithGroup1));

        ResponseAnswerGrouper responseAnswerGrouper2 = groupToResponseGrouperMap.get("group2");
        assertTrue(responseAnswerGrouper2.isAnswerCorrect("correct3", firstResponseWithGroup2));
        assertTrue(responseAnswerGrouper2.isAnswerCorrect("correct4", secondResponseWithGroup2));
    }
}
