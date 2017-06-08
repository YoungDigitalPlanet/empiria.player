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

package eu.ydp.empiria.player.client.module.connection;

import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class ConnectionSurfaceStyleProviderJUnitTest {

    private ConnectionSurfaceStyleProvider provider;

    @Before
    public void setUp() {
        provider = new ConnectionSurfaceStyleProvider();
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({"-1, 0", "-1, -1", "0, -1"})
    public void shouldThrowException(int leftIndex, int rightIndex) {
        // given
        MultiplePairModuleConnectType type = MultiplePairModuleConnectType.NORMAL;

        // when
        List<String> styles = provider.getStylesForSurface(type, leftIndex, rightIndex);
    }

    @Test
    @Parameters({"0, 0, qp-connection-line-0-0", "1, 2, qp-connection-line-1-2"})
    public void shouldGetStyleForNormal(int leftIndex, int rightIndex, String expectedNormalStyle) {
        // given
        MultiplePairModuleConnectType type = MultiplePairModuleConnectType.NORMAL;

        // when
        List<String> styles = provider.getStylesForSurface(type, leftIndex, rightIndex);

        // then
        assertEquals(styles.size(), 1);
        assertEquals(styles.get(0), expectedNormalStyle);
    }

    @Test
    @Parameters({"0, 0, qp-connection-line-0-0, qp-connection-line-correct-0-0", "1, 2, qp-connection-line-1-2, qp-connection-line-correct-1-2"})
    public void shouldGetStylesForCorrect(int leftIndex, int rightIndex, String expectedNormalStyle, String expectedCorrectStyle) {
        // given
        MultiplePairModuleConnectType type = MultiplePairModuleConnectType.CORRECT;

        // when
        List<String> styles = provider.getStylesForSurface(type, leftIndex, rightIndex);

        // then
        assertEquals(styles.size(), 2);
        assertEquals(styles.get(0), expectedNormalStyle);
        assertEquals(styles.get(1), expectedCorrectStyle);
    }

    @Test
    @Parameters({"0, 0, qp-connection-line-0-0, qp-connection-line-wrong-0-0", "1, 2, qp-connection-line-1-2, qp-connection-line-wrong-1-2"})
    public void shouldGetStylesForWrong(int leftIndex, int rightIndex, String expectedNormalStyle, String expectedWrongStyle) {
        // given
        MultiplePairModuleConnectType type = MultiplePairModuleConnectType.WRONG;

        // when
        List<String> styles = provider.getStylesForSurface(type, leftIndex, rightIndex);

        // then
        assertEquals(styles.size(), 2);
        assertEquals(styles.get(0), expectedNormalStyle);
        assertEquals(styles.get(1), expectedWrongStyle);
    }

}
