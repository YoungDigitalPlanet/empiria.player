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

package eu.ydp.empiria.player.client.module.connection;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.gwtutil.client.collections.KeyValue;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ConnectionModuleModelJUnitTest {

    private ConnectionModuleModel connectionModuleModel;

    // private final ResponseNodeParser responseNodeParser = new
    // ResponseNodeParser();

    @Before
    public void init() {
        connectionModuleModel = new ConnectionModuleModel(mockResponse(), mock(ResponseModelChangeListener.class));
    }

    private Response mockResponse() {
        ResponseBuilder responseBuilder = new ResponseBuilder();

        responseBuilder.withCorrectAnswers("CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1", "CONNECTION_RESPONSE_1_3 CONNECTION_RESPONSE_1_4");

        return responseBuilder.build();
    }

    @Test
    public void shouldParseResponseAndConvertToInternalKeyValueFormat() {
        List<KeyValue<String, String>> correctAnswers = connectionModuleModel.getCorrectAnswers();

        assertThat(correctAnswers.size(), is(equalTo(2)));
        assertThat(correctAnswers.get(0).getKey(), is(equalTo("CONNECTION_RESPONSE_1_0")));
        assertThat(correctAnswers.get(0).getValue(), is(equalTo("CONNECTION_RESPONSE_1_1")));
        assertThat(correctAnswers.get(1).getKey(), is(equalTo("CONNECTION_RESPONSE_1_3")));
        assertThat(correctAnswers.get(1).getValue(), is(equalTo("CONNECTION_RESPONSE_1_4")));
    }

    @Test
    public void checkUserResonseContainsAnswer() {
        connectionModuleModel.addAnswer("CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1");
        connectionModuleModel.addAnswer("CONNECTION_RESPONSE_1_3 CONNECTION_RESPONSE_1_4");

        boolean check = connectionModuleModel.checkUserResonseContainsAnswer("CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1");

        assertThat(check, is(equalTo(true)));
    }
}
