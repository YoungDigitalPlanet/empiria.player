package eu.ydp.empiria.player.client.module.accordion;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.xml.client.Node;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccordionContentGeneratorTest {

    @InjectMocks
    private AccordionContentGenerator testObj;
    @Mock
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;
    @Mock
    private BodyGeneratorSocket bodyGeneratorSocket;

    @Test
    public void shouldGenerateBody() {
        // given
        Node xmlNode = mock(Node.class);
        HasWidgets hasWidgets = mock(HasWidgets.class);

        // when
        testObj.generateBody(xmlNode, hasWidgets);

        // then
        verify(bodyGeneratorSocket).generateBody(xmlNode, hasWidgets);
    }

    @Test
    public void shouldGenerateInlineBody() {
        // given
        Node xmlNode = mock(Node.class);

        // when
        testObj.generateInlineBody(xmlNode);

        // then
        verify(inlineBodyGeneratorSocket).generateInlineBody(xmlNode);
    }
}