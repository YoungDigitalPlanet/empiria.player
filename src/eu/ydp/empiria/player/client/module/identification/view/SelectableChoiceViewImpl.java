package eu.ydp.empiria.player.client.module.identification.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.*;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class SelectableChoiceViewImpl extends Composite implements SelectableChoiceView {

	private static SelectableChoiceUiBinder uiBinder = GWT.create(SelectableChoiceUiBinder.class);

	@UiTemplate("SelectableChoiceView.ui.xml")
	interface SelectableChoiceUiBinder extends UiBinder<Widget, SelectableChoiceViewImpl> {
	}

	@UiField
	AbsolutePanel cover;

	@UiField
	FlowPanel optionPanel;

	@UiField(provided = true)
	Widget contentWidget;

	private final StyleNameConstants styleNameConstants;

	@Inject
	public SelectableChoiceViewImpl(@Assisted Widget contentWidget, StyleNameConstants styleNameConstants) {
		this.contentWidget = contentWidget;
		this.styleNameConstants = styleNameConstants;
		uiBinder.createAndBindUi(this);
		initWidget(optionPanel);
	}

	@Override
	public void setCoverId(String coverId) {
		cover.getElement().setId(coverId);
	}

	@Override
	public void markNotSelectedAnswerCorrect() {
		optionPanel.setStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_NOTSELECTED_CORRECT());
	}

	@Override
	public void markNotSelectedAnswerWrong() {
		optionPanel.setStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_NOTSELECTED_WRONG());
	}

	@Override
	public void markSelectedAnswerCorrect() {
		optionPanel.setStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_SELECTED_CORRECT());
	}

	@Override
	public void markSelectedAnswerWrong() {
		optionPanel.setStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_SELECTED_WRONG());
	}

	@Override
	public void markSelectedOption() {
		optionPanel.setStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_SELECTED());
	}

	@Override
	public void unmarkSelectedOption() {
		optionPanel.setStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION());
	}

	@Override
	public void lock() {
		optionPanel.addStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_LOCKED());
	}

	@Override
	public void unlock() {
		optionPanel.removeStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_LOCKED());
	}
}
