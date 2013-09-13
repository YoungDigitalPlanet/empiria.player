package eu.ydp.empiria.player.client.module.drawing.toolbox.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.model.color.ColorModel;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.ui.button.CustomPushButton;

public class ToolboxButton extends Composite implements HasClickHandlers {

	@UiField
	CustomPushButton button;
	@Inject
	private StyleNameConstants styleNames;
	private String currentColorStyle;

	private static ToolboxButtonUiBinder uiBinder = GWT.create(ToolboxButtonUiBinder.class);

	interface ToolboxButtonUiBinder extends UiBinder<Widget, ToolboxButton> {
	}

	@Inject
	public ToolboxButton() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void select() {
		button.addStyleName(styleNames.QP_DRAWING_TOOLBOX_TOOL_SELECTED());
	}

	public void unselect() {
		button.removeStyleName(styleNames.QP_DRAWING_TOOLBOX_TOOL_SELECTED());
	}

	public void setColor(ColorModel colorModel) {
		button.removeStyleDependentName(currentColorStyle);
		currentColorStyle = colorModel.toStringRgba();
		button.addStyleDependentName(currentColorStyle);
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		return button.addClickHandler(handler);
	}
}