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

package eu.ydp.empiria.player.client.module.mathjax.interaction;

import com.google.gwt.user.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.math.MathGap;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class MathJaxGapContainerTest {

    private MathJaxGapContainer testObj = new MathJaxGapContainer();

    @Test
    public void shouldReturnCorrectGapElement() {
        // given
        Element firstMathElement = mock(Element.class);
        Element secondMathElement = mock(Element.class);

        MathGap firstMathGap = mock(MathGap.class, RETURNS_DEEP_STUBS);
        MathGap secondMathGap = mock(MathGap.class, RETURNS_DEEP_STUBS);

        String firstGapIdentifier = "first";
        String secondGapIdentifier = "second";

        when(firstMathGap.getContainer().getElement()).thenReturn(firstMathElement);
        when(secondMathGap.getContainer().getElement()).thenReturn(secondMathElement);

        when(firstMathGap.getIdentifier()).thenReturn(firstGapIdentifier);
        when(secondMathGap.getIdentifier()).thenReturn(secondGapIdentifier);

        testObj.addMathGap(firstMathGap);
        testObj.addMathGap(secondMathGap);

        // when
        com.google.gwt.dom.client.Element firstResult = testObj.getMathGapElement(firstGapIdentifier);
        com.google.gwt.dom.client.Element secondResult = testObj.getMathGapElement(secondGapIdentifier);

        // then
        assertThat(firstResult).isEqualTo(firstMathElement);
        assertThat(secondResult).isEqualTo(secondMathElement);
    }
}
