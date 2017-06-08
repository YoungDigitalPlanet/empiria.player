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

package eu.ydp.empiria.player.client.view.player;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class ElementCacheJUnitTest {

    class SimpleCache extends AbstractElementCache<String> {
        @Override
        protected String getElement(Integer index) {
            return null;
        }
    }

    protected SimpleCache cache = null;

    @Before
    public void setUp() {
        cache = spy(new SimpleCache());
        when(cache.getElement(Matchers.anyInt())).then(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                return String.valueOf(invocation.getArguments()[0]);
            }
        });
    }

    @Test
    public void cacheEmptyTest() {
        assertTrue("cache is not empty", cache.isEmpty());
        cache.getOrCreateAndPut(0);
        cache.getOrCreateAndPut(1);
        assertFalse("cache is empty", cache.isEmpty());
    }

    @Test
    public void cacheAddTest() {
        assertTrue("wrong return value from cache", cache.getOrCreateAndPut(0).equals("0"));
        assertTrue("wrong return value from cache", cache.getOrCreateAndPut(1).equals("1"));
        assertTrue("wrong return value from cache", cache.getOrCreateAndPut(0).equals("0"));
        Mockito.verify(cache, Mockito.times(2)).getElement(Matchers.anyInt());
    }

    @Test
    public void cacheContainsTest() {
        cache.getOrCreateAndPut(0);
        cache.getOrCreateAndPut(1);
        assertFalse("wrong value in cache", cache.isPresent(20));
        assertTrue("missing value in cache", cache.isPresent(0));
        assertTrue("missing value in cache", cache.isPresent(1));
    }
}
