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

package eu.ydp.empiria.player.client.module.accordion.controller;

import com.google.inject.Provider;
import eu.ydp.empiria.player.client.module.accordion.Transition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccordionSectionsControllerProviderTest {

    @InjectMocks
    private AccordionSectionsControllerProvider testObj;
    @Mock
    private Provider<AccordionController<AccordionBothDirectionsController>> bothDirectionsController;
    @Mock
    private Provider<AccordionController<AccordionHorizontalController>> horizontalController;
    @Mock
    private Provider<AccordionController<AccordionVerticalController>> verticalController;
    @Mock
    private AccordionController<AccordionBothDirectionsController> bothDirections;
    @Mock
    private AccordionController<AccordionHorizontalController> horizontal;
    @Mock
    private AccordionController<AccordionVerticalController> vertical;

    @Before
    public void init() {
        when(bothDirectionsController.get()).thenReturn(bothDirections);
        when(horizontalController.get()).thenReturn(horizontal);
        when(verticalController.get()).thenReturn(vertical);
    }

    @Test
    public void shouldGetBothDirectionsController_whenTransitionIsAll() {
        // given
        Transition transition = Transition.ALL;

        // when
        AccordionController controller = testObj.getController(transition);

        // then
        assertThat(controller).isEqualTo(bothDirections);
    }

    @Test
    public void shouldGetBothDirectionsController_whenTransitionIsWidth() {
        // given
        Transition transition = Transition.WIDTH;

        // when
        AccordionController controller = testObj.getController(transition);

        // then
        assertThat(controller).isEqualTo(horizontal);
    }

    @Test
    public void shouldGetBothDirectionsController_whenTransitionIsHeight() {
        // given
        Transition transition = Transition.HEIGHT;

        // when
        AccordionController controller = testObj.getController(transition);

        // then
        assertThat(controller).isEqualTo(vertical);
    }
}