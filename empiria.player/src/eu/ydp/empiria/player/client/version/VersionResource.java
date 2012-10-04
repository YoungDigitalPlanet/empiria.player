package eu.ydp.empiria.player.client.version;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface VersionResource extends ClientBundle {
	
	@Source("version.txt")
	TextResource getVersionResource();
	
}
