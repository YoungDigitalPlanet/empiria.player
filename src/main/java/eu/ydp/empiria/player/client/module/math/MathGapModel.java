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

package eu.ydp.empiria.player.client.module.math;

import com.google.common.collect.Maps;
import eu.ydp.gwtutil.client.StringUtils;

import java.util.Map;

public class MathGapModel {

    private String uid;

    private Map<String, String> mathStyles;

    public String getUid() {
        return (uid == null) ? StringUtils.EMPTY_STRING : uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Map<String, String> getMathStyles() {
        if (mathStyles == null) {
            mathStyles = Maps.newHashMap();
        }
        return mathStyles;
    }

    public void setMathStyles(Map<String, String> mathStyles) {
        this.mathStyles = mathStyles;
    }

    public boolean containsStyle(String key) {
        return mathStyles.containsKey(key);
    }

    public String getStyle(String key) {
        return mathStyles.get(key);
    }
}
