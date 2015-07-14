package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.scroll;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.Window;
import eu.ydp.gwtutil.client.util.UserAgentChecker;

public class WindowToStickieScroller {

    private static final int TOP_MARGIN = 20;
    private static final int DELAY_MS = 500;

    public void scrollToStickie(final int absoluteTop) {
        if (UserAgentChecker.isMobileUserAgent()) {
            Scheduler.get().scheduleFixedDelay(new Scheduler.RepeatingCommand() {

                @Override
                public boolean execute() {
                    Window.scrollTo(Window.getScrollLeft(), absoluteTop - TOP_MARGIN);
                    return false;
                }
            }, DELAY_MS);
        }
    }

}
