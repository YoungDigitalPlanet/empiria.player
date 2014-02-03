package eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gwt.core.client.JsArray;

import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.js.ProgressAssetConfigJs;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.js.ProgressConfigJs;

public class ProgressConfig {

	private final int from;
	private final List<ProgressAssetConfig> assets;

	public ProgressConfig(int from, List<ProgressAssetConfig> assets) {
		this.from = from;
		this.assets = assets;
	}

	public int getFrom() {
		return this.from;
	}

	public List<ProgressAssetConfig> getAssets() {
		return this.assets;
	}

	public static ProgressConfig fromJs(ProgressConfigJs jsAction) {
		int from = jsAction.getFrom();
		List<ProgressAssetConfig> assets = getAssets(jsAction.getAssets());
		return new ProgressConfig(from, assets);
	}

	private static List<ProgressAssetConfig> getAssets(JsArray<ProgressAssetConfigJs> progresses) {
		List<ProgressAssetConfig> assets = Lists.newArrayList();

		for (int i = 0; i < progresses.length(); i++) {
			ProgressAssetConfigJs configJs = progresses.get(i);
			ProgressAssetConfig asset = ProgressAssetConfig.fromJs(configJs);
			assets.add(asset);
		}
		return assets;
	}

}
