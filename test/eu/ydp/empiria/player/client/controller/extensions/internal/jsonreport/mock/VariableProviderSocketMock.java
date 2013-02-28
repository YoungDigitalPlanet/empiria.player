package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport.mock;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;

import eu.ydp.empiria.player.client.controller.feedback.OutcomeCreator;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor;

public class VariableProviderSocketMock implements VariableProviderSocket {
	
	private final Map<String, Variable> variables = Maps.newHashMap();
	
	private final OutcomeCreator outcomeCreator = new OutcomeCreator();
	
	private int todo;
	
	private int done;
	
	private int errors;

	private int checks;

	private int mistakes;

	private int reset;

	private int showAnswers;

	public void setResultValues(int todo, int done, int errors){
		this.todo = todo;
		this.done = done;
		this.errors = errors;
		addResultVariables();
	}
	
	private void addResultVariables(){
		variables.put(DefaultVariableProcessor.TODO, outcomeCreator.createTodoOutcome(todo));
		variables.put(DefaultVariableProcessor.DONE, outcomeCreator.createDoneOutcome(done));
		variables.put(DefaultVariableProcessor.ERRORS, outcomeCreator.createErrorsOutcome(errors));
	}
	
	public void setHintValues(int checks, int mistakes, int reset, int showAnswers){
		this.checks = checks;
		this.mistakes = mistakes;
		this.reset = reset;
		this.showAnswers = showAnswers;
		addHintVariables();
	}
	
	private void addHintVariables() {
		variables.put(FlowActivityVariablesProcessor.CHECKS, outcomeCreator.createChecksOutcome(checks));
		variables.put(DefaultVariableProcessor.MISTAKES, outcomeCreator.createMistakesOutcome(mistakes));
		variables.put(FlowActivityVariablesProcessor.RESET, outcomeCreator.createResetOutcome(reset));	
		variables.put(FlowActivityVariablesProcessor.SHOW_ANSWERS, outcomeCreator.createShowAnswersOutcome(showAnswers));	
	}

	@Override
	public Set<String> getVariableIdentifiers() {
		return null;
	}

	@Override
	public Variable getVariableValue(String identifier) {
		return variables.get(identifier);
	}

}
