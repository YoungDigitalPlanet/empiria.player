package eu.ydp.empiria.player.client.module;

import java.util.List;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

public interface ResponseSocket {

	/** Get access to response */
	public Response getResponse(String id);

	public List<Boolean> evaluateResponse(Response response);
}
