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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public class DictionaryButtonViewImpl extends Composite implements DictionaryButtonView {

    private static DictionaryButtonViewIUiBinder uiBinder = GWT.create(DictionaryButtonViewIUiBinder.class);

    @UiTemplate("DictionaryButtonView.ui.xml")
    interface DictionaryButtonViewIUiBinder extends UiBinder<Widget, DictionaryButtonViewImpl> {
    }

    public DictionaryButtonViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    CustomPushButton showPopupButton;

    @Inject
    private UserInteractionHandlerFactory userInteractionHandlerFactory;

    @Override
    public void addHandler(Command command) {
        userInteractionHandlerFactory.createUserClickHandler(command).apply(showPopupButton);
    }
}
