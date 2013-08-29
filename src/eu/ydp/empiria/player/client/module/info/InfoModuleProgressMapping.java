package eu.ydp.empiria.player.client.module.info;

import java.util.Map;

import javax.annotation.PostConstruct;

import com.google.common.collect.Range;
import com.google.inject.Inject;

import eu.ydp.gwtutil.client.collections.SimpleRangeMap;

public class InfoModuleProgressMapping {
	private class RangeMapping {
		private final int rangeStart;
		private final String RangeStyleName;

		public RangeMapping(int rangeStart, String RangeStyleName) {
			this.rangeStart = rangeStart;
			this.RangeStyleName = RangeStyleName;
		}

		public int getRangeStart() {
			return rangeStart;
		}

		public String getRangeStyleName() {
			return RangeStyleName;
		}
	}

	@Inject private InfoModuleCssProgressMappingConfigurationParser cssMappingParser;
	private final SimpleRangeMap<Integer, String> progressToStyleName = SimpleRangeMap.<Integer, String> create();

	@PostConstruct
	public void postConstruct() {
		fillDefinedMappingRanges();
		fillMappingOutOfRange();
	}

	private void fillMappingOutOfRange() {
		progressToStyleName.put(Range.closed(101, Integer.MAX_VALUE), "");
		progressToStyleName.put(Range.closedOpen(Integer.MIN_VALUE, 0), "");
	}

	private void fillDefinedMappingRanges() {
		RangeMapping range = new RangeMapping(0, "");
		Map<Integer, String> cssProgressToStyleMapping = cssMappingParser.getCssProgressToStyleMapping();
		for (int percent = 0; percent <= 100; ++percent) {
			if (cssProgressToStyleMapping.containsKey(percent)) {
				progressToStyleName.put(Range.closedOpen(range.getRangeStart(), percent), range.getRangeStyleName());
				range = new RangeMapping(percent, cssProgressToStyleMapping.get(percent));
			}
		}
		progressToStyleName.put(Range.closed(range.getRangeStart(), 100), range.getRangeStyleName());
	}

	public String getStyleNameForProgress(Integer progress) {
		if (progress == null) {
			return "";
		}
		return progressToStyleName.get(progress);
	}

}
