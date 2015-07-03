package eu.ydp.empiria.player.client.controller.variables.objects.response;

import java.util.ArrayList;
import java.util.List;

public class ResponseValue {

    private List<String> answers;

    public ResponseValue(String firstValue) {
        answers = new ArrayList<String>();
        answers.add(firstValue);
    }

    public List<String> getAnswers() {
        return answers;
    }

    public boolean answersExists() {
        return answers.size() > 0;
    }

    public String getSingleAnswer() {
        return answers.get(0);
    }
}
