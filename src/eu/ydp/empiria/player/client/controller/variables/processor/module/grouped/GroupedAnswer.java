package eu.ydp.empiria.player.client.controller.variables.processor.module.grouped;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

public class GroupedAnswer {

	private String value;
	private boolean isUsed;
	private Response usedByResponse;

	public GroupedAnswer(String value) {
		this(value, false, null);
	}
	public GroupedAnswer(String value, boolean isUsed, Response usedByResponse) {
		this.value = value;
		this.isUsed = isUsed;
		this.usedByResponse = usedByResponse;
	}
	public boolean isUsed() {
		return isUsed;
	}
	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}
	public Response getUsedByResponse() {
		return usedByResponse;
	}
	public void setUsedByResponse(Response usedByResponse) {
		this.usedByResponse = usedByResponse;
	}
	public String getValue() {
		return value;
	}
	
}
