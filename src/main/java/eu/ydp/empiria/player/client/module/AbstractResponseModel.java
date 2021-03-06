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

package eu.ydp.empiria.player.client.module;

import com.google.common.base.Optional;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.core.flow.Stateful;

import java.util.Collection;
import java.util.List;

public abstract class AbstractResponseModel<T> implements Stateful {

    private Response response;
    private Optional<ResponseModelChangeListener> responseModelChange;

    public AbstractResponseModel(Response response, ResponseModelChangeListener responseModelChange) {
        this.response = response;
        this.setResponseModelChange(responseModelChange);
    }

    public AbstractResponseModel(Response response) {
        this.response = response;
        responseModelChange = Optional.absent();
    }

    protected abstract List<T> parseResponse(Collection<String> values);

    public boolean isCorrectAnswer(String answer) {
        List<String> correctAnswers = response.correctAnswers.getAllAnswers();
        return correctAnswers.contains(answer);
    }

    public boolean isUserAnswer(String answer) {
        List<String> userAnswers = response.values;
        return userAnswers.contains(answer);
    }

    public List<T> getCorrectAnswers() {
        return parseResponse(response.correctAnswers.getAllAnswers());
    }

    public List<T> getCurrentAnswers() {
        return parseResponse(response.values);
    }

    @Override
    public void setState(JSONArray newState) {
        response.reset();

        for (int i = 0; i < newState.size(); i++) {
            String responseValue = newState.get(i).isString().stringValue();
            response.add(responseValue);
        }
    }

    @Override
    public JSONArray getState() {
        JSONArray state = new JSONArray();

        for (String responseValue : response.values) {
            state.set(state.size(), createJSONString(responseValue));
        }

        return state;
    }

    public void reset() {
        response.reset();
    }

    public void clearAnswers() {
        reset();
        onModelChange();
    }

    public void addAnswer(String answerIdentifier) {
        response.add(answerIdentifier);
        onModelChange();
    }

    public void removeAnswer(String answerIdentifier) {
        response.remove(answerIdentifier);
        onModelChange();
    }

    protected void onModelChange() {
        if (responseModelChange.isPresent()) {
            responseModelChange.get().onResponseModelChange();
        }
    }

    private JSONString createJSONString(String value) {
        return new JSONString(value);
    }

    public void setResponseModelChange(ResponseModelChangeListener responseModelChange) {
        this.responseModelChange = Optional.of(responseModelChange);
    }

    public Response getResponse() {
        return response;
    }
}
