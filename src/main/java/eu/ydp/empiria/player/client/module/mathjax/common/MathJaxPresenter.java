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

package eu.ydp.empiria.player.client.module.mathjax.common;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class MathJaxPresenter {

    private final MathJaxView view;
    private final MathJaxNative mathJaxNative;

    @Inject
    public MathJaxPresenter(@Assisted MathJaxView view, MathJaxNative mathJaxNative) {
        this.view = view;
        this.mathJaxNative = mathJaxNative;
    }

    public Widget getView() {
        return view.asWidget();
    }

    public void setMmlScript(String script) {
        view.setMmlScript(script);
        Element scriptElement = view.asWidget().getElement().getFirstChildElement();
        mathJaxNative.addElementToRender(scriptElement);
    }

    public void rerenderMathElement(String moduleId){
        mathJaxNative.rerenderMathElement(moduleId);
    }
}
