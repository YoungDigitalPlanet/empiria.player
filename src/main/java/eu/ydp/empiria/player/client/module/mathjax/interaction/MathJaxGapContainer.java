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

package eu.ydp.empiria.player.client.module.mathjax.interaction;

import com.google.common.collect.Maps;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.module.math.MathGap;

import java.util.Map;

@Singleton
public class MathJaxGapContainer {

    private final Map<String, Widget> mathGaps = Maps.newHashMap();

    public MathJaxGapContainer() {
        initJavaScriptApi();
    }

    private native void initJavaScriptApi() /*-{
        var that = this;
        getMathGap = function (identifier) {
            return that.@MathJaxGapContainer::getMathGapElement(*)(identifier);
        };
    }-*/;

    public Element getMathGapElement(String identifier) {
        Widget gap = mathGaps.get(identifier);
        return gap.getElement();
    }

    public void addMathGap(MathGap mathGap) {
        mathGaps.put(mathGap.getIdentifier(), mathGap.getContainer());
    }

    public void addMathGap(Widget mathGap, String id) {
        mathGaps.put(id, mathGap);
    }
}
