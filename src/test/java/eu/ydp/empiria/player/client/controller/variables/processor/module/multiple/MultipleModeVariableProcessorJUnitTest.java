package eu.ydp.empiria.player.client.controller.variables.processor.module.multiple;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;
import eu.ydp.empiria.player.client.controller.variables.processor.module.counting.CorrectAnswersCounter;
import eu.ydp.empiria.player.client.controller.variables.processor.module.counting.ErrorAnswersCounter;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastAnswersChanges;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MultipleModeVariableProcessorJUnitTest {

    private MultipleModeVariableProcessor multipleModeVariableProcessor;

    @Mock
    private ErrorAnswersCounter errorAnswersCounter;
    @Mock
    private CorrectAnswersCounter correctAnswersCounter;
    @Mock
    private LastGivenAnswersChecker lastGivenAnswersChecker;
    @Mock
    private MultipleModeAnswersEvaluator multipleModeAnswersEvaluator;

    @Before
    public void setUp() throws Exception {
        multipleModeVariableProcessor = new MultipleModeVariableProcessor(errorAnswersCounter, correctAnswersCounter, lastGivenAnswersChecker,
                multipleModeAnswersEvaluator);
    }

    @Test
    public void shouldCountErrors() throws Exception {
        Response response = new ResponseBuilder().build();

        int amountOfErrros = 123;
        when(errorAnswersCounter.countErrorAnswersAdjustedToMode(response)).thenReturn(amountOfErrros);

        int countedErrors = multipleModeVariableProcessor.calculateErrors(response);

        assertThat(countedErrors, equalTo(amountOfErrros));
    }

    @Test
    public void shouldCountDone() throws Exception {
        Response response = new ResponseBuilder().build();

        int amountOfDone = 123;
        when(correctAnswersCounter.countCorrectAnswersAdjustedToCountMode(response)).thenReturn(amountOfDone);

        int countedDone = multipleModeVariableProcessor.calculateDone(response);

        assertThat(countedDone, equalTo(amountOfDone));
    }

    @Test
    public void shouldEvaluateAnswers() throws Exception {
        Response response = new ResponseBuilder().build();

        List<Boolean> expectedEvaluation = Lists.newArrayList(true, false);
        when(multipleModeAnswersEvaluator.evaluateAnswers(response)).thenReturn(expectedEvaluation);

        List<Boolean> evaluateAnswers = multipleModeVariableProcessor.evaluateAnswers(response);

        assertThat(evaluateAnswers, equalTo(expectedEvaluation));
    }

    @Test
    public void shouldCalculateMistakesWhenLastAnswerWasMistake() throws Exception {
        LastMistaken lastmistaken = LastMistaken.WRONG;
        int previousMistakes = 123;

        int newMistakes = multipleModeVariableProcessor.calculateMistakes(lastmistaken, previousMistakes);

        assertThat(newMistakes, equalTo(previousMistakes + 1));
    }

    @Test
    public void shouldCalculateMistakesWhenLastAnswerWasCorrect() throws Exception {
        LastMistaken lastmistaken = LastMistaken.CORRECT;
        int previousMistakes = 123;

        int newMistakes = multipleModeVariableProcessor.calculateMistakes(lastmistaken, previousMistakes);

        assertThat(newMistakes, equalTo(previousMistakes));
    }

    @Test
    public void shouldSetLastmistakenWhenIncorrectAnswerWasAdded() throws Exception {
        LastAnswersChanges answersChanges = createSampleAnswerChanges();
        CorrectAnswers correctAnswers = createCorrectAnswers("correct");

        Response response = new ResponseBuilder().withCorrectAnswers(correctAnswers).build();

        when(lastGivenAnswersChecker.isAnyAsnwerIncorrect(answersChanges.getAddedAnswers(), correctAnswers)).thenReturn(true);

        LastMistaken lastmistaken = multipleModeVariableProcessor.checkLastmistaken(response, answersChanges);

        assertThat(lastmistaken, equalTo(LastMistaken.WRONG));
    }

    @Test
    public void shouldNotSetLastmistakenWhenNoIncorrectAnswerWasAdded() throws Exception {
        LastAnswersChanges answersChanges = createSampleAnswerChanges();
        CorrectAnswers correctAnswers = createCorrectAnswers("correct");

        Response response = new ResponseBuilder().withCorrectAnswers(correctAnswers).build();

        when(lastGivenAnswersChecker.isAnyAsnwerIncorrect(answersChanges.getAddedAnswers(), correctAnswers)).thenReturn(false);

        LastMistaken lastmistaken = multipleModeVariableProcessor.checkLastmistaken(response, answersChanges);

        assertThat(lastmistaken, equalTo(LastMistaken.CORRECT));
    }

    private LastAnswersChanges createSampleAnswerChanges() {
        List<String> addedAnswers = Lists.newArrayList("addedAnswer");
        List<String> removedAnswers = Lists.newArrayList("removedAnswer");
        LastAnswersChanges answersChanges = new LastAnswersChanges(addedAnswers, removedAnswers);
        return answersChanges;
    }

    private CorrectAnswers createCorrectAnswers(String... answers) {
        CorrectAnswers correctAnswers = new CorrectAnswers();
        for (String correctAnswer : answers) {
            ResponseValue responseValue = new ResponseValue(correctAnswer);
            correctAnswers.add(responseValue);
        }
        return correctAnswers;
    }
}
