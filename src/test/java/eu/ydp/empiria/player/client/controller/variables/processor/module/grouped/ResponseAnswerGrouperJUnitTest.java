package eu.ydp.empiria.player.client.controller.variables.processor.module.grouped;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ResponseAnswerGrouperJUnitTest {

    private ResponseAnswerGrouper responseAnswerGrouper;
    private List<GroupedAnswer> groupedAnswers;
    private int responseIdCounter;

    @Before
    public void setUp() throws Exception {
        responseIdCounter = 0;
        groupedAnswers = Lists.newArrayList();
        responseAnswerGrouper = new ResponseAnswerGrouper(groupedAnswers);
    }

    @Test
    public void shouldRecognizeThatAnswerIsCorrectAndNotUsedByOtherResponse() throws Exception {
        Response queryingResponse = builder().build();

        addToGrouper(newGroupedAnswer("correctAnswer1"));
        addToGrouper(newGroupedAnswer("correctAnswer2"));

        boolean answerCorrect = responseAnswerGrouper.isAnswerCorrect("correctAnswer2", queryingResponse);

        assertThat(answerCorrect, equalTo(true));
    }

    @Test
    public void shouldRecognizeThatAnswerIsCorrectBevauseIsNotUsedAnymoreByOtherResponse() throws Exception {
        Response queryingResponse = builder().build();
        Response usingResponse = builder().build();

        addToGrouper(newGroupedAnswer("correctAnswer1", usingResponse));
        addToGrouper(newGroupedAnswer("correctAnswer2", usingResponse));

        boolean answerCorrect = responseAnswerGrouper.isAnswerCorrect("correctAnswer2", queryingResponse);

        assertThat(answerCorrect, equalTo(true));
    }

    @Test
    public void shouldRecognizeThatAnswerIsCorrectBecauseIsAlreadyUsingThisAnswer() throws Exception {
        Response queryingResponse = builder().build();
        Response usingResponse = builder().build();

        addToGrouper(newGroupedAnswer("correctAnswer1", usingResponse));
        addToGrouper(newGroupedAnswer("correctAnswer2", queryingResponse));

        boolean answerCorrect = responseAnswerGrouper.isAnswerCorrect("correctAnswer2", queryingResponse);

        assertThat(answerCorrect, equalTo(true));
    }

    @Test
    public void shouldRecognizeThatAnswerIsFalseBecauseIsUsedByOtherResponse() throws Exception {
        Response queryingResponse = builder().build();
        Response usingResponse = builder().withCurrentUserAnswers("correctAnswer2").build();

        addToGrouper(newGroupedAnswer("correctAnswer2", usingResponse));

        boolean answerCorrect = responseAnswerGrouper.isAnswerCorrect("correctAnswer2", queryingResponse);

        assertThat(answerCorrect, equalTo(false));
    }

    @Test
    public void shouldRecognizeThatAnswerIsFalseBecauseIsNotCorrectForAnyResponse() throws Exception {
        Response queryingResponse = builder().build();

        addToGrouper(newGroupedAnswer("correctAnswer2"));

        boolean answerCorrect = responseAnswerGrouper.isAnswerCorrect("WRONG", queryingResponse);

        assertThat(answerCorrect, equalTo(false));
    }

    private void addToGrouper(GroupedAnswer newGroupedAnswer) {
        groupedAnswers.add(newGroupedAnswer);
    }

    private GroupedAnswer newGroupedAnswer(String value) {
        return new GroupedAnswer(value);
    }

    private GroupedAnswer newGroupedAnswer(String value, Response usingResponse) {
        return new GroupedAnswer(value, true, usingResponse);
    }

    private ResponseBuilder builder() {
        responseIdCounter++;
        return new ResponseBuilder().withIdentifier("id" + responseIdCounter);
    }
}
