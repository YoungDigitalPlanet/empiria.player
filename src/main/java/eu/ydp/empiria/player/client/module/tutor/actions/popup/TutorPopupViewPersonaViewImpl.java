package eu.ydp.empiria.player.client.module.tutor.actions.popup;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class TutorPopupViewPersonaViewImpl extends Composite implements TutorPopupViewPersonaView {

    private static TutorPopupViewPersonaViewUiBinder uiBinder = GWT.create(TutorPopupViewPersonaViewUiBinder.class);

    interface TutorPopupViewPersonaViewUiBinder extends UiBinder<Widget, TutorPopupViewPersonaViewImpl> {
    }

    @UiField
    FlowPanel container;

    public TutorPopupViewPersonaViewImpl() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setAvatarUrl(String avatarUrl) {
        Image image = new Image(avatarUrl);
        container.add(image);
    }

}
