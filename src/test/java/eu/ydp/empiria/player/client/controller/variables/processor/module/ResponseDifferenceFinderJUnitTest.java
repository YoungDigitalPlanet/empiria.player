package eu.ydp.empiria.player.client.controller.variables.processor.module;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ResponseDifferenceFinderJUnitTest {

    private ResponseDifferenceFinder responseDifferenceFinder = new ResponseDifferenceFinder();

    @Test
    public void shouldRecognizeNewAnswerWasAdded() throws Exception {

        List<String> previousAnswers = Lists.newArrayList();
        List<String> currentAnswers = Lists.newArrayList("newAnswer");

        LastAnswersChanges answersChanges = responseDifferenceFinder.findChangesOfAnswers(previousAnswers, currentAnswers);

        assertThat(answersChanges.getAddedAnswers(), hasItem("newAnswer"));
        assertThat(answersChanges.getRemovedAnswers(), is(empty()));
    }

    @Test
    public void shouldRecognizeAnswerWasRemoved() throws Exception {

        List<String> previousAnswers = Lists.newArrayList("answerToRemove");
        List<String> currentAnswers = Lists.newArrayList();

        LastAnswersChanges answersChanges = responseDifferenceFinder.findChangesOfAnswers(previousAnswers, currentAnswers);

        assertThat(answersChanges.getAddedAnswers(), is(empty()));
        assertThat(answersChanges.getRemovedAnswers(), hasItem("answerToRemove"));
    }

    @Test
    public void shouldNotFindAnyChangesWhenAnswersAreSame() throws Exception {

        List<String> previousAnswers = Lists.newArrayList("answer");
        List<String> currentAnswers = Lists.newArrayList(previousAnswers);

        LastAnswersChanges answersChanges = responseDifferenceFinder.findChangesOfAnswers(previousAnswers, currentAnswers);

        assertThat(answersChanges.getAddedAnswers(), is(empty()));
        assertThat(answersChanges.getRemovedAnswers(), is(empty()));
    }

    @Test
    public void shouldIgnoreEmptyAnswers() throws Exception {

        List<String> previousAnswers = Lists.newArrayList("");
        List<String> currentAnswers = Lists.newArrayList("");

        LastAnswersChanges answersChanges = responseDifferenceFinder.findChangesOfAnswers(previousAnswers, currentAnswers);

        assertThat(answersChanges.getAddedAnswers(), is(empty()));
        assertThat(answersChanges.getRemovedAnswers(), is(empty()));
    }

}
