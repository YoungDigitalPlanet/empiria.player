package eu.ydp.empiria.player.client.controller.variables.processor.module;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedModeVariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.multiple.MultipleModeVariableProcessor;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class VariableProcessorFactoryJUnitTest {

	private VariableProcessorFactory variableProcessorFactory;

	@Mock
	private MultipleModeVariableProcessor multipleModeVariableProcessor;
	@Mock
	private GroupedModeVariableProcessor groupedModeVariableProcessor;
	
	@Before
	public void setUp() throws Exception {
		variableProcessorFactory = new VariableProcessorFactory(multipleModeVariableProcessor, groupedModeVariableProcessor);
	}

	@Test
	public void shouldCreateGroupedProcessorWhenHasGroup() throws Exception {
		Cardinality cardinality = Cardinality.ORDERED;
		boolean hasGroups = true;
		
		VariableProcessor variableProcessor = variableProcessorFactory.findAppropriateProcessor(cardinality, hasGroups);
		
		assertEquals(groupedModeVariableProcessor, variableProcessor);
	}

	@Test
	public void shouldCreateMultipleModeProcessorIsSingleCardinality() throws Exception {
		Cardinality cardinality = Cardinality.SINGLE;
		boolean hasGroups = false;
		
		VariableProcessor variableProcessor = variableProcessorFactory.findAppropriateProcessor(cardinality, hasGroups);
		
		assertEquals(multipleModeVariableProcessor, variableProcessor);
	}
	
	@Test
	public void shouldCreateMultipleModeProcessorIsMultipleCardinality() throws Exception {
		Cardinality cardinality = Cardinality.MULTIPLE;
		boolean hasGroups = false;
		
		VariableProcessor variableProcessor = variableProcessorFactory.findAppropriateProcessor(cardinality, hasGroups);
		
		assertEquals(multipleModeVariableProcessor, variableProcessor);
	}

	@Test
	public void shouldCreateMultipleModeProcessorWhenIsNotSupportedCardinality() throws Exception {
		Cardinality cardinality = Cardinality.ORDERED;
		boolean hasGroups = false;
		
		VariableProcessor variableProcessor = variableProcessorFactory.findAppropriateProcessor(cardinality, hasGroups);
		
		assertEquals(multipleModeVariableProcessor, variableProcessor);
	}
}
