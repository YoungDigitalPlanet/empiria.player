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

package eu.ydp.empiria.player.client.module.texteditor.model;

public class TextEditorModel {

    private final String content;

    public TextEditorModel(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public static TextEditorModel createEmpty() {
        return new TextEditorModel("");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TextEditorModel textEditorModel = (TextEditorModel) o;

        if (!content.equals(textEditorModel.content)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return content.hashCode();
    }
}
