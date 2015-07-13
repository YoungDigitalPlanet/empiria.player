package eu.ydp.empiria.player.client.controller.extensions.internal.stickies;

import com.google.gwt.user.client.ui.HasWidgets;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.controller.StickieDragController;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.controller.StickieDragHandlersManager;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.controller.StickieMinimizeMaximizeController;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.IStickiePresenter;

public interface StickieFactory {

    IStickieView createStickieView(HasWidgets parent, IStickiePresenter presenter, StickieDragHandlersManager stickieDragController);

    IStickiePresenter createStickiePresenter(IStickieProperties stickieProperties, StickieMinimizeMaximizeController minimizeMaximizeController,
                                             StickieRegistration stickieRegistration);

    StickieMinimizeMaximizeController createStickieMinimizeMaximizeController(IStickieProperties stickieProperties);

    StickieDragHandlersManager createStickieDragHandlerManager(StickieDragController stickieDragController);

    StickieDragController createStickieDragController(IStickiePresenter presenter, IStickieProperties properties);
}
