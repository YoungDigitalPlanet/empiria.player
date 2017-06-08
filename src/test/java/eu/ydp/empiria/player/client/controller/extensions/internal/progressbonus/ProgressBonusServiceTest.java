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

package eu.ydp.empiria.player.client.controller.extensions.internal.progressbonus;

import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusService;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class ProgressBonusServiceTest {

    private ProgressBonusService service;

    @Before
    public void setup() {
        service = new ProgressBonusService();
    }

    @Test
    public void shouldReturnPropperProgressBonusForId() {
        // given
        String progressBonusId = "PROGRESSBONUS_ID";
        ProgressBonusConfig config = mock(ProgressBonusConfig.class);
        service.register(progressBonusId, config);

        String progressBonusId2 = "PROGRESSBONUS_ID2";
        ProgressBonusConfig config2 = mock(ProgressBonusConfig.class);
        service.register(progressBonusId2, config2);

        // when
        ProgressBonusConfig returned = service.getProgressBonusConfig(progressBonusId);

        // then
        assertThat(returned).isEqualTo(config);
    }
}
