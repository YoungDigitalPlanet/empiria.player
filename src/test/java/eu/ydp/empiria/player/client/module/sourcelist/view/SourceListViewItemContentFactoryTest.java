package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.gwt.xml.client.Node;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.verify;

@RunWith(GwtMockitoTestRunner.class)
public class SourceListViewItemContentFactoryTest {

    @InjectMocks
    private SourceListViewItemContentFactory testObj;
    @Mock
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;

    @Test
    public void shouldGenerateInlineBody_whenTypeIsMath() {
        // given
        String content = "content";

        // when
        testObj.getSourceListViewItemContent(SourcelistItemType.COMPLEX_TEXT, content, inlineBodyGeneratorSocket);

        // then
        verify(inlineBodyGeneratorSocket).generateInlineBody(isA(Node.class));
    }
}