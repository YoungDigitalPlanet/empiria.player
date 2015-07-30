package eu.ydp.empiria.player.client.module.accordion.presenter;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.inject.Provider;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.module.accordion.AccordionContentGenerator;
import eu.ydp.empiria.player.client.module.accordion.structure.AccordionSectionBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class AccordionSectionFactoryTest {

    @InjectMocks
    private AccordionSectionFactory testObj;
    @Mock
    private Provider<AccordionSectionPresenter> sectionProvider;
    @Mock
    private AccordionSectionPresenter section;
    @Mock
    private HasWidgets hasWidgets;
    @Mock
    private AccordionContentGenerator generator;
    @Mock
    private AccordionSectionBean bean;

    @Before
    public void init() {
        when(sectionProvider.get()).thenReturn(section);
        when(section.getContentContainer()).thenReturn(hasWidgets);
    }

    @Test
    public void shouldCreateTitle_andContent() {
        // given
        XMLContent titleXml = mock(XMLContent.class);
        Element title = mock(Element.class);
        when(bean.getTitle()).thenReturn(titleXml);
        when(titleXml.getValue()).thenReturn(title);

        XMLContent contentXml = mock(XMLContent.class);
        Element content = mock(Element.class);
        when(bean.getContent()).thenReturn(contentXml);
        when(contentXml.getValue()).thenReturn(content);

        Widget widget = mock(Widget.class);
        when(generator.generateInlineBody(title)).thenReturn(widget);

        // when
        testObj.createSection(bean, generator);

        // then
        verify(generator).generateBody(content, hasWidgets);
        verify(section).setTitle(widget);
    }
}