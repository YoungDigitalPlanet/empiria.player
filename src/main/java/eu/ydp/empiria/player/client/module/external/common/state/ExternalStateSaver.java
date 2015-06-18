package eu.ydp.empiria.player.client.module.external.common.state;

import com.google.common.base.Optional;
import com.google.gwt.core.client.JavaScriptObject;

public class ExternalStateSaver {

	private Optional<JavaScriptObject> externalState = Optional.absent();

	public Optional<JavaScriptObject> getExternalState() {
		return externalState;
	}

	public void setExternalState(JavaScriptObject externalState) {
		this.externalState = Optional.of(externalState);
	}
}
