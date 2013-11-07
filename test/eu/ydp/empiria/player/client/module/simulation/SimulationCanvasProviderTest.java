package eu.ydp.empiria.player.client.module.simulation;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Optional;
import com.google.gwt.user.client.Element;

import eu.ydp.gwtcreatejs.client.loader.CreateJsContent;
import eu.ydp.gwtcreatejs.client.loader.CreateJsLoader;

@RunWith(MockitoJUnitRunner.class)
public class SimulationCanvasProviderTest {

	@InjectMocks
	private SimulationCanvasProvider simulationCanvasProvider;
	@Mock
	private CreateJsLoader createJsLoader;
	@Mock
	private CreateJsContent createJsContent;

	@Test
	public void testGetSimulationCanvas_contentIsNull() {
		// when
		Optional<Element> simulationElemrnt = simulationCanvasProvider.getSimulationCanvasElement(createJsLoader);
		// then
		assertFalse(simulationElemrnt.isPresent());
	}

	@Test
	public void testGetSimulationCanvas_canvasIsNull() {
		// given
		when(createJsLoader.getContent()).thenReturn(createJsContent);
		// when
		Optional<Element> simulationElemrnt = simulationCanvasProvider.getSimulationCanvasElement(createJsLoader);
		// then
		assertFalse(simulationElemrnt.isPresent());
	}
}
