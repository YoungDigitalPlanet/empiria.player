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

package eu.ydp.empiria.player.client.event;

import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CurrentPageScopeTest {

    @Test
    public void scopeCompareTest() {
        Page page = mock(Page.class);
        when(page.getCurrentPageNumber()).thenReturn(1);

        CurrentPageScope currentPageScope = new CurrentPageScope(page);
        CurrentPageScope pageScope = new CurrentPageScope(1);

        assertTrue(currentPageScope.equals(pageScope));
        assertTrue(pageScope.equals(currentPageScope));
        assertEquals(currentPageScope.compareTo(pageScope), 0);

        when(page.getCurrentPageNumber()).thenReturn(2);
        currentPageScope = new CurrentPageScope(page);
        assertEquals(pageScope.compareTo(currentPageScope), -1);
        assertEquals(currentPageScope.compareTo(pageScope), 1);
    }
}
