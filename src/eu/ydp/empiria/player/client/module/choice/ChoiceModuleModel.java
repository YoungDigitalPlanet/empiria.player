package eu.ydp.empiria.player.client.module.choice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;

public class ChoiceModuleModel extends AbstractResponseModel<String> {
	
	@Inject
	public ChoiceModuleModel(@Assisted Response response) {
		super(response);
	}

	@Override
	protected List<String> parseResponse(Collection<String> values) {
		return new ArrayList<String>(values);
	}
	
	public boolean isCorrectAnswer(String answer){
		return getCorrectAnswers().contains(answer);
	}
	
	public boolean isWrongAnswer(String answer){
		return !getCorrectAnswers().contains(answer);
	}
	
	public boolean isCurrentAnswer(String identifier){
		return getCurrentAnswers().contains(identifier);
	}
	
	public void selectResponse(String answer){
		response.add(answer);
	}
	
	public void unselectResponse(String answer){
		response.remove(answer);
	}
}
