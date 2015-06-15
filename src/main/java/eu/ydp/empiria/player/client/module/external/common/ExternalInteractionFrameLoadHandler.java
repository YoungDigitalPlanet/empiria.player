package eu.ydp.empiria.player.client.module.external.common;

import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalInteractionApi;

public interface ExternalInteractionFrameLoadHandler {
	void onExternalModuleLoaded(ExternalInteractionApi externalObject);
}
