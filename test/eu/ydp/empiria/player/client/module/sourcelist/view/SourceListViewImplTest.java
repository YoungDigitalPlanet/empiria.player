package eu.ydp.empiria.player.client.module.sourcelist.view;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.google.common.collect.BiMap;
import com.google.common.collect.Lists;
import com.google.gwt.event.dom.client.DragDropEventBase;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.gin.factory.TouchReservationFactory;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;
import eu.ydp.empiria.player.client.test.utils.ReflectionsUtils;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes;

@RunWith(MockitoJUnitRunner.class)
public class SourceListViewImplTest {

	@Mock
	private SourceListPresenter sourceListPresenter;

	@Mock private TouchReservationFactory touchReservationFactory;
	@Mock private Provider<SourceListViewItem> sourceListViewItemProvider;
	@Mock private SourceListViewItem viewItem;

	@InjectMocks
	private SourceListViewImpl instance;

	private final List<String> allIds = Lists.newArrayList("a","b","c","d","e","f");

	private FlowPanel items;

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}


	@Before
	public void before() {
		when(sourceListViewItemProvider.get()).then(new Answer<SourceListViewItem>() {
			@Override
			public SourceListViewItem answer(InvocationOnMock invocation) throws Throwable {
				return mock(SourceListViewItem.class);
			}
		});
		items = mock(FlowPanel.class);
		instance.items = items;
	}

	private void addItems(){
		for(String id : allIds){
			instance.createItem(id, id);
		}
	}

	@Test
	public void testDisableItems() throws Exception {
		ReflectionsUtils reflectionsUtils = new ReflectionsUtils();
		BiMap<String,SourceListViewItem> itemIdToItemCollection = (BiMap<String, SourceListViewItem>) reflectionsUtils.getValueFromFiledInObject("itemIdToItemCollection", instance);
		addItems();
		instance.disableItems(true);
		for (SourceListViewItem item : itemIdToItemCollection.values()) {
			verify(item).setDisableDrag(eq(true));
		}

		instance.disableItems(false);
		for (SourceListViewItem item : itemIdToItemCollection.values()) {
			verify(item).setDisableDrag(eq(false));
		}
	}

	@Test
	public void testOnDragEventDragStart() throws Exception {
		String itemContent = "itemContent";
		String itemId = "item";
		doReturn(viewItem).when(sourceListViewItemProvider).get();
		SourceListPresenter sourceListPresenter = mock(SourceListPresenter.class);
		String json = "{}";
		DragDataObject dataObject = mock(DragDataObject.class);
		doReturn(json).when(dataObject).toJSON();
		when(sourceListPresenter.getDragDataObject(anyString())).thenReturn(dataObject);
		DragDropEventBase event = mock(DragDropEventBase.class);

		instance.createItem(itemId, itemContent);
		instance.setSourceListPresenter(sourceListPresenter);
		instance.onDragEvent(DragDropEventTypes.DRAG_START, viewItem, event);

		verify(event).setData(eq("json"), eq(json));
		verify(sourceListPresenter).onDragEvent(eq(DragDropEventTypes.DRAG_START), eq(itemId));

	}

	@Test
	public void testOnDragEvent() throws Exception {
		addItems();
		DragDropEventBase event = mock(DragDropEventBase.class);
		ReflectionsUtils reflectionsUtils = new ReflectionsUtils();
		BiMap<String, SourceListViewItem> itemIdToItemCollection = (BiMap<String, SourceListViewItem>) reflectionsUtils.getValueFromFiledInObject(
				"itemIdToItemCollection", instance);

		instance.setSourceListPresenter(sourceListPresenter);

		for (Map.Entry<String, SourceListViewItem> item : itemIdToItemCollection.entrySet()) {
			for (DragDropEventTypes type : DragDropEventTypes.values()) {
				if (type != DragDropEventTypes.DRAG_START) {
					instance.onDragEvent(type, item.getValue(), event);
					verify(event, times(0)).setData(eq("json"), anyString());
					verify(sourceListPresenter).onDragEvent(eq(type), eq(item.getKey()));
				}
			}
		}
	}

	@Test
	public void testGetItemValue() throws Exception {
		ReflectionsUtils reflectionsUtils = new ReflectionsUtils();
		BiMap<String,SourceListViewItem> itemIdToItemCollection = (BiMap<String, SourceListViewItem>) reflectionsUtils.getValueFromFiledInObject("itemIdToItemCollection", instance);
		addItems();

		for(String id: allIds){
			instance.getItemValue(id);
		}

		for (SourceListViewItem item : itemIdToItemCollection.values()) {
			verify(item).getItemContent();
		}
	}

	@Test
	public void testCreateItem() throws Exception {
		String itemContent = "itemContent";
		String itemId = "item";
		doReturn(viewItem).when(sourceListViewItemProvider).get();

		instance.createItem(itemId, itemContent);

		verify(sourceListViewItemProvider).get();
		verify(items).add(eq(sourceListViewItemProvider.get()));
		verify(sourceListViewItemProvider.get()).setSourceListView(eq(instance));
		verify(sourceListViewItemProvider.get()).createAndBindUi(eq(itemContent));
	}

	@Test
	public void testHideItem() throws Exception {
		ReflectionsUtils reflectionsUtils = new ReflectionsUtils();
		BiMap<String,SourceListViewItem> itemIdToItemCollection = (BiMap<String, SourceListViewItem>) reflectionsUtils.getValueFromFiledInObject("itemIdToItemCollection", instance);
		addItems();
		instance.hideItem("a");
		SourceListViewItem viewItem = itemIdToItemCollection.get("a");
		verify(viewItem).hide();
		allIds.remove("a");
		for(String id:  allIds){
			viewItem = itemIdToItemCollection.get(id);
			verify(viewItem,times(0)).hide();
		}
	}

	@Test
	public void testHideItemIdNotPresent() throws Exception {
		ReflectionsUtils reflectionsUtils = new ReflectionsUtils();
		BiMap<String,SourceListViewItem> itemIdToItemCollection = (BiMap<String, SourceListViewItem>) reflectionsUtils.getValueFromFiledInObject("itemIdToItemCollection", instance);
		addItems();
		instance.hideItem("aa");

		for(String id:  allIds){
			viewItem = itemIdToItemCollection.get(id);
			verify(viewItem,times(0)).hide();
		}
	}

	@Test
	public void testShowItem() throws Exception {
		ReflectionsUtils reflectionsUtils = new ReflectionsUtils();
		BiMap<String,SourceListViewItem> itemIdToItemCollection = (BiMap<String, SourceListViewItem>) reflectionsUtils.getValueFromFiledInObject("itemIdToItemCollection", instance);
		addItems();
		instance.showItem("a");
		SourceListViewItem viewItem = itemIdToItemCollection.get("a");
		verify(viewItem).show();
		allIds.remove("a");
		for(String id:  allIds){
			viewItem = itemIdToItemCollection.get(id);
			verify(viewItem,times(0)).show();
		}
	}

	@Test
	public void testShowItemIdNotPresent() throws Exception {
		ReflectionsUtils reflectionsUtils = new ReflectionsUtils();
		BiMap<String,SourceListViewItem> itemIdToItemCollection = (BiMap<String, SourceListViewItem>) reflectionsUtils.getValueFromFiledInObject("itemIdToItemCollection", instance);
		addItems();
		instance.showItem("aa");
		for(String id:  allIds){
			viewItem = itemIdToItemCollection.get(id);
			verify(viewItem,times(0)).show();
		}
	}

}
