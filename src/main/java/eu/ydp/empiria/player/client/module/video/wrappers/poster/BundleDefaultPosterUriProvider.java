package eu.ydp.empiria.player.client.module.video.wrappers.poster;

import com.google.gwt.core.shared.GWT;

public class BundleDefaultPosterUriProvider implements DefaultPosterUriProvider {
	private static final PosterResources RESOURCES = GWT.create(PosterResources.class);

	@Override
	public String getDefaultPosterUri() {
		return RESOURCES.defaultPoster().getSafeUri().asString();
	}
}
