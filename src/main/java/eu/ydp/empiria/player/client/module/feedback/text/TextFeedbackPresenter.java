package eu.ydp.empiria.player.client.module.feedback.text;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;

public class TextFeedbackPresenter extends Composite implements TextFeedback {

	private static TextFeedbackViewUiBinder uiBinder = GWT.create(TextFeedbackViewUiBinder.class);

	@UiTemplate("TextFeedbackView.ui.xml") interface TextFeedbackViewUiBinder extends UiBinder<Widget, TextFeedbackPresenter> {
	}

	public TextFeedbackPresenter() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	FlowPanel feedbackTextPanel;

	@Override
	public void setTextElement(Widget widget) {
		feedbackTextPanel.add(widget);
	}

	@Override
	public void clearTextElement() {
		feedbackTextPanel.clear();
	}

	@Override
	public void show() {
		this.setVisible(true);
	}

	@Override
	public void hide() {
		this.setVisible(false);
	}
}
