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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SourcelistLockingControllerTest {

    @InjectMocks
    private SourcelistLockingController testObj;
    @Mock
    private SourcelistManagerModel model;
    @Mock
    private SourcelistClient client1;
    @Mock
    private SourcelistClient client2;
    @Mock
    private SourcelistClient client3;
    @Mock
    private Sourcelist sourcelist1;
    @Mock
    private Sourcelist sourcelist2;
    private final String CLIENT_1_ID = "id1";
    private final String CLIENT_2_ID = "id2";
    private final String CLIENT_3_ID = "id3";

    private final String ITEM_1_ID = "item1";
    private final String ITEM_2_ID = "item2";
    private final String ITEM_3_ID = "item3";

    private final String SOURCELIST_1_ID = "SOURCELIST_1_ID";
    private final String SOURCELIST_2_ID = "SOURCELIST_2_ID";

    @Before
    public void setUp() {
        client1 = mockClient(CLIENT_1_ID);
        client2 = mockClient(CLIENT_2_ID);
        client3 = mockClient(CLIENT_3_ID);

        prepareModel();
    }

    @Test
    public void shouldLockOthersSourcelists() {
        // when
        testObj.lockOthers(sourcelist1);

        // then
        verify(sourcelist1, never()).lockSourceList();
        verify(sourcelist2).lockSourceList();
        verify(client1, never()).lockDropZone();
        verify(client2).lockDropZone();
    }

    @Test
    public void shouldUnlockAll() {
        // when
        testObj.unlockAll();

        // then
        verify(sourcelist1).unlockSourceList();
        verify(sourcelist2).unlockSourceList();
        verify(client1).unlockDropZone();
        verify(client2).unlockDropZone();
        verify(client3).unlockDropZone();
    }

    @Test
    public void shouldNotUnlockIfGroupIsLocked() {
        // given
        when(model.isGroupLocked(sourcelist1)).thenReturn(true);

        // when
        testObj.unlockAll();

        // then
        verify(sourcelist1, never()).unlockSourceList();
        verify(client1, never()).unlockDropZone();
    }

    @Test
    public void shouldLockGroupByGroupId() {
        // when
        testObj.lockGroup(CLIENT_2_ID);

        // then
        verify(client1, never()).lockDropZone();
        verify(sourcelist1, never()).lockSourceList();

        verify(client2).lockDropZone();
        verify(client3).lockDropZone();
        verify(sourcelist2).lockSourceList();
    }

    @Test
    public void shouldDoNothing_lockingWithInvalidId() {
        // given
        final String INVALID_ID = "INVALID_ID";
        when(model.containsClient(INVALID_ID)).thenReturn(false);

        // when
        testObj.lockGroup(INVALID_ID);

        // then
        verify(model).containsClient(INVALID_ID);
        verifyZeroInteractions(model, client1, client2, client3, sourcelist1, sourcelist2);
    }

    @Test
    public void shouldUnlockOnlyGroupWithGivenClientId() {
        // when
        testObj.unlockGroup(CLIENT_2_ID);

        // then
        verify(client1, never()).unlockDropZone();
        verify(sourcelist1, never()).unlockSourceList();

        verify(client2).unlockDropZone();
        verify(client3).unlockDropZone();
        verify(sourcelist2).unlockSourceList();
    }

    @Test
    public void shouldDoNothing_unlockingWithInvalidId() {
        // given
        final String INVALID_ID = "INVALID_ID";
        when(model.containsClient(INVALID_ID)).thenReturn(false);

        // when
        testObj.unlockGroup(INVALID_ID);

        // then
        verify(model).containsClient(INVALID_ID);
        verifyZeroInteractions(model, client1, client2, client3, sourcelist1, sourcelist2);
    }

    private SourcelistClient mockClient(String string) {
        SourcelistClient client = mock(SourcelistClient.class);
        when(client.getIdentifier()).thenReturn(string);
        return client;
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
    }
}
