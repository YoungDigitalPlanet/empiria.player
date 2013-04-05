package eu.ydp.empiria.player.client.module.simulation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.user.client.Element;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.gwtutil.client.json.NativeMethodInvocator;

@SuppressWarnings("PMD")
public class SimulationControllerJUnitTest extends AbstractTestBase {

	private SimulationController simulationController;
	private NativeMethodInvocator methodInvocator;

	@Before
	public void before() {
		simulationController = injector.getInstance(SimulationController.class);
		methodInvocator = injector.getInstance(NativeMethodInvocator.class);
	}

	@Test
	public void testPauseAnimation() {
		Element element = mock(Element.class);
		simulationController.pauseAnimation(element);
		verify(methodInvocator).callMethod(Mockito.eq(element),Mockito.eq("pauseAnimation"));
	}

	@Test
	public void testResumeAnimation() {
		Element element = mock(Element.class);
		simulationController.resumeAnimation(element);
		verify(methodInvocator).callMethod(Mockito.eq(element),Mockito.eq("resumeAnimation"));
	}

}
