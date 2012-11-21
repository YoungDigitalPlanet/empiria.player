package eu.ydp.empiria.player.client.controller.feedback;

import static eu.ydp.empiria.player.client.controller.variables.objects.BaseType.IDENTIFIER;
import static eu.ydp.empiria.player.client.controller.variables.objects.BaseType.INTEGER;
import static eu.ydp.empiria.player.client.controller.variables.objects.Cardinality.MULTIPLE;
import static eu.ydp.empiria.player.client.controller.variables.objects.Cardinality.SINGLE;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.DONE;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.DONECHANGES;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.DONEHISTORY;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.ERRORS;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.LASTCHANGE;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.LASTMISTAKEN;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.MISTAKES;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.PREVIOUS;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.TODO;
import eu.ydp.empiria.player.client.controller.variables.objects.BaseType;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;

public class OutcomeCreator {
	
	private String moduleId;
	
	public OutcomeCreator(String moduleId){
		this.moduleId = moduleId;
	}
	
	public Outcome createOutcome(String name, Cardinality cardinality, BaseType baseType, String value){
		String identifier = moduleId + "-" + name;
		return new Outcome(identifier, cardinality, baseType, value);
	}
	
	public Outcome createDoneOutcome(int done){
		return createOutcome(DONE, SINGLE, INTEGER, String.valueOf(done));
	}
	
	public Outcome createTodoOutcome(int todo){
		return createOutcome(TODO, SINGLE, INTEGER, String.valueOf(todo));
	}
	
	public Outcome createLastChangeOutcome(String identifier){
		return createOutcome(LASTCHANGE, SINGLE, IDENTIFIER, identifier);
	}
	
	public Outcome createPreviousOutcome(String identifier){
		return createOutcome(PREVIOUS, SINGLE, IDENTIFIER, identifier);
	}
	
	public Outcome createLastMistakenOutcome(int isMistake){
		return createOutcome(LASTMISTAKEN, SINGLE, INTEGER, String.valueOf(isMistake));
	}
	
	public Outcome createDoneHistoryOutcome(int element){
		return createOutcome(DONEHISTORY, MULTIPLE, INTEGER, String.valueOf(element));
	}
	
	public Outcome createDoneChangesOutcome(int element){
		return createOutcome(DONECHANGES, MULTIPLE, INTEGER, String.valueOf(element));
	}
	
	public Outcome createMistakesOutcome(int mistakesNum){
		return createOutcome(MISTAKES, SINGLE, INTEGER, String.valueOf(mistakesNum));
	}
	
	public Outcome createErrorsOutcome(int errorsNum){
		return createOutcome(ERRORS, SINGLE, INTEGER, String.valueOf(errorsNum));
	}
}
