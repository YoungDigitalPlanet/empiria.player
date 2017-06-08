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

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.empiria.player.client.controller.item.ResponseSocket;
import eu.ydp.gwtutil.client.collections.KeyValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConnectionModuleModel extends AbstractResponseModel<KeyValue<String, String>> {

    private ResponseSocket responseSocket;

    @Inject
    public ConnectionModuleModel(@Assisted Response response, @Assisted ResponseModelChangeListener modelChangeListener) {
        super(response, modelChangeListener);
    }

    @Override
    protected List<KeyValue<String, String>> parseResponse(Collection<String> values) {
        ArrayList<KeyValue<String, String>> parsedList = new ArrayList<KeyValue<String, String>>();
        for (String value : values) {
            String[] responses = value.split(" ");
            if (responses.length == 2) {
                parsedList.add(new KeyValue<String, String>(responses[0], responses[1]));
            }
        }
        return parsedList;
    }

    public Response getResponse() {
        return super.getResponse();
    }

    public int getCurrentChoicePairingsNumber(String identifier) {
        int count = 0;
        for (KeyValue<String, String> answer : getCurrentAnswers()) {
            if (answer.getKey().equals(identifier) || answer.getValue().equals(identifier)) {
                count++;
            }
        }
        return count;
    }

    public boolean checkUserResonseContainsAnswer(String answer) {
        return super.getResponse().values.contains(answer);
    }

    public int getCurrentOverallPairingsNumber() {
        return getCurrentAnswers().size();
    }

    public void setResponseSocket(ResponseSocket responseSocket) {
        this.responseSocket = responseSocket;
    }

    public List<Boolean> evaluateResponse() {
        return responseSocket.evaluateResponse(getResponse());
    }

}
