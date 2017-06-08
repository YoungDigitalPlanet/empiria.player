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

package eu.ydp.empiria.player.client.util.style;

import com.google.common.base.Strings;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Style;

import java.util.Map;

public class CssHelper {

    private String prepareValue(String value) {
        return Strings.nullToEmpty(value).replaceAll("[ ;]+", ""); // NOPMD
    }

    public boolean checkIfEquals(Map<String, String> styles, String attributeName, String attributeValue) {
        boolean status = false;
        if (styles.containsKey(attributeName)) {
            String fromApplication = prepareValue(attributeValue);
            String fromCss = prepareValue(styles.get(attributeName));
            status = fromApplication.equalsIgnoreCase(fromCss);
        }
        return status;
    }

    public boolean checkIfEquals(Style styles, String attributeName, String attributeValue) {
        boolean status = false;
        String value = styles.getProperty(attributeName);
        if (value != null) {
            String fromApplication = prepareValue(attributeValue);
            String fromCss = prepareValue(value);
            status = fromApplication.equalsIgnoreCase(fromCss);
        }
        return status;
    }

    public native Style getComputedStyle(JavaScriptObject element)/*-{
        try {
            return $wnd.getComputedStyle(element);
        } catch (e) {
            return element.style;
        }
    }-*/;
}
