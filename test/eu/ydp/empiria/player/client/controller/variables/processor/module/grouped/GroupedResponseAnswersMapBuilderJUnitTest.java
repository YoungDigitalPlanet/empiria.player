package eu.ydp.empiria.player.client.controller.variables.processor.module.grouped;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponsesMapBuilder;
import eu.ydp.empiria.player.client.test.utils.ReflectionsUtils;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class GroupedResponseAnswersMapBuilderJUnitTest {

	private GroupedResponseAnswersMapBuilder responseAnswersMapBuilder;
	private ResponsesMapBuilder responsesMapBuilder = new ResponsesMapBuilder();
	private ReflectionsUtils reflectionsUtils = new ReflectionsUtils();

	@Before
	public void setUp() throws Exception {
		responseAnswersMapBuilder = new GroupedResponseAnswersMapBuilder();
	}

	@Test
	public void shouldGroupResponsesWithSameGroup() throws Exception {
		Response firstResponseWithGroup1 = new ResponseBuilder().withIdentifier("id1")
				.withCorrectAnswers("correct1")
				.withGroups("group1")
				.build();
		Response secondResponseWithGroup1 = new ResponseBuilder().withIdentifier("id2")
				.withCorrectAnswers("correct2")
				.withGroups("group1")
				.build();

		Response firstResponseWithGroup2 = new ResponseBuilder().withIdentifier("id3")
				.withCorrectAnswers("correct1")
				.withGroups("group2")
				.build();
		Response secondResponseWithGroup2 = new ResponseBuilder().withIdentifier("id4")
				.withCorrectAnswers("correct2")
				.withGroups("group2")
				.build();

		Map<String, Response> responses = responsesMapBuilder.buildResponsesMap(firstResponseWithGroup1, secondResponseWithGroup1, firstResponseWithGroup2,
				secondResponseWithGroup2);
		responseAnswersMapBuilder.initialize(responses);

		Map<String, ResponseAnswerGrouper> groupToResponseGrouperMap = responseAnswersMapBuilder.createResponseAnswerGroupersMap();

		assertResponsesGroupedTogether(groupToResponseGrouperMap, firstResponseWithGroup1, secondResponseWithGroup1);
		assertResponsesGroupedTogether(groupToResponseGrouperMap, firstResponseWithGroup2, secondResponseWithGroup2);
	}

	private void assertResponsesGroupedTogether(Map<String, ResponseAnswerGrouper> groupToResponseGrouperMap, Response... responses) {
		List<String> answersCorrectInGroup = Lists.newArrayList();

		for (Response response : responses) {
			answersCorrectInGroup.addAll(response.correctAnswers.getAllAnswers());
		}

		String groupName = responses[0].groups.get(0);
		ResponseAnswerGrouper responseAnswerGrouper = groupToResponseGrouperMap.get(groupName);
		verifyThatGrouperContainsGivenAnswers(answersCorrectInGroup, responseAnswerGrouper);
	}

	@SuppressWarnings("unchecked")
	private void verifyThatGrouperContainsGivenAnswers(List<String> answersCorrectInGroup, ResponseAnswerGrouper responseAnswerGrouper) {
		try {
			List<GroupedAnswer> groupedAnswers = (List<GroupedAnswer>) reflectionsUtils.getValueFromFiledInObject("groupedAnswers", responseAnswerGrouper);
			verifyGroupedAnswersContainsValues(groupedAnswers, answersCorrectInGroup);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void verifyGroupedAnswersContainsValues(List<GroupedAnswer> groupedAnswers, List<String> answersCorrectInGroup) {
		assertThat(groupedAnswers.size(), equalTo(answersCorrectInGroup.size()));

		for (GroupedAnswer groupedAnswer : groupedAnswers) {
			String groupedValue = groupedAnswer.getValue();
			assertThat(answersCorrectInGroup, hasItem(groupedValue));
		}
	}

}
