/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.module.sourcelist.presenter;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistManager;
import eu.ydp.empiria.player.client.module.sourcelist.predicates.ComplexTextPredicate;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SimpleSourceListItemBean;
import eu.ydp.empiria.player.client.module.sourcelist.structure.SourceListBean;
import eu.ydp.empiria.player.client.module.sourcelist.view.SourceListView;
import eu.ydp.empiria.player.client.overlaytypes.OverlayTypesParser;
import eu.ydp.empiria.player.client.util.dom.drag.DragDataObject;
import eu.ydp.empiria.player.client.util.events.internal.dragdrop.DragDropEventTypes;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SourceListPresenterImplTest {

    @Mock
    private SourceListView view;
    @Mock
    private SourcelistManager sourcelistManager;
    @Mock
    private OverlayTypesParser overlayTypesParser;
    @Mock
    private DragDataObject dragDataObject;
    @Mock
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;
    @Mock
    private ComplexTextPredicate complexTextChecker;

    private final int imagesWidth = 400;
    private final int imagesHeight = 300;

    @InjectMocks
    private final SourceListPresenterImpl sourceListPresenterImpl = spy(new SourceListPresenterImpl());

    @Before
    public void before() {
        doReturn(dragDataObject).when(sourceListPresenterImpl).getDragDataObject(anyString());
    }

    @Test(expected = NullPointerException.class)
    public void testNotSetBean() throws Exception {
        sourceListPresenterImpl.createAndBindUi(inlineBodyGeneratorSocket);
    }

    @Test
    public void testAsWidget() throws Exception {
        sourceListPresenterImpl.asWidget();
        verify(view).asWidget();
    }

    @Test
    public void testCreateAndBindUi() throws Exception {
        List<String> allItems = Lists.newArrayList("a", "b", "c", "d", "e", "f");
        SourceListBean bean = mock(SourceListBean.class);
        doReturn(getBeanItems(allItems)).when(bean).getSimpleSourceListItemBeans();
        sourceListPresenterImpl.setBean(bean);
        sourceListPresenterImpl.createAndBindUi(inlineBodyGeneratorSocket);

        for (String id : allItems) {
            verify(view).createItem(eq(new SourcelistItemValue(SourcelistItemType.TEXT, id, id + id)), eq(inlineBodyGeneratorSocket));
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
        SourceListBean bean = mock(SourceListBean.class);
        doReturn(true).when(bean).isMoveElements();
        sourceListPresenterImpl.setBean(bean);
        sourceListPresenterImpl.useItem(itemId);
        verify(view).hideItem(eq(itemId));
    }

    @Test
    public void testUseItemNotMoveElements() throws Exception {
        String itemId = "test";
        SourceListBean bean = mock(SourceListBean.class);
        doReturn(false).when(bean).isMoveElements();
        sourceListPresenterImpl.setBean(bean);
        sourceListPresenterImpl.useItem(itemId);
        verifyZeroInteractions(view);
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
        verify(sourcelistManager).dragStart(eq(moduleId));

        sourceListPresenterImpl.onDragEvent(DragDropEventTypes.DRAG_END, itemId);
        verify(sourcelistManager).dragFinished();

        ArrayList<DragDropEventTypes> allEvents = Lists.newArrayList(DragDropEventTypes.values());
        allEvents.remove(DragDropEventTypes.DRAG_START);
        allEvents.remove(DragDropEventTypes.DRAG_END);

        for (DragDropEventTypes event : allEvents) {
            sourceListPresenterImpl.onDragEvent(event, itemId);
        }
        verifyNoMoreInteractions(sourcelistManager);
    }

    private List<SimpleSourceListItemBean> getBeanItems(List<String> itemIds) {
        return Lists.transform(itemIds, new Function<String, SimpleSourceListItemBean>() {
            @Override
            public SimpleSourceListItemBean apply(String id) {
                SimpleSourceListItemBean bean = mock(SimpleSourceListItemBean.class);
                doReturn(id).when(bean).getAlt();
                doReturn(new SourcelistItemValue(SourcelistItemType.TEXT, id, id + id)).when(bean).getItemValue(complexTextChecker);
                return bean;
            }
        });
    }

    @Test
    public void testUseAndRestockItems() throws Exception {
        List<String> allItems = Lists.newArrayList("a", "b", "c", "d", "e", "f");
        List<String> toUseItems = Lists.newArrayList("a", "b", "c", "d");
        SourceListBean bean = mock(SourceListBean.class);
        doReturn(getBeanItems(allItems)).when(bean).getSimpleSourceListItemBeans();
        doReturn(true).when(bean).isMoveElements();
        sourceListPresenterImpl.setBean(bean);

        sourceListPresenterImpl.useAndRestockItems(toUseItems);
        for (String id : allItems) {
            verify(view).showItem(eq(id));
        }

        for (String id : toUseItems) {
            verify(view).hideItem(eq(id));
        }
        verifyNoMoreInteractions(view);

    }

    @Test
    public void testOnDropEvent() throws Exception {
        String itemId = "id";
        String moduleId = "mId";
        sourceListPresenterImpl.onDropEvent(itemId, moduleId);
        verify(sourcelistManager).dragEndSourcelist(eq(itemId), eq(moduleId));
    }

    @Test
    public void testLockSourceList() throws Exception {
        List<String> allItems = Lists.newArrayList("a", "b", "c", "d", "e", "f");
        SourceListBean bean = mock(SourceListBean.class);
        doReturn(getBeanItems(allItems)).when(bean).getSimpleSourceListItemBeans();
        sourceListPresenterImpl.setBean(bean);

        // when
        sourceListPresenterImpl.lockSourceList();
        verify(view).lockForDragDrop();
        for (String id : allItems) {
            verify(view).lockItemForDragDrop(eq(id));
        }
    }

    @Test
    public void testUnlockSourceList() throws Exception {
        List<String> allItems = Lists.newArrayList("a", "b", "c", "d", "e", "f");
        SourceListBean bean = mock(SourceListBean.class);
        doReturn(getBeanItems(allItems)).when(bean).getSimpleSourceListItemBeans();
        sourceListPresenterImpl.setBean(bean);

        sourceListPresenterImpl.unlockSourceList();
        for (String id : allItems) {
            verify(view).unlockItemForDragDrop(eq(id));
        }

    }

    @Test
    public void getMaxItemSize() throws Exception {
        SourceListBean bean = mock(SourceListBean.class);
        doReturn(imagesHeight).when(bean).getImagesHeight();
        doReturn(imagesWidth).when(bean).getImagesWidth();
        sourceListPresenterImpl.setBean(bean);
        HasDimensions dimension = mock(HasDimensions.class);
        int width = 900;
        doReturn(width).when(dimension).getWidth();
        doReturn(dimension).when(view).getMaxItemSize();
        HasDimensions itemSize = sourceListPresenterImpl.getMaxItemSize();
        verify(view).getMaxItemSize();
        assertThat(width).isEqualTo(itemSize.getWidth());
        assertThat(imagesHeight).isEqualTo(itemSize.getHeight());

    }

}
