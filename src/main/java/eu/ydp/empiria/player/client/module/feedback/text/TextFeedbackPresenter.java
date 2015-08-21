package eu.ydp.empiria.player.client.module.feedback.text;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class TextFeedbackPresenter extends Composite implements TextFeedback {

    private static TextFeedbackViewUiBinder uiBinder = GWT.create(TextFeedbackViewUiBinder.class);

    @UiTemplate("TextFeedbackView.ui.xml")
    interface TextFeedbackViewUiBinder extends UiBinder<Widget, TextFeedbackPresenter> {
    }

    public TextFeedbackPresenter() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    FlowPanel feedbackTextPanel;

    @Override
    public void setTextElement(Widget widget) {
        if (!isFeedbackPanelContainsWidgets(feedbackTextPanel)) {
            feedbackTextPanel.add(widget);
        }
        feedbackTextPanel.setStyleName("qp-feedback-text");
    }

    @Override
    public void hideTextElement() {
        feedbackTextPanel.setStyleName("qp-feedback-hide");
    }

    @Override
    public void show() {
        this.setVisible(true);
    }

    @Override
    public void hide() {
        this.setVisible(false);
    }

    private boolean isFeedbackPanelContainsWidgets(FlowPanel feedbackTextPanel) {
        return feedbackTextPanel.getWidgetCount() > 0;
    }
}
