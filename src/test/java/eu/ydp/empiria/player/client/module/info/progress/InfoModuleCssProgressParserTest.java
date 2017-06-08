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

package eu.ydp.empiria.player.client.module.info.progress;

import eu.ydp.empiria.player.client.style.ModuleStyle;
import eu.ydp.empiria.player.client.style.ModuleStyleImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class InfoModuleCssProgressParserTest {

    private InfoModuleCssProgressParser testObj;
    @Spy
    private final ModuleStyle moduleStyle = new ModuleStyleImpl(new HashMap<String, String>());

    private String prefix = "-empiria-info-item-result-";
    private final String styleName = "xxx-x";

    @Before
    public void init(){
        testObj = new InfoModuleCssProgressParser(prefix, moduleStyle);
    }

    @Test
    public void getCssProgressToStyleMappingNoConfiguration() throws Exception {
        Map<Integer, String> toStyleMapping = testObj.getCssProgressToStyleMapping();
        assertThat(toStyleMapping).isEmpty();
    }

    @Test
    public void getCssProgressToStyleMapping() throws Exception {
        for (int x = 0; x <= 100; x += 9) {
            moduleStyle.put(prefix + x, styleName + x);
        }

        Map<Integer, String> styleMapping = testObj.getCssProgressToStyleMapping();
        assertThat(styleMapping).isNotEmpty();
        assertThat(styleMapping).hasSize(12);

        for (int x = 0; x <= 100; x += 9) {
            assertThat(styleMapping.get(x)).isEqualTo(styleName + x);
        }

    }

}
