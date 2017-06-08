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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.inject.Singleton;

@Singleton
public class MathJaxNative {

    public void renderMath() {
        renderMathNative();
    }

    public void addElementToRender(Element element) {
        addElementNative(element);
    }

    private native void addElementNative(Element element) /*-{
        $wnd.MathJax.Hub.yElements.push(element);
    }-*/;

    private native void renderMathNative() /*-{
        var mathJax = $wnd.MathJax;
        if (mathJax && typeof(mathJax.Hub.yProcessElements) === 'function') {
            mathJax.Hub.yProcessElements();
        }
    }-*/;

    public void renderMath(JavaScriptObject callback) {
        renderMathNative(callback);
    }

    private native void renderMathNative(JavaScriptObject callback) /*-{
        var mathJax = $wnd.MathJax;
        if (mathJax && typeof(mathJax.Hub.yProcessElements) === 'function') {
            mathJax.Hub.yProcessElements(callback);
        }
    }-*/;

    public void rerenderMathElement(String divId) {
        rerenderMathElementNative(divId);
    }

    private native void rerenderMathElementNative(String divId) /*-{
        var mathJax = $wnd.MathJax;
        if (mathJax && typeof(mathJax.Hub.yRerenderElement) === 'function') {
            mathJax.Hub.yRerenderElement(divId);
        }
    }-*/;
}
