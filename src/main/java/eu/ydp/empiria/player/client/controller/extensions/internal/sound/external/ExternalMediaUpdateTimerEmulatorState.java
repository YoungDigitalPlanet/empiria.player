package eu.ydp.empiria.player.client.controller.extensions.internal.sound.external;

import com.google.inject.Inject;
import eu.ydp.gwtutil.client.date.DateService;

public class ExternalMediaUpdateTimerEmulatorState {

    @Inject
    DateService dateService;

    private int initialMediaTimeMillis;
    private long initialReferenceTimeMillis;

    public void init(int initialTimeMillis) {
        this.initialMediaTimeMillis = initialTimeMillis;
        this.initialReferenceTimeMillis = getCurrentReferenceTimeMillis();
    }

    public int findCurrentMediaTimeMillis() {
        long currentReferenceTimeMillis = getCurrentReferenceTimeMillis();
        int delta = (int) (currentReferenceTimeMillis - initialReferenceTimeMillis);
        int currentMediaTimeMillis = initialMediaTimeMillis + delta;
        return currentMediaTimeMillis;
    }

    private long getCurrentReferenceTimeMillis() {
        return dateService.getTimeMillis();
    }
}
