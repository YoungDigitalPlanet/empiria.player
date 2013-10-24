package eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress;

import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.js.ProgressAssetConfigJs;
import eu.ydp.gwtutil.client.util.geom.Size;

public class ProgressAssetConfig {

	private final Size size;
	private final String path;
	private final int count;

	public ProgressAssetConfig(String path, int count, Size size) {
		this.path = path;
		this.count = count;
		this.size = size;
	}

	public String getPath() {
		return this.path;
	}

	public int getCount() {
		return this.count;
	}

	public Size getSize() {
		return this.size;
	}

	public static ProgressAssetConfig fromJs(ProgressAssetConfigJs configJs) {
		Size size = new Size(configJs.getWidth(), configJs.getHeight());
		String path = configJs.getAsset();
		int count = configJs.getCount();
		return new ProgressAssetConfig(path, count, size);
	}
}
