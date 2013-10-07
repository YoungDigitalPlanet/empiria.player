package eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress;

import eu.ydp.gwtutil.client.util.geom.Size;

public class ProgressAssetConfig {

	private String path;
	private Size size;

	public String getPath() {
		return path;
	}

	public int getCount() {
		return 0;
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
