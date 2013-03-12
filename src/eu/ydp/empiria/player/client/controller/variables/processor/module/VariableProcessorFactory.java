package eu.ydp.empiria.player.client.controller.variables.processor.module;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.processor.module.grouped.GroupedModeVariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.module.multiple.MultipleModeVariableProcessor;

public class VariableProcessorFactory {

	private MultipleModeVariableProcessor multipleModeVariableProcessor;
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
		}else{
			variableProcessor = multipleModeVariableProcessor;
		}
		
//		switch (cardinality) {
//		case SINGLE:
//			variableProcessor = multipleModeVariableProcessor;  //single it's just a special case of multiple 
//			break;
//		case MULTIPLE:
//			break;
//		default:
//			throw new RuntimeException("Unsuported Cardinality: "+cardinality+" for variable processing");
//		}
		return variableProcessor;
	}
	
}
