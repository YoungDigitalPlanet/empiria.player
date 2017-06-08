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

package eu.ydp.empiria.player.client.module.texteditor.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.workmode.ModeStyleNameConstants;
import eu.ydp.empiria.player.client.module.texteditor.TextEditorStyleNameConstants;

public class TextEditorViewImpl extends Composite implements TextEditorView {

    private static TextEditorViewUiBinder uiBinder = GWT.create(TextEditorViewUiBinder.class);

    @UiTemplate("TextEditorView.ui.xml")
    interface TextEditorViewUiBinder extends UiBinder<Widget, TextEditorViewImpl> {
    }

    @UiField
    Panel mainPanel;

    @UiField
    TextArea textEditor;

    @Inject
    private ModeStyleNameConstants styleNameConstants;

    @Inject
    private TextEditorStyleNameConstants textEditorStyleNameConstants;

    @Override
    public void init() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void lock() {
        mainPanel.addStyleName(textEditorStyleNameConstants.QP_TEXT_EDITOR_LOCKED());
    }

    @Override
    public void unlock() {
        mainPanel.removeStyleName(textEditorStyleNameConstants.QP_TEXT_EDITOR_LOCKED());
    }

    @Override
    public void enablePreviewMode() {
        mainPanel.addStyleName(styleNameConstants.QP_MODULE_MODE_PREVIEW());
    }
}
