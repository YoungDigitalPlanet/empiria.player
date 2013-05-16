package eu.ydp.empiria.player.client.controller.variables.processor.module;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.processor.module.expression.ExpressionModeVariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedModeVariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.multiple.MultipleModeVariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.ordering.OrderingModeVariableProcessor;

@RunWith(MockitoJUnitRunner.class)
public class VariableProcessorFactoryJUnitTest {

	private VariableProcessorFactory variableProcessorFactory;

	@Mock
	private MultipleModeVariableProcessor multipleModeVariableProcessor;
	@Mock
	private GroupedModeVariableProcessor groupedModeVariableProcessor;
	@Mock
	private ExpressionModeVariableProcessor expressionVariableProcessor;
	@Mock
	private OrderingModeVariableProcessor orderingModeVariableProcessor;

	@Before
	public void setUp() throws Exception {
		variableProcessorFactory = new VariableProcessorFactory(multipleModeVariableProcessor, groupedModeVariableProcessor, expressionVariableProcessor,
				orderingModeVariableProcessor);
	}

	@Test
	public void shouldCreateGroupedProcessorWhenHasGroup() throws Exception {
		Cardinality cardinality = Cardinality.ORDERED;
		boolean hasGroups = true;
		boolean isInExpression = false;

		VariableProcessor variableProcessor = variableProcessorFactory.findAppropriateProcessor(cardinality, hasGroups, isInExpression);

		assertEquals(groupedModeVariableProcessor, variableProcessor);
	}

	@Test
	public void shouldCreateMultipleModeProcessorIsSingleCardinality() throws Exception {
		Cardinality cardinality = Cardinality.SINGLE;
		boolean hasGroups = false;
		boolean isInExpression = false;

		VariableProcessor variableProcessor = variableProcessorFactory.findAppropriateProcessor(cardinality, hasGroups, isInExpression);

		assertEquals(multipleModeVariableProcessor, variableProcessor);
	}

	@Test
	public void shouldCreateMultipleModeProcessorIsMultipleCardinality() throws Exception {
		Cardinality cardinality = Cardinality.MULTIPLE;
		boolean hasGroups = false;
		boolean isInExpression = false;

		VariableProcessor variableProcessor = variableProcessorFactory.findAppropriateProcessor(cardinality, hasGroups, isInExpression);

		assertEquals(multipleModeVariableProcessor, variableProcessor);
	}

	@Test
	public void shouldCreateMultipleModeProcessorWhenIsNotSupportedCardinality() throws Exception {
		Cardinality cardinality = Cardinality.ORDERED;
		boolean hasGroups = false;
		boolean isInExpression = false;

		VariableProcessor variableProcessor = variableProcessorFactory.findAppropriateProcessor(cardinality, hasGroups, isInExpression);

		assertEquals(orderingModeVariableProcessor, variableProcessor);
	}

	@Test
	public void shouldCreateExpressionVariableProcessor() throws Exception {
		Cardinality cardinality = Cardinality.SINGLE;
		boolean hasGroups = false;
		boolean isInExpression = true;

		VariableProcessor variableProcessor = variableProcessorFactory.findAppropriateProcessor(cardinality, hasGroups, isInExpression);

		assertEquals(expressionVariableProcessor, variableProcessor);
	}
}
