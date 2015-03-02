package eu.ydp.empiria.player.client.version;

import com.google.gwt.core.client.GWT;

public class Version {

	private static VersionResource versionResource = GWT.create(VersionResource.class);

	public static String getVersion() {
		return versionResource.getVersionResource().getText();
	}

}
