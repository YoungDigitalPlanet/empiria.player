package eu.ydp.empiria.player.client.controller.variables.processor.module;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;

public class VariableProcessorFactory {

	private SingleModeVariableProcessor singleModeVariableProcessor;
	
	@Inject
	public VariableProcessorFactory(SingleModeVariableProcessor singleModeVariableProcessor) {
		this.singleModeVariableProcessor = singleModeVariableProcessor;
	}

	public VariableProcessor findAppropriateProcessor(Cardinality cardinality, boolean hasGroups){
//		VariableProcessor variableProcessor = null;
//		
//		if(hasGroups){
////			variableProcessor = groupedVariableProcessor;
//		}
//		
//		switch (cardinality) {
//		case SINGLE:
//			variableProcessor = singleModeVariableProcessor;
//			break;
//		case MULTIPLE:
//			
//			break;
//		default:
//			throw new RuntimeException("Unsuported Cardinality: "+cardinality+" for variable processing");
//		}
//		
//		return variableProcessor;
		return singleModeVariableProcessor;
	}
	
}
