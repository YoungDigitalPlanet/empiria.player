package eu.ydp.empiria.player.client.module.progressbonus;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.gwtutil.client.util.RandomWrapper;
import eu.ydp.gwtutil.client.util.geom.Size;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ProgressAssetProvider {

    @Inject
    private RandomWrapper random;
    @Inject
    private ProgressConfigResolver configResolver;

    private final Function<List<ShowImageDTO>, Integer> listSizeExtractor = new Function<List<ShowImageDTO>, Integer>() {

        @Override
        public Integer apply(List<ShowImageDTO> images) {
            return images.size();
        }
    };

    public ProgressAsset createFrom(int index) {
        Map<Integer, List<ShowImageDTO>> resolvedConfig = configResolver.resolveProgressConfig();
        return buildProgressAsset(resolvedConfig, index);
    }

    public ProgressAsset createRandom() {
        Map<Integer, List<ShowImageDTO>> resolvedConfig = configResolver.resolveProgressConfig();
        int randomIndex = getRandomIndex(resolvedConfig);
        return buildProgressAsset(resolvedConfig, randomIndex);
    }

    private int getRandomIndex(Map<Integer, List<ShowImageDTO>> resolvedConfig) {
        int largestDTOsSize = largestSize(resolvedConfig);
        return random.nextInt(largestDTOsSize);
    }

    private int largestSize(Map<Integer, List<ShowImageDTO>> resolvedConfig) {
        final List<ShowImageDTO> longestList = Ordering.natural().onResultOf(listSizeExtractor).max(resolvedConfig.values());
        return longestList.size();
    }

    private ProgressAsset buildProgressAsset(Map<Integer, List<ShowImageDTO>> resolvedConfig, int index) {
        ProgressAsset progressAsset = new ProgressAsset(index);
        int lowerBound = Integer.MIN_VALUE;
        ShowImageDTO imageDTO = new ShowImageDTO("", new Size(0, 0));
        for (Entry<Integer, List<ShowImageDTO>> element : resolvedConfig.entrySet()) {
            Range<Integer> range = Range.closedOpen(lowerBound, element.getKey());
            progressAsset.add(range, imageDTO);
            lowerBound = element.getKey();
            imageDTO = getElementAtIndex(element.getValue(), index);
        }
        Range<Integer> lastRange = Range.atLeast(lowerBound);
        progressAsset.add(lastRange, imageDTO);

        return progressAsset;
    }

    private ShowImageDTO getElementAtIndex(final List<ShowImageDTO> images, int index) {
        if (index >= images.size()) {
            index = images.size() - 1;
        }
        return images.get(index);
    }
}
