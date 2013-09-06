package eu.ydp.empiria.player.client.module.drawing.toolbox.tool;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import eu.ydp.empiria.player.client.module.drawing.view.DrawCanvas;
import eu.ydp.empiria.player.client.util.position.Point;

@RunWith(MockitoJUnitRunner.class)
public class EraserToolTest {

	@InjectMocks
	private EraserTool eraserTool;

	@Mock
	private DrawCanvas canvas;

	@Test
	public void shouldErasePoint() throws Exception {
		// given
		Point point = new Point(1, 2);

		// when
		eraserTool.start(point);

		// then
		verify(canvas).erasePoint(point);
	}

	@Test
	public void shouldEraseLine() throws Exception {
		// given
		Point startPoint = new Point(1, 1);
		Point endPoint = new Point(2, 2);

		// when
		eraserTool.move(startPoint, endPoint);

		// then
		verify(canvas).eraseLine(startPoint, endPoint);
	}
}
