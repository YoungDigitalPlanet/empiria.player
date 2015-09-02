package eu.ydp.empiria.player.client.module.feedback.image;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.feedback.*;
import eu.ydp.empiria.player.client.module.feedback.FeedbackStyleNameConstants;

public class ImageFeedbackPresenter extends Composite implements ImageFeedback {

    private static ImageFeedbackViewUiBinder uiBinder = GWT.create(ImageFeedbackViewUiBinder.class);

    @Inject
    private FeedbackStyleNameConstants feedbackStyleNameConstants;
    @Inject
    private FeedbackMarkStyleProvider typeStyleProvider;

    @UiTemplate("ImageFeedbackView.ui.xml")
    interface ImageFeedbackViewUiBinder extends UiBinder<Widget, ImageFeedbackPresenter> {
    }

    public ImageFeedbackPresenter() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    FlowPanel feedbackImageModule;

    @UiField
    Image feedbackImage;

    @Override
    public void setUrl(String url) {
        feedbackImage.setUrl(url);
    }

    @Override
    public String getUrl() {
        return feedbackImage.getUrl();
    }

    @Override
    public void show(FeedbackMark mark) {
        clearStyleNames();
        addStyleName(typeStyleProvider.getStyleName(mark));
        this.setVisible(true);
    }

    @Override
    public void hide() {
        this.setVisible(false);
    }

    private void clearStyleNames() {
        removeStyleName(feedbackStyleNameConstants.QP_FEEDBACK_ALLOK());
        removeStyleName(feedbackStyleNameConstants.QP_FEEDBACK_OK());
        removeStyleName(feedbackStyleNameConstants.QP_FEEDBACK_WRONG());
    }
}
