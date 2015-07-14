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
