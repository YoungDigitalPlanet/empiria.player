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