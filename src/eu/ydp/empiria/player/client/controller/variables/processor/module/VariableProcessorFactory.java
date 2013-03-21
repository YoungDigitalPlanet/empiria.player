package eu.ydp.empiria.player.client.controller.variables.processor.module;

import java.util.logging.Logger;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedModeVariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.multiple.MultipleModeVariableProcessor;

public class VariableProcessorFactory {

	private static final Logger LOGGER = Logger.getLogger(VariableProcessorFactory.class.getName());
	
	private final MultipleModeVariableProcessor multipleModeVariableProcessor;
	private final GroupedModeVariableProcessor groupedModeVariableProcessor;
	
	@Inject
	public VariableProcessorFactory(
			MultipleModeVariableProcessor multipleModeVariableProcessor,
			GroupedModeVariableProcessor groupedModeVariableProcessor) {
		this.multipleModeVariableProcessor = multipleModeVariableProcessor;
		this.groupedModeVariableProcessor = groupedModeVariableProcessor;
	}

	public VariableProcessor findAppropriateProcessor(Cardinality cardinality, boolean hasGroups){
		VariableProcessor variableProcessor = null;
		
		if(hasGroups){
			variableProcessor = groupedModeVariableProcessor;
		}else if(isSingleOrMultiple(cardinality)){
			variableProcessor = multipleModeVariableProcessor;
		}else{
			LOGGER.warning("Unknown Cardinality: "+cardinality+" for variable processing. Will be treated as multiple cardinality");
			variableProcessor = multipleModeVariableProcessor;
		}
		
		return variableProcessor;
	}

	private boolean isSingleOrMultiple(Cardinality cardinality) {
		return cardinality == Cardinality.SINGLE || cardinality == Cardinality.MULTIPLE;
	}
	
}
