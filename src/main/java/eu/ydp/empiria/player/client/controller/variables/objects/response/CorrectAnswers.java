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

package eu.ydp.empiria.player.client.controller.variables.objects.response;

import java.util.ArrayList;
import java.util.List;

public class CorrectAnswers {

    private List<ResponseValue> values;

    public CorrectAnswers() {
        values = new ArrayList<ResponseValue>();
    }

    public void add(ResponseValue rv) {
        values.add(rv);
    }

    public void add(String answer, int forIndex) {
        if (values.size() > forIndex)
            values.get(forIndex).getAnswers().add(answer);
    }

    public int getAnswersCount() {
        return values.size();
    }

    public boolean answersExists() {
        return values.size() > 0;
    }

    public boolean containsAnswer(String test) {
        for (ResponseValue rv : values) {
            if (rv.getAnswers().contains(test))
                return true;
        }
        return false;
    }

    public ResponseValue getResponseValue(int index) {
        return values.get(index);
    }

    public String getSingleAnswer() {
        ResponseValue response = getResponseValue(0);
        String answer;
        if (response.answersExists()) {
            answer = response.getSingleAnswer();
        } else {
            answer = "";
        }
        return answer;
    }

    public List<String> getAllAnswers() {
        List<String> answers = new ArrayList<String>();

        for (ResponseValue responseValue : values) {
            answers.addAll(responseValue.getAnswers());
        }

        return answers;
    }

    public List<ResponseValue> getAllResponsValues() {
        return this.values;
    }

}
