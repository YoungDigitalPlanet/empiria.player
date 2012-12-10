package eu.ydp.empiria.player.client.controller.feedback;

import static eu.ydp.empiria.player.client.controller.feedback.FeedbackPropertyName.ALL_OK;
import static eu.ydp.empiria.player.client.controller.feedback.FeedbackPropertyName.DONE;
import static eu.ydp.empiria.player.client.controller.feedback.FeedbackPropertyName.ERRORS;
import static eu.ydp.empiria.player.client.controller.feedback.FeedbackPropertyName.OK;
import static eu.ydp.empiria.player.client.controller.feedback.FeedbackPropertyName.RESULT;
import static eu.ydp.empiria.player.client.controller.feedback.FeedbackPropertyName.TODO;
import static eu.ydp.empiria.player.client.controller.feedback.FeedbackPropertyName.WRONG;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.module.IUniqueModule;

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
		OutcomeListBuilder builder = OutcomeListBuilder.init().
										put(creator.createLastMistakenOutcome(0));
		FeedbackProperties properties = getProperties(builder);

		assertThat("not null", properties, is(notNullValue()));
		assertThat("wrong", properties.getBooleanProperty(WRONG), is(equalTo(false)));
		assertThat("ok", properties.getBooleanProperty(OK), is(equalTo(true)));
	}
	
	@Test
	public void shouldApplyWrongProperty() {
		OutcomeListBuilder builder = OutcomeListBuilder.init().
										put(creator.createLastMistakenOutcome(1));
		FeedbackProperties properties = getProperties(builder);

		assertThat("not null", properties, is(notNullValue()));
		assertThat("wrong", properties.getBooleanProperty(WRONG), is(equalTo(true)));
		assertThat("ok", properties.getBooleanProperty(OK), is(equalTo(false)));
	}

	@Test
	public void shouldApplyModuleResultWhen_thereIsError() {
		OutcomeListBuilder builder = OutcomeListBuilder.init().
											put(creator.createDoneOutcome(1)).
											put(creator.createErrorsOutcome(1)).
											put(creator.createTodoOutcome(2));
		FeedbackProperties properties = getProperties(builder);

		assertThat("not null", properties, is(notNullValue()));
		assertThat("allOk", properties.getBooleanProperty(ALL_OK), is(equalTo(false))); 
		assertThat("done", properties.getIntegerProperty(DONE), is(equalTo(1))); 
		assertThat("todo", properties.getIntegerProperty(TODO), is(equalTo(2))); 
		assertThat("errors", properties.getIntegerProperty(ERRORS), is(equalTo(1)));
		assertThat("result", properties.getDoubleProperty(RESULT), is(equalTo(50.0)));
	}
	
	@Test
	public void shouldApplyModuleResultWhen_thereIsNoError() {
		OutcomeListBuilder builder = OutcomeListBuilder.init().
											put(creator.createDoneOutcome(1)).
											put(creator.createErrorsOutcome(0)).
											put(creator.createTodoOutcome(2));
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
		OutcomeListBuilder builder = OutcomeListBuilder.init().
											put(creator.createDoneOutcome(2)).
											put(creator.createErrorsOutcome(0)).
											put(creator.createTodoOutcome(2));
		FeedbackProperties properties = getProperties(builder);

		assertThat("not null", properties, is(notNullValue()));
		assertThat("allOk", properties.getBooleanProperty(ALL_OK), is(equalTo(true))); 
		assertThat("done", properties.getIntegerProperty(DONE), is(equalTo(2))); 
		assertThat("todo", properties.getIntegerProperty(TODO), is(equalTo(2))); 
		assertThat("errors", properties.getIntegerProperty(ERRORS), is(equalTo(0)));
		assertThat("result", properties.getDoubleProperty(RESULT), is(equalTo(100.0)));		 
	}
	
	private FeedbackProperties getProperties(OutcomeListBuilder builder){
		IUniqueModule module = mock(IUniqueModule.class);
		when(module.getIdentifier()).thenReturn(moduleId);
		
		Map<String, Outcome> outcomes = builder.getMap();
		FeedbackPropertiesCreator converter = new FeedbackPropertiesCreator();
		return converter.getPropertiesFromVariables(module.getIdentifier(), outcomes);
	}

}
