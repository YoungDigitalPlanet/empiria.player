package eu.ydp.empiria.player.client.module.sourcelist.view;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

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
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class SourceListViewImplTest {

    @InjectMocks
    private SourceListViewImpl testObj;
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
    @Mock
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
                doReturn(sourceListViewItemHeight).when(viewItem).getHeight();
                doReturn(sourceListViewItemWidth).when(viewItem).getWidth();
                return viewItem;
            }
        });
        testObj.items = items;
    }

    @Test
    public void testOnDragEventDragStart() throws Exception {
        // given
        String itemContent = "itemContent";
        String itemId = "item";
        SourceListPresenter sourceListPresenter = mock(SourceListPresenter.class);
        String json = "{}";
        DragDataObject dataObject = mock(DragDataObject.class);
        doReturn(json).when(dataObject).toJSON();
        when(sourceListPresenter.getDragDataObject(anyString())).thenReturn(dataObject);
        DragDropEventBase event = mock(DragDropEventBase.class);

        // when
        testObj.createItem(new SourcelistItemValue(SourcelistItemType.TEXT, itemContent, itemId), inlineBodyGeneratorSocket);
        testObj.setSourceListPresenter(sourceListPresenter);
        testObj.onDragEvent(DragDropEventTypes.DRAG_START, viewItem, event);

        // then
        verify(event).setData(eq("json"), eq(json));
        verify(sourceListPresenter).onDragEvent(eq(DragDropEventTypes.DRAG_START), eq(itemId));

    }

    @Test
    public void shouldNotSetData_whenNotDragStartEvent() throws Exception {
        // given
        String itemContent = "itemContent";
        String itemId = "item";
        DragDropEventBase event = mock(DragDropEventBase.class);

        // when
        testObj.createItem(new SourcelistItemValue(SourcelistItemType.TEXT, itemContent, itemId), inlineBodyGeneratorSocket);
        testObj.setSourceListPresenter(sourceListPresenter);
        testObj.onDragEvent(DragDropEventTypes.DRAG_CANCEL, viewItem, event);

        // then
        verify(event, never()).setData(eq("json"), anyString());
        verify(sourceListPresenter).onDragEvent(eq(DragDropEventTypes.DRAG_CANCEL), eq(itemId));

    }

    @Test
    public void shouldGetItemValue() {
        // given
        testObj.createItem(new SourcelistItemValue(SourcelistItemType.TEXT, "a", "a"), inlineBodyGeneratorSocket);

        // when
        testObj.getItemValue("a");

        // then
        verify(viewItem).getItemContent();
    }

    @Test
    public void shouldCreateItem() {
        // given
        String itemContent = "itemContent";
        String itemId = "item";
        doReturn(viewItem).when(sourceListViewItemProvider).get();

        // when
        SourcelistItemValue sourcelistItemValue = new SourcelistItemValue(SourcelistItemType.TEXT, itemId, itemContent);
        testObj.createItem(sourcelistItemValue, inlineBodyGeneratorSocket);

        // then
        verify(sourceListViewItemProvider).get();
        verify(items).add(eq(sourceListViewItemProvider.get()));
        verify(sourceListViewItemProvider.get()).setSourceListView(eq(testObj));
        verify(sourceListViewItemProvider.get()).createAndBindUi(eq(sourcelistItemValue), eq(inlineBodyGeneratorSocket));
    }

    @Test
    public void shouldHideItem() {
        // given
        testObj.createItem(new SourcelistItemValue(SourcelistItemType.TEXT, "a", "a"), inlineBodyGeneratorSocket);

        // when
        testObj.hideItem("a");

        // then
        verify(viewItem).hide();

    }

    @Test
    public void shouldNotHideItem_whenIdNotPresent() {
        // given
        testObj.createItem(new SourcelistItemValue(SourcelistItemType.TEXT, "a", "a"), inlineBodyGeneratorSocket);

        // when
        testObj.hideItem("aa");

        // then
        verify(viewItem, never()).show();
    }

    @Test
    public void shouldShowItem() {
        // given
        testObj.createItem(new SourcelistItemValue(SourcelistItemType.TEXT, "a", "a"), inlineBodyGeneratorSocket);

        // when
        testObj.showItem("a");

        // then
        verify(viewItem).show();
    }

    @Test
    public void shouldNotShowItem_whenIdNotPresent() {
        // given
        testObj.createItem(new SourcelistItemValue(SourcelistItemType.TEXT, "a", "a"), inlineBodyGeneratorSocket);

        // when
        testObj.showItem("aa");

        // then
        verify(viewItem, never()).show();
    }

    @Test
    public void shouldLockItemForDrag() {
        // given
        testObj.createItem(new SourcelistItemValue(SourcelistItemType.TEXT, "a", "a"), inlineBodyGeneratorSocket);

        // when
        testObj.lockItemForDragDrop("a");

        // then
        verify(viewItem).lockForDragDrop();
    }

    @Test
    public void shouldUnlockItemForDrag() {
        // given
        testObj.createItem(new SourcelistItemValue(SourcelistItemType.TEXT, "a", "a"), inlineBodyGeneratorSocket);

        // when
        testObj.unlockItemForDragDrop("a");

        // then
        verify(viewItem).unlockForDragDrop();
    }

    @Test
    public void shouldGetMaxItemSize() {
        // given
        testObj.createItem(new SourcelistItemValue(SourcelistItemType.TEXT, "a", "a"), inlineBodyGeneratorSocket);

        // when
        HasDimensions maxItemSize = testObj.getMaxItemSize();

        // then
        assertThat(maxItemSize.getHeight()).isEqualTo(sourceListViewItemHeight);
        assertThat(maxItemSize.getWidth()).isEqualTo(sourceListViewItemWidth);
    }
}
