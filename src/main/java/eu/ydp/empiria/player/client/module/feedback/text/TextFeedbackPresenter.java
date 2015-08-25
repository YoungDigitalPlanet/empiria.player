package eu.ydp.empiria.player.client.module.feedback.text;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.feedback.FeedbackStyleNameConstants;

public class TextFeedbackPresenter extends Composite implements TextFeedback {

    private static TextFeedbackViewUiBinder uiBinder = GWT.create(TextFeedbackViewUiBinder.class);

    @Inject
    private FeedbackStyleNameConstants feedbackStyleNameConstants;

    @UiTemplate("TextFeedbackView.ui.xml")
    interface TextFeedbackViewUiBinder extends UiBinder<Widget, TextFeedbackPresenter> {
    }

    public TextFeedbackPresenter() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    FlowPanel feedbackTextPanel;

    @Override
    public void show(Widget widget) {
        addNewFeedback(widget);
        removeStyleName(feedbackStyleNameConstants.QP_FEEDBACK_TEXT_MODULE_HIDDEN());
    }

    @Override
    public void hide() {
        addStyleName(feedbackStyleNameConstants.QP_FEEDBACK_TEXT_MODULE_HIDDEN());
    }

    private void addNewFeedback(Widget widget) {
        feedbackTextPanel.clear();
        feedbackTextPanel.add(widget);
    }
}
