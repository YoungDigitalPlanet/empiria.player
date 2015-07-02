package eu.ydp.empiria.player.client.util.events.external;

import com.google.common.base.Optional;
import com.google.gwt.core.client.JavaScriptObject;

public class ExternalEventDispatcher {

	private Optional<ExternalCallback> optionalCallback = Optional.absent();

	public void setCallbackFunction(ExternalCallback callback) {
		this.optionalCallback = Optional.of(callback);
	}

	public void dispatch(ExternalEvent externalEvent) {
		if (optionalCallback.isPresent()) {
			ExternalCallback callback = optionalCallback.get();
			JavaScriptObject jsonEvent = externalEvent.getJSONObject();
			callback.callback(jsonEvent);
		}
	}
}
