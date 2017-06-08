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

package eu.ydp.empiria.player.client.module.ordering.drag;

import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionOrientation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SortableTest {

    @InjectMocks
    private Sortable testObj;
    @Mock
    private SortableNative sortableNative;

    @Test
    public void shouldinitNative() {
        // given
        final String id = "ID";
        OrderInteractionOrientation orderInteractionOrientation = OrderInteractionOrientation.HORIZONTAL;
        SortCallback callback = mock(SortCallback.class);

        // when
        testObj.init(id, orderInteractionOrientation, callback);

        // then
        verify(sortableNative).init(id, orderInteractionOrientation.getAxis(), callback);
    }

    @Test
    public void shouldEnableNative() {
        // given
        final String id = "ID";

        // when
        testObj.enable(id);

        // then
        verify(sortableNative).enable(id);
    }

    @Test
    public void shouldDisableNative() {
        // given
        final String id = "ID";

        // when
        testObj.disable(id);

        // then
        verify(sortableNative).disable(id);
    }
}
