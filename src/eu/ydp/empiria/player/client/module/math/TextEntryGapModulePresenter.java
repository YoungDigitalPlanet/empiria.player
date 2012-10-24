package eu.ydp.empiria.player.client.module.math;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.components.ExListBox;
import eu.ydp.empiria.player.client.module.gap.GapBase.Presenter;
import eu.ydp.empiria.player.client.module.gap.GapBase.PresenterHandler;

public class TextEntryGapModulePresenter implements Presenter {
	
	@UiTemplate("TextEntryGap.ui.xml")
	interface TextEntryGapModuleUiBinder extends UiBinder<Widget, TextEntryGapModulePresenter>{};
	
	private final TextEntryGapModuleUiBinder uiBinder = GWT.create(TextEntryGapModuleUiBinder.class);
	
	@UiField
	protected FlowPanel mainPanel;
	
	@UiField
	protected FlowPanel markPanel;
	
	@UiField
	protected TextBox textBox;
	
	private ChangeHandler changeHandler;
	
	public TextEntryGapModulePresenter() {
		uiBinder.createAndBindUi(this);
	}
	
	@UiHandler("textBox")
	protected void handleChange(ChangeEvent event) {
		if (changeHandler != null) {
			changeHandler.onChange(event);
		}
	}

	@Override
	public void setWidth(double value, Unit unit) {
		textBox.setWidth(value + unit.getType());
	}
	
	@Override
	public int getOffsetWidth() {
		return textBox.getOffsetWidth();
	}

	@Override
	public void setHeight(double value, Unit unit) {
		textBox.setHeight(value + unit.getType());		
	}
	
	@Override
	public int getOffsetHeight() {
		return textBox.getOffsetHeight();
	}

	@Override
	public void setFontSize(double value, Unit unit) {
		textBox.getElement().getStyle().setFontSize(value, unit);	
	}
	
	@Override
	public int getFontSize() {
		return Integer.parseInt(textBox.getElement().getStyle().getFontSize().replace("px", ""));	
	}

	@Override
	public void installViewInContainer(HasWidgets container) {
		container.add(mainPanel);		
	}

	@Override
	public void setText(String text) {
		textBox.setText(text);
	}

	@Override
	public String getText() {
		return textBox.getText();
	}

	@Override
	public HasWidgets getContainer() {
		return mainPanel;
	}

	@Override
	public void setViewEnabled(boolean enabled) {
		textBox.setEnabled(enabled);
	}

	@Override
	public void setMarkMode(String mode) {
		markPanel.addStyleDependentName(mode);
	}

	@Override
	public void removeMarking() {
		markPanel.removeStyleDependentName(Presenter.NONE);
		markPanel.removeStyleDependentName(Presenter.CORRECT);
		markPanel.removeStyleDependentName(Presenter.WRONG);		
	}

	@Override
	public void addPresenterHandler(PresenterHandler handler) {
		changeHandler = handler;
	}

	@Override
	public void removeFocusFromTextField() {
		textBox.getElement().blur();		
	}

	@Override
	public void setMaxLength(int length) {
		textBox.setMaxLength(length);		
	}

	@Override
	public ExListBox getListBox() {
		return null;
	}
}
