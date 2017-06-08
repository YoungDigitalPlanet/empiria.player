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

package eu.ydp.empiria.player.client.controller.session.times;

import eu.ydp.empiria.player.client.controller.session.SessionDataManager;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SessionTimeUpdaterJUnitTest {

    private SessionTimeUpdater sessionTimeUpdater;

    @Mock
    private SessionDataManager sessionDataManager;

    @Before
    public void setUp() throws Exception {
        sessionTimeUpdater = new SessionTimeUpdater(sessionDataManager);
    }

    @After
    public void tearDown() {
        Mockito.verifyNoMoreInteractions(sessionDataManager);
    }

    @Test
    public void shouldIgnoreAnyOtherEventThanPageChange() {
        PlayerEventTypes[] playerEventTypes = PlayerEventTypes.values();

        for (PlayerEventTypes eventType : playerEventTypes) {
            if (eventType != PlayerEventTypes.PAGE_CHANGE) {
                PlayerEvent event = new PlayerEvent(eventType);
                shouldIgnoreEvent(event);
            }
        }
    }

    private void shouldIgnoreEvent(PlayerEvent event) {
        sessionTimeUpdater.onPlayerEvent(event);
        Mockito.verifyZeroInteractions(sessionDataManager);
    }

    @Test
    public void shouldBeginTimeCountingInFirstOpenPage() {
        final int newPageNumber = 12;
        shouldBeginTimeCountingInOpenedPage(newPageNumber);
    }

    @Test
    public void shouldStopCountingInPreviousAndStartInNewPage() {
        final int previousPageNumber = 12;
        shouldBeginTimeCountingInOpenedPage(previousPageNumber);

        final int newPageNumber = 1243;
        shouldBeginTimeCountingInOpenedPage(newPageNumber);

        shouldStopCountingInPreviousPage(previousPageNumber);
    }

    private void shouldBeginTimeCountingInOpenedPage(int newPageNumber) {
        PlayerEvent event = pageChangeEventForPage(newPageNumber);

        sessionTimeUpdater.onPlayerEvent(event);

        verify(sessionDataManager).beginItemSession(newPageNumber);
    }

    private void shouldStopCountingInPreviousPage(int previousPageNumber) {
        verify(sessionDataManager).endItemSession(previousPageNumber);
    }

    private PlayerEvent pageChangeEventForPage(int pageNumber) {
        return new PlayerEvent(PlayerEventTypes.PAGE_CHANGE, pageNumber, null);
    }

}
