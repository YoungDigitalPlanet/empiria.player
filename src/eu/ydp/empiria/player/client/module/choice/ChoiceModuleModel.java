package eu.ydp.empiria.player.client.module.choice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.scopes.module.ModuleScoped;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;

public class ChoiceModuleModel extends AbstractResponseModel<String> {

	@Inject
	public ChoiceModuleModel(@ModuleScoped Response response) {
		super(response);
	}

	@Override
	protected List<String> parseResponse(Collection<String> values) {
		return new ArrayList<String>(values);
	}

	public boolean isWrongAnswer(String answer) {
		return !isCorrectAnswer(answer);
	}

	public void initialize(ResponseModelChangeListener modelChangeListener) {
		responseModelChange = modelChangeListener;
	}
}
