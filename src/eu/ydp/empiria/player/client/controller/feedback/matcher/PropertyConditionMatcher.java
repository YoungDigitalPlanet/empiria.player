package eu.ydp.empiria.player.client.controller.feedback.matcher;

import eu.ydp.empiria.player.client.controller.feedback.FeedbackProperties;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.PropertyConditionBean;

public class PropertyConditionMatcher implements FeedbackPropertyMatcher {
	
	private PropertyConditionBean condition;
	
	private FeedbackProperties properties;
	
	private MatchOperator operator;
	
	@Override
	public boolean match(FeedbackCondition condition, FeedbackProperties properties) {
		boolean matches = false;
		
		if(condition instanceof PropertyConditionBean){
			this.properties = properties;
			this.condition = (PropertyConditionBean)condition;
			this.operator = MatchOperator.getMatchOperator(this.condition.getOperator());
			matches = checkPropertyCondition();
		}
		
		return matches;
	}
	
	protected boolean checkPropertyCondition(){
		boolean matches = false;
		
		if(properties.hasValue(condition.getProperty())){
			matches = matchPropertyValue(properties.getProperty(condition.getProperty()));
		}
		
		return matches;
	}
	
	protected boolean matchPropertyValue(Object value){
		boolean matches = false;
		
		if(value instanceof Boolean){
			matches = matchBooleanValue((Boolean) value);
		}else if(value instanceof String){
			matches = matchStringValue((String) value);
		}else if(value instanceof Double){
			matches = matchNumberValue((Double) value);
		}else if(value instanceof Integer){
			matches = matchNumberValue((Integer) value);
		}
		
		return matches;
	}
	
	protected boolean matchBooleanValue(Boolean propertyValue){
		Boolean conditionValue = Boolean.valueOf(condition.getValue());
		return operator.checkBoolean(propertyValue, conditionValue);
	}
	
	protected boolean matchStringValue(String propertyValue){
		return operator.checkString(propertyValue, condition.getValue());
	}
	
	protected boolean matchNumberValue(Number propertyValue) {
		boolean isMatch = false;
		
		if (propertyValue instanceof Double) {
			isMatch = operator.checkNumber((Double) propertyValue, Double.valueOf(condition.getValue()));
		} else if(propertyValue instanceof Integer) {
			isMatch = operator.checkNumber((Integer) propertyValue, Integer.valueOf(condition.getValue()));
		}
		
		return isMatch;
	}

}
