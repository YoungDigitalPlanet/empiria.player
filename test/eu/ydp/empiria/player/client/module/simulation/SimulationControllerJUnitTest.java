package eu.ydp.empiria.player.client.module.simulation;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.user.client.Element;

import eu.ydp.gwtutil.client.json.NativeMethodInvocator;

@RunWith(MockitoJUnitRunner.class)
public class SimulationControllerJUnitTest {

	@InjectMocks
	private SimulationController simulationController;
	@Mock
	private NativeMethodInvocator methodInvocator;

	@Test
	public void testPauseAnimation() {
		Element element = mock(Element.class);
		simulationController.pauseAnimation(element);
		verify(methodInvocator).callMethod(Mockito.eq(element), Mockito.eq("pauseAnimation"));
	}

	@Test
	public void testResumeAnimation() {
		Element element = mock(Element.class);
		simulationController.resumeAnimation(element);
		verify(methodInvocator).callMethod(Mockito.eq(element), Mockito.eq("resumeAnimation"));
	}
}
