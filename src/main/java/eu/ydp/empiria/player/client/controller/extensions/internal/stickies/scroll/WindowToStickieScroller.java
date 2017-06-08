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
