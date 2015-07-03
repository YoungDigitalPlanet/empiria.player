package eu.ydp.empiria.player.client.module.tutor.actions.popup;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import java.util.List;

public interface TutorPopupViewWidget extends IsWidget {
    Widget getWidget(int personaIndex);

    void addWidget(TutorPopupViewPersonaView personaView);

    List<Widget> getAllWidgets();

    Widget getCloseButton();
}
