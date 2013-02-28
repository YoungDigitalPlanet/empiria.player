package eu.ydp.empiria.player.client.controller.variables.processor.item;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.item.ResponseDifferenceFinder;

public class ResponseDifferenceFinderJUnitTest {

	private ResponseDifferenceFinder responseDifferenceFinder = new ResponseDifferenceFinder();
	
	@Test
	public void testFindDifferencesBetweenResponses_shouldFindNoDifference() throws Exception {
		Response previousResponse = createResponseWithAnswers("Answer1", "Answer2");
		Response currentResponse = createResponseWithAnswers("Answer1", "Answer2");
		
		List<String> differences = responseDifferenceFinder.findDifferencesBetweenResponses(previousResponse, currentResponse);
		
		assertEquals(0, differences.size());
	}
	
	@Test
	public void testFindDifferencesBetweenResponses_shouldIgnoreEmptyStringAdded() throws Exception {
		Response previousResponse = createResponseWithAnswers("Answer1", "Answer2");
		Response currentResponse = createResponseWithAnswers("Answer1", "Answer2", "");
		
		List<String> differences = responseDifferenceFinder.findDifferencesBetweenResponses(previousResponse, currentResponse);
		
		assertEquals(0, differences.size());
	}
	
	@Test
	public void testFindDifferencesBetweenResponses_shouldIgnoreEmptyStringRemoved() throws Exception {
		Response previousResponse = createResponseWithAnswers("Answer1", "Answer2", "");
		Response currentResponse = createResponseWithAnswers("Answer1", "Answer2");
		
		List<String> differences = responseDifferenceFinder.findDifferencesBetweenResponses(previousResponse, currentResponse);
		
		assertEquals(0, differences.size());
	}
	
	@Test
	public void testFindDifferencesBetweenResponses_shouldFindNewAnswerAdded() throws Exception {
		Response previousResponse = createResponseWithAnswers("Answer1");
		Response currentResponse = createResponseWithAnswers("Answer1", "Answer2");
		
		List<String> differences = responseDifferenceFinder.findDifferencesBetweenResponses(previousResponse, currentResponse);
		
		assertEquals(1, differences.size());
		assertTrue(differences.contains("+Answer2"));
	}
	
	@Test
	public void testFindDifferencesBetweenResponses_shouldFindAnswerRemoved() throws Exception {
		Response previousResponse = createResponseWithAnswers("Answer1", "Answer2");
		Response currentResponse = createResponseWithAnswers("Answer1");
		
		List<String> differences = responseDifferenceFinder.findDifferencesBetweenResponses(previousResponse, currentResponse);
		
		assertEquals(1, differences.size());
		assertTrue(differences.contains("-Answer2"));
	}
	
	

	private Response createResponseWithAnswers(String ... currentAnswers) {
		Response response = new Response(null, Arrays.asList(currentAnswers), null, null, null, null, null);
		return response;
	}
	
}
