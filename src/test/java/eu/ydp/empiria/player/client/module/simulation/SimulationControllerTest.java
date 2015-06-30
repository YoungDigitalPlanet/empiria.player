package eu.ydp.empiria.player.client.module.simulation;

import com.google.gwt.user.client.Element;
import eu.ydp.gwtutil.client.json.NativeMethodInvocator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimulationControllerTest {

    @InjectMocks
    private SimulationController testObj;
    @Mock
    private NativeMethodInvocator methodInvocator;

    @Test
    public void testPauseAnimation() {
        // given
        Element element = mock(Element.class);
        String functionName = "pauseAnimation";

        // when
        testObj.pauseAnimation(element);

        // then
        verify(methodInvocator).callMethod(Matchers.eq(element), Matchers.eq(functionName));
    }

    @Test
    public void testResumeAnimation() {
        // given
        Element element = mock(Element.class);
        String functionName = "resumeAnimation";

        // when
        testObj.resumeAnimation(element);

        // then
        verify(methodInvocator).callMethod(Matchers.eq(element), Matchers.eq(functionName));
    }

    @Test
    public void testOnWindowResized() {
        // given
        Element element = mock(Element.class);
        String functionName = "onWindowResized";

        // when
        testObj.onWindowResized(element);

        // then
        verify(methodInvocator).callMethod(Matchers.eq(element), Matchers.eq(functionName));
    }
}
