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

package eu.ydp.empiria.player.client.module.abstractmodule.structure;

import eu.ydp.empiria.player.client.structure.ModuleBean;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@SuppressWarnings("PMD")
public class ShuffleHelperTest {

    private static class BeanItem implements HasFixed {
        private final int index;
        private final boolean shuffle;

        public BeanItem(int index, boolean shuffle) {
            this.index = index;
            this.shuffle = shuffle;
        }

        @Override
        public boolean isFixed() {
            return shuffle;
        }

        @Override
        public String toString() {
            return "index=" + index + ", shuffle=" + shuffle;
        }

    }

    public static class Bean extends ModuleBean implements HasShuffle {
        @Override
        public boolean isShuffle() {
            return false;
        }
    }

    private final List<BeanItem> testList = new ArrayList<ShuffleHelperTest.BeanItem>();

    @Before
    public void before() {
        for (int x = 0; x < 9; ++x) {
            testList.add(new BeanItem(x, x % 2 == 0));
        }
    }

    @Test
    public void shuffleEmptyListTest() {
        ShuffleHelper instance = new ShuffleHelper();
        Bean bean = Mockito.mock(Bean.class);
        when(bean.isShuffle()).thenReturn(true);
        testList.clear();
        List<BeanItem> randomizeChoices = instance.randomizeIfShould(bean, testList);
        assertTrue(testList.size() == randomizeChoices.size());
    }

    @Test
    public void sizeShuffleTrueTest() {
        ShuffleHelper instance = new ShuffleHelper();
        Bean bean = Mockito.mock(Bean.class);
        when(bean.isShuffle()).thenReturn(true);
        List<BeanItem> randomizeChoices = instance.randomizeIfShould(bean, testList);
        assertTrue(testList.size() == randomizeChoices.size());
    }

    @Test
    public void sizeShuffleFalseTest() {
        ShuffleHelper instance = new ShuffleHelper();
        Bean bean = Mockito.mock(Bean.class);
        when(bean.isShuffle()).thenReturn(false);
        List<BeanItem> randomizeChoices = instance.randomizeIfShould(bean, testList);
        assertTrue(testList.size() == randomizeChoices.size());
    }

    @Test
    public void shuffleTrueTest() {
        ShuffleHelper instance = new ShuffleHelper();
        Bean bean = Mockito.mock(Bean.class);
        when(bean.isShuffle()).thenReturn(true);
        List<BeanItem> randomizeChoices = instance.randomizeIfShould(bean, testList);
        assertNotSame(testList, randomizeChoices);
    }

    @Test
    public void shuffleFalseTest() {
        ShuffleHelper instance = new ShuffleHelper();
        Bean bean = Mockito.mock(Bean.class);
        when(bean.isShuffle()).thenReturn(false);
        List<BeanItem> randomizeChoices = instance.randomizeIfShould(bean, testList);
        assertEquals(testList, randomizeChoices);
    }

    @Test
    public void fixedWithShuffleTrueTest() {
        ShuffleHelper instance = new ShuffleHelper();
        Bean bean = Mockito.mock(Bean.class);
        when(bean.isShuffle()).thenReturn(true);
        List<BeanItem> randomizeChoices = instance.randomizeIfShould(bean, testList);
        for (int x = 0; x < testList.size(); ++x) {
            if (testList.get(x).isFixed()) {
                assertEquals(testList.get(x), randomizeChoices.get(x));
            }
        }
    }

    @Test
    public void fixedWithShuffleFalseTest() {
        ShuffleHelper instance = new ShuffleHelper();
        Bean bean = Mockito.mock(Bean.class);
        when(bean.isShuffle()).thenReturn(false);
        List<BeanItem> randomizeChoices = instance.randomizeIfShould(bean, testList);
        for (int x = 0; x < testList.size(); ++x) {
            if (testList.get(x).isFixed()) {
                assertEquals(testList.get(x), randomizeChoices.get(x));
            }
        }
    }

}
