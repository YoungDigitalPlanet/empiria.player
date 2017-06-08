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

package eu.ydp.empiria.player.client.module.ordering;

import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.ordering.drag.DragController;
import eu.ydp.empiria.player.client.module.ordering.presenter.OrderInteractionPresenter;
import eu.ydp.empiria.player.client.module.ordering.structure.OrderInteractionOrientation;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderInteractionModuleTest {

    @InjectMocks
    private OrderInteractionModule testObj;
    @Mock
    private OrderInteractionModuleModel moduleModel;
    @Mock
    private OrderInteractionPresenter presenter;
    @Mock
    private DragController dragController;
    @Mock
    private EventsBus eventsBus;
    @Mock
    private Provider<CurrentPageScope> providerCurrentPageScope;

    @Test
    public void shouldInitDragOnStart() {
        // given
        boolean locked = false;
        when(presenter.getOrientation()).thenReturn(OrderInteractionOrientation.VERTICAL);

        // when
        testObj.onBodyLoad();

        // then
        verify(presenter).getOrientation();
        verify(dragController).init(OrderInteractionOrientation.VERTICAL);
        verify(presenter).setLocked(locked);
    }

    @Test
    public void shouldInitializeModuleModel() {
        // given

        // when
        testObj.initalizeModule();

        // then
        verify(moduleModel).initialize(testObj);
    }
}
