package eu.ydp.empiria.player.client.controller.variables.processor.module.grouped;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.thirdparty.guava.common.collect.Lists;
import com.google.gwt.thirdparty.guava.common.collect.Maps;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;

@RunWith(MockitoJUnitRunner.class)
public class GroupedAnswersManagerJUnitTest {

	private GroupedAnswersManager groupedAnswersManager;

	private Map<String, ResponseAnswerGrouper> responseAnswerGrouperMap;

	@Mock
	private GroupedResponseAnswersMapBuilder responseAnswersMapBuilder;
	@Mock
	private Map<String, Response> responses;

	@Before
	public void setUp() throws Exception {
		responseAnswerGrouperMap = Maps.newHashMap();
		groupedAnswersManager = new GroupedAnswersManager(responseAnswersMapBuilder);
	}

	@Test
	public void shouldRecognizeAnswerIsCorrectInAnyOfGroups() throws Exception {
		String answer = "answer";
		Response response = new ResponseBuilder().build();
		List<String> groups = Lists.newArrayList("group1", "group2");

		createMockedNotFittingGrouper(answer, response, "group1");
		createMockedFittingGrouper(answer, response, "group2");
		initializeGroupedAnswersManager();

		boolean answerCorrectInAnyOfGroups = groupedAnswersManager.isAnswerCorrectInAnyOfGroups(answer, response, groups);

		assertThat(answerCorrectInAnyOfGroups, equalTo(true));
	}

	@Test
	public void shouldRecognizeAnswerIsNotCorrect() throws Exception {
		String answer = "answer";
		Response response = new ResponseBuilder().build();
		List<String> groups = Lists.newArrayList("group1", "group2");

		createMockedNotFittingGrouper(answer, response, "group1");
		createMockedNotFittingGrouper(answer, response, "group2");
		initializeGroupedAnswersManager();

		boolean answerCorrectInAnyOfGroups = groupedAnswersManager.isAnswerCorrectInAnyOfGroups(answer, response, groups);

		assertThat(answerCorrectInAnyOfGroups, equalTo(false));
	}

	private void createMockedFittingGrouper(String answer, Response response, String groupName) {
		createMockedGrouper(answer, response, groupName, true);
	}

	private void createMockedNotFittingGrouper(String answer, Response response, String groupName) {
		createMockedGrouper(answer, response, groupName, false);
	}

	private void createMockedGrouper(String answer, Response response, String groupName, boolean fittingToAnswer) {
		ResponseAnswerGrouper mockedResponseAnswerGrouper = Mockito.mock(ResponseAnswerGrouper.class);

		when(mockedResponseAnswerGrouper.isAnswerCorrect(answer, response)).thenReturn(fittingToAnswer);

		responseAnswerGrouperMap.put(groupName, mockedResponseAnswerGrouper);
	}

	private void initializeGroupedAnswersManager() {
		when(responseAnswersMapBuilder.createResponseAnswerGroupersMap()).thenReturn(responseAnswerGrouperMap);

		groupedAnswersManager.initialize(responses);
	}
}
