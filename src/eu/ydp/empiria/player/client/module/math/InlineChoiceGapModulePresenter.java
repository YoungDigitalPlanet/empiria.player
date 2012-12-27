package eu.ydp.empiria.player.client.module.math;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.gap.GapBase.PresenterHandler;
import eu.ydp.empiria.player.client.module.gap.GapModulePresenter;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBox;

public class InlineChoiceGapModulePresenter implements GapModulePresenter {

	@UiTemplate("InlineChoiceGap.ui.xml")
	interface InlineChoiceGapModuleUiBinder extends UiBinder<Widget, InlineChoiceGapModulePresenter>{};

	private final InlineChoiceGapModuleUiBinder uiBinder = GWT.create(InlineChoiceGapModuleUiBinder.class);

	@UiField
	protected FlowPanel mainPanel;

	@UiField
	protected ExListBox listBox;

	public InlineChoiceGapModulePresenter() {
		uiBinder.createAndBindUi(this);
	}

	@Override
	public void setWidth(double value, Unit unit) {
		listBox.setWidth(value + unit.getType());
	}

	@Override
	public int getOffsetWidth() {
		return listBox.getOffsetWidth();
	}

	@Override
	public void setHeight(double value, Unit unit) {
		listBox.setHeight(value + unit.getType());
	}

	@Override
	public int getOffsetHeight() {
		return listBox.getOffsetHeight();
	}

	@Override
	public void setMaxLength(int length) {
	}

	@Override
	public void setFontSize(double value, Unit unit) {
		listBox.getElement().getStyle().setFontSize(value, unit);
	}

	@Override
	public int getFontSize() {
		return Integer.parseInt(listBox.getElement().getStyle().getFontSize().replace("px", ""));
	}

	@Override
	public void setText(String text) {
	}

	@Override
	public String getText() {
		return "";
	}

	@Override
	public HasWidgets getContainer() {
		return mainPanel;
	}

	@Override
	public void installViewInContainer(HasWidgets container) {
		container.add(mainPanel);
	}

	@Override
	public void setViewEnabled(boolean enabled) {
		listBox.setEnabled(enabled);
	}

	@Override
	public void setMarkMode(String mode) {
		mainPanel.addStyleDependentName(mode);
	}

	@Override
	public void removeMarking() {
		mainPanel.removeStyleDependentName(GapModulePresenter.NONE);
		mainPanel.removeStyleDependentName(GapModulePresenter.CORRECT);
		mainPanel.removeStyleDependentName(GapModulePresenter.WRONG);
	}

	@Override
	public void addPresenterHandler(PresenterHandler handler) {
	}

	@Override
	public void removeFocusFromTextField() {
	}

	@Override
	public ExListBox getListBox() {
		return listBox;
	}

}
