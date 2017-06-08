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

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.gin.factory.DictionaryModuleFactory;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;
import eu.ydp.empiria.player.client.module.dictionary.external.view.ExplanationView;

import javax.inject.Inject;

public class ExplanationController {

    private final ExplanationView explanationView;

    private Entry entry;
    private final ExplanationDescriptionSoundController explanationDescriptionSoundController;
    private final EntryDescriptionSoundController entryDescriptionSoundController;

    @Inject
    public ExplanationController(ExplanationView explanationView,
                                 DictionaryModuleFactory dictionaryModuleFactory) {
        this.explanationView = explanationView;
        this.explanationDescriptionSoundController = dictionaryModuleFactory.getExplanationDescriptionSoundController(explanationView);
        this.entryDescriptionSoundController = dictionaryModuleFactory.geEntryDescriptionSoundController(explanationView);
    }

    public void init() {
        addExplanationPlayButtonHandler();
        addEntryExamplePanelHandler();
        addEntryPlayButtonHandler();
    }

    public void processEntry(Entry entry) {
        explanationView.processEntry(entry);
        this.entry = entry;
    }

    public void show() {
        explanationView.show();
    }

    public void hide() {
        explanationDescriptionSoundController.stop();
        entryDescriptionSoundController.stop();
        explanationView.hide();
    }

    public IsWidget getView() {
        return explanationView;
    }

    private void addEntryExamplePanelHandler() {
        this.explanationView.addEntryExamplePanelHandler(new MouseUpHandler() {

            @Override
            public void onMouseUp(MouseUpEvent event) {
                entryDescriptionSoundController.playOrStopEntrySound(entry.getEntrySound());
            }
        });
    }

    private void addExplanationPlayButtonHandler() {
        this.explanationView.addPlayButtonHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                explanationDescriptionSoundController.playOrStopExplanationSound(entry.getEntryExampleSound());
            }
        });
    }

    private void addEntryPlayButtonHandler() {
        this.explanationView.addEntryPlayButtonHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                entryDescriptionSoundController.playOrStopEntrySound(entry.getEntrySound());
            }
        });
    }
}