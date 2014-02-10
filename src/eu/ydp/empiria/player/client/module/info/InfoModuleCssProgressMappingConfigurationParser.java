package eu.ydp.empiria.player.client.module.info;

import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.gin.binding.CachedModuleScoped;
import eu.ydp.empiria.player.client.style.ModuleStyle;
import eu.ydp.gwtutil.client.NumberUtils;

public class InfoModuleCssProgressMappingConfigurationParser {
	private static final String REPORT_PROGRESS_PREFIX = "-empiria-info-item-result-";

	@Inject
	@CachedModuleScoped
	private ModuleStyle moduleStyle;

	public Map<Integer, String> getCssProgressToStyleMapping() {
		final Map<Integer, String> progressToStyleNameMapping = Maps.newHashMap();
		for (Map.Entry<String, String> cssEntry : moduleStyle.entrySet()) {
			if (isReportProgressEntry(cssEntry) && isNotEmptyValue(cssEntry)) {
				processProgressEntry(progressToStyleNameMapping, cssEntry);
			}
		}
		return progressToStyleNameMapping;
	}

	private void processProgressEntry(final Map<Integer, String> progressToStyleName, Map.Entry<String, String> cssEntry) {
		final Integer progress = getProgressPercentValue(cssEntry);
		if (progress != null) {
			progressToStyleName.put(progress, cssEntry.getValue());
		}
	}

	private Integer getProgressPercentValue(Map.Entry<String, String> cssEntry) {
		String progressValue = cssEntry.getKey().replace(REPORT_PROGRESS_PREFIX, "");
		return NumberUtils.tryParseInt(progressValue, null);
	}

	private boolean isNotEmptyValue(Map.Entry<String, String> cssEntry) {
		return !Strings.isNullOrEmpty(cssEntry.getValue());
	}

	private boolean isReportProgressEntry(Map.Entry<String, String> cssEntry) {
		return cssEntry.getKey().startsWith(REPORT_PROGRESS_PREFIX);
	}
}
