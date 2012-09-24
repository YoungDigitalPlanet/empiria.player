package eu.ydp.empiria.player.client.controller.variables.processor.item;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.gwtutil.client.collections.ListCreator;

public class DefaultVariableProcessorJUnitTest {

	private DefaultVariableProcessor variableProcessor;
	private Response response;
	
	@Before
	public void init() {
		variableProcessor = new DefaultVariableProcessor();
		response = mock(Response.class);
	}
	
	@Test
	public void findSingleResponseErrors() {
		verifyErrorsCount(0, 0, Cardinality.SINGLE, ListCreator.create(new Vector<String>()).add("").build());
		verifyErrorsCount(1, 1, Cardinality.SINGLE, ListCreator.create(new Vector<String>()).add("x").build());
		verifyErrorsCount(0, 0, Cardinality.MULTIPLE, ListCreator.create(new Vector<String>()).build());
		verifyErrorsCount(1, 1, Cardinality.MULTIPLE, ListCreator.create(new Vector<String>()).add("x").add("y").build());
		verifyErrorsCount(0, 0, Cardinality.COMMUTATIVE, ListCreator.create(new Vector<String>()).build());
		verifyErrorsCount(1, 1, Cardinality.COMMUTATIVE, ListCreator.create(new Vector<String>()).add("x").add("y").build());
		verifyErrorsCount(0, 0, Cardinality.ORDERED, ListCreator.create(new Vector<String>()).build());
		verifyErrorsCount(0, 1, Cardinality.ORDERED, ListCreator.create(new Vector<String>()).add("x").add("y").build());
	}
	
	private void verifyErrorsCount(int expectedErrorsCountNoInteraction, int expectedErrorsCountWithInteraction, Cardinality card, List<String> values){
		response.values = (Vector<String>)values; // NOPMD
		response.cardinality = card;
		assertThat( variableProcessor.findSingleResponseErrors(response, false, false), equalTo( expectedErrorsCountNoInteraction ));
		assertThat( variableProcessor.findSingleResponseErrors(response, false, true), equalTo( expectedErrorsCountWithInteraction ));
		assertThat( variableProcessor.findSingleResponseErrors(response, true, false), equalTo(0));
		assertThat( variableProcessor.findSingleResponseErrors(response, true, true), equalTo(0));
	}	
	
	@Test
	public void countTodoExpectsCountsWhenCorrectResponseDefined() {
		Map<String, Response> responses = new TreeMap<String, Response>();
		responses.put("ID_RESPONSE_10", mockResponse(1));
		responses.put("ID_RESPONSE_11", mockResponse(0));
		Map<String, Outcome> outcomes = new TreeMap<String, Outcome>();
		outcomes.put(DefaultVariableProcessor.TODO, mockOutcome());
		outcomes.put("ID_RESPONSE_10-" + DefaultVariableProcessor.TODO, mockOutcome());
		outcomes.put("ID_RESPONSE_11-" + DefaultVariableProcessor.TODO, mockOutcome());

		variableProcessor.processResponseVariables(responses, outcomes, false);

		assertThat(outcomes.get(DefaultVariableProcessor.TODO).getValuesShort(), equalTo("1"));
	}
	
	private Outcome mockOutcome() {
		Outcome mockedOutcome = new Outcome();
		mockedOutcome.values = new Vector<String>();
		return mockedOutcome;
	}
	
	private Response mockResponse(int responseValuesCount) {
		Response mockedResponse = mock(Response.class);
		doCallRealMethod().when(mockedResponse).isModuleAdded();
		doCallRealMethod().when(mockedResponse).setModuleAdded();
		mockedResponse.setModuleAdded();
		mockedResponse.groups = new TreeMap<String, List<Integer>>();
		mockedResponse.correctAnswers = mock(CorrectAnswers.class);		
		when(mockedResponse.correctAnswers.getResponseValuesCount()).thenReturn(responseValuesCount);
		
		return mockedResponse;
	}	
}
