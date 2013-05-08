package eu.ydp.empiria.player.client.module.expression;

import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

public class ResponsesTestingHelper {
	
	public Response getResponse(String identifier, String value) {
		return new Response(null, Lists.newArrayList(value), null, identifier, null, null, null);
	}
}
