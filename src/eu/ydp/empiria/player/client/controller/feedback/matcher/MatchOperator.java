package eu.ydp.empiria.player.client.controller.feedback.matcher;

import com.google.common.collect.ComparisonChain;

import eu.ydp.empiria.player.client.controller.feedback.FeedbackPropertyName;

public enum MatchOperator {
	
	EQUAL("=="), NOT_EQUAL("!="), LESS("<"), LESS_EQUAL("<="), GREATER(">"), GREATER_EQUAL(">="), NONE("");
	
	private String operator;
	
	private MatchOperator(String operator) {
		this.operator = operator;
	}
	
	public boolean checkBoolean(Boolean value1, Boolean value2) {
		Boolean areValuesMatch = false;
		
		if ( operator.equals(EQUAL) && (value1 == value2) ) {
			areValuesMatch = true;
		} else if ( operator.equals(NOT_EQUAL) && (value1 != value2) ) {
			areValuesMatch = true;
		}
		
		return areValuesMatch;
	}
	
	public boolean checkString(String value1, String value2) {
		Boolean areValuesMatch = false;
		
		if ( operator.equals(EQUAL) && value1.equals(value2) ) {
			areValuesMatch = true;
		} else if ( operator.equals(NOT_EQUAL) && !value1.equals(value2) ) {
			areValuesMatch = true;
		}
		
		return areValuesMatch;
	}
	
	public <A extends Number & Comparable<A>> boolean checkNumber(A value1, A value2) {
		Boolean areValuesMatch = false;
		
		if ( operator.equals(EQUAL.getName()) && (ComparisonChain.start().compare(value1, value2).result() == 0) ) {
			areValuesMatch = true;
		} else if ( operator.equals(NOT_EQUAL.getName()) && (ComparisonChain.start().compare(value1, value2).result() != 0) ) {
			areValuesMatch = true;
		} else if ( operator.equals(LESS.getName()) && (ComparisonChain.start().compare(value1, value2).result() == -1) ) {
			areValuesMatch = true;
		} else if ( operator.equals(LESS_EQUAL.getName()) && (ComparisonChain.start().compare(value1, value2).result() <= 0) ) {
			areValuesMatch = true;
		} else if ( operator.equals(GREATER.getName()) && (ComparisonChain.start().compare(value1, value2).result() > 0) ) {
			areValuesMatch = true;
		} else if ( operator.equals(GREATER_EQUAL.getName()) && (ComparisonChain.start().compare(value1, value2).result() >= 0) ) {
			areValuesMatch = true;
		}
		
		return areValuesMatch;
	}
	
	public static MatchOperator getMatchOperator(String value){
		MatchOperator searchedOperator = NONE;
		
		for(MatchOperator matchOperator: values()) {
			if (matchOperator.operator.equals(value)) {
				searchedOperator = matchOperator;
				break;
			}
		}
		
		return searchedOperator;
	}
	
	public static boolean exists(FeedbackPropertyName name){
		return !NONE.equals(name);
	}
	
	public static boolean exists(String value){
		return !NONE.equals(getMatchOperator(value));
	}
	
	public String getName() {
		return operator;
	}
}
