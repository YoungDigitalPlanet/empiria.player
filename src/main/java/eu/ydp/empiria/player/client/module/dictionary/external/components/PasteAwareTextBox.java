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

package eu.ydp.empiria.player.client.module.dictionary.external.components;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.TextBox;

public class PasteAwareTextBox extends TextBox {

    private PasteListener pasteListener;

    public PasteAwareTextBox() {
        super();
        registerNativePasteEvent(this.getElement());
    }

    private void onPaste() {
        pasteListener.onPaste();
    }

    public void addPasteListener(PasteListener pasteListener) {
        this.pasteListener = pasteListener;
    }

    public interface PasteListener {
        void onPaste();
    }

    private native void registerNativePasteEvent(Element element)/*-{
        var instance = this;
        element.addEventListener("input", function () {
            instance.@eu.ydp.empiria.player.client.module.dictionary.external.components.PasteAwareTextBox::onPaste()();
        }, false);
    }-*/;
}
