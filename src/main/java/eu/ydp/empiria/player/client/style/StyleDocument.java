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

import java.util.List;
import java.util.Map;

public class StyleDocument {

    protected StyleSheet styleSheet;
    protected String basePath;

    public StyleDocument(JavaScriptObject styleSheet, String basePath) {
        this.styleSheet = new StyleSheet(styleSheet);
        int index1 = basePath.lastIndexOf('\\');
        int index2 = basePath.lastIndexOf('/');
        if (index1 > index2) {
            this.basePath = basePath.substring(0, index1 + 1);
        } else if (index2 > index1) {
            this.basePath = basePath.substring(0, index2 + 1);
        } else {
            this.basePath = basePath;
        }
    }

    public Map<String, String> getDeclarationsForSelectors(List<String> selectors) {
        Map<String, String> result = styleSheet.getDeclarationsForSelectors(selectors);
        for (String key : result.keySet()) {
            String value = result.get(key);
            if (value.trim().toLowerCase().startsWith("url(")) {
                String value2 = value.trim().toLowerCase();
                String path = value2.substring(value2.indexOf("url(") + 4, value2.lastIndexOf(')'));
                path = path.trim();
                if ((path.charAt(0) == '\"' && path.endsWith("\"")) || (path.charAt(0) == '/' && path.endsWith("/"))) {
                    path = path.substring(1, path.length() - 1);
                }
                path = basePath + path;
                result.put(key, path);
            }
        }
        return result;
    }

    public String getBasePath() {
        return basePath;
    }

}
