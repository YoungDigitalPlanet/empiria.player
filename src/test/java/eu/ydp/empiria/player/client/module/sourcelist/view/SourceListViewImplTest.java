package eu.ydp.empiria.player.client.module.sourcelist.view;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import com.google.common.collect.Lists;
import com.google.gwt.event.dom.client.DragDropEventBase;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.factory.TouchReservationFactory;
import eu.ydp.empiria.player.client.module.dragdrop.*;
import eu.ydp.empiria.player.client.module.sourcelist.presenter.SourceListPresenter;
import eu.ydp.empiria.player.client.ui.drop.FlowPanelWithDropZone;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.events.internal.dragdrop.DragDropEventTypes;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;
import java.util.List;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class SourceListViewImplTest {

    @Mock
    private SourceListPresenter sourceListPresenter;
    @Mock
    private TouchReservationFactory touchReservationFactory;
    @Mock
    private Provider<SourceListViewItem> sourceListViewItemProvider;
    @Mock
    private SourceListViewItem viewItem;
    @Mock
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;

    @InjectMocks
    private SourceListViewImpl instance;

    private final List<String> allIds = Lists.newArrayList("a", "b", "c", "d", "e", "f");

    private FlowPanelWithDropZone items;
    private final int sourceListViewItemHeight = 11;
    private final int sourceListViewItemWidth = 12;

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
                doReturn(sourceListViewItemHeight).when(listViewItemMock).getHeight();
                doReturn(sourceListViewItemWidth).when(listViewItemMock).getWidth();
                return listViewItemMock;
            }
        });
        items = mock(FlowPanelWithDropZone.class);
        instance.items = items;
    }

    SourceListViewItem listViewItemMock = mock(SourceListViewItem.class);

    private void addItems() {
        instance.createItem(new SourcelistItemValue(SourcelistItemType.TEXT, "a", "a"), inlineBodyGeneratorSocket);
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

        instance.createItem(new SourcelistItemValue(SourcelistItemType.TEXT, itemContent, itemId), inlineBodyGeneratorSocket);
        instance.setSourceListPresenter(sourceListPresenter);
        instance.onDragEvent(DragDropEventTypes.DRAG_START, viewItem, event);

        verify(event).setData(eq("json"), eq(json));
        verify(sourceListPresenter).onDragEvent(eq(DragDropEventTypes.DRAG_START), eq(itemId));

    }

    @Test
    public void shouldNotSetData_whenNotDragStartEvent() throws Exception {
        // given
        String itemContent = "itemContent";
        String itemId = "item";
        DragDropEventBase event = mock(DragDropEventBase.class);

        instance.createItem(new SourcelistItemValue(SourcelistItemType.TEXT, itemContent, itemId), inlineBodyGeneratorSocket);
        instance.setSourceListPresenter(sourceListPresenter);
        instance.onDragEvent(DragDropEventTypes.DRAG_CANCEL, listViewItemMock, event);

        verify(event, never()).setData(eq("json"), anyString());
        verify(sourceListPresenter).onDragEvent(eq(DragDropEventTypes.DRAG_CANCEL), eq(itemId));

    }

    @Test
    public void shouldGetItemValue() {
        addItems();

        instance.getItemValue("a");

        verify(listViewItemMock).getItemContent();
    }

    @Test
    public void shouldCreateItem() {
        String itemContent = "itemContent";
        String itemId = "item";
        doReturn(viewItem).when(sourceListViewItemProvider).get();

        SourcelistItemValue sourcelistItemValue = new SourcelistItemValue(SourcelistItemType.TEXT, itemId, itemContent);
        instance.createItem(sourcelistItemValue, inlineBodyGeneratorSocket);

        verify(sourceListViewItemProvider).get();
        verify(items).add(eq(sourceListViewItemProvider.get()));
        verify(sourceListViewItemProvider.get()).setSourceListView(eq(instance));
        verify(sourceListViewItemProvider.get()).createAndBindUi(eq(sourcelistItemValue), eq(inlineBodyGeneratorSocket));
    }

    @Test
    public void shouldHideItem() {
        // given
        addItems();

        // when
        instance.hideItem("a");

        // then
        verify(listViewItemMock).hide();

    }

    @Test
    public void shouldNotHideItem_whenIdNotPresent() {
        // given
        addItems();

        // when
        instance.hideItem("aa");

        // then
        verify(listViewItemMock, never()).show();
    }

    @Test
    public void shouldShowItem() {
        // given
        addItems();

        // when
        instance.showItem("a");

        // then
        verify(listViewItemMock).show();
    }

    @Test
    public void shouldNotShowItem_whenIdNotPresent() {
        // given
        addItems();

        // when
        instance.showItem("aa");

        // then
        verify(listViewItemMock, never()).show();
    }

    @Test
    public void shouldLockItemForDrag() {
        // given
        addItems();

        // when
        instance.lockItemForDragDrop("a");

        // then
        verify(listViewItemMock).lockForDragDrop();
    }

    @Test
    public void shouldUnlockItemForDrag() {
        // given
        addItems();

        // when
        instance.unlockItemForDragDrop("a");

        // then
        verify(listViewItemMock).unlockForDragDrop();
    }

    @Test
    public void shouldGetMaxItemSize() {
        // given
        addItems();

        // when
        HasDimensions maxItemSize = instance.getMaxItemSize();

        // then
        assertThat(maxItemSize.getHeight()).isEqualTo(sourceListViewItemHeight);
        assertThat(maxItemSize.getWidth()).isEqualTo(sourceListViewItemWidth);
    }
}
