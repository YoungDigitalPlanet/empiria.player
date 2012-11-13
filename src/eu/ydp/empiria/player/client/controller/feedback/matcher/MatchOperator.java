package eu.ydp.empiria.player.client.controller.feedback.matcher;

public class MatchOperator {
	
	private String operator;
	
	public MatchOperator(String operator){
		this.operator = operator;
	}
	
	public boolean checkBoolean(Boolean value1, Boolean value2){
		return false;
	}
	
	public boolean checkString(String value1, String value2){
		return false;
	}
	
	public boolean checkNumber(Number value1, Number value2){
		return false;
	}
	
}
