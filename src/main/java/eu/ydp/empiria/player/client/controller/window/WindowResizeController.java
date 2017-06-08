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

package eu.ydp.empiria.player.client.controller.window;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.proxy.WindowDelegate;

public class WindowResizeController implements ResizeHandler {

    private final WindowResizeTimer windowResizeTimer;

    @Inject
    public WindowResizeController(WindowResizeTimer windowResizeTimer, WindowDelegate windowDelegate) {
        this.windowResizeTimer = windowResizeTimer;
        windowDelegate.addResizeHandler(this);
    }

    @Override
    public void onResize(ResizeEvent event) {
        windowResizeTimer.schedule(250);
    }
}
