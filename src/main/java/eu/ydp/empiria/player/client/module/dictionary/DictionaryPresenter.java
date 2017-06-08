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

package eu.ydp.empiria.player.client.module.dictionary;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.MainController;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryButtonView;
import eu.ydp.empiria.player.client.module.dictionary.view.DictionaryPopupView;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import javax.inject.Inject;

public class DictionaryPresenter {

    private final DictionaryButtonView dictionaryButtonView;

    private final DictionaryPopupView dictionaryPopupView;

    private final MainController mainController;

    @Inject
    public DictionaryPresenter(@ModuleScoped DictionaryButtonView dictionaryButtonView, @ModuleScoped DictionaryPopupView dictionaryPopupView,
                               MainController mainController) {
        this.dictionaryButtonView = dictionaryButtonView;
        this.dictionaryPopupView = dictionaryPopupView;
        this.mainController = mainController;
    }

    public void bindUi() {
        dictionaryButtonView.addHandler(new Command() {

            @Override
            public void execute(NativeEvent event) {
                showPopup();
            }
        });
        dictionaryPopupView.addHandler(new Command() {

            @Override
            public void execute(NativeEvent event) {
                hidePopup();
            }
        });
    }

    private void hidePopup() {
        dictionaryPopupView.hide();
    }

    private void showPopup() {
        Panel container = dictionaryPopupView.getContainer();
        mainController.init(container);
        dictionaryPopupView.show();
    }

    public Widget getView() {
        return dictionaryButtonView.asWidget();
    }
}
