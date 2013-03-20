package eu.ydp.empiria.player.client.controller.variables.objects.response;

import java.util.Map;

import com.google.common.collect.Maps;

public class ResponsesMapBuilder {

	public Map<String, Response> buildResponsesMap(Response ...responses ){
		Map<String, Response> responsesMap = Maps.newHashMap();
		
		for (Response response : responses) {
			responsesMap.put(response.identifier, response);
		}
		return responsesMap;
	}
	
}
