package eu.ydp.empiria.player.client.controller.multiview;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.google.gwt.dev.util.collect.HashMap;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.gwtutil.client.collections.KeyValue;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@RunWith(ExMockRunner.class)
@PrepareForTest({FlowPanel.class, Widget.class})
public class PagePlaceHolderPanelCreatorJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private PagePlaceHolderPanelCreator placeHolderPanelCreator;
	private PanelCache panelsCache;
	private FlowPanel mainFlowPanel;
	

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}
	
	@Before
	public void prepareTests(){
		panelsCache = mock(PanelCache.class);
		mainFlowPanel = mock(FlowPanel.class);
		placeHolderPanelCreator = new PagePlaceHolderPanelCreator(panelsCache, mainFlowPanel);
	}
	
	@After
	public void tearDown() throws Exception {
		Mockito.verifyNoMoreInteractions(panelsCache, mainFlowPanel);
	}

	@Test
	public void testCreatePanelsUntilIndex_cacheIsEmpty() throws Exception {
		int pageIndex = 1;
		
		when(panelsCache.isEmpty())
			.thenReturn(true);
		
		FlowPanel zeroPanelPlaceHolderKey = mock(FlowPanel.class);
		FlowPanel zeroPanelPlaceHolderValue = mock(FlowPanel.class);
		KeyValue<FlowPanel, FlowPanel> zeroPanelPlaceHolderPair = new KeyValue<FlowPanel, FlowPanel>(zeroPanelPlaceHolderKey, zeroPanelPlaceHolderValue);
		when(panelsCache.getOrCreateAndPut(0))
			.thenReturn(zeroPanelPlaceHolderPair);
		
		FlowPanel firstPanelPlaceHolderKey = mock(FlowPanel.class);
		FlowPanel firstPanelPlaceHolderValue = mock(FlowPanel.class);
		KeyValue<FlowPanel, FlowPanel> firstPanelPlaceHolderPair = new KeyValue<FlowPanel, FlowPanel>(firstPanelPlaceHolderKey, firstPanelPlaceHolderValue);
		when(panelsCache.getOrCreateAndPut(1))
			.thenReturn(firstPanelPlaceHolderPair);
		
		placeHolderPanelCreator.createPanelsUntilIndex(pageIndex);
		
		
		verify(panelsCache).isEmpty();
		verify(panelsCache).getOrCreateAndPut(0);
		verify(mainFlowPanel).add(zeroPanelPlaceHolderKey);
		
		verify(panelsCache).getOrCreateAndPut(1);
		verify(mainFlowPanel).add(firstPanelPlaceHolderKey);
	}
	
	@Test
	public void testCreatePanelsUntilIndex_cacheIsNotEmpty() throws Exception {
		int pageIndex = 2;
		
		when(panelsCache.isEmpty())
			.thenReturn(false);
		
		Map<Integer, KeyValue<FlowPanel, FlowPanel>> panelsCacheMap = preparePanelsCacheMap(0, 1);
		when(panelsCache.getCache())
			.thenReturn(panelsCacheMap);
		
		FlowPanel secondPanelPlaceHolderKey = mock(FlowPanel.class);
		FlowPanel secondPanelPlaceHolderValue = mock(FlowPanel.class);
		KeyValue<FlowPanel, FlowPanel> secondPanelPlaceHolderPair = new KeyValue<FlowPanel, FlowPanel>(secondPanelPlaceHolderKey, secondPanelPlaceHolderValue);
		when(panelsCache.getOrCreateAndPut(2))
			.thenReturn(secondPanelPlaceHolderPair);
		
		placeHolderPanelCreator.createPanelsUntilIndex(pageIndex);
		
		
		verify(panelsCache).isEmpty();
		verify(panelsCache).getCache();
		verify(panelsCache).getOrCreateAndPut(2);
		verify(mainFlowPanel).add(secondPanelPlaceHolderKey);
	}

	private Map<Integer, KeyValue<FlowPanel, FlowPanel>> preparePanelsCacheMap(int ... indexes) {
		Map<Integer, KeyValue<FlowPanel, FlowPanel>> panelsCacheMap = new HashMap<Integer, KeyValue<FlowPanel,FlowPanel>>();
		for(int index : indexes){
			panelsCacheMap.put(index, new KeyValue<FlowPanel, FlowPanel>());
		}
		return panelsCacheMap;
	}
}
