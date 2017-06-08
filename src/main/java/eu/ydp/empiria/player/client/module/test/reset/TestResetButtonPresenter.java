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

package eu.ydp.empiria.player.client.module.test.reset;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.flow.FlowManager;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkMode;
import eu.ydp.empiria.player.client.controller.workmode.PlayerWorkModeService;
import eu.ydp.empiria.player.client.module.test.reset.view.TestResetButtonView;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.reset.LessonResetEvent;
import eu.ydp.empiria.player.client.util.events.internal.reset.LessonResetEventTypes;
import eu.ydp.gwtutil.client.event.factory.Command;

public class TestResetButtonPresenter {

    private final TestResetButtonView testResetButtonView;
    private final FlowManager flowManager;
    private final PlayerWorkModeService playerWorkModeService;
    private boolean locked;
    private final EventsBus eventsBus;

    @Inject
    public TestResetButtonPresenter(TestResetButtonView testResetButtonView, FlowManager flowManager, PlayerWorkModeService playerWorkModeService,
                                    EventsBus eventsBus) {
        this.testResetButtonView = testResetButtonView;
        this.flowManager = flowManager;
        this.playerWorkModeService = playerWorkModeService;
        this.eventsBus = eventsBus;
        attachHandler();
    }

    private void attachHandler() {
        testResetButtonView.addHandler(new Command() {
            @Override
            public void execute(NativeEvent event) {
                if (!locked) {
                    changeWorkModeToTest();
                    navigateToFirstItem();
                    resetLesson();
                }
            }

        });
    }

    public Widget getView() {
        return testResetButtonView.asWidget();
    }

    public void lock() {
        locked = true;
        testResetButtonView.lock();
    }

    public void unlock() {
        locked = false;
        testResetButtonView.unlock();
    }

    public void enablePreviewMode() {
        lock();
        testResetButtonView.enablePreviewMode();
    }

    private void changeWorkModeToTest() {
        playerWorkModeService.tryToUpdateWorkMode(PlayerWorkMode.TEST);
    }

    private void navigateToFirstItem() {
        flowManager.invokeFlowRequest(new FlowRequest.NavigateFirstItem());
    }

    protected void resetLesson() {
        eventsBus.fireEvent(new LessonResetEvent(LessonResetEventTypes.RESET));
    }
}
