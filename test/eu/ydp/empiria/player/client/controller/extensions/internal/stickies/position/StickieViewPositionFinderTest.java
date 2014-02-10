package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.StickieViewPositionFinder.Axis;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.ContainerDimensions;
import eu.ydp.gwtutil.client.geom.Point;

@RunWith(MockitoJUnitRunner.class)
public class StickieViewPositionFinderTest {

	private StickieViewPositionFinder finder;

	@Mock
	private CenterPositionFinder positionFinder;
	@Mock
	private WidgetSizeHelper sizeHelper;

	private ContainerDimensions stickieDimensions;
	private ContainerDimensions playerDimensions;
	private ContainerDimensions pageDimensions;

	@Before
	public void setUp() {
		finder = new StickieViewPositionFinder(positionFinder, sizeHelper);

		stickieDimensions = new ContainerDimensions.Builder().width(10).height(10).absoluteLeft(0).absoluteTop(0).build();

		pageDimensions = new ContainerDimensions.Builder().width(100).height(100).absoluteLeft(10).absoluteTop(10).build();

		playerDimensions = new ContainerDimensions.Builder().width(1000).height(1000).absoluteLeft(0).absoluteTop(0).build();

		when(sizeHelper.getPlayerContainerDimensions()).thenReturn(playerDimensions);
	}

	@Test
	public void shouldCalculateCenterPosition() throws Exception {
		// given
		Integer centerXCoordinate = 213;
		when(positionFinder.getCenterPosition(stickieDimensions.getWidth(), pageDimensions.getAbsoluteLeft(), Axis.HORIZONTAL)).thenReturn(centerXCoordinate);

		Integer centerYCoordinate = 1231231;
		when(positionFinder.getCenterPosition(stickieDimensions.getHeight(), pageDimensions.getAbsoluteTop(), Axis.VERTICAL)).thenReturn(centerYCoordinate);

		// when
		Point<Integer> centerPosition = finder.calculateCenterPosition(stickieDimensions, pageDimensions);

		// then
		assertEquals(centerXCoordinate, centerPosition.getX());
		assertEquals(centerYCoordinate, centerPosition.getY());
	}

	@Test
	public void shouldHoldStickieInRightEdgeOfPage() throws Exception {
		// given
		int rightPageEdge = pageDimensions.getWidth();
		int outOfRightPageEdgeCoordinate = rightPageEdge + 100;
		Point<Integer> outOfRightPageEdgePosition = new Point<Integer>(outOfRightPageEdgeCoordinate, 0);

		// when
		Point<Integer> correctedPosition = finder.refinePosition(outOfRightPageEdgePosition, stickieDimensions, pageDimensions);

		// then
		int expectedStickieXPosition = rightPageEdge - stickieDimensions.getWidth();
		assertEquals(expectedStickieXPosition, (int) correctedPosition.getX());
	}

	@Test
	public void shouldHoldStickieInLeftEdgeOfPage() throws Exception {
		// given
		int outOfLeftScreenEdgeCoordinate = -100;
		Point<Integer> outOfRightPageEdgePosition = new Point<Integer>(outOfLeftScreenEdgeCoordinate, 0);

		// when
		Point<Integer> correctedPosition = finder.refinePosition(outOfRightPageEdgePosition, stickieDimensions, pageDimensions);

		// then
		assertEquals(0, (int) correctedPosition.getX());
	}

	@Test
	public void shouldHoldStickieInBottomEdgeOfPlayer() throws Exception {
		// given
		int playerBottomEdgeCoordinate = playerDimensions.getAbsoluteTop() + playerDimensions.getHeight();
		int belowPlayerBottomEdgeCoordinate = playerBottomEdgeCoordinate + 100;

		// when
		Point<Integer> belowPlayerBottomEdgePosition = new Point<Integer>(0, belowPlayerBottomEdgeCoordinate);
		Point<Integer> correctedPosition = finder.refinePosition(belowPlayerBottomEdgePosition, stickieDimensions, pageDimensions);

		// then
		int expectedYCoordinate = playerBottomEdgeCoordinate - pageDimensions.getAbsoluteTop() - stickieDimensions.getHeight();
		assertEquals(expectedYCoordinate, (int) correctedPosition.getY());
	}

	@Test
	public void shouldHoldStickieInTopEdgeOfPlayer() throws Exception {
		// given
		int playerTopEdgeCoordinate = playerDimensions.getAbsoluteTop();
		int abovePlayerTopEdgeCoordinate = playerTopEdgeCoordinate - 100;

		// when
		Point<Integer> abovePlayerTopEdgePosition = new Point<Integer>(0, abovePlayerTopEdgeCoordinate);
		Point<Integer> correctedPosition = finder.refinePosition(abovePlayerTopEdgePosition, stickieDimensions, pageDimensions);

		// then
		int expectedYCoordinate = playerTopEdgeCoordinate - pageDimensions.getAbsoluteTop();
		assertEquals(expectedYCoordinate, (int) correctedPosition.getY());
	}

	@Test
	public void shouldNotChangedPositionWhenIsFitting() throws Exception {
		// given
		Point<Integer> fittingPosition = new Point<Integer>(50, 50);

		// when
		Point<Integer> correctedPosition = finder.refinePosition(fittingPosition, stickieDimensions, pageDimensions);

		// then
		assertEquals(fittingPosition.getX(), correctedPosition.getX());
		assertEquals(fittingPosition.getY(), correctedPosition.getY());
	}
}
