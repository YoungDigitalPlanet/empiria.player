package eu.ydp.empiria.player.client.controller.events.interaction;

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;

public abstract class InteractionEvent {

	public abstract InteractionEventType getType();

	public abstract Map<String, Object> getParams();

	public static InteractionEvent fromJsObject(JavaScriptObject jsObject){
		String type = getJsObjectType(jsObject);

		if (type.equals(InteractionEventType.STATE_CHANGED.toString())){
			try{
				boolean userInteract = getJsObjectUserInteract(jsObject);
				JavaScriptObject senderJs = getJsObjectSender(jsObject);
				return new StateChangedInteractionEvent(userInteract, false, null);
			} catch (Exception e) {
			}
		}

		if (type.equals(InteractionEventType.FEEDBACK_SOUND.toString())){
			try{
				String url = getJsObjectUrl(jsObject);
				return new FeedbackInteractionSoundEvent(url);
			} catch (Exception e) {
			}
		}

		if (type.equals(InteractionEventType.FEEDBACK_MUTE.toString())){
			try{
				boolean mute = getJsObjectMute(jsObject);
				return new FeedbackInteractionMuteEvent(mute);
			} catch (Exception e) {
			}
		}

		return null;
	}

	private static native String getJsObjectType(JavaScriptObject obj)/*-{
		if (typeof obj.type == 'string')
			return obj.type;
		return "";
	}-*/;
	private static native JavaScriptObject getJsObjectSender(JavaScriptObject obj)/*-{
		if (typeof obj.sender == 'object')
			return obj.sender;
		return [];
	}-*/;
	private static native boolean getJsObjectUserInteract(JavaScriptObject obj)/*-{
		if (typeof obj.userInteract == 'boolean')
			return obj.userInteract;
		return "";
	}-*/;
	private static native String getJsObjectUrl(JavaScriptObject obj)/*-{
		if (typeof obj.url == 'string')
			return obj.url;
		return "";
	}-*/;
	private static native boolean getJsObjectMute(JavaScriptObject obj)/*-{
		if (typeof obj.mute == 'boolean')
			return obj.mute;
		return false;
	}-*/;
}
