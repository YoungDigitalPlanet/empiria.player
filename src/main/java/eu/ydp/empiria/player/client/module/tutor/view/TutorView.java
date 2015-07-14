package eu.ydp.empiria.player.client.module.tutor.view;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.gwtutil.client.animation.holder.AnimationHolder;
import eu.ydp.gwtutil.client.animation.holder.ImageHolder;
import eu.ydp.gwtutil.client.event.factory.Command;

public interface TutorView extends AnimationHolder, ImageHolder, IsWidget {
    void bindUi();

    void addClickHandler(Command command);
}
