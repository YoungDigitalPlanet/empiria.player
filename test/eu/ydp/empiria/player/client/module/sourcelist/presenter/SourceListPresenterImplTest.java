package eu.ydp.empiria.player.client.module.sourcelist.presenter;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SimpleSourceListItemBean;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.module.sourcelist.view.SourceListView;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.events.dragdrop.DragDropEventTypes;

@RunWith(MockitoJUnitRunner.class)
public class SourceListPresenterImplTest {

	@Mock private SourceListView view;
	@Mock private SourcelistManager sourcelistManager;
	@Mock private OverlayTypesParser overlayTypesParser;
	@Mock private DragDataObject dragDataObject;

	@InjectMocks
	private final SourceListPresenterImpl sourceListPresenterImpl = spy(new SourceListPresenterImpl());

	@Before
	public void before() {
		doReturn(dragDataObject).when(sourceListPresenterImpl).getDragDataObject(anyString());
	}

	@Test(expected = NullPointerException.class)
	public void testNotSetBean() throws Exception {
		sourceListPresenterImpl.createAndBindUi();
	}

	@Test
	public void testAsWidget() throws Exception {
		sourceListPresenterImpl.asWidget();
		verify(view).asWidget();
	}

	@Test
	public void testCreateAndBindUi() throws Exception {
		List<String> allItems = Lists.newArrayList("a","b","c","d","e","f");
		SourceListBean bean = mock(SourceListBean.class);
		doReturn(getBeanItems(allItems)).when(bean).getSimpleSourceListItemBeans();
		sourceListPresenterImpl.setBean(bean);
		sourceListPresenterImpl.createAndBindUi();

		for(String id : allItems){
			verify(view).createItem(eq(id), eq(id));
		}

		verify(view).createAndBindUi();
		verify(view).setSourceListPresenter(eq(sourceListPresenterImpl));
		verifyNoMoreInteractions(view);
	}


	@Test
	public void testGetItemValue() throws Exception {
		String itemId = "id";
		sourceListPresenterImpl.getItemValue(itemId);
		verify(view).getItemValue(itemId);
		verifyNoMoreInteractions(view);
	}

	@Test
	public void testUseItem() throws Exception {
		String itemId = "test";
		sourceListPresenterImpl.useItem(itemId);
		verify(view).hideItem(eq(itemId));
	}

	@Test
	public void testRestockItem() throws Exception {
		String itemId = "test";
		sourceListPresenterImpl.restockItem(itemId);
		verify(view).showItem(eq(itemId));
	}

	@Test
	public void testOnDragEvent() throws Exception {
		String moduleId = "mId";
		String itemId = "iId";
		sourceListPresenterImpl.setModuleId(moduleId);

		sourceListPresenterImpl.onDragEvent(DragDropEventTypes.DRAG_START, itemId);
		verify(sourcelistManager).dragStart(eq(itemId));

		sourceListPresenterImpl.onDragEvent(DragDropEventTypes.DRAG_CANCELL, itemId);
		verify(sourcelistManager).dragCanceled();

		sourceListPresenterImpl.onDragEvent(DragDropEventTypes.DRAG_END, itemId);
		verify(sourcelistManager).dragEndSourcelist(eq(itemId),eq(moduleId));

		ArrayList<DragDropEventTypes> allEvents = Lists.newArrayList(DragDropEventTypes.values());
		allEvents.remove(DragDropEventTypes.DRAG_START);
		allEvents.remove(DragDropEventTypes.DRAG_END);
		allEvents.remove(DragDropEventTypes.DRAG_CANCELL);

		for(DragDropEventTypes event: allEvents){
			sourceListPresenterImpl.onDragEvent(event, itemId);
		}
		verifyNoMoreInteractions(sourcelistManager);
	}

	private List<SimpleSourceListItemBean> getBeanItems(List<String> itemIds){
		return Lists.transform(itemIds, new Function<String, SimpleSourceListItemBean>() {
			@Override
			public SimpleSourceListItemBean apply(String id) {
				SimpleSourceListItemBean bean = mock(SimpleSourceListItemBean.class);
				doReturn(id).when(bean).getAlt();
				doReturn(new SourcelistItemValue(SourcelistItemType.TEXT, id)).when(bean).getItemValue();
				return bean;
			}
		});
	}

	@Test
	public void testUseAndRestockItems() throws Exception {
		List<String> allItems = Lists.newArrayList("a","b","c","d","e","f");
		List<String> toUseItems = Lists.newArrayList("a","b","c","d");
		SourceListBean bean = mock(SourceListBean.class);
		doReturn(getBeanItems(allItems)).when(bean).getSimpleSourceListItemBeans();
		sourceListPresenterImpl.setBean(bean);

		sourceListPresenterImpl.useAndRestockItems(toUseItems);
		for(String id : allItems){
			verify(view).showItem(eq(id));
		}

		for(String id : toUseItems){
			verify(view).hideItem(eq(id));
		}
		verifyNoMoreInteractions(view);

	}

	@Test
	public void testOnDropEvent() throws Exception {
		String itemId = "id";
		String moduleId = "mId";
		sourceListPresenterImpl.setModuleId(moduleId);
		sourceListPresenterImpl.onDropEvent(itemId);
		verify(sourcelistManager).dragEndSourcelist(eq(itemId), eq(moduleId));
	}

}
