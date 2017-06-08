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

package eu.ydp.empiria.player.client.controller.variables.processor.global;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class IgnoredModulesTest {

    private final IgnoredModules testObj = new IgnoredModules();

    @Test
    public void shouldReturnTrue_whenListContainsId() {
        // given
        String id = "sample_id";
        testObj.addIgnoredID(id);

        // when
        boolean result = testObj.isIgnored(id);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalse_whenListNotContainsId() {
        // given
        String id = "sample_id";

        // when
        boolean result = testObj.isIgnored(id);

        // then
        assertThat(result).isFalse();
    }
}
