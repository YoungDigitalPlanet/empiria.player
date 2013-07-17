package eu.ydp.empiria.player.client.module.choice.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.module.choice.presenter.SimpleChoicePresenter;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceButtonBase;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class SimpleChoiceViewImpl implements SimpleChoiceView {
	private static final String TYPE_SINGLE = "single";

	private static final String TYPE_MULTI = "multi";
	@UiField
	Panel optionPanel;

	@UiField
	Panel mainPanel;

	@UiField
	Panel cover;

	@UiField
	Panel contentWidgetPlace;

	@UiField
	Panel markAnswersPanel;

	@UiField
	Panel labelPanel;

	@UiField
	Panel buttonPlace;

	private Widget widget;

	private SimpleChoicePresenter presenter;

	private ChoiceButtonBase button;

	@Inject
	private StyleNameConstants styleNameConstants;

	private static SimpleChoiceViewUiBinder uiBinder = GWT.create(SimpleChoiceViewUiBinder.class);

	@UiTemplate("SimpleChoiceView.ui.xml")
	interface SimpleChoiceViewUiBinder extends UiBinder<Widget, SimpleChoiceViewImpl> {
	}

	@Inject
	public SimpleChoiceViewImpl(@Assisted SimpleChoicePresenter presenter) {
		widget = uiBinder.createAndBindUi(this);
		this.presenter = presenter;
		addListenersToCover();
	}

	@Override
	public Widget asWidget() {
		return widget;
	}

	@Override
	public void markCorrect() {
		removeInactiveStyle();
		markAnswersPanel.setStyleName("qp-choice-button-" + getButtonType() + "-markanswers-correct");
		markAnswersPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_CORRECT());
		optionPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_CORRECT());
		labelPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_CORRECT());
	}

	@Override
	public void markWrong() {
		removeInactiveStyle();
		markAnswersPanel.setStyleName("qp-choice-button-" + getButtonType() + "-markanswers-wrong");
		markAnswersPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_WRONG());
		optionPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_WRONG());
		labelPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_WRONG());
	}

	@Override
	public void unmarkCorrect() {
		addInactiveStyle();
		optionPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_CORRECT());
		labelPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_CORRECT());
	}

	@Override
	public void unmarkWrong() {
		addInactiveStyle();
		optionPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_WRONG());
		labelPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_WRONG());
	}

	private void addInactiveStyle() {
		markAnswersPanel.setStyleName("qp-choice-button-" + getButtonType() + "-markanswers");
		markAnswersPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_INACTIVE());
		optionPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_INACTIVE());
		labelPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_INACTIVE());
		labelPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_NONE());
	}

	private void removeInactiveStyle() {
		optionPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_INACTIVE());
		labelPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_INACTIVE());
	}

	private String getButtonType() {
		return presenter.isMulti() ? TYPE_MULTI : TYPE_SINGLE;
	}

	@Override
	public Widget getFeedbackPlaceHolder() {
		return labelPanel;
	}

	@Override
	public void reset() {
		button.setSelected(false);
		markAnswersPanel.setStyleName("qp-choice-button-" + getButtonType() + "-markanswers-none");
	}

	@Override
	public void setLocked(boolean locked) {
		button.setButtonEnabled(!locked);
	}

	@Override
	public void setButton(ChoiceButtonBase button) {
		this.button = button;
		buttonPlace.add(button);
		addButtonListeners();
		setAnswerePanelStyle();
	}

	private void setAnswerePanelStyle() {
		markAnswersPanel.addStyleName("qp-choice-button-" + getButtonType() + "-markanswers");
	}

	@Override
	public void setContent(Widget contentWidget) {
		contentWidgetPlace.add(contentWidget);
	}

	private void addButtonListeners() {
		button.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				setMouseOver();
			}
		});

		button.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				setMouseOut();
			}
		});

		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onChoiceClick();
			}
		});
	}

	private void addListenersToCover() {
		cover.addDomHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				setMouseOver();
			}
		}, MouseOverEvent.getType());
		cover.addDomHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				setMouseOut();
			}
		}, MouseOutEvent.getType());
		cover.addDomHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onChoiceClick();
			}
		}, ClickEvent.getType());
	}

	private void setMouseOver() {
		button.setMouseOver(true);
	}

	private void setMouseOut() {
		button.setMouseOver(false);
	}

	private void onChoiceClick() {
		presenter.onChoiceClick();
	}

	@Override
	public void setSelected(boolean select) {
		button.setSelected(select);
	}

	@Override
	public boolean isSelected() {
		return button.isSelected();
	}

}
