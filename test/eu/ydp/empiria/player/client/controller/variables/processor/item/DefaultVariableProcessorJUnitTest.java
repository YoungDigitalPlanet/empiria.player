package eu.ydp.empiria.player.client.controller.variables.processor.item;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;
import eu.ydp.gwtutil.client.collections.ListCreator;
import eu.ydp.gwtutil.xml.XMLParser;

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
	
	@Test
	public void processCardinalityMultipleEvaluateUser() {
		Response mockResponse = mockMultiplePairResponse(Evaluate.USER);		
		mockResponse.add("CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1");
		mockResponse.add("CONNECTION_RESPONSE_1_2 CONNECTION_RESPONSE_1_1");
		mockResponse.add("CONNECTION_RESPONSE_1_4 CONNECTION_RESPONSE_1_3");
		mockResponse.add("CONNECTION_RESPONSE_1_4 CONNECTION_RESPONSE_1_5");
		mockResponse.add("CONNECTION_RESPONSE_1_6 CONNECTION_RESPONSE_1_3");
		
		variableProcessor.processSingleResponse(mockResponse);
		List<Boolean> result = variableProcessor.evaluateAnswer(mockResponse);
		
		assertThat(result, contains(true, false, false, true, false));		
	}
	
	@Test
	public void processCardinalityMultipleEvaluateCorrect() {
		Response mockResponse = mockMultiplePairResponse(Evaluate.CORRECT);		
		mockResponse.add("CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1");
		mockResponse.add("CONNECTION_RESPONSE_1_2 CONNECTION_RESPONSE_1_1");
		mockResponse.add("CONNECTION_RESPONSE_1_4 CONNECTION_RESPONSE_1_3");
		mockResponse.add("CONNECTION_RESPONSE_1_4 CONNECTION_RESPONSE_1_5");
		mockResponse.add("CONNECTION_RESPONSE_1_6 CONNECTION_RESPONSE_1_3");
		
		variableProcessor.processSingleResponse(mockResponse);
		List<Boolean> result = variableProcessor.evaluateAnswer(mockResponse);
		
		assertThat(result, contains(true, false, true, false));		
	}
	
	@Test
	public void processCheckMistakesCardinalitySingleAnswerCorrect() {
		Outcome outcome = mockOutcome();
		outcome.values.add("+RESPONSE_1_0");
		Response response = mock(Response.class);
		response.cardinality = Cardinality.SINGLE;
		response.correctAnswers = new CorrectAnswers();
		ResponseValue responseValue1 = new ResponseValue("RESPONSE_1_0");
		ResponseValue responseValue2 = new ResponseValue("RESPONSE_1_1");
		response.correctAnswers.add(responseValue1);
		response.correctAnswers.add(responseValue2);
		
		DefaultVariableProcessorMock dvp = mockDefaultVariableProcessor();
		int mistakesCounter = dvp.invokeProcessCheckMistakes(response, outcome);
		assertThat(mistakesCounter, is(0));		
	}
	
	@Test
	public void processCheckMistakesCardinalitySingleAnswerWrong() {
		Outcome outcome = mockOutcome();
		outcome.values.add("+RESPONSE_1_4");
		Response response = mock(Response.class);
		response.cardinality = Cardinality.SINGLE;
		response.correctAnswers = new CorrectAnswers();
		ResponseValue responseValue1 = new ResponseValue("RESPONSE_1_0");
		ResponseValue responseValue2 = new ResponseValue("RESPONSE_1_1");
		response.correctAnswers.add(responseValue1);
		response.correctAnswers.add(responseValue2);
		
		DefaultVariableProcessorMock dvp = mockDefaultVariableProcessor();
		int mistakesCounter = dvp.invokeProcessCheckMistakes(response, outcome);
		assertThat(mistakesCounter, is(1));		
	}
	
	@Test
	public void processCheckMistakesCardinalityMultipleAnswerCorrect() {
		Outcome outcome = mockOutcome();
		outcome.values.add("+RESPONSE_1_0");
		outcome.values.add("+RESPONSE_1_1");
		Response response = mock(Response.class);
		response.cardinality = Cardinality.MULTIPLE;
		response.correctAnswers = new CorrectAnswers();
		ResponseValue responseValue1 = new ResponseValue("RESPONSE_1_0");
		ResponseValue responseValue2 = new ResponseValue("RESPONSE_1_1");
		response.correctAnswers.add(responseValue1);
		response.correctAnswers.add(responseValue2);
		
		DefaultVariableProcessorMock dvp = mockDefaultVariableProcessor();
		int mistakesCounter = dvp.invokeProcessCheckMistakes(response, outcome);
		assertThat(mistakesCounter, is(0));		
	}
	
	@Test
	public void processCheckMistakesCardinalityMultipleAnswerWrong() {
		Outcome outcome = mockOutcome();
		outcome.values.add("+RESPONSE_1_4");
		outcome.values.add("+RESPONSE_1_7");
		Response response = mock(Response.class);
		response.cardinality = Cardinality.MULTIPLE;
		response.correctAnswers = new CorrectAnswers();
		ResponseValue responseValue1 = new ResponseValue("RESPONSE_1_0");
		ResponseValue responseValue2 = new ResponseValue("RESPONSE_1_1");
		response.correctAnswers.add(responseValue1);
		response.correctAnswers.add(responseValue2);
		
		DefaultVariableProcessorMock dvp = mockDefaultVariableProcessor();
		int mistakesCounter = dvp.invokeProcessCheckMistakes(response, outcome);
		assertThat(mistakesCounter, is(2));		
	}
	
	@Test
	public void processCheckMistakesCardinalityOrderedAnswerCorrect() {
		Outcome outcome = mockOutcome();
		outcome.values.add("->RESPONSE_1_0");
		outcome.values.add("->RESPONSE_1_1");
		Response response = mock(Response.class);
		response.cardinality = Cardinality.ORDERED;
		response.correctAnswers = new CorrectAnswers();
		ResponseValue responseValue1 = new ResponseValue("RESPONSE_1_0");
		ResponseValue responseValue2 = new ResponseValue("RESPONSE_1_1");
		response.correctAnswers.add(responseValue1);
		response.correctAnswers.add(responseValue2);
		
		DefaultVariableProcessorMock dvp = mockDefaultVariableProcessor();
		int mistakesCounter = dvp.invokeProcessCheckMistakes(response, outcome);
		assertThat(mistakesCounter, is(0));
	}
	
	@Test
	public void processCheckMistakesCardinalityOrderedAnswerWrong() {
		Outcome outcome = mockOutcome();
		outcome.values.add("->RESPONSE_1_0");
		outcome.values.add("->RESPONSE_1_4");
		Response response = mock(Response.class);
		response.cardinality = Cardinality.ORDERED;
		response.correctAnswers = new CorrectAnswers();
		ResponseValue responseValue1 = new ResponseValue("RESPONSE_1_0");
		ResponseValue responseValue2 = new ResponseValue("RESPONSE_1_1");
		response.correctAnswers.add(responseValue1);
		response.correctAnswers.add(responseValue2);
		
		DefaultVariableProcessorMock dvp = mockDefaultVariableProcessor();
		int mistakesCounter = dvp.invokeProcessCheckMistakes(response, outcome);
		assertThat(mistakesCounter, is(1));
	}
	
	private Response mockMultiplePairResponse(Evaluate evaluateType) {
		String evaluate = Evaluate.USER.equals(evaluateType) ? "evaluate=\"user\" " : "";
		StringBuilder sb = new StringBuilder();
		sb.append("<responseDeclaration baseType=\"directedPair\" cardinality=\"multiple\" " + evaluate + "identifier=\"CONNECTION_RESPONSE_1\">");
		sb.append("		<correctResponse>");
		sb.append("			<value>CONNECTION_RESPONSE_1_0 CONNECTION_RESPONSE_1_1</value>");
		sb.append("			<value>CONNECTION_RESPONSE_1_2 CONNECTION_RESPONSE_1_3</value>");
		sb.append("			<value>CONNECTION_RESPONSE_1_4 CONNECTION_RESPONSE_1_5</value>");
		sb.append("			<value>CONNECTION_RESPONSE_1_6 CONNECTION_RESPONSE_1_7</value>");
		sb.append("		</correctResponse>");
		sb.append("</responseDeclaration>");
		
		return new Response(XMLParser.parse(sb.toString()).getDocumentElement());
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
		
	public DefaultVariableProcessorMock mockDefaultVariableProcessor() {		
		return new DefaultVariableProcessorMock();
	}
	
	private class DefaultVariableProcessorMock extends DefaultVariableProcessor {
		public int invokeProcessCheckMistakes(Response response, Outcome moduleLastChange) {
			return processCheckMistakes(response, moduleLastChange);
		}
	}
}
