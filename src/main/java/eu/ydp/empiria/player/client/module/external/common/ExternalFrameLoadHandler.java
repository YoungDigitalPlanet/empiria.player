package eu.ydp.empiria.player.client.module.external.common;

import eu.ydp.empiria.player.client.module.external.common.api.ExternalApi;

public interface ExternalFrameLoadHandler<T extends ExternalApi> {
	void onExternalModuleLoaded(T externalObject);
}
