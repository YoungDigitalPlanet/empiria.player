package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.IStickieProperties;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickiePropertiesTestable;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.IStickiePresenter;
import eu.ydp.gwtutil.client.geom.Point;


public class StickieDragControllerJUnitTest {

	private StickieDragController stickieDragController;
	private IStickiePresenter stickiePresenter;
	private IStickieProperties stickieProperties;
	
	@Before
	public void setUp() throws Exception {
		stickiePresenter = Mockito.mock(IStickiePresenter.class);
		stickieProperties = new StickiePropertiesTestable();
		stickieDragController = new StickieDragController(stickiePresenter, stickieProperties);
	}

	@Test
	public void shouldNotDragStickieWithoutDragStart() throws Exception {
		Point<Integer> currentStickiePosition = stickieProperties.getPosition();
		
		Point<Integer> point = new Point<Integer>(666, 777);
		stickieDragController.dragMove(point);
		
		Mockito.verifyZeroInteractions(stickiePresenter);
		assertEquals(currentStickiePosition, stickieProperties.getPosition());
	}
	
	@Test
	public void shouldDragStickie() throws Exception {
		Point<Integer> currentStickiePosition = stickieProperties.getPosition();
		
		Point<Integer> dragStartPosition = new Point<Integer>(10, 100);
		stickieDragController.dragStart(dragStartPosition);
		
		Point<Integer> moveDragPosition = new Point<Integer>(20, 200);
		stickieDragController.dragMove(moveDragPosition);
		
		//then
		ArgumentCaptor<Point<Integer>> newStickiePositionCaptor = new ArgumentCaptor<Point<Integer>>();
		verify(stickiePresenter).moveStickieToPosition(newStickiePositionCaptor.capture());
		
		Point<Integer> newStickiePosition = newStickiePositionCaptor.getValue();
		assertEquals(currentStickiePosition.getX() + 10, (int) newStickiePosition.getX());
		assertEquals(currentStickiePosition.getY() + 100, (int) newStickiePosition.getY());
	}
	
	@Test
	public void shouldNotDragStickieAfterDragEnd() throws Exception {
		Point<Integer> currentStickiePosition = stickieProperties.getPosition();
		
		stickieDragController.dragStart(new Point<Integer>(666, 666));
		stickieDragController.dragEnd();
		
		Point<Integer> point = new Point<Integer>(666, 777);
		stickieDragController.dragMove(point);
		
		Mockito.verifyZeroInteractions(stickiePresenter);
		assertEquals(currentStickiePosition, stickieProperties.getPosition());
	}
}
