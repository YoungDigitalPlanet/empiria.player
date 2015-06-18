package eu.ydp.empiria.player.client.module.external.interaction.api;

import com.google.gwt.core.client.js.JsProperty;
import com.google.gwt.core.client.js.JsType;

@JsType
public interface ExternalInteractionStatus {
	@JsProperty
	int getErrors();

	@JsProperty
	int getDone();
}
