package eu.ydp.empiria.player.client.controller.feedback;

import static eu.ydp.empiria.player.client.controller.variables.objects.Cardinality.SINGLE;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor.CHECKS;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor.RESET;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor.SHOW_ANSWERS;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.DONE;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.ERRORS;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.LASTCHANGE;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.LASTMISTAKEN;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.MISTAKES;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.TODO;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;

public class OutcomeCreator {
	
	private final String moduleId;
	
	public OutcomeCreator(){
		this("");
	}
	
	public OutcomeCreator(String moduleId){
		this.moduleId = moduleId;
	}
	
	public Outcome createOutcome(String name, Cardinality cardinality, String value){
		String identifier = moduleId + "-" + name;
		return new Outcome(identifier, cardinality, value);
	}
	
	public Outcome createDoneOutcome(int done){
		return createOutcome(DONE.toString(), SINGLE,  String.valueOf(done));
	}
	
	public Outcome createTodoOutcome(int todo){
		return createOutcome(TODO.toString(), SINGLE,  String.valueOf(todo));
	}
	
	public Outcome createLastChangeOutcome(String identifier){
		return createOutcome(LASTCHANGE.toString(), SINGLE,  identifier);
	}
	
	public Outcome createLastMistakenOutcome(int isMistake){
		return createOutcome(LASTMISTAKEN.toString(), SINGLE,  String.valueOf(isMistake));
	}
	
	public Outcome createMistakesOutcome(int mistakesNum){
		return createOutcome(MISTAKES.toString(), SINGLE,  String.valueOf(mistakesNum));
	}
	
	public Outcome createChecksOutcome(int mistakesNum){
		return createOutcome(CHECKS, SINGLE,  String.valueOf(mistakesNum));
	}
	
	public Outcome createResetOutcome(int mistakesNum){
		return createOutcome(RESET, SINGLE,  String.valueOf(mistakesNum));
	}
	
	public Outcome createShowAnswersOutcome(int mistakesNum){
		return createOutcome(SHOW_ANSWERS, SINGLE,  String.valueOf(mistakesNum));
	}
	
	public Outcome createErrorsOutcome(int errorsNum){
		return createOutcome(ERRORS.toString(), SINGLE,  String.valueOf(errorsNum));
	}
}
