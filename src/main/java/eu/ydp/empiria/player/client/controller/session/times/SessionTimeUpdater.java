package eu.ydp.empiria.player.client.controller.session.times;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.session.SessionDataManager;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;

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
