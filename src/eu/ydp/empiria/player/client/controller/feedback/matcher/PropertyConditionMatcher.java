package eu.ydp.empiria.player.client.controller.feedback.matcher;

import eu.ydp.empiria.player.client.controller.feedback.FeedbackProperties;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.PropertyConditionBean;
import eu.ydp.gwtutil.client.operator.MatchOperator;

public class PropertyConditionMatcher extends ConditionMatcherBase implements FeedbackMatcher {
	
	private PropertyConditionBean condition;
	
	private FeedbackProperties properties;
	
	private MatchOperator operator;
	
	@Override
	public boolean match(FeedbackCondition condition, FeedbackProperties properties) {
		boolean matches = false;
		
		if (condition instanceof PropertyConditionBean) {
			this.properties = properties;
			this.condition = (PropertyConditionBean) condition;
			this.operator = MatchOperator.getOperator(this.condition.getOperator());
			matches = checkPropertyCondition();
		}
		
		return matches;
	}
	
	protected boolean checkPropertyCondition() {
		boolean matches = false;
		
		if (properties.hasValue(condition.getProperty())) {
			matches = matchPropertyValue(properties.getProperty(condition.getProperty()));
		}
		
		return matches;
	}
	
	protected boolean matchPropertyValue(Object value) {
		boolean matches = false;
		
		if(value instanceof Boolean){
			matches = operator.match((Boolean) value, Boolean.valueOf(condition.getValue()));
		}else if(value instanceof String){
			matches = operator.match((String) value, condition.getValue());
		}else if(value instanceof Double){
			matches = operator.match((Double) value, Double.valueOf(condition.getValue()));
		}else if(value instanceof Integer){
			matches = operator.match((Integer) value, Integer.valueOf(condition.getValue()));
		}
		
		return matches;
	}
}
