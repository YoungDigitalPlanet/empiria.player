package eu.ydp.empiria.player.client.module.info.progress;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.gin.binding.CachedModuleScoped;
import eu.ydp.empiria.player.client.style.ModuleStyle;
import eu.ydp.gwtutil.client.NumberUtils;

import java.util.Map;

public class InfoModuleCssProgressParser {

    private ModuleStyle moduleStyle;
    private String prefix;

    @Inject
    public InfoModuleCssProgressParser(@Assisted String prefix, @CachedModuleScoped ModuleStyle moduleStyle) {
        this.prefix = prefix;
        this.moduleStyle = moduleStyle;
    }

    public Map<Integer, String> getCssProgressToStyleMapping() {
        final Map<Integer, String> progressToStyleNameMapping = Maps.newHashMap();
        for (Map.Entry<String, String> cssEntry : moduleStyle.entrySet()) {
            String key = cssEntry.getKey();
            String value = cssEntry.getValue();
            if (isPrefixedEntry(prefix, key)) {
                Integer progress = getProgressPercentValue(prefix, key);
                if (progress != null) {
                    progressToStyleNameMapping.put(progress, value);
                }
            }
        }
        return progressToStyleNameMapping;
    }

    private Integer getProgressPercentValue(String prefix, String key) {
        String progressValue = key.replace(prefix, "");
        return NumberUtils.tryParseInt(progressValue, null);
    }

    private boolean isPrefixedEntry(String prefix, String cssEntry) {
        return cssEntry.startsWith(prefix);
    }
}
