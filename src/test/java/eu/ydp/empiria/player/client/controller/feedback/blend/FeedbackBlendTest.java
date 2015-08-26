package eu.ydp.empiria.player.client.controller.feedback.blend;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.feedback.FeedbackStyleNameConstants;
import eu.ydp.empiria.player.client.module.feedback.text.TextFeedback;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class FeedbackBlendTest {

    private FeedbackBlend testObj;
    @Mock
    private FeedbackBlendView view;
    @Mock
    private FeedbackStyleNameConstants styleNameConstants;
    @Mock
    private RootPanelDelegate rootPanelDelegate;
    @Mock
    private RootPanel rootPanel;
    @Mock
    private UserInteractionHandlerFactory userInteractionHandlerFactory;
    @Mock
    private TextFeedback textFeedback;
    @Captor
    private ArgumentCaptor<Command> commandCaptor;

    private String blendHidden ="blendHidden";

    @Before
    public void init(){
        when(styleNameConstants.QP_FEEDBACK_BLEND_HIDDEN()).thenReturn(blendHidden);
        when(rootPanelDelegate.getRootPanel()).thenReturn(rootPanel);
        Widget viewWidget = mock(Widget.class);
        when(view.asWidget()).thenReturn(viewWidget);

        testObj = new FeedbackBlend(view, styleNameConstants, rootPanelDelegate, userInteractionHandlerFactory);
        verify(userInteractionHandlerFactory).applyUserClickHandler(commandCaptor.capture(), eq(viewWidget));
    }

    @Test
    public void shouldRemoveStyle_onShow(){
        // when
        testObj.show(textFeedback);

        // then
        verify(view).removeStyleName(blendHidden);
    }

    @Test
    public void shouldHideFeedback_onClick_whenShown(){
        // given
        NativeEvent nativeEvent = mock(NativeEvent.class);
        Command value = commandCaptor.getValue();
        testObj.show(textFeedback);

        // when
        value.execute(nativeEvent);

        // then
        verify(textFeedback).hide();
    }

    @Test
    public void shouldAddStyle_onHide() {
        // when
        testObj.hide();

        // then
        verify(view).addStyleName(blendHidden);
    }

    @Test
    public void shouldNotHideFeedback_onClick_whenNotShown(){
        // given
        NativeEvent nativeEvent = mock(NativeEvent.class);
        Command value = commandCaptor.getValue();

        // when
        value.execute(nativeEvent);

        // then
        verify(textFeedback, never()).hide();
    }

    @Test
    public void shouldHideBlend_onClick(){
        // given
        NativeEvent nativeEvent = mock(NativeEvent.class);
        Command value = commandCaptor.getValue();

        // when
        value.execute(nativeEvent);

        // then
        verify(view).addStyleName(blendHidden);
    }
}