package eu.ydp.empiria.player.client.controller.feedback.blend;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.feedback.FeedbackStyleNameConstants;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

    private String blendHidden ="blendHidden";

    @Before
    public void init(){
        when(styleNameConstants.QP_FEEDBACK_BLEND_HIDDEN()).thenReturn(blendHidden);
        when(rootPanelDelegate.getRootPanel()).thenReturn(rootPanel);
        testObj = new FeedbackBlend(view, styleNameConstants, rootPanelDelegate);
    }

    @Test
    public void shouldRemoveStyle_onShow(){
        // when
        testObj.show();

        // then
        verify(view).removeStyleName(blendHidden);
    }

    @Test
    public void shouldAddStyle_onHide() {
        // when
        testObj.hide();

        // then
        verify(view).addStyleName(blendHidden);
    }
}