package eu.ydp.empiria.player.client.module.info.progress;

import com.google.common.collect.Range;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.module.item.ProgressToStringRangeMap;

import javax.annotation.PostConstruct;
import java.util.Map;

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

    private ProgressToStringRangeMap progressToStyleName;
    private InfoModuleCssProgressParser cssMappingParser;

    @Inject
    public InfoModuleProgressMapping(@Assisted InfoModuleCssProgressParser cssMappingParser,
                                     ProgressToStringRangeMap progressToStyleName) {
        this.cssMappingParser = cssMappingParser;
        this.progressToStyleName = progressToStyleName;
    }

    @PostConstruct
    public void postConstruct() {
        fillDefinedMappingRanges();
    }


    private void fillDefinedMappingRanges() {
        RangeMapping range = new RangeMapping(0, "");
        Map<Integer, String> cssProgressToStyleMapping = cssMappingParser.getCssProgressToStyleMapping();
        for (int percent = 0; percent <= 100; ++percent) {
            if (cssProgressToStyleMapping.containsKey(percent)) {
                progressToStyleName.addValueForRange(Range.closedOpen(range.getRangeStart(), percent), range.getRangeStyleName());
                range = new RangeMapping(percent, cssProgressToStyleMapping.get(percent));
            }
        }
        progressToStyleName.addValueForRange(Range.closed(range.getRangeStart(), 100), range.getRangeStyleName());
    }

    public String getStyleNameForProgress(Integer progress) {
        if (progress == null) {
            return "";
        }
        return progressToStyleName.getValueForProgress(progress);
    }

}
