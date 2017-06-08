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

package eu.ydp.empiria.player.client.module.accordion;

import com.google.gwt.xml.client.Element;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.accordion.presenter.AccordionPresenter;
import eu.ydp.empiria.player.client.module.accordion.structure.AccordionBean;
import eu.ydp.empiria.player.client.module.accordion.structure.AccordionJAXBParser;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AccordionModuleTest {

    @InjectMocks
    private AccordionModule testObj;
    @Mock
    private AccordionJAXBParser parser;
    @Mock
    private JAXBParser<AccordionBean> jaxbParser;
    @Mock
    private AccordionPresenter presenter;
    @Mock
    private AccordionBean bean;

    @Before
    public void init() {
        when(parser.create()).thenReturn(jaxbParser);
        when(jaxbParser.parse(anyString())).thenReturn(bean);
    }

    @Test
    public void shouldInitializePresenter() {
        // given
        Element element = mock(Element.class);
        ModuleSocket moduleSocket = mock(ModuleSocket.class);
        BodyGeneratorSocket bodyGeneratorSocket = mock(BodyGeneratorSocket.class);
        EventsBus eventsBus = mock(EventsBus.class);

        // when
        testObj.initModule(element, moduleSocket, bodyGeneratorSocket, eventsBus);

        // then
        verify(presenter).initialize(eq(bean), isA(AccordionContentGenerator.class));
    }

}