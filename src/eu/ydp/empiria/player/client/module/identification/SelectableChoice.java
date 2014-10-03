package eu.ydp.empiria.player.client.module.identification;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

import javax.annotation.PostConstruct;

public class SelectableChoice extends Composite {

	private static SelectableChoiceUiBinder uiBinder = GWT.create(SelectableChoiceUiBinder.class);

	@UiTemplate("SelectableChoiceView.ui.xml")
	interface SelectableChoiceUiBinder extends UiBinder<Widget, SelectableChoice> {
	}

	@UiField
	AbsolutePanel container;

	@UiField
	AbsolutePanel cover;

	@UiField
	FlowPanel panel;

	@UiField(provided = true)
	Widget contentWidget;

	private final StyleNameConstants styleNameConstants;
	private final String identifier;
	private final String coverId;

	private boolean selected;

	@Inject
	public SelectableChoice(@Assisted String identifier, @Assisted Widget contentWidget, StyleNameConstants styleNameConstants) {
		this.identifier = identifier;
		this.contentWidget = contentWidget;
		this.styleNameConstants = styleNameConstants;
		coverId = Document.get()
						  .createUniqueId();

		uiBinder.createAndBindUi(this);
		initWidget(panel);
	}

	@PostConstruct
	public void setUpCoverId() {
		cover.getElement()
			 .setId(coverId);
		setSelected(false);
	}

	public String getIdentifier() {
		return identifier;
	}

	public Widget getCover() {
		return cover;
	}

	public boolean getSelected() {
		return selected;
	}

	public void setSelected(boolean sel) {
		selected = sel;
		updatePanelStyleName();
	}

	public void markAnswers(boolean mark, boolean correct) {
		if (mark) {
			if (selected) {
				if (correct) {
					panel.setStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_SELECTED_CORRECT());
				} else {
					panel.setStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_SELECTED_WRONG());
				}
			} else {
				if (correct) {
					panel.setStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_NOTSELECTED_WRONG());
				} else {
					panel.setStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_NOTSELECTED_CORRECT());
				}
			}
		} else {
			updatePanelStyleName();
		}
	}

	private void updatePanelStyleName() {
		if (selected) {
			panel.setStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_SELECTED());
		} else {
			panel.setStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION());
		}
	}

	public void setLocked(boolean locked) {
		if (locked) {
			panel.addStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_LOCKED());
		} else {
			panel.removeStyleName(styleNameConstants.QP_IDENTIFICATION_OPTION_LOCKED());
		}
	}
}