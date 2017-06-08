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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.session.SessionDataManager;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;

@Singleton
public class SessionTimeUpdater implements PlayerEventHandler {

    private final SessionDataManager sessionDataManager;
    private Integer currentPageNumber;

    @Inject
    public SessionTimeUpdater(SessionDataManager sessionDataManager) {
        this.sessionDataManager = sessionDataManager;
    }

    @Override
    public void onPlayerEvent(PlayerEvent event) {
        if (event.getType() == PlayerEventTypes.PAGE_CHANGE) {
            Integer newPageNumber = (Integer) event.getValue();
            if (newPageNumber != null) {
                updateSessionTimesWithPageChange(newPageNumber);
            }
        }
    }

    private void updateSessionTimesWithPageChange(int newPageNumber) {
        if (currentPageNumber != null) {
            sessionDataManager.endItemSession(currentPageNumber);
        }
        sessionDataManager.beginItemSession(newPageNumber);

        currentPageNumber = newPageNumber;
    }
}
