package eu.ydp.empiria.player.client.controller.extensions.internal.stickies;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.controller.extensions.internal.stickies.presenter.ContainerDimensions;

public interface IStickieView extends HasText, IsWidget {

    void setMinimized(boolean minimized);

    void setColorIndex(int colorIndex);

    void remove();

    void setPosition(int x, int y);

    ContainerDimensions getStickieDimensions();

    ContainerDimensions getParentDimensions();

}
