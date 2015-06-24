package eu.ydp.empiria.player.client.util.events.dispatcher;

import com.google.common.base.Optional;
import com.google.gwt.core.client.JavaScriptObject;
import eu.ydp.empiria.player.client.util.events.pagechange.Event;

public class EventDispatcher {

	private Optional<JavaScriptFunction> callbackFunction = Optional.absent();

	public void setCallbackFunction(JavaScriptFunction callbackFunction) {
		this.callbackFunction = Optional.of(callbackFunction);
	}

	public void dispatch(Event event) {
		if (callbackFunction.isPresent()) {
			JavaScriptFunction jsCallbackFunction = callbackFunction.get();
			JavaScriptObject jsObjectWithPayload = event.getJSObject();
			jsCallbackFunction.callback(jsObjectWithPayload);
		}
	}
}
