package eu.ydp.empiria.player.client.module.choice;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;

public class ChoiceModuleModel extends AbstractResponseModel<String> {
	
	@Inject
	public ChoiceModuleModel(@Assisted Response response) {
		super(response);
	}
	
}
