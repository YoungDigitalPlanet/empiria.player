package eu.ydp.empiria.player.client.module.choice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;

public class ChoiceModuleModel extends AbstractResponseModel<String> {
	
	@Inject
	public ChoiceModuleModel(@Assisted Response response, @Assisted ResponseModelChangeListener modelChangeListener) {
		super(response, modelChangeListener);
	}

	@Override
	protected List<String> parseResponse(Collection<String> values) {
		return new ArrayList<String>(values);
	}
	
	public boolean isWrongAnswer(String answer){
		return !isCorrectAnswer(answer);
	}
}
