package eu.ydp.empiria.player.client.module.drawing.toolbox.tool;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

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

	@After
	public void verifyNoMoreInteractions() {
		Mockito.verifyNoMoreInteractions(canvas, colorModel);
	}
	
	@Test
	public void shouldDrawPoint() throws Exception {
		//given
		Point point = new Point(1, 2);
		
		//when
		pencilTool.start(point);
		
		//then
		InOrder inOrder = Mockito.inOrder(canvas);
		inOrder.verify(canvas).setLineWidth(PencilTool.LINE_WIDTH);
		inOrder.verify(canvas).drawPoint(point, colorModel);
	}
	
	@Test
	public void shouldDrawLine() throws Exception {
		//given
		Point startPoint = new Point(1, 1);
		Point endPoint = new Point(2, 2);
		
		//when
		pencilTool.move(startPoint, endPoint);
		
		//then
		InOrder inOrder = Mockito.inOrder(canvas);
		inOrder.verify(canvas).setLineWidth(PencilTool.LINE_WIDTH);
		inOrder.verify(canvas).drawLine(startPoint, endPoint, colorModel);
	}
}
