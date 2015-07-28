package eu.ydp.empiria.player.client.module.accordion.view.section;

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
public class AccordionSectionViewFactoryTest {

    @InjectMocks
    private AccordionSectionViewFactory testObj;
    @Mock
    private Provider<AccordionSectionView> provider;
    @Mock
    private AccordionSectionView view;
    @Mock
    private HasWidgets hasWidgets;
    @Mock
    private AccordionContentGenerator generator;
    @Mock
    private AccordionSectionBean bean;

    @Before
    public void init() {
        when(provider.get()).thenReturn(view);
        when(view.getContentContainer()).thenReturn(hasWidgets);
    }

    @Test
    public void shouldCreateTitle() {
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
        testObj.createView(bean, generator);

        // then
        verify(generator).generateInlineBody(title);
        verify(generator).generateBody(content, hasWidgets);
        verify(view).setTitle(widget);
    }

}