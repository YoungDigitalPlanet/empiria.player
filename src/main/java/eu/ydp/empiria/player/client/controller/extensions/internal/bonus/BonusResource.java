package eu.ydp.empiria.player.client.controller.extensions.internal.bonus;

import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.js.BonusResourceJs;
import eu.ydp.gwtutil.client.util.geom.Size;

public class BonusResource {

	private final String asset;
	private final BonusResourceType type;
	private final Size size;

	public BonusResource(String asset, BonusResourceType type, Size size) {
		this.asset = asset;
		this.type = type;
		this.size = size;
	}

	public String getAsset() {
		return this.asset;
	}

	public BonusResourceType getType() {
		return this.type;
	}

	public Size getSize() {
		return this.size;
	}

	public static BonusResource fromJs(BonusResourceJs jsBonus) {
		String asset = jsBonus.getAsset();
		String stringType = jsBonus.getType();
		BonusResourceType type = BonusResourceType.valueOf(stringType);
		Size size = new Size(jsBonus.getWidth(), jsBonus.getHeight());

		return new BonusResource(asset, type, size);
	}
}
