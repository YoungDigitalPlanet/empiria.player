package eu.ydp.empiria.player.client.module.expression;

import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.objects.response.ResponseBuilder;
import eu.ydp.empiria.player.client.module.expression.model.ExpressionBean;

@RunWith(MockitoJUnitRunner.class)
public class ExpressionSetsFinderJUnitTest {

	private ExpressionSetsFinder expressionSetsFinder;
	
	@Mock
	private IdentifiersFromExpressionExtractor identifiersFromExpressionExtractor;
	@Mock
	private ExpressionToPartsDivider expressionToPartsDivider;
	
	@Before
	public void setUp() throws Exception {
		expressionSetsFinder = new ExpressionSetsFinder(identifiersFromExpressionExtractor, expressionToPartsDivider);
	}

	@Test
	public void shouldUpdateExpressionSetsInBean() throws Exception {
		//given
		ExpressionBean expression = new ExpressionBean();
		String template = "template";
		String leftPart = "leftPart";
		String rightPart = "rightPart";
		expression.setTemplate(template);
		Response responseA = new ResponseBuilder().withIdentifier("a").build();
		expression.getResponses().add(responseA);
		Response responseB = new ResponseBuilder().withIdentifier("b").build();
		expression.getResponses().add(responseB);
		Response responseC = new ResponseBuilder().withIdentifier("c").build();
		expression.getResponses().add(responseC);
		
		List<String> expressionParts = Lists.newArrayList(leftPart, rightPart);
		when(expressionToPartsDivider.divideExpressionOnEquality(template, expression.getResponses()))
			.thenReturn(expressionParts);
		
		when(identifiersFromExpressionExtractor.extractResponseIdentifiersFromTemplate(leftPart))
			.thenReturn(Lists.newArrayList("a"));
		
		when(identifiersFromExpressionExtractor.extractResponseIdentifiersFromTemplate(rightPart))
		.thenReturn(Lists.newArrayList("b"));
		
		when(identifiersFromExpressionExtractor.extractResponseIdentifiersFromTemplate(template))
		.thenReturn(Lists.newArrayList("a", "b", "c"));
		
		//when
		expressionSetsFinder.updateResponsesSetsInExpression(expression);
		
		//then
		List<Set<Response>> setsOfResponses = expression.getSetsOfResponses();
		assertEquals(3, setsOfResponses.size());
		assertTrue(setsOfResponses.contains(Sets.newHashSet(responseA)));
		assertTrue(setsOfResponses.contains(Sets.newHashSet(responseB)));
		assertTrue(setsOfResponses.contains(Sets.newHashSet(responseA, responseB, responseC)));
	}
	
}
