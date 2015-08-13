package eu.ydp.empiria.player.client.module.button;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.xml.client.Element;
import com.google.inject.Binder;
import com.google.inject.Module;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.controller.CurrentPageProperties;
import eu.ydp.empiria.player.client.controller.flow.request.FlowRequestInvoker;
import eu.ydp.empiria.player.client.controller.workmode.ModeStyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.feedback.FeedbackEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class FeedbackAudioMuteButtonModuleTest extends AbstractTestBaseWithoutAutoInjectorInit {

    private static final String DISABLED_STYLE_NAME = "qp-feedback-audio-mute-off-button qp-feedback-audio-mute-disabled";

    private EventsBus eventsBus;
    private CurrentPageProperties currentPageProperties;
    private CustomPushButton button;
    private ModeStyleNameConstants styleNameConstants;

    private FeedbackAudioMuteButtonModule testObj;
    protected ClickHandler handler;
    private FlowRequestInvoker requestInvoker;

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

        testObj = spy(injector.getInstance(FeedbackAudioMuteButtonModule.class));
        eventsBus = injector.getInstance(EventsBus.class);
        currentPageProperties = injector.getInstance(CurrentPageProperties.class);
        requestInvoker = mock(FlowRequestInvoker.class);
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

    private static class CustomGuiceModule implements Module {
        @Override
        public void configure(Binder binder) {
            CustomPushButton customPushButton = mock(CustomPushButton.class);
            binder.bind(CustomPushButton.class)
                    .toInstance(customPushButton);
            CurrentPageProperties currentPageProperties = mock(CurrentPageProperties.class);
            binder.bind(CurrentPageProperties.class)
                    .toInstance(currentPageProperties);
        }
    }

    @Test
    public void shouldFireEventOnInvoke() {
        // when
        testObj.invokeRequest();

        // then
        verify(eventsBus).fireEvent(any(FeedbackEvent.class));
    }

    @Test
    public void testChangeStyleOnTestPageLoadedExpectsVisibleWhenPageContainsInteractiveModules() {
        when(currentPageProperties.hasInteractiveModules()).thenReturn(true);
        testObj.onPlayerEvent(new PlayerEvent(PlayerEventTypes.PAGE_LOADED));

        String expected = FeedbackAudioMuteButtonModule.STYLE_NAME__OFF;
        assertEquals(testObj.getStyleName(), expected);
    }

    @Test
    public void testChangeStyleOnTestPageLoadedExpectsHiddenWhenPageContainsInteractiveModules() {
        when(currentPageProperties.hasInteractiveModules()).thenReturn(false);

        testObj.onPlayerEvent(new PlayerEvent(PlayerEventTypes.PAGE_LOADED));

        String expected = FeedbackAudioMuteButtonModule.STYLE_NAME__OFF + " " + FeedbackAudioMuteButtonModule.STYLE_NAME__DISABLED;
        assertEquals(testObj.getStyleName(), expected);
    }

    @Test
    public void shouldNotInvokeActionInPreviewMode() {
        // given
        testObj.initModule(mock(Element.class));
        doReturn(null).when(testObj)
                .getCurrentGroupIdentifier();
        testObj.setFlowRequestsInvoker(requestInvoker);
        testObj.enablePreviewMode();

        // when
        handler.onClick(null);

        // then
        verifyZeroInteractions(requestInvoker);
    }

    @Test
    public void shouldNotOverwriteStyleInPreview() {
        // given
        final String inactiveStyleName = "STYLE_NAME";
        testObj.initModule(mock(Element.class));
        doReturn(null).when(testObj)
                .getCurrentGroupIdentifier();
        when(styleNameConstants.QP_MODULE_MODE_PREVIEW()).thenReturn(inactiveStyleName);
        testObj.enablePreviewMode();

        // when
        testObj.updateStyleName();

        // then
        InOrder inOrder = inOrder(button);
        inOrder.verify(button)
                .setStyleName(DISABLED_STYLE_NAME);
        inOrder.verify(button)
                .addStyleName(inactiveStyleName);
    }
}
