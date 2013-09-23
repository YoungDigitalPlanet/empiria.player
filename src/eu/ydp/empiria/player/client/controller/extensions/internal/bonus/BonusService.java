package eu.ydp.empiria.player.client.controller.extensions.internal.bonus;

import java.util.Map;

import com.google.common.collect.Maps;

public class BonusService {

	private final Map<String, BonusConfig> cache = Maps.newHashMap();

	public void registerBonus(String bonusId, BonusConfig bonusConfig) {
		cache.put(bonusId, bonusConfig);
	}

	public BonusConfig getBonusConfig(String bonusId) {
		return cache.get(bonusId);
	}
}
