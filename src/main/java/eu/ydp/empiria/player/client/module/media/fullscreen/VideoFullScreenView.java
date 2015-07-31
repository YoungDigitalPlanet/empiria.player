package eu.ydp.empiria.player.client.module.media.fullscreen;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;

public interface VideoFullScreenView extends IsWidget {

    FlowPanel getContainer();

    FlowPanel getControls();

    boolean isAttached();

    void removeFromParent();

    Element getElement();
}
