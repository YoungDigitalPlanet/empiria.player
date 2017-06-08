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

import eu.ydp.empiria.player.client.util.events.internal.multiplepair.PairConnectEventTypes;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("PMD")
public class ConnectionViewResizeHandlerJUnitTest {

    private final ConnectionViewResizeHandler instance = new ConnectionViewResizeHandler();
    private final ConnectionEventHandler eventHandler = mock(ConnectionEventHandler.class);

    @Test
    public void testOnResize_eventHandlerWasSet() {
        instance.setConnectionModuleViewImpl(eventHandler);
        instance.onResize(null);
        verify(eventHandler).fireConnectEvent(Matchers.eq(PairConnectEventTypes.REPAINT_VIEW), Matchers.anyString(), Matchers.anyString(), Matchers.eq(true));
    }

    @Test
    public void testOnResize_noEventHandler() {
        instance.onResize(null);
        Mockito.verifyZeroInteractions(eventHandler);
    }

}
