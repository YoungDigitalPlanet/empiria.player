package eu.ydp.empiria.player.client.controller.variables.processor;

import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.DONE;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.ERRORS;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.LASTMISTAKEN;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.MISTAKES;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.TODO;
import static java.lang.Integer.valueOf;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.flow.FlowDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariablePossessorBase;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;

public class OutcomeAccessor {

	@Inject
	private SessionDataSupplier sessionDataSupplier;
	@Inject
	private FlowDataSupplier flowDataSupplier;

	public int getCurrentPageTodo() {
		return getVariableAsInt(TODO);
	}

	public int getCurrentPageDone() {
		return getVariableAsInt(DONE);
	}

	public int getCurrentPageErrors() {
		return getVariableAsInt(ERRORS);
	}

	public int getCurrentPageMistakes() {
		return getVariableAsInt(MISTAKES);
	}

	public LastMistaken getCurrentPageLastMistaken() {
		String lastmistaken = getVariableAsString(LASTMISTAKEN);
		return LastMistaken.valueOf(lastmistaken.toUpperCase());
	}

	@SuppressWarnings("unchecked")
	public boolean isLastActionSelection() {
		VariableProviderSocket variableProviderSocket = getVariableProvider();
		if (variableProviderSocket instanceof VariablePossessorBase<?>) {
			VariablePossessorBase<Variable> variablePossessor = (VariablePossessorBase<Variable>) variableProviderSocket;
			return variablePossessor.isLastAnswerSelectAction();
		}
		return false;
	}

	private int getVariableAsInt(VariableName variable) {
		String todoString = getVariableAsString(variable);
		return valueOf(todoString);
	}

	private String getVariableAsString(VariableName variable) {
		VariableProviderSocket variableProvider = getVariableProvider();
		Variable variableValue = variableProvider.getVariableValue(variable.toString());
		String todoString = variableValue.getValuesShort();
		return todoString;
	}

	private VariableProviderSocket getVariableProvider() {
		int currentPageIndex = flowDataSupplier.getCurrentPageIndex();
		VariableProviderSocket variableProvider = sessionDataSupplier.getItemSessionDataSocket(currentPageIndex).getVariableProviderSocket();
		return variableProvider;
	}

}
