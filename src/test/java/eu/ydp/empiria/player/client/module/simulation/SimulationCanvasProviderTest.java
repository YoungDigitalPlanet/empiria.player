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

package eu.ydp.empiria.player.client.module.simulation;

import com.google.common.base.Optional;
import com.google.gwt.user.client.Element;
import eu.ydp.gwtcreatejs.client.loader.CreateJsContent;
import eu.ydp.gwtcreatejs.client.loader.CreateJsLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimulationCanvasProviderTest {

    @InjectMocks
    private SimulationCanvasProvider simulationCanvasProvider;
    @Mock
    private CreateJsLoader createJsLoader;
    @Mock
    private CreateJsContent createJsContent;

    @Test
    public void testGetSimulationCanvas_contentIsNull() {
        // when
        Optional<Element> simulationElemrnt = simulationCanvasProvider.getSimulationCanvasElement(createJsLoader);
        // then
        assertFalse(simulationElemrnt.isPresent());
    }

    @Test
    public void testGetSimulationCanvas_canvasIsNull() {
        // given
        when(createJsLoader.getContent()).thenReturn(createJsContent);
        // when
        Optional<Element> simulationElemrnt = simulationCanvasProvider.getSimulationCanvasElement(createJsLoader);
        // then
        assertFalse(simulationElemrnt.isPresent());
    }
}
