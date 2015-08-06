package eu.ydp.empiria.player.client.module.img.picture.player.structure;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class PictureTitleProviderTest {

    @InjectMocks
    private PictureTitleProvider testObj;
    @Mock
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;
    private PicturePlayerBean bean;
    @Mock
    private Widget titleWidget;
    private String titleXml;

    @Before
    public void setUp() throws Exception {
        bean = new PicturePlayerBean();
        PicturePlayerTitleBean titleBean = new PicturePlayerTitleBean();
        bean.setTitleBean(titleBean);
        Element titleElement = mock(Element.class, RETURNS_DEEP_STUBS);
        XMLContent xmlContent = mock(XMLContent.class);
        when(xmlContent.getValue()).thenReturn(titleElement);
        titleBean.setTitleName(xmlContent);
        when(inlineBodyGeneratorSocket.generateInlineBody(titleElement)).thenReturn(titleWidget);
        when(titleElement.getChildNodes().toString()).thenReturn(titleXml);
    }


    @Test
    public void shouldReturnTitleXmlString() {
        // when
        String titleString = testObj.getPictutreTitleString(bean);
        // then
        assertThat(titleString).isEqualTo(titleXml);
    }

    @Test
    public void shouldReturnEmptyXmlString() {
        // given
        bean.setTitleBean(null);
        // when
        String titleString = testObj.getPictutreTitleString(bean);
        // then
        assertThat(titleString).isEqualTo("");
    }

    @Test
    public void shouldCreateEmptyTitleWidget() {
        // given
        bean.setTitleBean(null);
        // when
        Widget titleWidgetResult = testObj.getPictureTitleWidget(bean, inlineBodyGeneratorSocket);
        // then
        assertThat(titleWidgetResult).isNotEqualTo(titleWidget);
    }

    @Test
    public void shoulCreateTitleWidget() {
        // when
        Widget titleWidgetResult = testObj.getPictureTitleWidget(bean, inlineBodyGeneratorSocket);
        // then
        assertThat(titleWidgetResult).isEqualTo(titleWidget);
    }
}
