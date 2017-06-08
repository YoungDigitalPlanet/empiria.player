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

package eu.ydp.empiria.player.client.module.test.submit;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.flow.FlowManager;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.module.test.submit.view.TestPageSubmitButtonView;
import eu.ydp.gwtutil.client.event.factory.Command;

public class TestPageSubmitButtonPresenter {

    private final TestPageSubmitButtonView testPageSubmitButtonView;
    private final FlowRequestInvoker flowRequestInvoker;
    private boolean locked;

    @Inject
    public TestPageSubmitButtonPresenter(TestPageSubmitButtonView testPageSubmitButtonView, FlowManager flowManager) {
        this.testPageSubmitButtonView = testPageSubmitButtonView;
        this.flowRequestInvoker = flowManager.getFlowRequestInvoker();
        addHandlerToButton();
    }

    private void addHandlerToButton() {
        testPageSubmitButtonView.addHandler(new Command() {

            @Override
            public void execute(NativeEvent event) {
                if (!locked) {
                    nextPage();
                }
            }
        });
    }

    private void nextPage() {
        flowRequestInvoker.invokeRequest(new FlowRequest.NavigateNextItem());
    }

    public Widget getView() {
        return testPageSubmitButtonView.asWidget();
    }

    public void lock() {
        locked = true;
        testPageSubmitButtonView.lock();
    }

    public void unlock() {
        locked = false;
        testPageSubmitButtonView.unlock();
    }

    public void enableTestSubmittedMode() {
        lock();
    }

    public void enablePreviewMode() {
        lock();
        testPageSubmitButtonView.enablePreviewMode();
    }

    public void disableTestSubmittedMode() {
        unlock();
    }
}
