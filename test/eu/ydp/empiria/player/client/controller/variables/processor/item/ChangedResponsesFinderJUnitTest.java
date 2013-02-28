package eu.ydp.empiria.player.client.controller.variables.processor.item;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.response.DtoChangedResponse;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

@RunWith(MockitoJUnitRunner.class)
public class ChangedResponsesFinderJUnitTest {

	private ChangedResponsesFinder changedResponsesFinder;
	private ResponseDifferenceFinder responseDifferenceFinder = new ResponseDifferenceFinder();
	
	@Before
	public void setUp() throws Exception {
		changedResponsesFinder = new ChangedResponsesFinder(responseDifferenceFinder);
	}

	
	@Test
	public void testFindResponsesWhereAnswersChanged_noAnswerHasChanged() throws Exception {
		//given
		Response unchagedResponse = createResponse("id1", Lists.newArrayList("answer1", "answer2"));
		Response unchagedResponse2 = createResponse("id2", Lists.newArrayList("answer1", "answer2"));
		
		Map<String, Response> previousResponses = convertToMap(unchagedResponse, unchagedResponse2);
		Map<String, Response> currentResponses = convertToMap(unchagedResponse, unchagedResponse2);
		
		//when
		List<DtoChangedResponse> changedResponses = changedResponsesFinder.findResponsesWhereAnswersChanged(previousResponses, currentResponses);
		
		//then
		assertEquals(0, changedResponses.size());
	}
	
	@Test
	public void testFindResponsesWhereAnswersChanged_previousResponseNotExists() throws Exception {
		//given
		Response response = createResponse("id1", Lists.newArrayList("answer1", "answer2"));
		Response newResponse = createResponse("id2", Lists.newArrayList("answer1", "answer2"));
		
		Map<String, Response> previousResponses = convertToMap(response);
		Map<String, Response> currentResponses = convertToMap(response, newResponse);
		
		//when
		List<DtoChangedResponse> changedResponses = changedResponsesFinder.findResponsesWhereAnswersChanged(previousResponses, currentResponses);
		
		//then
		assertEquals(1, changedResponses.size());
		DtoChangedResponse changedResponse = changedResponses.get(0);
		assertNull(changedResponse.getPreviousResponse());
		assertEquals(newResponse, changedResponse.getCurrentResponse());
		assertTrue(changedResponse.getChanges().size() > 0);
	}
	
	@Test
	public void testFindResponsesWhereAnswersChanged_answersForResponseChanged() throws Exception {
		//given
		Response changedPreviousResponse = createResponse("id2", Lists.newArrayList("previousAnswer"));
		Response changedCurrentResponse = createResponse("id2", Lists.newArrayList("currentAnswer"));
		
		
		Map<String, Response> previousResponses = convertToMap(changedPreviousResponse);
		Map<String, Response> currentResponses = convertToMap(changedCurrentResponse);
		
		//when
		List<DtoChangedResponse> changedResponses = changedResponsesFinder.findResponsesWhereAnswersChanged(previousResponses, currentResponses);
		
		//then
		assertEquals(1, changedResponses.size());
		DtoChangedResponse changedResponse = changedResponses.get(0);
		assertEquals(changedPreviousResponse, changedResponse.getPreviousResponse());
		assertEquals(changedCurrentResponse, changedResponse.getCurrentResponse());
		assertTrue(changedResponse.getChanges().size() > 0);
	}

	private Map<String, Response> convertToMap(Response ... responses) {
		Map<String, Response> responsesMap = Maps.uniqueIndex(Arrays.asList(responses), new Function<Response, String>() {
			@Override
			public String apply(Response response) {
				return response.getID();
			}
		});
		return responsesMap;
	}

	private Response createResponse(String id, List<String> answers) {
		Response response =  new Response(null, answers, null, id, null, null, null);
		return response;
	}
}
