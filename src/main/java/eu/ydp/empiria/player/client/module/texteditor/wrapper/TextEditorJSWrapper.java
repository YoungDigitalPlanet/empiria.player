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

package eu.ydp.empiria.player.client.module.texteditor.wrapper;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.inject.Inject;

public class TextEditorJSWrapper {

    @Inject
    private TextEditorOptions options;

    public void convert(String moduleId) {
        convertNative(moduleId, options.getOptions());
    }

    public void setContent(String moduleId, String text) {
        setContentNative(moduleId, text);
    }

    public String getContent(String moduleId) {
        return getContentNative(moduleId);
    }

    public void lock(String moduleId) {
        lockNative(moduleId);
    }

    public void unlock(String moduleId) {
        unlockNative(moduleId);
    }

    private native final void convertNative(String moduleId, JavaScriptObject options) /*-{
        $wnd.$("#" + moduleId + " textarea").jqte(options);
    }-*/;

    private native final void setContentNative(String moduleId, String text) /*-{
        $wnd.$("#" + moduleId + " textarea").jqteVal(text);
    }-*/;

    private native final String getContentNative(String moduleId) /*-{
        return $wnd.$("#" + moduleId + " textarea").val();
    }-*/;

    private native final void lockNative(String moduleId) /*-{
        $wnd.$('#' + moduleId + ' .jqte_editor').attr('contenteditable', 'false');
    }-*/;

    private native final void unlockNative(String moduleId) /*-{
        $wnd.$('#' + moduleId + ' .jqte_editor').attr('contenteditable', 'true');
    }-*/;
}
