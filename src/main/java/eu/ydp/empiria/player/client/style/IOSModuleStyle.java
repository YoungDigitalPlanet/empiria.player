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

import com.google.common.base.Objects;

import java.util.HashMap;
import java.util.Map;

public class IOSModuleStyle extends HashMap<String, String> implements ModuleStyle {
    private static final long serialVersionUID = 1L;

    public IOSModuleStyle(Map<String, String> styles) {
        putAll(styles);
    }

    /*
     * HACK for IOS
     */
    @Override
    public String get(Object key) {
        for (Map.Entry<String, String> entry : entrySet()) {
            if (Objects.equal(key, entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

}
