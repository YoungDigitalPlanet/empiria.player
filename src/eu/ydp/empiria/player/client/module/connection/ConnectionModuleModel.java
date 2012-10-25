package eu.ydp.empiria.player.client.module.connection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.gwtutil.client.collections.KeyValue;

public class ConnectionModuleModel extends AbstractResponseModel<KeyValue<String, String>> {

	@Inject
	public ConnectionModuleModel(@Assisted Response response, @Assisted ResponseModelChangeListener modelChangeListener) {
		super(response, modelChangeListener);
	}

	@Override
	protected List<KeyValue<String, String>> parseResponse(Collection<String> values) {		
		ArrayList<KeyValue<String, String>> parsedList = new ArrayList<KeyValue<String,String>>();
		for (String value : values) {
			String[] responses = value.split(" ");			
			if (responses.length == 2) {
				parsedList.add(new KeyValue<String, String>(responses[0], responses[1]));
			}
		}
		return parsedList;		
	}

	public Response getResponse() {		
		return response;
	}	
	
	public void addAnswer(String answer){
		response.add(answer);
		onModelChange();
	}
	
	public void removeAnswer(String answer){
		response.remove(answer);
		onModelChange();
	}

	public int getCurrentChoicePairingsNumber(String identifier) {
		int count = 0;
		for (KeyValue<String, String> answer : getCurrentAnswers()) {
			if (answer.getKey().equals(identifier) || answer.getValue().equals(identifier)) {
				count++;;
			}
		}
		return count;
	}	
	
	public int getCurrentOverallPairingsNumber() {
		return getCurrentAnswers().size();
	}	
}
