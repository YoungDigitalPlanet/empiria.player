package eu.ydp.empiria.player.client.module.external;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.*;
import java.util.*;

public class ExternalInteractionResponseModel extends AbstractResponseModel<String> {

	public ExternalInteractionResponseModel(Response response,
			ResponseModelChangeListener responseModelChange) {
		super(response, responseModelChange);
	}

	@Override
	protected List<String> parseResponse(Collection<String> values) {
		return null;
	}
}
