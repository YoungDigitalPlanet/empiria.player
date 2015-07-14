package eu.ydp.empiria.player.client.module;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

import java.util.List;

public interface ResponseSocket {

    /**
     * Get access to response
     */
    public Response getResponse(String id);

    public List<Boolean> evaluateResponse(Response response);
}
