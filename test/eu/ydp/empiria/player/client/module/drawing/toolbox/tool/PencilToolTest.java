package eu.ydp.empiria.player.client.module.drawing.toolbox.tool;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import eu.ydp.empiria.player.client.module.drawing.view.DrawCanvas;
import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.empiria.player.client.util.position.Point;

@RunWith(MockitoJUnitRunner.class)
public class PencilToolTest {

	@InjectMocks
	private PencilTool pencilTool;
	
	@Mock
	private ColorModel colorModel;
	@Mock
	private DrawCanvas canvas;

	@Test
	public void shouldDrawPoint() throws Exception {
		//given
		Point point = new Point(1, 2);
		
		//when
		pencilTool.start(point);
		
		//then
		verify(canvas).drawPoint(point, colorModel);
	}
	
	@Test
	public void shouldDrawLine() throws Exception {
		//given
		Point startPoint = new Point(1, 1);
		Point endPoint = new Point(2, 2);
		
		//when
		pencilTool.move(startPoint, endPoint);
		
		//then
		verify(canvas).drawLine(startPoint, endPoint, colorModel);
	}
}
