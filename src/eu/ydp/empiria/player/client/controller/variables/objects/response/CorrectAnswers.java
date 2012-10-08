package eu.ydp.empiria.player.client.controller.variables.objects.response;

import java.util.ArrayList;
import java.util.List;

public class CorrectAnswers {

	private List<ResponseValue> values;
	
	public CorrectAnswers(){
		values = new ArrayList<ResponseValue>();
	}
	
	public void add(ResponseValue rv){
		values.add(rv);
	}
	
	public void add(String answer, int forIndex){
		if (values.size() > forIndex)
			values.get(forIndex).getAnswers().add(answer);
	}
	
	public int getResponseValuesCount(){
		return values.size();
	}
	
	public boolean containsAnswer(String test){
		for (ResponseValue rv : values){
			if (rv.getAnswers().contains(test))
				return true;
		}
		return false;
	}
	
	public ResponseValue getResponseValue(int index){
		return values.get(index);
	}
	
	public String getSingleAnswer(){
		return values.get(0).getAnswers().get(0);
	}
}
