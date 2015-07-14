package eu.ydp.empiria.player.client.module.item;

import com.google.common.collect.Range;
import eu.ydp.gwtutil.client.StringUtils;
import eu.ydp.gwtutil.client.collections.SimpleRangeMap;

public class ProgressToStringRangeMap {

    private static final int PROGRESS_MIN = 0;
    private static final int PROGRESS_MAX = 100;

    private final SimpleRangeMap<Integer, String> feedbacks = SimpleRangeMap.<Integer, String>create();

    public ProgressToStringRangeMap() {
        fillMappingOutOfRange();
    }

    private void fillMappingOutOfRange() {
        feedbacks.put(Range.closedOpen(Integer.MIN_VALUE, PROGRESS_MIN), "");
        feedbacks.put(Range.openClosed(PROGRESS_MAX, Integer.MAX_VALUE), "");
    }

    public void addValueForRange(Range<Integer> range, String value) {
        feedbacks.put(range, value);
    }

    public String getValueForProgress(int progress) {
        String feedback;
        feedback = feedbacks.get(progress);
        if (feedback != null) {
            return feedback;
        }
        return StringUtils.EMPTY_STRING;
    }
}
