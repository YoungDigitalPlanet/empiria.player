package eu.ydp.empiria.player.client.controller.assets;

import com.google.inject.Inject;

public class AssetOpenDelegatorService {

	@Inject
	private AssetOpenJSDelegator assetOpenJSDelegator;

	@Inject
	private URLOpenService urlOpenService;

	public void open(String path) {
		if (externalLinkSupported()) {
			delegateToExternal(path);
		} else {
			openInWindow(path);
		}
	}

	private boolean externalLinkSupported() {
		return assetOpenJSDelegator.empiriaExternalLinkSupported();
	}

	private void delegateToExternal(String path) {
		assetOpenJSDelegator.empiriaExternalLinkOpen(path);
	}

	private void openInWindow(String path) {
		urlOpenService.open(path);
	}
}
