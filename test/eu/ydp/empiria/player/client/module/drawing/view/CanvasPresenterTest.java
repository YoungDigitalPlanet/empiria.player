package eu.ydp.empiria.player.client.module.drawing.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;
import eu.ydp.empiria.player.client.module.drawing.toolbox.tool.Tool;
import eu.ydp.empiria.player.client.util.position.Point;
import eu.ydp.gwtutil.client.util.geom.Size;

@RunWith(MockitoJUnitRunner.class)
public class CanvasPresenterTest {

	@InjectMocks
	private CanvasPresenter canvasPresenter;
	
	@Mock
	private CanvasView canvasView;
	@Mock
	private Tool tool;
	
	@Before
	public void setUp() throws Exception {
		canvasPresenter.setTool(tool);
		verify(canvasView).initializeInteractionHandlers(canvasPresenter);
	}
	
	@After
	public void tearDown() {
		verify(tool).setUp();
		Mockito.verifyNoMoreInteractions(tool, canvasView);
	}
	
	@Test
	public void shouldDrawPointWhenMouseIsDown() throws Exception {
		//given
		Point point = new Point(1, 1);
		
		//when
		canvasPresenter.mouseDown(point);
		
		//then
		verify(tool).start(point);
	}
	
	@Test
	public void shouldDrawLineWhenMouseDownAndMoved() throws Exception {
		//given
		Point point = new Point(1, 1);
		Point moveToPoint = new Point(2, 2);
		
		//when
		canvasPresenter.mouseDown(point);
		canvasPresenter.mouseMove(moveToPoint);
		
		//then
		verify(tool).start(point);
		verify(tool).move(point, moveToPoint);
	}
	
	@Test
	public void shouldStopDrawingWhenMouseUp() throws Exception {
		//given
		Point point = new Point(1, 1);
		Point moveToPoint = new Point(2, 2);
		
		//when
		canvasPresenter.mouseDown(point);
		canvasPresenter.mouseUp();
		canvasPresenter.mouseMove(moveToPoint);
	
		
		//then
		verify(tool).start(point);
	}
	
	@Test
	public void shouldContinueDrawingAfterMouseOutAndComeBackFromPointWhereYouCameBack() throws Exception {
		//given
		Point point = new Point(1, 1);
		Point moveToPoint = new Point(2, 2);
		
		//when
		canvasPresenter.mouseDown(point);
		canvasPresenter.mouseOut();
		canvasPresenter.mouseMove(moveToPoint);
		
		//then
		verify(tool).start(point);
		verify(tool).move(moveToPoint, moveToPoint);
	}
	
	@Test
	public void shouldNotDrawAnythingWhenMouseOutAndBackButWithoutDrawingStartedByMouseDown() throws Exception {
		//given
		Point moveToPoint = new Point(2, 2);
		
		//when
		canvasPresenter.mouseOut();
		canvasPresenter.mouseMove(moveToPoint);
		
		//then
		//nothing
	}
	
	@Test
	public void shouldBackgroundImageWithSize() throws Exception {
		//given
		String url = "url";
		Size size = new Size(100, 100);
		
		//when
		canvasPresenter.setImage(url, size);
		
		//then
		verify(canvasView).setSize(size);
		verify(canvasView).setBackground(url);
	}
}
