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

package eu.ydp.empiria.player.client.controller.extensions.internal.bonus;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class BonusServiceTest {

    private BonusService bonusService;

    @Before
    public void setUp() throws Exception {
        bonusService = new BonusService();
    }

    @Test
    public void shouldReturnPropperBonusForId() {
        // given
        String bonusId = "BONUS_ID";
        BonusConfig bonusConfig = mock(BonusConfig.class);
        bonusService.registerBonus(bonusId, bonusConfig);

        String bonusId2 = "BONUS_ID2";
        BonusConfig bonusConfig2 = mock(BonusConfig.class);
        bonusService.registerBonus(bonusId2, bonusConfig2);

        // when
        BonusConfig returned = bonusService.getBonusConfig(bonusId);

        // then
        assertThat(returned).isEqualTo(bonusConfig);
    }
}
