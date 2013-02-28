package eu.ydp.empiria.player.client.controller.variables.processor.item;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.thirdparty.guava.common.collect.Lists;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

public class GroupedResponseManager {

	private Map<String, List<Response>> groupNameToResponsesMap;
	
	
	
	
	public void initialize(Map<String, Response> responses){
		groupNameToResponsesMap = new HashMap<String, List<Response>>();
		assignResponsesToGroups(responses);
	}

	private void assignResponsesToGroups(Map<String, Response> responses) {
		Set<Entry<String, Response>> entrySet = responses.entrySet();
		for (Entry<String, Response> responseEntry : entrySet) {
			Response response = responseEntry.getValue();
			addResponseToRelatedGroups(response);
		}
	}

	private void addResponseToRelatedGroups(Response response) {
		List<String> groups = response.groups;
		for (String groupName : groups) {
			addResponseToGroup(groupName, response);
		}
	}

	private void addResponseToGroup(String groupName, Response response) {
		List<Response> alreadyAssignedResponses = groupNameToResponsesMap.get(groupName);
		if(alreadyAssignedResponses == null){
			alreadyAssignedResponses = Lists.newArrayList();
			groupNameToResponsesMap.put(groupName, alreadyAssignedResponses);
		}
		alreadyAssignedResponses.add(response);
	}
	
}
