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

package eu.ydp.empiria.player.client.module.connection.presenter;

import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEvent;
import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEventTypes;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SuppressWarnings("PMD")
public class ConnectionEventHandlerJUnitTest {

    private static final String TARGET = "test2";
    private static final String SOURCE = "test1";
    ConnectionEventHandler instance = new ConnectionEventHandler();
    PairConnectEventHandler handler = mock(PairConnectEventHandler.class);

    @Before
    public void before() {
        instance.addPairConnectEventHandler(handler);
    }

    @Test
    public void testFireConnectEvent_userInteract() {
        ArgumentCaptor<PairConnectEvent> argumentCaptor = ArgumentCaptor.forClass(PairConnectEvent.class);
        for (PairConnectEventTypes type : PairConnectEventTypes.values()) {
            instance.fireConnectEvent(type, SOURCE, TARGET, true);
        }
        verifyInteraction(argumentCaptor, true);
    }

    @Test
    public void testFireConnectEvent_noUserInteract() {
        ArgumentCaptor<PairConnectEvent> argumentCaptor = ArgumentCaptor.forClass(PairConnectEvent.class);
        for (PairConnectEventTypes type : PairConnectEventTypes.values()) {
            instance.fireConnectEvent(type, SOURCE, TARGET, false);
        }
        verifyInteraction(argumentCaptor, false);
    }

    private void verifyInteraction(ArgumentCaptor<PairConnectEvent> argumentCaptor, boolean userInteract) {
        verify(handler, times(PairConnectEventTypes.values().length)).onConnectionEvent(argumentCaptor.capture());
        List<PairConnectEvent> allValues = argumentCaptor.getAllValues();
        int index = 0;
        for (PairConnectEvent event : allValues) {
            assertEquals(SOURCE, event.getSourceItem());
            assertEquals(TARGET, event.getTargetItem());
            assertEquals(PairConnectEventTypes.values()[index], event.getType());
            assertEquals(userInteract, event.isUserAction());
            index++;
        }
    }

}
