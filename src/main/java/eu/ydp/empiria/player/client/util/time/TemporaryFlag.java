package eu.ydp.empiria.player.client.util.time;

import com.google.gwt.user.client.Timer;

public class TemporaryFlag {

    private final Timer timer = new Timer() {
        @Override
        public void run() {
        }
    };

    public void setFor(int milliSeconds) {
        timer.schedule(milliSeconds);
    }

    public boolean isSet() {
        return timer.isRunning();
    }
}
