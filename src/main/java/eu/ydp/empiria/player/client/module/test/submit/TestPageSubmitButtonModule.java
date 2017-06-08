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

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.workmode.WorkModePreviewClient;
import eu.ydp.empiria.player.client.controller.workmode.WorkModeTestSubmittedClient;
import eu.ydp.empiria.player.client.module.core.flow.Lockable;
import eu.ydp.empiria.player.client.module.core.base.SimpleModuleBase;

public class TestPageSubmitButtonModule extends SimpleModuleBase implements Lockable, WorkModePreviewClient, WorkModeTestSubmittedClient {

    private final TestPageSubmitButtonPresenter presenter;

    @Inject
    public TestPageSubmitButtonModule(TestPageSubmitButtonPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Widget getView() {
        return presenter.getView();
    }

    @Override
    public void lock(boolean lock) {
        if (lock) {
            presenter.lock();
        } else {
            presenter.unlock();
        }
    }

    @Override
    public void enableTestSubmittedMode() {
        presenter.enableTestSubmittedMode();
    }

    @Override
    public void disableTestSubmittedMode() {
        presenter.disableTestSubmittedMode();
    }

    @Override
    public void enablePreviewMode() {
        presenter.enablePreviewMode();
    }

    @Override
    protected void initModule(Element element) {
    }
}
