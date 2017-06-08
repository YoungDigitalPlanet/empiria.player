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

class TextEditorMobileOptions implements TextEditorOptions {

    @Override
    public JavaScriptObject getOptions() {
        return getMobileOptions();
    }

    private native final JavaScriptObject getMobileOptions() /*-{
        var options = {
            fsize: false,
            format: false,
            color: false,
            b: false,
            i: false,
            u: false,
            ol: false,
            ul: false,
            sup: false,
            sub: false,
            left: false,
            center: false,
            right: false,
            strike: false,

            link: false,
            indent: false,
            outdent: false,
            unlink: false,
            remove: false,
            source: false,
            rule: false
        };
        return options;
    }-*/;

}
