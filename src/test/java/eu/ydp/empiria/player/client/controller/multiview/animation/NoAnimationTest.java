package eu.ydp.empiria.player.client.controller.multiview.animation;

import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class NoAnimationTest {

    private final NoAnimation instance = new NoAnimation();
    @Mock
    private FlowPanel flowPanel;
    @Mock
    private Element element;
    @Mock
    private Style style;
    @Mock
    private AnimationEndCallback animationEndCallback;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        doReturn(style).when(element).getStyle();
        doReturn(element).when(flowPanel).getElement();
    }

    @Test
    public void goTo() throws Exception {
        instance.goTo(flowPanel, 100, 90);
        verify(style).setLeft(eq(100d), eq(Unit.PCT));

        instance.goTo(flowPanel, 120, 9);
        verify(style).setLeft(eq(120d), eq(Unit.PCT));

    }

    @Test
    public void addAnimationEndCallback() throws Exception {
        instance.addAnimationEndCallback(animationEndCallback);
        instance.goTo(flowPanel, 100, 90);
        verify(animationEndCallback).onComplate(eq(100));
    }

    @Test
    public void removeAnimationEndCallback() throws Exception {
        instance.addAnimationEndCallback(animationEndCallback);
        instance.removeAnimationEndCallback(animationEndCallback);
        instance.goTo(flowPanel, 100, 90);
        verifyZeroInteractions(animationEndCallback);
    }

    @Test
    public void getPositionX() throws Exception {
        instance.goTo(flowPanel, 100, 90);
        assertThat(instance.getPositionX()).isEqualTo(100d);

        instance.goTo(flowPanel, 110, 90);
        assertThat(instance.getPositionX()).isEqualTo(110d);
    }

    @Test
    public void isRunning() throws Exception {
        assertThat(instance.isRunning()).isFalse();
        instance.goTo(flowPanel, 100, 90);
        assertThat(instance.isRunning()).isFalse();
    }

}
