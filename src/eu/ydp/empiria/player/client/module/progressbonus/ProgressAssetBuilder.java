package eu.ydp.empiria.player.client.module.progressbonus;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;

public class ProgressAssetBuilder {

	@Inject
	private ProgressAsset progressAsset;
	private final Map<Integer, List<ShowImageDTO>> builderMap = Maps.newTreeMap();

	public void add(int from, List<ShowImageDTO> dtos) {
		builderMap.put(from, dtos);
	}

	public ProgressAsset build() {
		int lowerBound = Integer.MIN_VALUE;
		List<ShowImageDTO> dtos = Lists.newArrayList();
		for (Entry<Integer, List<ShowImageDTO>> element : builderMap.entrySet()) {
			Range<Integer> range = Range.closedOpen(lowerBound, element.getKey());
			progressAsset.add(range, dtos);
			lowerBound = element.getKey();
			dtos = element.getValue();
		}
		Range<Integer> lastRange = Range.atLeast(lowerBound);
		progressAsset.add(lastRange, dtos);

		return progressAsset;
	}
}
