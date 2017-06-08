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

package eu.ydp.empiria.player.client.module.test.reset.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.workmode.ModeStyleNameConstants;
import eu.ydp.empiria.player.client.module.test.TestStyleNameConstants;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public class TestResetButtonViewImpl extends Composite implements TestResetButtonView {

    private static TestResetButtonViewIUiBinder uiBinder = GWT.create(TestResetButtonViewIUiBinder.class);

    @UiTemplate("TestResetButtonView.ui.xml")
    interface TestResetButtonViewIUiBinder extends UiBinder<Widget, TestResetButtonViewImpl> {
    }

    public TestResetButtonViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    CustomPushButton testResetButton;

    @Inject
    private UserInteractionHandlerFactory userInteractionHandlerFactory;
    @Inject
    private ModeStyleNameConstants styleNameConstants;
    @Inject
    private TestStyleNameConstants testStyleNameConstants;

    @Override
    public void addHandler(Command command) {
        userInteractionHandlerFactory.createUserClickHandler(command)
                .apply(this);
    }

    @Override
    public void lock() {
        addStyleName(testStyleNameConstants.QP_TEST_RESET_DISABLED());
    }

    @Override
    public void unlock() {
        removeStyleName(testStyleNameConstants.QP_TEST_RESET_DISABLED());
    }

    @Override
    public void enablePreviewMode() {
        addStyleName(styleNameConstants.QP_MODULE_MODE_PREVIEW());
    }
}
