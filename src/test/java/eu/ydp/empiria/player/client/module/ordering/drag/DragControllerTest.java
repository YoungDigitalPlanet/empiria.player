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

import eu.ydp.empiria.player.client.module.ordering.model.OrderingItemsDao;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionOrientation;
import eu.ydp.empiria.player.client.module.ordering.view.OrderInteractionView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DragControllerTest {

    private static final String ID = "ID";
    private static final String ID_SELECTOR = ".ID";

    @InjectMocks
    private DragController testobj;

    @Mock
    private OrderingItemsDao orderingItemsDao;
    @Mock
    private OrderInteractionView interactionView;
    @Mock
    private Sortable sortable;
    @Mock
    private SortCallback callback;

    @Before
    public void setup() {
        when(interactionView.getMainPanelUniqueCssClass()).thenReturn(ID);
    }

    @Test
    public void shouldInitializeSortable() {
        // given
        OrderInteractionOrientation orientation = OrderInteractionOrientation.VERTICAL;

        // when
        testobj.init(orientation);

        // then
        verify(sortable).init(ID_SELECTOR, orientation, callback);
    }

    @Test
    public void shouldEnableDrag() {
        // when
        testobj.enableDrag();

        // then
        verify(sortable).enable(ID_SELECTOR);
    }

    @Test
    public void shouldDisableDrag() {
        // when
        testobj.disableDrag();

        // then
        verify(sortable).disable(ID_SELECTOR);
    }

}
