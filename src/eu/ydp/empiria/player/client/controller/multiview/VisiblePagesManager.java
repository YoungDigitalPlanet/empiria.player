package eu.ydp.empiria.player.client.controller.multiview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;

import eu.ydp.gwtutil.client.collections.KeyValue;

public class VisiblePagesManager {

	@Inject private PanelCache panelCache;
	private int visiblePageCount;

	public void setVisiblePageCount(int visiblePageCount) {
		this.visiblePageCount = visiblePageCount;
	}

	private List<Integer> getPageIndexScanRange(int currentVisiblePage) {
		int halfToShowPages = (int) Math.ceil(visiblePageCount / 2d);
		int start = currentVisiblePage - halfToShowPages + 1;
		int end = currentVisiblePage + halfToShowPages;
		List<Integer> range = Lists.newArrayList();
		for (int page = start; page < end; ++page) {
			range.add(page);
		}
		return range;
	}

	public List<Integer> getPagesToAttache(int currentVisiblePage) {
		Set<Integer> deatachedPageIds = getDetachedPagesIds();
		ArrayList<Integer> toAttacheIds = Lists.newArrayList();

		List<Integer> indexScanRange = getPageIndexScanRange(currentVisiblePage);
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

		List<Integer> indexScanRange = getPageIndexScanRange(currentVisiblePage);
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
