package eu.ydp.empiria.player.client.module.selection.view;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.WidgetCollection;

import eu.ydp.empiria.player.client.module.selection.model.UserAnswerType;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class SelectionButtonGridElementImpl extends FlowPanel implements SelectionButtonGridElement {

	private StyleNameConstants styleNameConstants;

	public SelectionButtonGridElementImpl(StyleNameConstants styleNameConstants) {
		this.styleNameConstants = styleNameConstants;
	}

	@Override
	public void addClickHandler(ClickHandler clickHandler) {
		getButton().addClickHandler(clickHandler);
	}

	@Override
	public void select() {
		getButton().select();
	}

	@Override
	public void unselect() {
		getButton().unselect();
	}

	@Override
	public void setButtonEnabled(boolean b) {
		getButton().setButtonEnabled(b);
	}

	@Override
	public void updateStyle() {
		getButton().updateStyle();
	}

	@Override
	public void updateStyle(UserAnswerType styleState) {
		this.setStyleName(getButtonStyleNameForState(styleState));
		updateStyle();
	}

	@Override
	public Widget asWidget() {
		return this;
	}

	private SelectionChoiceButton getButton() {
		WidgetCollection children = getChildren();
		// each grid element panel should contain only one child ex. button
		if (children.size() != 1) {
			throw new RuntimeException("SelectionGridElement panel contains " + children.size() + " elements when 1 was expected!");
		}
		SelectionChoiceButton button = (SelectionChoiceButton) children.get(0);
		return button;
	}

	private String getButtonStyleNameForState(UserAnswerType styleState) {
		switch (styleState) {
		case CORRECT:
			return styleNameConstants.QP_MARKANSWERS_BUTTON_CORRECT();
		case WRONG:
			return styleNameConstants.QP_MARKANSWERS_BUTTON_WRONG();
		case DEFAULT:
			return styleNameConstants.QP_MARKANSWERS_BUTTON_INACTIVE();
		case NONE:
			return styleNameConstants.QP_MARKANSWERS_BUTTON_NONE();
		default:
			return "";
		}
	}
}
