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

package eu.ydp.empiria.player.client.module.identification.math;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.identification.math.view.IdentificationMathView;
import eu.ydp.empiria.player.client.module.mathjax.interaction.MathJaxGapContainer;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static eu.ydp.empiria.player.client.resources.EmpiriaTagConstants.ATTR_RESPONSE_IDENTIFIER;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class IdentificationMathModuleTest {

    @InjectMocks
    private IdentificationMathModule testObj;
    @Mock
    private IdentificationMathView view;
    @Mock
    private MathJaxGapContainer mathJaxGapContainer;
    @Mock
    private BodyGeneratorSocket bodyGeneratorSocket;
    @Mock
    private Element element;

    @Test
    public void shouldGenerateBodyFromElement() {
        // when
        testObj.initModule(element, mock(ModuleSocket.class), bodyGeneratorSocket, mock(EventsBus.class));

        // then
        verify(bodyGeneratorSocket).generateBody(element, view);
    }

    @Test
    public void shouldAddViewToMathJaxContainer() {
        // given
        String responseIdentifier = "response";
        when(element.getAttribute(ATTR_RESPONSE_IDENTIFIER)).thenReturn(responseIdentifier);

        Widget widget = mock(Widget.class);
        when(view.asWidget()).thenReturn(widget);

        // when
        testObj.initModule(element, mock(ModuleSocket.class), bodyGeneratorSocket, mock(EventsBus.class));

        // then
        verify(mathJaxGapContainer).addMathGap(widget, responseIdentifier);
    }
}