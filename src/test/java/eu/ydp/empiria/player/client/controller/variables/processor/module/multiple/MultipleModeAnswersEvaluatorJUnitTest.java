package eu.ydp.empiria.player.client.controller.variables.processor.module.multiple;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MultipleModeAnswersEvaluatorJUnitTest {

    private MultipleModeAnswersEvaluator multipleModeAnswersEvaluator;

    @Before
    public void setUp() throws Exception {
        multipleModeAnswersEvaluator = new MultipleModeAnswersEvaluator();
    }

    @Test
    public void shouldEvaluateAnswersInUserMode() throws Exception {
        Response response = builder().withEvaluate(Evaluate.USER).withCorrectAnswers("correct1", "correct2").withCurrentUserAnswers("correct2", "wrong")
                .build();

        List<Boolean> evaluations = multipleModeAnswersEvaluator.evaluateAnswers(response);

        List<Boolean> expectedEvaluations = Lists.newArrayList(true, false);
        assertThat(evaluations, equalTo(expectedEvaluations));
    }

    @Test
    public void shouldEvaluateAnswersInDefaultMode() throws Exception {
        Response response = builder().withEvaluate(Evaluate.DEFAULT).withCorrectAnswers("correct1", "correct2").withCurrentUserAnswers("correct2", "wrong")
                .build();

        List<Boolean> evaluations = multipleModeAnswersEvaluator.evaluateAnswers(response);

        List<Boolean> expectedEvaluations = Lists.newArrayList(true, false);
        assertThat(evaluations, equalTo(expectedEvaluations));
    }

    @Test
    public void shouldEvaluateAnswersInCorrectMode() throws Exception {
        Response response = builder().withEvaluate(Evaluate.CORRECT).withCorrectAnswers("correct1", "correct2").withCurrentUserAnswers("correct2", "wrong")
                .build();

        List<Boolean> evaluations = multipleModeAnswersEvaluator.evaluateAnswers(response);

        List<Boolean> expectedEvaluations = Lists.newArrayList(false, true);
        assertThat(evaluations, equalTo(expectedEvaluations));
    }

    private ResponseBuilder builder() {
        return new ResponseBuilder().withCardinality(Cardinality.MULTIPLE);
    }
}
