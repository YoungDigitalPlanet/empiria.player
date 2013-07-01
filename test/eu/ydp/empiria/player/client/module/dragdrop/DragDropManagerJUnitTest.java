package eu.ydp.empiria.player.client.module.dragdrop;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.google.common.collect.ArrayListMultimap;
//import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Multimap;
import com.google.gwt.junit.GWTMockUtilities;

import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ParentedModuleBase;
import eu.ydp.empiria.player.client.module.simpletext.SimpleTextModule;
import eu.ydp.empiria.player.client.module.sourcelist.SourceListModule;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEvent;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@RunWith(ExMockRunner.class)
@PrepareForTest({ ParentedModuleBase.class })
public class DragDropManagerJUnitTest {

//	private static final String TEST1 = "kot";
//	private static final String TEST2 = "tygrys";

	private DragDropManager dragDropManager;

	@Before
	public void init() {
		dragDropManager = new DragDropManager();
		dragDropManager.eventsBus = mock(EventsBus.class);
		dragDropManager.scopeFactory = mock(PageScopeFactory.class);
		dragDropManager.helper = mock(DragDropManagerHelper.class);
	}

	@Test
	public void registerDropZoneCurrentContainerSourcelists() {
		SimpleTextModule module = mock(SimpleTextModule.class);
		HasChildren parentModule = mockParentModule(module);
		buildSourcelistsCollection(parentModule);
		DragDropEvent eventMock = mockDragDropEvent(module, DragDropEventTypes.REGISTER_DROP_ZONE, null);

		dragDropManager.registerDropZone(eventMock);
		dragDropManager.onPlayerEvent(new PlayerEvent(PlayerEventTypes.PAGE_LOADED));

		assertThat(dragDropManager.dropZones.size(), is(equalTo(2)));
	}

	@Test
	public void registerDropZoneMultipleSourcelists() {
		SimpleTextModule module = mock(SimpleTextModule.class);
		HasChildren parentModule = mockParentModule(module);
		buildSourcelistsCollection(parentModule);
		HasChildren topParentModule = mock(HasChildren.class);
		when(parentModule.getParentModule()).thenReturn(topParentModule);
		buildSourcelistsCollection(topParentModule);
		DragDropEvent eventMock = mockDragDropEvent(module, DragDropEventTypes.REGISTER_DROP_ZONE, null);

		dragDropManager.registerDropZone(eventMock);
		dragDropManager.onPlayerEvent(new PlayerEvent(PlayerEventTypes.PAGE_LOADED));

		assertThat(dragDropManager.dropZones.size(), is(equalTo(4)));
	}

	@Test
	public void fireDragEndEventToProperSourcelist() {
		SourceListModule dragSource = mock(SourceListModule.class);
		DragDropEvent startEventMock = mockDragDropEvent(dragSource, DragDropEventTypes.DRAG_START, null);
		SimpleTextModule gap = mock(SimpleTextModule.class);
		DragDropEvent endEventMock = mockDragDropEvent(gap, DragDropEventTypes.DRAG_END, null);

		dragDropManager.onDragEvent(startEventMock);
		dragDropManager.onDragEvent(endEventMock);

		Mockito.verify(dragDropManager.helper).fireEventFromSource(dragSource, endEventMock.getDragDataObject(), DragDropEventTypes.DRAG_END, gap);
	}

//	@Test
//	public void fireDragEndEventPreviousValueIsNullFirstTime() {
//		SourceListModule dragSource = mock(SourceListModule.class);
//		DragDropEvent endEventMock1 = mockDragDropEvent(dragSource, DragDropEventTypes.DRAG_END, TEST1);
//
//		dragDropManager.onDragEvent(endEventMock1);
//
//		assertThat(endEventMock1.getDragDataObject().getValue(), is(TEST1));
//		assertThat(endEventMock1.getDragDataObject().getPreviousValue(), equalTo(null));
//	}
//
//	@Test
//	public void fireDragEndEventSendsPreviousValue() {
//		SourceListModule dragSource = mock(SourceListModule.class);
//		DragDropEvent endEventMock1 = mockDragDropEvent(dragSource, DragDropEventTypes.DRAG_END, TEST1);
//		DragDropEvent endEventMock2 = mockDragDropEvent(dragSource, DragDropEventTypes.DRAG_END, TEST2);
//
//		dragDropManager.onDragEvent(endEventMock1);
//		dragDropManager.onDragEvent(endEventMock2);
//
//		assertThat(dragDropManager.gapValuesCache.containsKey(dragSource), is(true));
//		assertThat(dragDropManager.gapValuesCache.containsValue(TEST1), is(false));
//		assertThat(dragDropManager.gapValuesCache.get(dragSource), is(TEST2));
//		assertThat(endEventMock2.getDragDataObject().getValue(), is(TEST2));
//		assertThat(endEventMock2.getDragDataObject().getPreviousValue(), is(TEST1));
//	}

	@Test
	public void disableNotAssociatedGapsNothingToBeDisabled() {
		SourceListModule sourceList1 = mock(SourceListModule.class);
		SourceListModule sourceList2 = mock(SourceListModule.class);
		SourceListModule sourceList3 = mock(SourceListModule.class);
		SimpleTextModule gap1 = mock(SimpleTextModule.class);
		SimpleTextModule gap2 = mock(SimpleTextModule.class);
		SourceListModule dragSource = sourceList1;
		dragDropManager.dropZones = fillArrayListMultimap(new IModule[]{gap1, sourceList1, gap2, sourceList1, gap1, sourceList2, gap1, sourceList3});

		List<IModule> result = dragDropManager.findInapplicableGaps(dragSource);

		assertThat(result.size(), is(equalTo(0)));
	}

	@Test
	public void disableNotAssociatedGaps() {
		SourceListModule sourceList1 = mock(SourceListModule.class);
		SourceListModule sourceList2 = mock(SourceListModule.class);
		SourceListModule sourceList3 = mock(SourceListModule.class);
		SimpleTextModule gap1 = mock(SimpleTextModule.class);
		SimpleTextModule gap2 = mock(SimpleTextModule.class);
		SourceListModule dragSource = sourceList1;
		dragDropManager.dropZones = fillArrayListMultimap(new IModule[]{gap2, sourceList1, gap1, sourceList2, gap1, sourceList3});

		List<IModule> result = dragDropManager.findInapplicableGaps(dragSource);

		assertThat((SimpleTextModule)result.get(0), is(equalTo(gap1)));
	}

	private DragDropEvent mockDragDropEvent(IModule module, DragDropEventTypes eventType, String dragDataObjectValue) {
		DragDropEvent eventMock = new DragDropEvent(eventType, module);
		MockDragDataObject dragDataObject = new MockDragDataObject();

		if (dragDataObjectValue != null) {
			dragDataObject.setItemId(dragDataObjectValue);
		}
		eventMock.setDragDataObject(dragDataObject);
		eventMock.setIModule(module);
		return eventMock;
	}

	private void buildSourcelistsCollection(HasChildren parentModule) {
		List<IModule> childs = new ArrayList<IModule>();
		when(parentModule.getChildrenModules()).thenReturn(childs);
		childs.add(mock(SourceListModule.class));
		childs.add(mock(SourceListModule.class));
	}

	private HasChildren mockParentModule(SimpleTextModule module) {
		HasChildren parentModule = mock(HasChildren.class);
		when(module.getParentModule()).thenReturn(parentModule);
		return parentModule;
	}

	@SuppressWarnings("unchecked")
	private Multimap<IModule, SourceListModule> fillArrayListMultimap(IModule[] list) {
		ArrayListMultimap<IModule, SourceListModule> collection = ArrayListMultimap.create();
		for (int i = 0; i < list.length; i += 2) {
			collection.put(list[i], (SourceListModule) list[i + 1]);
		}
		return collection;
	}

    @BeforeClass
    public static void prepareTestEnviroment() {
    	/**
    	 * disable GWT.create() behavior for pure JUnit testing
    	 */
    	GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void restoreEnviroment() {
    	/**
    	 * restore GWT.create() behavior
    	 */
    	GWTMockUtilities.restore();
    }

    public class MockDragDataObject implements DragDataObject {
    	String itemId;
    	String sourceId;
		@Override
		public String getItemId() {
			return itemId;
		}
		@Override
		public void setItemId(String itemId) {
			this.itemId = itemId;
		}
		@Override
		public String getSourceId() {
			return sourceId;
		}
		@Override
		public void setSourceId(String sourceId) {
			this.sourceId = sourceId;
		}
		@Override
		public String toJSON() {
			return "{}";
		}



    }
}
