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

package eu.ydp.empiria.player.client.module.dragdrop;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.module.draggap.math.MathDragGapModule;
import eu.ydp.empiria.player.client.util.events.internal.EventType;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.time.TemporaryFlag;
import eu.ydp.gwtutil.client.util.geom.HasDimensions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SourcelistManagerImplTest {

    @InjectMocks
    private SourcelistManagerImpl manager;
    @Mock
    private SourcelistManagerModel model;
    @Mock(extraInterfaces = {ResizableSourcelistClient.class})
    private SourcelistClient client1;
    @Mock(extraInterfaces = {ResizableSourcelistClient.class})
    private SourcelistClient client2;
    @Mock(extraInterfaces = {ResizableSourcelistClient.class})
    private SourcelistClient client3;
    @Mock
    private MathDragGapModule client4;
    @Mock
    private Sourcelist sourcelist1;
    @Mock
    private Sourcelist sourcelist2;
    @Mock
    private SourcelistLockingController sourcelistLockingController;
    @Mock
    private TemporaryFlag dragEndLocked;
    @Mock
    private EventsBus eventsBus;
    @Mock
    private PageScopeFactory pageScopeFactory;
    @Captor
    private ArgumentCaptor<PlayerEvent> playerEventCaptor;
    @Captor
    private ArgumentCaptor<EventType[]> argumentCaptor;
    @Mock
    private CurrentPageScope pageScope;


    private final String CLIENT_1_ID = "id1";
    private final String CLIENT_2_ID = "id2";
    private final String CLIENT_3_ID = "id3";

    private final String ITEM_1_ID = "item1";
    private final String ITEM_2_ID = "item2";
    private final String ITEM_3_ID = "item3";

    private final String SOURCELIST_1_ID = "SOURCELIST_1_ID";
    private final String SOURCELIST_2_ID = "SOURCELIST_2_ID";

    private final static int DRAG_END_LOCKED_TIME = 50;

    @Before
    public void setUp() {
        when(client1.getIdentifier()).thenReturn(CLIENT_1_ID);
        when(client2.getIdentifier()).thenReturn(CLIENT_2_ID);
        when(client3.getIdentifier()).thenReturn(CLIENT_3_ID);

        prepareModel();
    }

    @Test
    public void shouldRegisterClient() {
        // when
        manager.registerModule(client1);

        // then
        verify(model).registerClient(client1);
    }

    @Test
    public void shouldAddHandlers() {
        //given
        CurrentPageScope currentPageScope = mock(CurrentPageScope.class);
        when(pageScopeFactory.getCurrentPageScope()).thenReturn(currentPageScope);
        // when
        manager.init();

        // then
        verify(eventsBus).addHandler(argumentCaptor.capture(), eq(manager), eq(currentPageScope));
        assertThat(argumentCaptor.getAllValues()).containsOnly(PlayerEvent.getTypes(PlayerEventTypes.PAGE_CONTENT_GROWN, PlayerEventTypes.PAGE_CONTENT_DECREASED));
    }

    @Test
    public void shouldRegisterSourcelist() {
        // when
        manager.registerSourcelist(sourcelist1);

        // then
        verify(model).registerSourcelist(sourcelist1);
    }

    @Test
    public void shouldLockUnrelatedClientsOnDragStart() {
        // when
        manager.dragStart(CLIENT_1_ID);

        // then
        verify(sourcelistLockingController).lockOthers(sourcelist1);
        verify(sourcelistLockingController, never()).lockOthers(sourcelist2);
    }

    @Test
    public void shouldUnlockAllClientsOnDragFinished() {
        // given
        when(model.isGroupLocked(sourcelist1)).thenReturn(false);
        when(model.isGroupLocked(sourcelist2)).thenReturn(false);

        // when
        manager.dragFinished();

        // then
        verify(sourcelistLockingController).unlockAll();
    }

    @Test
    public void shouldMoveItemBetweenClients() {
        // given
        final String newId = "item_2";
        final String oldId = "item_3";

        when(client3.getDragItemId()).thenReturn(oldId);

        // when
        manager.dragEnd(newId, CLIENT_2_ID, CLIENT_3_ID);

        // then
        InOrder inOrder = inOrder(client2, client3, sourcelist2);
        inOrder.verify(sourcelist2).restockItem(oldId);
        inOrder.verify(client2).removeDragItem();
        inOrder.verify(client3).setDragItem(newId);
    }

    @Test
    public void shouldIgnoreMovingItemBetweenSameClient() {
        // given
        final String newId = "item_2";

        // when
        manager.dragEnd(newId, CLIENT_2_ID, CLIENT_2_ID);

        // then
        Mockito.verifyNoMoreInteractions(client2);
    }

    @Test
    public void shouldMoveItemFromSourcelist() {
        //given
        when(dragEndLocked.isSet()).thenReturn(false);

        // when
        manager.dragEnd(ITEM_2_ID, SOURCELIST_2_ID, CLIENT_3_ID);

        // then
        verify(client3).setDragItem(ITEM_2_ID);
        verify(sourcelist2).useItem(ITEM_2_ID);
        verify(sourcelist2).restockItem(ITEM_3_ID);
    }

    @Test
    public void shouldNotMoveItemWhenOtherItemMovedBefore() {
        // given
        when(dragEndLocked.isSet()).thenReturn(true);

        // when
        manager.dragEnd(ITEM_2_ID, SOURCELIST_2_ID, CLIENT_3_ID);

        // then
        verify(client3, never()).setDragItem(ITEM_2_ID);
        verify(sourcelist2, never()).useItem(ITEM_2_ID);
        verify(sourcelist2, never()).restockItem(ITEM_3_ID);
    }

    @Test
    public void shouldLockDragEndForMoment() {
        // given
        when(dragEndLocked.isSet()).thenReturn(false);

        // when
        manager.dragEnd(ITEM_2_ID, SOURCELIST_2_ID, CLIENT_3_ID);

        // then
        verify(dragEndLocked).setFor(DRAG_END_LOCKED_TIME);
    }

    @Test
    public void shouldMoveItemToSourcelist() {
        // when
        manager.dragEndSourcelist(ITEM_2_ID, CLIENT_2_ID);

        // then
        verify(sourcelist2).restockItem(ITEM_2_ID);
        verify(client2).removeDragItem();
    }

    @Test
    public void shouldMoveItemFromSourcelistToItself() {
        // when
        manager.dragStart(SOURCELIST_2_ID);
        manager.dragEndSourcelist(ITEM_2_ID, SOURCELIST_2_ID);

        // then
        verify(sourcelist2).useItem(ITEM_2_ID);
        verify(sourcelist2).restockItem(ITEM_2_ID);
    }

    @Test
    public void shouldReturnValueFromSourcelist() {
        // when
        manager.getValue(ITEM_2_ID, CLIENT_3_ID);

        // then
        verify(sourcelist2).getItemValue(ITEM_2_ID);
        verifyNoMoreInteractions(sourcelist1);
    }

    @Test
    public void shouldRestockAllItems() {
        // given
        ArrayList<String> sourcelist1ItemsIds = Lists.newArrayList(ITEM_1_ID);
        ArrayList<String> sourcelist2ItemsIds = Lists.newArrayList(ITEM_2_ID, ITEM_3_ID);

        // when
        manager.onUserValueChanged();

        // then
        verify(sourcelist1).useAndRestockItems(sourcelist1ItemsIds);
        verify(sourcelist2).useAndRestockItems(sourcelist2ItemsIds);
    }

    @Test
    public void shouldResizeAllClients() {
        // given
        PlayerEvent event = mock(PlayerEvent.class);
        HasDimensions dim1 = mock(HasDimensions.class);
        HasDimensions dim2 = mock(HasDimensions.class);
        when(sourcelist1.getItemSize()).thenReturn(dim1);
        when(sourcelist2.getItemSize()).thenReturn(dim2);

        // when
        manager.onPlayerEvent(event);

        // then
        verify((ResizableSourcelistClient) client1).setSize(dim1);
        verify((ResizableSourcelistClient) client2).setSize(dim2);
        verify((ResizableSourcelistClient) client3).setSize(dim2);
    }

    @Test
    public void shouldRemoveItemsFromSourcelistOnStartup() {
        // given
        PlayerEvent event = mock(PlayerEvent.class);

        // when
        manager.onPlayerEvent(event);

        // then
        verify(sourcelist1).useItem(ITEM_1_ID);
        verify(sourcelist2).useItem(ITEM_2_ID);
        verify(sourcelist2).useItem(ITEM_3_ID);
    }

    @Test
    public void shouldLockOnlyGroupWithGivenClient() {
        // when
        manager.lockGroup(CLIENT_2_ID);

        // then
        verify(client1, never()).lockDropZone();
        verify(sourcelist1, never()).lockSourceList();

        verify(sourcelistLockingController).lockGroup(CLIENT_2_ID);
    }

    @Test
    public void shouldNotLockAnythingOnNotMatchingClient() {
        // given
        when(model.containsClient(CLIENT_2_ID)).thenReturn(false);

        // when
        manager.lockGroup(CLIENT_2_ID);

        // then
        verifyZeroInteractions(client1, client2, client3, sourcelist1, sourcelist2);
    }

    @Test
    public void shouldUnlockOnlyGroupWithGivenClient() {
        // when
        manager.unlockGroup(CLIENT_2_ID);

        // then
        verify(sourcelistLockingController).unlockGroup(CLIENT_2_ID);
    }

    @Test
    public void shouldNotUnlockAnythingOnNotMatchingClient() {
        // given
        when(model.containsClient(CLIENT_2_ID)).thenReturn(false);

        // when
        manager.unlockGroup(CLIENT_2_ID);

        // then
        verifyZeroInteractions(client1, client2, client3, sourcelist1, sourcelist2);
    }

    @Test
    public void shouldUnlockAllSourcelistsAndClientsWhichGroupIsNotLocked() {
        // given
        when(model.isGroupLocked(sourcelist1)).thenReturn(true);
        when(model.isGroupLocked(sourcelist2)).thenReturn(false);

        // when
        manager.dragFinished();

        // then
        verify(sourcelistLockingController).unlockAll();
    }

    @Test
    public void sendEventAfterGapResize() {
        //given
        PlayerEvent event = mock(PlayerEvent.class);
        HasDimensions dim1 = mock(HasDimensions.class);
        when(model.getClients(sourcelist1)).thenReturn(Lists.<SourcelistClient>newArrayList(client1, client4));
        when(sourcelist1.getItemSize()).thenReturn(dim1);

        //when
        manager.onPlayerEvent(event);

        //then
        verify(eventsBus).fireAsyncEvent(playerEventCaptor.capture(), eq(pageScope));
        PlayerEvent value = playerEventCaptor.getValue();
        assertThat(value.getType()).isEqualTo(PlayerEventTypes.SOURCE_LIST_CLIENTS_SET_SIZE_COMPLETED);
    }


    private void prepareModel() {
        when(sourcelist1.getIdentifier()).thenReturn(SOURCELIST_1_ID);
        when(sourcelist2.getIdentifier()).thenReturn(SOURCELIST_2_ID);
        when(client1.getDragItemId()).thenReturn(ITEM_1_ID);
        when(client2.getDragItemId()).thenReturn(ITEM_2_ID);
        when(client3.getDragItemId()).thenReturn(ITEM_3_ID);
        when(model.containsClient(CLIENT_1_ID)).thenReturn(true);
        when(model.containsClient(CLIENT_2_ID)).thenReturn(true);
        when(model.containsClient(CLIENT_3_ID)).thenReturn(true);
        when(model.getClientById(CLIENT_1_ID)).thenReturn(client1);
        when(model.getClientById(CLIENT_2_ID)).thenReturn(client2);
        when(model.getClientById(CLIENT_3_ID)).thenReturn(client3);
        when(model.getSourcelistById(SOURCELIST_1_ID)).thenReturn(sourcelist1);
        when(model.getSourcelistById(SOURCELIST_2_ID)).thenReturn(sourcelist2);
        when(model.getSourceLists()).thenReturn(Sets.newHashSet(sourcelist1, sourcelist2));
        when(model.getSourcelistByClientId(CLIENT_1_ID)).thenReturn(sourcelist1);
        when(model.getSourcelistByClientId(CLIENT_2_ID)).thenReturn(sourcelist2);
        when(model.getSourcelistByClientId(CLIENT_3_ID)).thenReturn(sourcelist2);
        when(model.getClients(sourcelist1)).thenReturn(Lists.newArrayList(client1));
        when(model.getClients(sourcelist2)).thenReturn(Lists.newArrayList(client2, client3));
        when(pageScopeFactory.getCurrentPageScope()).thenReturn(pageScope);

    }
}
