package eu.ydp.empiria.player.client.controller.multiview;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import eu.ydp.gwtutil.client.collections.KeyValue;

public class PagePlaceHolderPanelCreator {

	private PanelCache panelsCache;
	private FlowPanel mainFlowPanel;
	
	@Inject
	public PagePlaceHolderPanelCreator(PanelCache panelsCache, @Named("multiPageControllerMainPanel") FlowPanel mainFlowPanel) {
		this.panelsCache = panelsCache;
		this.mainFlowPanel = mainFlowPanel;
	}

	public void createPanelsUntilIndex(int index){
		int firstPanelToCreateIndex = 0;
		if(!panelsCache.isEmpty()){
			int currentMaxIndex = getMaxIndexOfCachedPanels();
			firstPanelToCreateIndex = currentMaxIndex + 1;
		}
		
		createAndPutToCachePanelsForIndexes(firstPanelToCreateIndex, index);
	}
	
	private int getMaxIndexOfCachedPanels() {
		Map<Integer, KeyValue<FlowPanel, FlowPanel>> cache = panelsCache.getCache();
		Set<Integer> cachedPagesIndexes = cache.keySet();
		return Collections.max(cachedPagesIndexes);
	}

	private void createAndPutToCachePanelsForIndexes(int startIndex, int endIndex){
		for(int i=startIndex; i<=endIndex; i++){
			createAndPutToCache(i);
		}
	}

	private KeyValue<FlowPanel, FlowPanel> createAndPutToCache(int index) {
		KeyValue<FlowPanel, FlowPanel> panel = panelsCache.getOrCreateAndPut(index);
		mainFlowPanel.add(panel.getKey());
		return panel;
	}
	
}
