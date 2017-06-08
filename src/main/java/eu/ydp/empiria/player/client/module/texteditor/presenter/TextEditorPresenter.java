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

package eu.ydp.empiria.player.client.module.texteditor.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.texteditor.model.TextEditorModel;
import eu.ydp.empiria.player.client.module.texteditor.view.TextEditorView;
import eu.ydp.empiria.player.client.module.texteditor.wrapper.TextEditorJSWrapper;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class TextEditorPresenter {

    private final TextEditorView view;
    private final TextEditorJSWrapper textEditorJSWrapper;
    private String moduleId;

    @Inject
    public TextEditorPresenter(@ModuleScoped TextEditorView view, TextEditorJSWrapper textEditorJSWrapper) {
        this.view = view;
        this.textEditorJSWrapper = textEditorJSWrapper;
    }

    public void init(String moduleId) {
        this.moduleId = moduleId;
        view.init();
    }

    public void convertEditor() {
        textEditorJSWrapper.convert(moduleId);
    }

    public void setTextEditorModel(TextEditorModel textEditorModel) {
        setContent(textEditorModel.getContent());
    }

    public TextEditorModel getTextEditorModel() {
        return new TextEditorModel(getContent());
    }

    private void setContent(String text) {
        textEditorJSWrapper.setContent(moduleId, text);
    }

    private String getContent() {
        return textEditorJSWrapper.getContent(moduleId);
    }

    public void lock() {
        textEditorJSWrapper.lock(moduleId);
        view.lock();
    }

    public void unlock() {
        textEditorJSWrapper.unlock(moduleId);
        view.unlock();
    }

    public void enablePreviewMode() {
        textEditorJSWrapper.lock(moduleId);
        view.enablePreviewMode();
    }

    public Widget getView() {
        return view.asWidget();
    }
}
