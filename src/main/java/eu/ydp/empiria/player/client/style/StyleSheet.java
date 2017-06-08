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

package eu.ydp.empiria.player.client.style;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides access to jscsspStylesheet object from http://glazman.org/JSCSSP/
 */
public class StyleSheet {

    private JavaScriptObject styleSheet;

    protected StyleSheet(JavaScriptObject jsCssModel) {
        this.styleSheet = jsCssModel;
    }

    public final native JsArray<JsCssRule> getCssRules(JavaScriptObject styleSheet) /*-{
        return styleSheet.cssRules;
    }-*/;

    /**
     * Adds style properties to existing JSOModel. All style properties are converted to lowercase.
     */
    public final Map<String, String> getDeclarationsForSelectors(List<String> selectors) {
        JsArray<JsCssRule> rules = getCssRules(styleSheet);
        Map<String, String> result = new HashMap<String, String>();
        int ln = rules.length();
        for (int i = 0; i < ln; i++) {
            JsCssRule rule = rules.get(i);
            if (rule.isStyleRule() && selectors.contains(rule.getSelector())) {
                JsArray<JsCssDeclaration> declarations = rule.getDeclarations();
                int dln = declarations.length();
                for (int j = 0; j < dln; j++) {
                    JsCssDeclaration declaration = declarations.get(j);
                    if (declaration.getProperty() != null && declaration.getValue() != null)
                        result.put(declaration.getProperty(), declaration.getValue());
                }
            }
        }
        return result;
    }

    ;

}
