package eu.ydp.empiria.player.client.controller.feedback;

import static com.google.common.base.Optional.absent;
import static com.google.common.base.Optional.fromNullable;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.DONE;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.ERRORS;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.LASTCHANGE;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.LASTMISTAKEN;
import static eu.ydp.empiria.player.client.controller.variables.processor.item.DefaultVariableProcessor.TODO;

import java.util.Map;
import java.util.logging.Logger;

import com.google.common.base.Optional;

import eu.ydp.empiria.player.client.controller.variables.objects.Variable;

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

	public FeedbackProperties getPropertiesFromVariables(String variableIdentifier, Map<String, ? extends Variable> variables){
		this.variableIdentifier = variableIdentifier;
		this.variables = variables;
		return createProperties();
	}
	
	private FeedbackProperties createProperties(){		
		FeedbackProperties properties = new FeedbackProperties();
		
		properties.addBooleanProperty(FeedbackPropertyName.OK, getInteger(LASTMISTAKEN) == 0);
		properties.addBooleanProperty(FeedbackPropertyName.WRONG, getInteger(LASTMISTAKEN) == 1);
		properties.addBooleanProperty(FeedbackPropertyName.ALL_OK, isAllOk());
		properties.addIntegerProperty(FeedbackPropertyName.DONE, getInteger(DONE));
		properties.addIntegerProperty(FeedbackPropertyName.TODO, getInteger(TODO));
		properties.addIntegerProperty(FeedbackPropertyName.ERRORS, getInteger(ERRORS));
		properties.addDoubleProperty(FeedbackPropertyName.RESULT, getResultValue());
		
		boolean isSelect = checkIfFeedbackIsOnSelectAction();
		properties.addBooleanProperty(FeedbackPropertyName.SELECTED, isSelect);
		properties.addBooleanProperty(FeedbackPropertyName.UNSELECT, !isSelect);
		
		return  properties;
	}

	private boolean checkIfFeedbackIsOnSelectAction() {
		String variableValue = getVariableValue(LASTCHANGE);
		boolean isSelect;
		if(variableValue != null && variableValue.startsWith("+")){
			isSelect = true;
		}else{
			isSelect = false;
		}
		return isSelect;
	}

	private boolean isAllOk(){
		return getInteger(DONE) == getInteger(TODO) && 
				getInteger(TODO) != 0 && getInteger(ERRORS) == 0;
	}
	
	private double getResultValue(){
		double result = Double.POSITIVE_INFINITY;
		
		try {
			result = 100*getInteger(DONE)/getInteger(TODO);
		} catch (ArithmeticException exception) {
			Logger.getLogger(getClass().getName()).info(exception.getMessage());
		}
		
		return result;
	}
	
	private Integer getInteger(String variableName){
		return Integer.valueOf(getOptionalValue(variableName).or("0"));
	}
	
	private String getVariableValue(String variableName){
		Optional<String> value = absent();
		
		try {
			value = fromNullable(variables.get(variableIdentifier + "-" + variableName).values.get(0));
		} catch (Exception exception) {
			Logger.getLogger(getClass().getName()).info(exception.getMessage());
		}
		
		return value.orNull();
	}
	
	private Optional<String> getOptionalValue(String variableName){
		return fromNullable(getVariableValue(variableName));
	}
	
}
