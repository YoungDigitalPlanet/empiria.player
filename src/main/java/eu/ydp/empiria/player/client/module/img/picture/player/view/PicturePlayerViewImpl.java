package eu.ydp.empiria.player.client.module.img.picture.player.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.img.picture.player.presenter.PicturePlayerPresenter;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public class PicturePlayerViewImpl extends Composite implements PicturePlayerView {

    private PicturePlayerUiBinder uiBinder = GWT.create(PicturePlayerUiBinder.class);
    private StyleNameConstants styleNameConstants;
    private PicturePlayerPresenter presenter;

    @UiTemplate(value = "PicturePlayerView.ui.xml")
    interface PicturePlayerUiBinder extends UiBinder<Widget, PicturePlayerViewImpl> {
    }

    @UiField
    protected Image image;
    @UiField
    protected FlowPanel container;

    @Inject
    public PicturePlayerViewImpl(StyleNameConstants styleNameConstants) {
        this.styleNameConstants = styleNameConstants;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void addFullscreenButton() {
        CustomPushButton fullScreenButton = new CustomPushButton();
        fullScreenButton.setStyleName(styleNameConstants.QP_MEDIA_FULLSCREEN_BUTTON());
        fullScreenButton.addClickHandler(createOnOpenFullscreenCommand());

        container.add(fullScreenButton);
    }

    @Override
    public void setPresenter(PicturePlayerPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setImage(String title, String url) {
        image.setAltText(title);
        image.setUrl(url);
    }

    private ClickHandler createOnOpenFullscreenCommand() {
        return new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                presenter.openFullscreen();
            }
        };
    }
}
