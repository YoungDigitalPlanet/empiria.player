package eu.ydp.empiria.player.client.controller.feedback;

import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken.CORRECT;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken.WRONG;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.DONE;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.ERRORS;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.LASTCHANGE;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.LASTMISTAKEN;
import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.TODO;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.LastMistaken;
import eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName;

/*
 EDIT_RESPONSE_2-DONE - ile zrobionych poprawnie
 EDIT_RESPONSE_2-TODO - ile do zrobienia
 EDIT_RESPONSE_2-LASTCHANGE - zmiana odpowiedzi np. +ChoiceA;-ChoiceB
 EDIT_RESPONSE_2-PREVIOUS - poprzednia odpowiedź użytkownika np CHOICE_1
 EDIT_RESPONSE_2-LASTMISTAKEN - czy ostatnia zmiana to wrong czy ok
 EDIT_RESPONSE_2-DONEHISTORY - tablica (historia) trzymająca 0/1 - 0 - niepoprawnie, 1 - poprawnie rozwiązane
 EDIT_RESPONSE_2-DONECHANGES - trzyma historię zmian, z dobrej na złą - -1, ze złej na dobrą - 1, bez zmian - 0
 EDIT_RESPONSE_2-MISTAKES - liczba wszsystkich błędów popełnionych w trakcie rozwiązywania zadania
 EDIT_RESPONSE_2-ERRORS - czy są w tej chwili jakieś błędne odpowiedzi
 */

public class FeedbackPropertiesCreator {

	private String variableIdentifier;

	private Map<String, ? extends Variable> variables;

	public FeedbackProperties getPropertiesFromVariables(String variableIdentifier, Map<String, ? extends Variable> variables) {
		this.variableIdentifier = variableIdentifier;
		this.variables = variables;
		
		if(variables == null){
			this.variables = Maps.newHashMap();
		}
		
		return createProperties();
	}

	private FeedbackProperties createProperties() {
		FeedbackProperties properties = new FeedbackProperties();

		properties.addBooleanProperty(FeedbackPropertyName.OK, CORRECT.equals(getLastmistaken()) );
		properties.addBooleanProperty(FeedbackPropertyName.WRONG, WRONG.equals(getLastmistaken()) );
		properties.addBooleanProperty(FeedbackPropertyName.ALL_OK, isAllOk());
		properties.addIntegerProperty(FeedbackPropertyName.DONE, getIntegerVariable(DONE));
		properties.addIntegerProperty(FeedbackPropertyName.TODO, getIntegerVariable(TODO));
		properties.addIntegerProperty(FeedbackPropertyName.ERRORS, getIntegerVariable(ERRORS));
		properties.addDoubleProperty(FeedbackPropertyName.RESULT, getResultValue());

		boolean isSelect = checkIfFeedbackIsOnSelectAction();
		properties.addBooleanProperty(FeedbackPropertyName.SELECTED, isSelect);
		properties.addBooleanProperty(FeedbackPropertyName.UNSELECT, !isSelect);

		return properties;
	}

	private LastMistaken getLastmistaken() {
		String lastmistaken = getVariableValue(LASTMISTAKEN);
		return lastmistakenStringToEnum(lastmistaken);
	}

	private LastMistaken lastmistakenStringToEnum(String lastmistaken) {
		if (lastmistaken == null){
			return LastMistaken.NONE;
		} else {
			return LastMistaken.valueOf(lastmistaken);
		}
	}

	private boolean checkIfFeedbackIsOnSelectAction() {
		String variableValue = getVariableValue(LASTCHANGE);
		boolean isSelect;
		if (isVariableNotEmpty(variableValue) && variableValue.startsWith("+")) {
			isSelect = true;
		} else {
			isSelect = false;
		}
		return isSelect;
	}

	private boolean isVariableNotEmpty(String variableValue) {
		if ((variableValue == null) || variableValue.equals("+")) {
			return false;
		} else {
			return true;
		}
	}

	private boolean isAllOk() {
		int todo = getIntegerVariable(TODO);
		return getIntegerVariable(DONE) == todo && todo != 0 && getIntegerVariable(ERRORS) == 0;
	}

	private double getResultValue() {
		int done = getIntegerVariable(DONE);
		int todo = getIntegerVariable(TODO);

		double result = Double.POSITIVE_INFINITY;
		if (todo > 0) {
			result = 100 * done / todo;
		}

		return result;
	}

	private Integer getIntegerVariable(VariableName variableName) {
		int value = 0;
		String variableValue = getVariableValue(variableName);
		if (variableValue != null) {
			value = Integer.valueOf(variableValue);
		}

		return value;
	}

	private String getVariableValue(VariableName variableName){
		return getVariableValue(variableName.toString());
	}
	
	private String getVariableValue(String variableName) {
		String namedVariableIdentifier = variableIdentifier + "-" + variableName;
		Variable variable = variables.get(namedVariableIdentifier);

		String variableValue = null;
		if (variable != null) {
			List<String> variableValues = variable.values;
			if (!variableValues.isEmpty()) {
				variableValue = variableValues.get(0);
			}
		}

		return variableValue;
	}
}
