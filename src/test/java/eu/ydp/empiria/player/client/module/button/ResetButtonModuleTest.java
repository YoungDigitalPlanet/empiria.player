package eu.ydp.empiria.player.client.module.button;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.xml.client.Element;
import com.google.inject.Binder;
import com.google.inject.Module;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequest;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.controller.workmode.ModeStyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings("PMD")
public class ResetButtonModuleTest extends AbstractTestBaseWithoutAutoInjectorInit {

    private static final String DISABLED_STYLE_NAME = "qp-reset-button-disabled";

    ResetButtonModule instance;
    FlowRequestInvoker requestInvoker;
    protected ClickHandler handler;

    private CustomPushButton button;
    private ModeStyleNameConstants styleNameConstants;

    private static class CustomGuiceModule implements Module {
        @Override
        public void configure(Binder binder) {
            binder.bind(CustomPushButton.class)
                    .toInstance(mock(CustomPushButton.class));
        }
    }

    @BeforeClass
    public static void disarm() {
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void rearm() {
        GWTMockUtilities.restore();
    }

    @Before
    public void before() {
        setUp(new Class<?>[]{CustomPushButton.class}, new Class<?>[]{}, new Class<?>[]{EventsBus.class}, new CustomGuiceModule());
        instance = spy(injector.getInstance(ResetButtonModule.class));
        requestInvoker = mock(FlowRequestInvoker.class);
        instance.setFlowRequestsInvoker(requestInvoker);
        button = injector.getInstance(CustomPushButton.class);
        styleNameConstants = injector.getInstance(ModeStyleNameConstants.class);
        doAnswer(new Answer<ClickHandler>() {

            @Override
            public ClickHandler answer(InvocationOnMock invocation) throws Throwable {
                handler = (ClickHandler) invocation.getArguments()[0];
                return null;
            }
        }).when(button)
                .addClickHandler(any(ClickHandler.class));
    }

    @Test
    public void testOnDeliveryEvent() {
        Mockito.verifyZeroInteractions(requestInvoker);
    }

    @Test
    public void testInvokeRequest() {
        doReturn(null).when(instance)
                .getCurrentGroupIdentifier();

        instance.invokeRequest();
        verify(requestInvoker).invokeRequest(Matchers.any(FlowRequest.Reset.class));
    }

    @Test
    public void testGetStyleName() {
        assertEquals("qp-reset-button", instance.getStyleName());
    }

    @Test
    public void shouldNotInvokeActionInPreviewMode() {
        // given
        instance.initModule(mock(Element.class));
        doReturn(null).when(instance)
                .getCurrentGroupIdentifier();
        instance.setFlowRequestsInvoker(requestInvoker);
        instance.enablePreviewMode();

        // when
        handler.onClick(null);

        // then
        verifyZeroInteractions(requestInvoker);
    }

    @Test
    public void shouldNotOverwriteStyleInPreview() {
        // given
        final String inactiveStyleName = "STYLE_NAME";
        instance.initModule(mock(Element.class));
        doReturn(null).when(instance)
                .getCurrentGroupIdentifier();
        when(styleNameConstants.QP_MODULE_MODE_PREVIEW()).thenReturn(inactiveStyleName);
        instance.enablePreviewMode();

        // when
        instance.updateStyleName();

        // then
        InOrder inOrder = inOrder(button);
        inOrder.verify(button)
                .setStyleName(DISABLED_STYLE_NAME);
        inOrder.verify(button)
                .addStyleName(inactiveStyleName);
        inOrder.verify(button)
                .setStyleName(DISABLED_STYLE_NAME);
        inOrder.verify(button)
                .addStyleName(inactiveStyleName);
    }
}
