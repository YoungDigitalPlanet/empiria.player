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

package eu.ydp.empiria.player.client.module.dictionary.external.controller;

import com.google.gwt.user.client.ui.Panel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.dictionary.external.view.MainView;

@Singleton
public class MainController implements WordsLoadingListener, ExplanationListener {

    @Inject
    private WordsController wordsController;
    @Inject
    private MainView mainView;
    @Inject
    private ExplanationController explanationController;

    private Panel wrappingPanel;
    private boolean shouldBeInitialized = true;

    public void init(Panel panel) {
        if (shouldBeInitialized) {
            this.wrappingPanel = panel;
            wordsController.load();
            explanationController.init();
            shouldBeInitialized = false;
        }
    }

    @Override
    public void onWordsLoaded() {
        mainView.init();
        wrappingPanel.add(mainView);
        mainView.addViewToContainerView(explanationController.getView());

        if (Options.getViewType() == ViewType.HALF) {
            explanationController.show();
        }
    }

    @Override
    public void onEntryLoaded(final Entry entry) {
        if (Options.getViewType() == ViewType.HALF) {
            mainView.hideMenu();
            explanationController.show();
        }

        if (entry != null) {
            processEntry(entry);
        }
    }

    private void processEntry(Entry entry) {
        explanationController.processEntry(entry);
    }

    @Override
    public void onBackClick() {
        if (Options.getViewType() == ViewType.HALF) {
            mainView.showMenu();
            explanationController.hide();
        }
    }
}