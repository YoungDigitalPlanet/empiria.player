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