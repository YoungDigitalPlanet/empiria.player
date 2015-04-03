package eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress;

import java.util.Map;

import com.google.common.collect.Maps;

public class ProgressBonusService {

	private final Map<String, ProgressBonusConfig> cache = Maps.newHashMap();

	public void register(String id, ProgressBonusConfig config) {
		cache.put(id, config);
	}

	public ProgressBonusConfig getProgressBonusConfig(String id) {
		return cache.get(id);
	}
}