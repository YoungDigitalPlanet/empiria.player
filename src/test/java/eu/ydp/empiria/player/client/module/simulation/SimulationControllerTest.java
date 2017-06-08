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

import com.google.gwt.user.client.Element;
import eu.ydp.gwtutil.client.json.NativeMethodInvocator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimulationControllerTest {

    @InjectMocks
    private SimulationController testObj;
    @Mock
    private NativeMethodInvocator methodInvocator;

    @Test
    public void testPauseAnimation() {
        // given
        Element element = mock(Element.class);
        String functionName = "pauseAnimation";

        // when
        testObj.pauseAnimation(element);

        // then
        verify(methodInvocator).callMethod(Matchers.eq(element), Matchers.eq(functionName));
    }

    @Test
    public void testResumeAnimation() {
        // given
        Element element = mock(Element.class);
        String functionName = "resumeAnimation";

        // when
        testObj.resumeAnimation(element);

        // then
        verify(methodInvocator).callMethod(Matchers.eq(element), Matchers.eq(functionName));
    }

    @Test
    public void testOnWindowResized() {
        // given
        Element element = mock(Element.class);
        String functionName = "onWindowResized";

        // when
        testObj.onWindowResized(element);

        // then
        verify(methodInvocator).callMethod(Matchers.eq(element), Matchers.eq(functionName));
    }
}
