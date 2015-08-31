package eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;

import java.util.Map;

@Singleton
public class ProgressBonusService {

    private final Map<String, ProgressBonusConfig> cache = Maps.newHashMap();

    public void register(String id, ProgressBonusConfig config) {
        cache.put(id, config);
    }

    public ProgressBonusConfig getProgressBonusConfig(String id) {
        return cache.get(id);
    }
}
