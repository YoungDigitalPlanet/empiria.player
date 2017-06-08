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

package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.connection.exception.CssStyleException;

public class ConnectionStyleCheckerHandler implements Handler {

    private final IsWidget view;
    private final ConnectionStyleChecker connectionStyleChecker;
    private boolean wasChecked = false;

    public ConnectionStyleCheckerHandler(IsWidget view, ConnectionStyleChecker connectionStyleChecker) {
        this.view = view;
        this.connectionStyleChecker = connectionStyleChecker;
        checkStylesAndShowError();
    }

    @Override
    public void onAttachOrDetach(AttachEvent event) {
        if (!wasChecked) {
            checkStylesAndShowError();
            wasChecked = true;
        }
    }

    private void checkStylesAndShowError() {
        try {
            checkStyles();
        } catch (CssStyleException ex) {
            showError(ex);
        }
    }

    private void checkStyles() {
        connectionStyleChecker.areStylesCorrectThrowsExceptionWhenNot(view);
    }

    private void showError(CssStyleException exception) {
        view.asWidget().getElement().setInnerText(exception.getMessage());
    }
}
