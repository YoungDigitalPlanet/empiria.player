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

package eu.ydp.empiria.player.client.module.media.progress;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ProgressUpdateLogicTest {

    private ProgressUpdateLogic testObj;

    @Before
    public void init() {
        testObj = new ProgressUpdateLogic();
    }

    @Test
    public void shouldReturnTrue_whenDiffIsBiggerThanOne() {
        // when
        int lastTime = 5;
        double currentTime = lastTime + 2;

        // when
        boolean result = testObj.isReadyToUpdate(currentTime, lastTime);

        // than
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnTrue_whenCurrentTimeIsSmaller() {
        // when
        int lastTime = 5;
        double currentTime = lastTime - 2;

        // when
        boolean result = testObj.isReadyToUpdate(currentTime, lastTime);

        // than
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnFalse_whenDiffIsLessThanOne() {
        // when
        int lastTime = 5;
        double currentTime = lastTime + 0.5;

        // when
        boolean result = testObj.isReadyToUpdate(currentTime, lastTime);

        // than
        assertThat(result).isFalse();
    }
}
