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

package eu.ydp.empiria.player.client.module.dictionary.external.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.dictionary.DictionaryStyleNameConstants;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.dictionary.external.controller.ExplanationListener;
import eu.ydp.empiria.player.client.module.dictionary.external.model.Entry;

@Singleton
public class ExplanationViewImpl extends Composite implements ExplanationView {

    private static ExplanationViewUiBinder uiBinder = GWT.create(ExplanationViewUiBinder.class);

    @UiTemplate("ExplanationView.ui.xml")
    interface ExplanationViewUiBinder extends UiBinder<Widget, ExplanationViewImpl> {
    }

    @Inject
    private DictionaryStyleNameConstants styleNameConstants;

    @UiField
    Panel typePanel;
    @UiField
    Panel entryPanel;
    @UiField
    Panel entryDescriptionPanel;
    @UiField
    Panel entryExamplePanel;
    @UiField
    Panel labelPanel;

    @UiField
    Label typeLabel;
    @UiField
    Label entryLabel;
    @UiField
    InlineHTML entryDescriptionLabel;
    @UiField
    Label entryExampleLabel;

    @UiField
    PushButton backButton;
    @UiField
    PushButton playButton;
    @UiField
    PushButton entryPlayButton;
    @UiField
    Label labelLabel;

    @Inject
    private Provider<ExplanationListener> listenerProvider;

    public ExplanationViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("backButton")
    public void backButtonClick(ClickEvent event) {
        listenerProvider.get().onBackClick();
    }

    @Override
    public void addEntryExamplePanelHandler(MouseUpHandler handler) {
        entryExamplePanel.asWidget().addHandler(handler, MouseUpEvent.getType());
    }

    @Override
    public void addPlayButtonHandler(ClickHandler handler) {
        playButton.addClickHandler(handler);
    }

    @Override
    public void addEntryPlayButtonHandler(ClickHandler handler) {
        entryPlayButton.addClickHandler(handler);
    }

    @Override
    public void processEntry(Entry entry) {
        typeLabel.setText(entry.getType());
        entryLabel.setText(entry.getEntry());
        entryDescriptionLabel.setHTML(entry.getEntryDescription());
        entryExampleLabel.setText(entry.getEntryExample());
        labelLabel.setText(entry.getLabel());
    }

    @Override
    public void show() {
        Style style = getElement().getStyle();
        style.setDisplay(Display.BLOCK);
    }

    @Override
    public void hide() {
        Style style = getElement().getStyle();
        style.setDisplay(Display.NONE);
    }

    @Override
    public void setExplanationPlayButtonStyle() {
        playButton.setStylePrimaryName(styleNameConstants.QP_DICTIONARY_EXPLANATION_PLAY_BUTTON_PLAYING());
    }

    @Override
    public void setExplanationStopButtonStyle() {
        playButton.setStylePrimaryName(styleNameConstants.QP_DICTIONARY_EXPLANATION_PLAY_BUTTON());
    }

    @Override
    public void setEntryPlayButtonStyle() {
        entryPlayButton.setStylePrimaryName(styleNameConstants.QP_DICTIONARY_EXPLANATION_ENTRY_PLAY_BUTTON_PLAYING());
    }

    @Override
    public void setEntryStopButtonStyle() {
        entryPlayButton.setStylePrimaryName(styleNameConstants.QP_DICTIONARY_EXPLANATION_ENTRY_PLAY_BUTTON());
    }

}
