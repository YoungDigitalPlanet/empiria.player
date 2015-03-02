package eu.ydp.empiria.player.client.controller.extensions.internal.media.external;

public interface FullscreenVideoConnector {

	void addConnectorListener(String id, FullscreenVideoConnectorListener listener);

	void openFullscreen(String id, Iterable<String> sources, double currentTimeMillis);
}
