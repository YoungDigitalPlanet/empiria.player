package eu.ydp.empiria.player.client.view.player;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.util.localisation.LocalePublisher;
import eu.ydp.empiria.player.client.util.localisation.LocaleVariable;

public class PlayerViewCarrier {

    public Widget getHeaderView() {
        return new Label(LocalePublisher.getText(LocaleVariable.PLAYER_HEADER));
    }

    public Widget getFooterView() {
        return new Label(LocalePublisher.getText(LocaleVariable.PLAYER_FOOTER));
    }

}
