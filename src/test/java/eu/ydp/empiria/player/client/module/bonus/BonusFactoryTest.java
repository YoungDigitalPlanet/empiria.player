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

package eu.ydp.empiria.player.client.module.bonus;

import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusResource;
import eu.ydp.gwtutil.client.util.geom.Size;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusResourceType.IMAGE;
import static eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusResourceType.SWIFFY;
import static eu.ydp.empiria.player.client.module.bonus.BonusConfigMockCreator.createBonus;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BonusFactoryTest {

    @InjectMocks
    private BonusFactory bonusFactory;
    @Mock
    private Provider<ImageBonus> imageProvider;
    @Mock
    private Provider<SwiffyBonus> swiffyProvider;

    @Before
    public void setUp() {
        when(imageProvider.get()).thenReturn(mock(ImageBonus.class));
        when(swiffyProvider.get()).thenReturn(mock(SwiffyBonus.class));
    }

    @Test
    public void createImage() {
        // given
        String asset = "bonus.png";
        Size size = new Size(1, 2);
        BonusResource bonusResource = createBonus(asset, size, IMAGE);

        // when
        BonusWithAsset bonus = bonusFactory.createBonus(bonusResource);

        // then
        assertThat(bonus).isInstanceOf(ImageBonus.class);
        verify(bonus).setAsset(asset, size);
    }

    @Test
    public void createSwiffy() {
        // given
        String asset = "bonus.png";
        Size size = new Size(1, 2);
        BonusResource bonusResource = createBonus(asset, size, SWIFFY);

        // when
        BonusWithAsset bonus = bonusFactory.createBonus(bonusResource);

        // then
        assertThat(bonus).isInstanceOf(SwiffyBonus.class);
        verify(bonus).setAsset(asset, size);
    }
}
