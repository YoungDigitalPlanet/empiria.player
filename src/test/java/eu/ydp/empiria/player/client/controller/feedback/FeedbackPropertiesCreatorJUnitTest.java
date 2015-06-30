package eu.ydp.empiria.player.client.controller.feedback;

import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import eu.ydp.empiria.player.client.module.IUniqueModule;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static eu.ydp.empiria.player.client.controller.feedback.FeedbackPropertyName.*;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken.CORRECT;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken.NONE;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FeedbackPropertiesCreatorJUnitTest {

    private OutcomeCreator creator;

    private String moduleId;

    @Before
    public void initialize() {
        moduleId = "CHOICE_1";
        creator = new OutcomeCreator(moduleId);
    }

    @Test
    public void shouldApplyOkProperty() {
        // given
        OutcomeListBuilder builder = OutcomeListBuilder.init().put(creator.createLastMistakenOutcome(CORRECT));

        // when
        FeedbackProperties properties = getProperties(builder);

        // then
        assertThat("not null", properties, is(notNullValue()));
        assertThat("wrong", properties.getBooleanProperty(WRONG), is(equalTo(false)));
        assertThat("ok", properties.getBooleanProperty(OK), is(equalTo(true)));
    }

    @Test
    public void shouldApplyNoProperty() {
        // given
        OutcomeListBuilder builder = OutcomeListBuilder.init().put(creator.createLastMistakenOutcome(NONE));

        // when
        FeedbackProperties properties = getProperties(builder);

        // then
        assertThat("not null", properties, is(notNullValue()));
        assertThat("wrong", properties.getBooleanProperty(WRONG), is(equalTo(false)));
        assertThat("ok", properties.getBooleanProperty(OK), is(equalTo(false)));
    }

    @Test
    public void shouldApplyWrongProperty() {
        // given
        OutcomeListBuilder builder = OutcomeListBuilder.init().put(creator.createLastMistakenOutcome(LastMistaken.WRONG));

        // when
        FeedbackProperties properties = getProperties(builder);

        // then
        assertThat("not null", properties, is(notNullValue()));
        assertThat("wrong", properties.getBooleanProperty(WRONG), is(equalTo(true)));
        assertThat("ok", properties.getBooleanProperty(OK), is(equalTo(false)));
    }

    @Test
    public void shouldApplyModuleResultWhen_thereIsError() {
        OutcomeListBuilder builder = OutcomeListBuilder.init().put(creator.createDoneOutcome(1)).put(creator.createErrorsOutcome(1))
                .put(creator.createTodoOutcome(2));
        FeedbackProperties properties = getProperties(builder);

        assertThat("not null", properties, is(notNullValue()));
        assertThat("allOk", properties.getBooleanProperty(ALL_OK), is(equalTo(false)));
        assertThat("done", properties.getIntegerProperty(DONE), is(equalTo(1)));
        assertThat("todo", properties.getIntegerProperty(TODO), is(equalTo(2)));
        assertThat("errors", properties.getIntegerProperty(ERRORS), is(equalTo(1)));
        assertThat("result", properties.getDoubleProperty(RESULT), is(equalTo(50.0)));
    }

    @Test
    public void shouldApplyModuleResultWhen_thereIsZero() {
        OutcomeListBuilder builder = OutcomeListBuilder.init().put(creator.createDoneOutcome(0)).put(creator.createErrorsOutcome(0))
                .put(creator.createTodoOutcome(0));
        FeedbackProperties properties = getProperties(builder);

        assertThat("not null", properties, is(notNullValue()));
        assertThat("allOk", properties.getBooleanProperty(ALL_OK), is(equalTo(true)));
    }

    @Test
    public void shouldRecognizeWhenAnswerIsSelected() {
        OutcomeListBuilder builder = OutcomeListBuilder.init().put(creator.createDoneOutcome(1)).put(creator.createErrorsOutcome(1))
                .put(creator.createTodoOutcome(2)).put(creator.createLastChangeOutcome("+ SelectNewAnswerOutcome"));
        FeedbackProperties properties = getProperties(builder);

        assertThat("SELECTED", properties.getBooleanProperty(FeedbackPropertyName.SELECTED), is(equalTo(true)));
        assertThat("UNSELECT", properties.getBooleanProperty(FeedbackPropertyName.UNSELECT), is(equalTo(false)));
    }

    @Test
    public void shouldRecognizeWhenAnswerIsUnselected() {
        OutcomeListBuilder builder = OutcomeListBuilder.init().put(creator.createDoneOutcome(1)).put(creator.createErrorsOutcome(1))
                .put(creator.createTodoOutcome(2)).put(creator.createLastChangeOutcome("-SelectNewAnswerOutcome"));
        FeedbackProperties properties = getProperties(builder);

        assertThat("SELECTED", properties.getBooleanProperty(FeedbackPropertyName.SELECTED), is(equalTo(false)));
        assertThat("UNSELECT", properties.getBooleanProperty(FeedbackPropertyName.UNSELECT), is(equalTo(true)));
    }

    @Test
    public void shouldApplyModuleResultWhen_thereIsNoError() {
        OutcomeListBuilder builder = OutcomeListBuilder.init().put(creator.createDoneOutcome(1)).put(creator.createErrorsOutcome(0))
                .put(creator.createTodoOutcome(2));
        FeedbackProperties properties = getProperties(builder);

        assertThat("not null", properties, is(notNullValue()));
        assertThat("allOk", properties.getBooleanProperty(ALL_OK), is(equalTo(false)));
        assertThat("done", properties.getIntegerProperty(DONE), is(equalTo(1)));
        assertThat("todo", properties.getIntegerProperty(TODO), is(equalTo(2)));
        assertThat("errors", properties.getIntegerProperty(ERRORS), is(equalTo(0)));
        assertThat("result", properties.getDoubleProperty(RESULT), is(equalTo(50.0)));
    }

    @Test
    public void shouldApplyModuleResultWhen_allAllDoneNoError() {
        OutcomeListBuilder builder = OutcomeListBuilder.init().put(creator.createDoneOutcome(2)).put(creator.createErrorsOutcome(0))
                .put(creator.createTodoOutcome(2));
        FeedbackProperties properties = getProperties(builder);

        assertThat("not null", properties, is(notNullValue()));
        assertThat("allOk", properties.getBooleanProperty(ALL_OK), is(equalTo(true)));
        assertThat("done", properties.getIntegerProperty(DONE), is(equalTo(2)));
        assertThat("todo", properties.getIntegerProperty(TODO), is(equalTo(2)));
        assertThat("errors", properties.getIntegerProperty(ERRORS), is(equalTo(0)));
        assertThat("result", properties.getDoubleProperty(RESULT), is(equalTo(100.0)));
    }

    private FeedbackProperties getProperties(OutcomeListBuilder builder) {
        IUniqueModule module = mock(IUniqueModule.class);
        when(module.getIdentifier()).thenReturn(moduleId);
        FeedbackPropertiesCreator creator = new FeedbackPropertiesCreator();
        Map<String, Outcome> outcomes = builder.getMap();
        return creator.getPropertiesFromVariables(module.getIdentifier(), outcomes);
    }

}
