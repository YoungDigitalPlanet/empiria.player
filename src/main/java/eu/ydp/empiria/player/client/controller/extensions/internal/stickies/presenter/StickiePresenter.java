package eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.IStickieProperties;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.IStickieView;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.StickieRegistration;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.controller.StickieMinimizeMaximizeController;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.position.StickieViewPositionFinder;
import eu.ydp.gwtutil.client.debug.gwtlogger.Logger;
import eu.ydp.gwtutil.client.geom.Point;

public class StickiePresenter implements IStickiePresenter {

    private static final Logger LOGGER = new Logger();

    private final IStickieProperties stickieProperties;
    private final StickieViewPositionFinder positionFinder;
    private final StickieMinimizeMaximizeController minimizeMaximizeController;
    private StickieRegistration stickieRegistration;

    private IStickieView view;

    @Inject
    public StickiePresenter(@Assisted IStickieProperties stickieProperties, @Assisted StickieMinimizeMaximizeController minimizeMaximizeController,
                            @Assisted StickieRegistration stickieRegistration, StickieViewPositionFinder positionFinder) {
        this.stickieProperties = stickieProperties;
        this.stickieRegistration = stickieRegistration;
        this.positionFinder = positionFinder;
        this.minimizeMaximizeController = minimizeMaximizeController;
    }

    @Override
    public void negateStickieMinimize() {
        boolean minimized = stickieProperties.isMinimized();
        view.setMinimized(!minimized);

        ContainerDimensions stickieDimensions = view.getStickieDimensions();

        Point<Integer> newStickiePosition;
        if (minimized) {
            ContainerDimensions parentDimensions = view.getParentDimensions();
            newStickiePosition = minimizeMaximizeController.positionMaximizedStickie(stickieDimensions, parentDimensions);
        } else {
            newStickiePosition = minimizeMaximizeController.positionMinimizedStickie(stickieDimensions);
        }

        stickieProperties.setMinimized(!minimized);
        stickieProperties.setPosition(newStickiePosition);
        updateStickieView();
    }

    @Override
    public void deleteStickie() {
        if (stickieRegistration == null) {
            LOGGER.severe("StickiePresenter@" + this.hashCode() + ": Trying to remove already removed stickie!");
            return;
        }
        stickieRegistration.removeStickie();
        stickieRegistration = null;
    }

    @Override
    public void updateStickieView() {
        view.setText(stickieProperties.getStickieContent());
        view.setPosition(stickieProperties.getX(), stickieProperties.getY());
        view.setMinimized(stickieProperties.isMinimized());
        view.setColorIndex(stickieProperties.getColorIndex());
    }

    @Override
    public void centerPositionToView() {
        ContainerDimensions stickieDimensions = view.getStickieDimensions();
        ContainerDimensions parentDimensions = view.getParentDimensions();

        Point<Integer> centerPosition = positionFinder.calculateCenterPosition(stickieDimensions, parentDimensions);
        correctAndUpdatePosition(centerPosition, stickieDimensions);
    }

    @Override
    public void correctStickiePosition() {
        Point<Integer> currentPosition = stickieProperties.getPosition();
        correctAndUpdatePosition(currentPosition);
    }

    private void correctAndUpdatePosition(Point<Integer> point) {
        ContainerDimensions stickieDimensions = view.getStickieDimensions();
        correctAndUpdatePosition(point, stickieDimensions);
    }

    private void correctAndUpdatePosition(Point<Integer> position, ContainerDimensions stickieDimensions) {
        ContainerDimensions parentDimensions = view.getParentDimensions();
        Point<Integer> refinedPosition = positionFinder.refinePosition(position, stickieDimensions, parentDimensions);

        stickieProperties.setPosition(refinedPosition);
        updateStickieView();
    }

    @Override
    public void setView(IStickieView stickieView) {
        this.view = stickieView;
    }

    @Override
    public void changeContentText(String contentText) {
        stickieProperties.setStickieContent(contentText);
        updateStickieView();
    }

    @Override
    public void moveStickieToPosition(Point<Integer> newPosition) {
        minimizeMaximizeController.resetCachedMinimizedPosition();
        correctAndUpdatePosition(newPosition);
    }
}
