package eu.ydp.empiria.player.client.controller.variables.objects.response;

import java.util.ArrayList;
import java.util.List;

public class ResponseValue {

	private List<String> answers;
	
	public ResponseValue(String firstValue){
		answers = new ArrayList<String>();
		answers.add(firstValue);
	}
	
	public List<String> getAnswers(){
		return answers;
	}
}
