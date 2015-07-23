package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.gwtutil.client.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType.*;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class SourceListViewItemContentFactoryTest {

    @InjectMocks
    private SourceListViewItemContentFactory testObj;
    @Mock
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;
    @Mock
    private XMLParser xmlParser;
    @Mock
    private Element element;

    @Before
    public void init() {
        Document document = mock(Document.class);
        when(xmlParser.parse(anyString())).thenReturn(document);
        when(document.getDocumentElement()).thenReturn(element);
    }

    @Test
    public void shouldGenerateInlineBody_whenTypeIsMath() {
        // given
        String content = "content";
        SourcelistItemValue sourcelistItemValue = new SourcelistItemValue(COMPLEX_TEXT, content, "some id");

        // when
        testObj.createSourceListContentWidget(sourcelistItemValue, inlineBodyGeneratorSocket);

        // then
        verify(inlineBodyGeneratorSocket).generateInlineBody(element);
    }
}