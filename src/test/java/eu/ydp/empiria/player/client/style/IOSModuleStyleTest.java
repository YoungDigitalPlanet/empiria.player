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

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class IOSModuleStyleTest {

    @Test
    public void get() throws Exception {
        Map<String, String> map = Maps.newHashMap();
        map.put("x", "xx");
        map.put("x2", "xx2");
        IOSModuleStyle moduleStyle = new IOSModuleStyle(map);
        assertThat(map).isEqualTo(moduleStyle);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String entryKey = entry.getKey();
            String moduleStyleValue = moduleStyle.get(entryKey);
            assertThat(entry.getValue()).isEqualTo(moduleStyleValue);
        }
    }

    @Test
    public void getWithNull() throws Exception {
        Map<String, String> map = Maps.newHashMap();
        map.put("x", "xx");
        map.put("x2", "xx2");
        IOSModuleStyle moduleStyle = new IOSModuleStyle(map);
        assertThat(moduleStyle.get(null)).isNull();
    }
}
