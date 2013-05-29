package eu.ydp.empiria.player.client.module.colorfill;

import java.util.Collection;
import java.util.List;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;

public class ColorfillInteractionModuleModel extends AbstractResponseModel<String> {

	public ColorfillInteractionModuleModel(Response response, ResponseModelChangeListener responseModelChange) {
		super(response, responseModelChange);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<String> parseResponse(Collection<String> values) {
		// TODO Auto-generated method stub
		return null;
	}

}
