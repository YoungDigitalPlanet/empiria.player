package eu.ydp.empiria.player.client.controller.events.activity;

import com.google.gwt.core.client.JavaScriptObject;

import eu.ydp.empiria.player.client.module.containers.group.GroupIdentifier;

public class FlowActivityEvent {

	protected FlowActivityEventType type;
	protected GroupIdentifier groupIdentifier;

	public FlowActivityEvent(FlowActivityEventType type, GroupIdentifier groupId) {
		this.type = type;
		this.groupIdentifier = groupId;
	}

	public FlowActivityEventType getType() {
		return type;
	}

	public GroupIdentifier getGroupIdentifier() {
		return groupIdentifier;
	}

	public static FlowActivityEvent fromJsObject(JavaScriptObject jsObject) {
		String currTypeString = getTypeJs(jsObject);
		final String groupIdentifierString = getGroupIdentifierJs(jsObject); // NOPMD
		if (currTypeString != null) {
			currTypeString = currTypeString.trim().toUpperCase();
			for (FlowActivityEventType currType : FlowActivityEventType.values()) {
				if (currType.toString().equals(currTypeString)) {
					return new FlowActivityEvent(currType, new GroupIdentifier() { // NOPMD

								@Override
								public String getIdentifier() {
									return groupIdentifierString;
								}
							});
				}
			}
		}
		return null;
	}

	private static native String getTypeJs(JavaScriptObject jsObject)/*-{
																		if (typeof jsObject.type == 'string') {
																		return jsObject.type;
																		}
																		return "";
																		}-*/;

	private static native String getGroupIdentifierJs(JavaScriptObject jsObject)/*-{
																				if (typeof jsObject.groupIdentifier == 'string') {
																				return jsObject.groupIdentifier;
																				}
																				return "";
																				}-*/;

}
