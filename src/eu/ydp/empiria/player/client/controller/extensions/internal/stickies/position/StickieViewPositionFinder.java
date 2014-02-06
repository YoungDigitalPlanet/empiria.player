package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.ContainerDimensions;
import eu.ydp.gwtutil.client.geom.Point;

public class StickieViewPositionFinder {

	public static enum Axis {
		HORIZONTAL, VERTICAL
	};

	private final WidgetSizeHelper sizeHelper;
	final CenterPositionFinder positionFinder;

	@Inject
	public StickieViewPositionFinder(CenterPositionFinder positionFinder, WidgetSizeHelper sizeHelper) {
		this.positionFinder = positionFinder;
		this.sizeHelper = sizeHelper;
	}

	/**
	 * Finds position for a stickie relatively to item container, taking all circumstances into consideration.
	 */
	public Point<Integer> calculateCenterPosition(ContainerDimensions stickieDimensions, ContainerDimensions parentDimensions) {
		int x = positionFinder.getCenterPosition(stickieDimensions.getWidth(), parentDimensions.getAbsoluteLeft(), Axis.HORIZONTAL);
		int y = positionFinder.getCenterPosition(stickieDimensions.getHeight(), parentDimensions.getAbsoluteTop(), Axis.VERTICAL);

		return new Point<Integer>(x, y);
	}

	public Point<Integer> refinePosition(Point<Integer> position, ContainerDimensions stickieDimensions, ContainerDimensions parentDimensions) {
		int refinedX = refinePositionHorizontal(position.getX(), stickieDimensions, parentDimensions);
		int refinedY = refinePositionVertical(position.getY(), stickieDimensions, parentDimensions);
		return new Point<Integer>(refinedX, refinedY);
	}

	private int refinePositionHorizontal(int left, ContainerDimensions stickieDimensions, ContainerDimensions parentDimensions) {
		int size = stickieDimensions.getWidth();
		int min = 0;
		int max = parentDimensions.getWidth();

		return refinePosition(left, size, min, max);
	}

	private int refinePositionVertical(int top, ContainerDimensions stickieDimensions, ContainerDimensions parentDimensions) {
		int size = stickieDimensions.getHeight();
		ContainerDimensions playerContainerDimensions = sizeHelper.getPlayerContainerDimensions();
		int min = playerContainerDimensions.getAbsoluteTop() - parentDimensions.getAbsoluteTop();
		int max = playerContainerDimensions.getAbsoluteTop() + playerContainerDimensions.getHeight() - parentDimensions.getAbsoluteTop();

		return refinePosition(top, size, min, max);
	}

	int refinePosition(int coord, int size, int min, int max) {

		int newCoord;

		if (coord < min) {
			newCoord = min;
		} else if (coord > max - size) {
			newCoord = max - size;
		} else {
			newCoord = coord;
		}

		return newCoord;
	}

}
