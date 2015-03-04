package eu.ydp.empiria.player.client.module.bonus;

import eu.ydp.gwtutil.client.util.geom.Size;

public interface BonusWithAsset extends Bonus {

	void setAsset(String url, Size size);
}
