package eu.ydp.empiria.player.client.module.external.state;

import com.google.common.base.Optional;
import com.google.gwt.core.client.JavaScriptObject;

public class ExternalInteractionStateSaver {

	private Optional<JavaScriptObject> externalState = Optional.absent();

	public Optional<JavaScriptObject> getExternalState() {
		return externalState;
	}

	public void setExternalState(JavaScriptObject externalState) {
		this.externalState = Optional.of(externalState);
	}
}
