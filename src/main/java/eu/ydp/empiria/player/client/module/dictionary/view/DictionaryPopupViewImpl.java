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

package eu.ydp.empiria.player.client.module.dictionary.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

import javax.inject.Inject;

public class DictionaryPopupViewImpl extends Composite implements DictionaryPopupView {

    private static DictionaryPopupViewIUiBinder uiBinder = GWT.create(DictionaryPopupViewIUiBinder.class);

    @UiTemplate("DictionaryPopupView.ui.xml")
    interface DictionaryPopupViewIUiBinder extends UiBinder<Widget, DictionaryPopupViewImpl> {
    }

    @Inject
    private RootPanelDelegate rootPanelDelegate;

    @Inject
    private UserInteractionHandlerFactory userInteractionHandlerFactory;

    public DictionaryPopupViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    FlowPanel container;

    @UiField
    CustomPushButton closeButton;

    @Override
    public void addHandler(Command command) {
        userInteractionHandlerFactory.createUserClickHandler(command).apply(closeButton);
    }

    @Override
    public void show() {
        RootPanel rootPanel = rootPanelDelegate.getRootPanel();
        rootPanel.add(this);
    }

    @Override
    public void hide() {
        RootPanel rootPanel = rootPanelDelegate.getRootPanel();
        rootPanel.remove(this);
    }

    @Override
    public Panel getContainer() {
        return container;
    }
}
