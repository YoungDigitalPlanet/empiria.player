package eu.ydp.empiria.player.client.controller.variables.processor.item;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.Evaluate;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.CorrectAnswers;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseValue;
import eu.ydp.gwtutil.client.collections.ListCreator;
import eu.ydp.gwtutil.client.util.BrowserNativeInterface;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.junit.mock.UserAgentCheckerNativeInterfaceMock;
import eu.ydp.gwtutil.xml.XMLParser;

public class DefaultVariableProcessorJUnitTest {

	private DefaultVariableProcessor variableProcessor;
	private Response response;

	@Before
	public void init() {
		BrowserNativeInterface nativeInterface = UserAgentCheckerNativeInterfaceMock.getNativeInterfaceMock(UserAgentCheckerNativeInterfaceMock.FIREFOX_MOBILE_UA);
		UserAgentChecker.setNativeInterface(nativeInterface);
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
	public void processSingleResponseCardinalitySingleResponseCorrect() {
		CorrectAnswers correctAnswers = mockCorrectAnswers();
		Vector<String> userAnswers = new Vector<String>();
		userAnswers.add("RESPONSE_1_0");
		ArrayList<Boolean> answersEvaluation = new ArrayList<Boolean>();

		DefaultVariableProcessorMock dvp = mockDefaultVariableProcessor();
		boolean passed = dvp.invokeProcessSingleResponseCardinalitySingle(correctAnswers, userAnswers, answersEvaluation);
		assertThat(passed, is(true));
		assertThat(answersEvaluation.get(0), is(true));
	}

	@Test
	public void processSingleResponseCardinalitySingleResponseWrong() {
		CorrectAnswers correctAnswers = mockCorrectAnswers();
		Vector<String> userAnswers = new Vector<String>();
		userAnswers.add("RESPONSE_1_4");
		ArrayList<Boolean> answersEvaluation = new ArrayList<Boolean>();

		DefaultVariableProcessorMock dvp = mockDefaultVariableProcessor();
		boolean passed = dvp.invokeProcessSingleResponseCardinalitySingle(correctAnswers, userAnswers, answersEvaluation);
		assertThat(passed, is(false));
		assertThat(answersEvaluation.get(0), is(false));
	}

	@Test
	public void processSingleResponseCardinalitySingleUserAnswersInvalidSize() {
		CorrectAnswers correctAnswers = mockCorrectAnswers();
		Vector<String> userAnswers = new Vector<String>();
		ArrayList<Boolean> answersEvaluation = new ArrayList<Boolean>();

		DefaultVariableProcessorMock dvp = mockDefaultVariableProcessor();
		boolean passed = dvp.invokeProcessSingleResponseCardinalitySingle(correctAnswers, userAnswers, answersEvaluation);
		assertThat(passed, is(false));
		assertThat(answersEvaluation.get(0), is(false));
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
		response.correctAnswers = mockCorrectAnswers();

		DefaultVariableProcessorMock dvp = mockDefaultVariableProcessor();
		int mistakesCounter = dvp.invokeProcessCheckMistakes(response, outcome);
		assertThat(mistakesCounter, is(0));
	}

	@Test
	public void processCheckMistakesCardinalitySingleAnswerInvalidFormat() {
		Outcome outcome = mockOutcome();
		outcome.values.add("RESPONSE_1_4");
		Response response = mock(Response.class);
		response.cardinality = Cardinality.SINGLE;
		response.correctAnswers = mockCorrectAnswers();

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
		response.correctAnswers = mockCorrectAnswers();

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
		response.correctAnswers = mockCorrectAnswers();

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
		response.correctAnswers = mockCorrectAnswers();

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
		response.correctAnswers = mockCorrectAnswers();

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
		response.correctAnswers = mockCorrectAnswers();

		DefaultVariableProcessorMock dvp = mockDefaultVariableProcessor();
		int mistakesCounter = dvp.invokeProcessCheckMistakes(response, outcome);
		assertThat(mistakesCounter, is(1));
	}

	@Test
	public void processCheckMistakesCardinalityOrderedAnswerInvalidFormat() {
		Outcome outcome = mockOutcome();
		outcome.values.add("RESPONSE_1_0");
		Response response = mock(Response.class);
		response.cardinality = Cardinality.ORDERED;
		response.correctAnswers = mockCorrectAnswers();

		DefaultVariableProcessorMock dvp = mockDefaultVariableProcessor();
		int mistakesCounter = dvp.invokeProcessCheckMistakes(response, outcome);
		assertThat(mistakesCounter, is(0));
	}

	@Test
	public void processSingleResponseWrongUserAnswersSize() {
		Response response = mock(Response.class);
		response.cardinality = Cardinality.ORDERED;
		response.correctAnswers = mockCorrectAnswers();
		response.values = new Vector<String>();
		response.values.add("RESPONSE_1_0");

		DefaultVariableProcessorMock dvp = mockDefaultVariableProcessor();
		boolean passed = dvp.invokeProcessSingleResponse(response);
		assertThat(passed, is(false));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void processSingleResponseUnsupportedOperationException() {
		Response response = mock(Response.class);
		response.cardinality = Cardinality.ORDERED;
		response.evaluate = Evaluate.CORRECT;

		DefaultVariableProcessorMock dvp = mockDefaultVariableProcessor();
		dvp.invokeProcessSingleResponse(response);
	}

	@Test
	public void processSingleResponseAnswerCorrect() {
		Response response = mock(Response.class);
		response.cardinality = Cardinality.ORDERED;
		response.correctAnswers = mockCorrectAnswers();
		response.groups = new TreeMap<String, List<Integer>>();
		response.values = new Vector<String>();
		response.values.add("RESPONSE_1_0");
		response.values.add("RESPONSE_1_1");

		DefaultVariableProcessorMock dvp = mockDefaultVariableProcessor();
		boolean passed = dvp.invokeProcessSingleResponse(response);
		assertThat(passed, is(true));
	}

	@Test
	public void processSingleResponseAnswerWrong() {
		Response response = mock(Response.class);
		response.cardinality = Cardinality.ORDERED;
		response.correctAnswers = mockCorrectAnswers();
		response.groups = new TreeMap<String, List<Integer>>();
		response.values = new Vector<String>();
		response.values.add("RESPONSE_1_4");
		response.values.add("RESPONSE_1_5");

		DefaultVariableProcessorMock dvp = mockDefaultVariableProcessor();
		boolean passed = dvp.invokeProcessSingleResponse(response);
		assertThat(passed, is(false));
	}
	
	@Test
	public void shouldUpdateLastMistakeInCommutativeGroup() {
		Map<String, List<Boolean>> groupsAnswersUsed = new TreeMap<String, List<Boolean>>();
		List<Boolean> answers = Lists.newArrayList(false, false, true);
		String groupId = "group1";
		groupsAnswersUsed.put(groupId, answers);
		
		Outcome outcome = new Outcome();
		outcome.values.add("");
		
		DefaultVariableProcessorMock dvp = mockDefaultVariableProcessor();
		dvp.mockGroupsAnswersUsed(groupsAnswersUsed);
		
		answers = Lists.newArrayList(true, false, true);
		groupsAnswersUsed.put(groupId, answers);
		dvp.invokeUpdateLastMistakeInGroup(groupId, outcome);
		assertThat(outcome.values.get(0), is("0"));
		
		dvp.invokeUpdateGroupsPoints();
		answers = Lists.newArrayList(false, false, true);
		groupsAnswersUsed.put(groupId, answers);
		dvp.invokeUpdateLastMistakeInGroup(groupId, outcome);
		assertThat(outcome.values.get(0), is("1"));
	}

	private CorrectAnswers mockCorrectAnswers() {
		CorrectAnswers correctAnswers = new CorrectAnswers();
		correctAnswers.add(new ResponseValue("RESPONSE_1_0"));
		correctAnswers.add(new ResponseValue("RESPONSE_1_1"));
		return correctAnswers;
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
		
		public boolean invokeProcessSingleResponse(Response response) {
			return processSingleResponse(response);
		}

		public boolean invokeProcessSingleResponseCardinalitySingle(CorrectAnswers correctAnswers, Vector<String> userAnswers, ArrayList<Boolean> answersEvaluation) {
			return processSingleResponseCardinalitySingle(correctAnswers, userAnswers, answersEvaluation);
		}
		
		public void mockGroupsAnswersUsed(Map<String, List<Boolean>> groupsAnswersUsed) {
			this.groupsAnswersUsed = groupsAnswersUsed;
		}
		
		public void invokeUpdateGroupsPoints() {
			updateGroupsPoints();
		}
		
		public void invokeUpdateLastMistakeInGroup(String group, Outcome outcome) {
			updateLastMistakeInGroup(group, outcome);
		}
	}
}
