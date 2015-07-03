package eu.ydp.empiria.player.client.controller.multiview;

import com.google.common.collect.*;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.collections.KeyValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class VisiblePagesManager {

    @Inject
    private PanelCache panelCache;
    private int visiblePageCount = 1;

    public void setVisiblePageCount(int visiblePageCount) {
        this.visiblePageCount = visiblePageCount;
    }

    private Set<Integer> getPageIndexScanRange(int currentVisiblePage) {
        if (visiblePageCount == 0) {
            return ImmutableSet.of();
        }
        int halfToShowPages = (int) Math.ceil(visiblePageCount / 2d);
        int start = currentVisiblePage - halfToShowPages;
        int end = currentVisiblePage + halfToShowPages;
        return ContiguousSet.create(Range.open(start, end), DiscreteDomain.integers());
    }

    public List<Integer> getPagesToAttache(int currentVisiblePage) {
        Set<Integer> deatachedPageIds = getDetachedPagesIds();
        ArrayList<Integer> toAttacheIds = Lists.newArrayList();

        Set<Integer> indexScanRange = getPageIndexScanRange(currentVisiblePage);
        for (int page : indexScanRange) {
            if (deatachedPageIds.contains(page)) {
                toAttacheIds.add(page);
            }
        }
        return toAttacheIds;
    }

    private Set<Integer> getDetachedPagesIds() {
        Set<Integer> createdPageIds = Sets.newHashSet(panelCache.getCache().keySet());
        Set<Integer> activePagesIds = getActivePagesIds();
        createdPageIds.removeAll(activePagesIds);
        return createdPageIds;
    }

    public Set<Integer> getPagesToDetach(int currentVisiblePage) {
        Set<Integer> activePanels = getActivePagesIds();

        Set<Integer> indexScanRange = getPageIndexScanRange(currentVisiblePage);
        for (int page : indexScanRange) {
            activePanels.remove(page);
        }

        return activePanels;
    }

    private Set<Integer> getActivePagesIds() {
        Map<Integer, KeyValue<FlowPanel, FlowPanel>> cache = panelCache.getCache();
        Set<Integer> activePanels = Sets.newHashSet();
        for (Entry<Integer, KeyValue<FlowPanel, FlowPanel>> entry : cache.entrySet()) {
            Integer key = entry.getKey();
            KeyValue<FlowPanel, FlowPanel> value = entry.getValue();
            if (value.getValue().isAttached()) {
                activePanels.add(key);
            }
        }
        return activePanels;
    }
}
