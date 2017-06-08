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

package eu.ydp.empiria.player.client.controller.multiview;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.util.dom.redraw.ForceRedrawHack;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;

import java.util.logging.Logger;

public class ResizeContinuousUpdater {

    enum ResizeTimerState {
        WAITING_FOR_CONTENT, PAGE_IS_RESIZING, PAGE_STOPPED_RESIZING
    }

    private static final Logger LOGGER = Logger.getLogger(ResizeContinuousUpdater.class.getName());
    static final int DELAY_MILLIS = 200;
    static final int IDLE_DELAY_MILLIS = 500;
    static final int REPEAT_COUNT = 20;
    static final int WAIT_STOP_GROWING_ITERATIONS = 1;
    private final PlayerEvent PAGE_CONTENT_GROWN_EVENT = new PlayerEvent(PlayerEventTypes.PAGE_CONTENT_GROWN);
    private final PlayerEvent PAGE_CONTENT_DECREASED_EVENT = new PlayerEvent(PlayerEventTypes.PAGE_CONTENT_DECREASED);
    private PlayerEvent currentResizeEvent = PAGE_CONTENT_GROWN_EVENT;

    private final PageScopeFactory pageScopeFactory;
    private final EventsBus eventsBus;

    private ResizeTimerState timerState = ResizeTimerState.WAITING_FOR_CONTENT;
    private int previousPageHeight = 0;
    private int resizeCounter = 0;
    private int pageStoppedResizingCounter = 0;

    private final MultiPageController pageView;
    private final ForceRedrawHack redrawHack;

    @Inject
    public ResizeContinuousUpdater(PageScopeFactory pageScopeFactory, EventsBus eventsBus, MultiPageController pageView, ForceRedrawHack redrawHack) {
        this.pageScopeFactory = pageScopeFactory;
        this.eventsBus = eventsBus;
        this.pageView = pageView;
        this.redrawHack = redrawHack;
    }

    public void reset() {
        resizeCounter = 0;
        previousPageHeight = 0;
        pageStoppedResizingCounter = 0;
        timerState = ResizeTimerState.WAITING_FOR_CONTENT;
    }

    public int runContinuousResizeUpdateAndReturnRescheduleTime() {
        int height = pageView.getCurrentPageHeight();

        int rescheduleTime = 0;

        switch (timerState) {
            case WAITING_FOR_CONTENT:
                rescheduleTime = waitForContent(height);
                break;
            case PAGE_IS_RESIZING:
                rescheduleTime = monitorPageResizing(height);
                break;
            case PAGE_STOPPED_RESIZING:
                rescheduleTime = monitorPageNotStartedResizing(height);
                break;
            default:
                LOGGER.info("Unknown timerState: " + timerState);
        }

        previousPageHeight = height;

        return rescheduleTime;
    }

    private int waitForContent(int height) {
        if (height > 0) {
            timerState = ResizeTimerState.PAGE_IS_RESIZING;
            resizePageContainer(height);
        }
        return DELAY_MILLIS;
    }

    private int monitorPageResizing(int height) {
        if (isPageResizing(height)) {
            pageStoppedResizingCounter = 0;
            resizePageContainer(height);
        } else {
            if (pageStoppedResizingCounter >= WAIT_STOP_GROWING_ITERATIONS) {
                resizePageContainer(height);
                eventsBus.fireAsyncEvent(currentResizeEvent, pageScopeFactory.getCurrentPageScope());
                timerState = ResizeTimerState.PAGE_STOPPED_RESIZING;
            }

            pageStoppedResizingCounter++;
        }
        redrawHack.redraw();
        return DELAY_MILLIS;
    }

    private int monitorPageNotStartedResizing(int height) {
        int rescheduleTime = DELAY_MILLIS;
        if (isPageResizing(height)) {
            timerState = ResizeTimerState.PAGE_IS_RESIZING;
            pageStoppedResizingCounter = 0;
            resizeCounter = 0;
        } else {
            if (resizeCounter >= REPEAT_COUNT) {
                rescheduleTime = IDLE_DELAY_MILLIS;
            }
            resizeCounter++;
        }

        return rescheduleTime;
    }

    private void resizePageContainer(int height) {
        pageView.setHeight(height);
        pageView.hideCurrentPageProgressBar();
    }

    private boolean isPageResizing(int height) {
        if (height > previousPageHeight) {
            currentResizeEvent = PAGE_CONTENT_GROWN_EVENT;
        } else if (height < previousPageHeight) {
            currentResizeEvent = PAGE_CONTENT_DECREASED_EVENT;
        }

        return height != previousPageHeight;
    }

}
