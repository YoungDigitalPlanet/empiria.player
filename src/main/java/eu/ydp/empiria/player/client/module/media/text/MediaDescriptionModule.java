package eu.ydp.empiria.player.client.module.media.text;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.module.media.button.AbstractMediaController;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class MediaDescriptionModule extends AbstractMediaController<MediaDescriptionModule> {
    private static ImgDescriptionModuleUiBinder uiBinder = GWT.create(ImgDescriptionModuleUiBinder.class);

    interface ImgDescriptionModuleUiBinder extends UiBinder<Widget, MediaDescriptionModule> {
    }

    protected final static StyleNameConstants styleNames = PlayerGinjectorFactory.getPlayerGinjector().getStyleNameConstants(); // NOPMD

    @UiField
    protected FlowPanel text;

    public MediaDescriptionModule() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public MediaDescriptionModule getNewInstance() {
        return new MediaDescriptionModule();
    }

    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public void init() { // NOPMD

    }

    @Override
    public void setStyleNames() {
        if (isInFullScreen()) {
            text.removeStyleName(styleNames.QP_MEDIA_DESCRIPTION());
            text.addStyleName(styleNames.QP_MEDIA_DESCRIPTION() + FULL_SCREEN_SUFFIX);
        }
    }
}
