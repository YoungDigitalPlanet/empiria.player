package eu.ydp.empiria.player.client.module.math;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class TextEntryGap extends Composite implements MathGap {

	private static TextEntryGapUiBinder uiBinder = GWT
			.create(TextEntryGapUiBinder.class);

	interface TextEntryGapUiBinder extends UiBinder<Widget, TextEntryGap> {
	}
	
	@UiField
	Panel markPanel;
	@UiField
	TextBox textBox;
	

	public TextEntryGap() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public TextBox getTextBox(){
		return textBox;
	}

	@Override
	public String getValue() {
		return textBox.getText();
	}


	@Override
	public void setValue(String v) {
		textBox.setText(v);
	}


	@Override
	public void setEnabled(boolean enabled) {
		textBox.setEnabled(enabled);
	}


	@Override
	public Widget getContainer() {
		return this;
	}


	@Override
	public void reset() {
		textBox.setText("");
	}


	@Override
	public void mark(boolean correct, boolean wrong) {
		if (!correct  &&  !wrong){ 
			markPanel.addStyleDependentName("none");
		} else if (correct){
			markPanel.addStyleDependentName("correct");
		} else if (wrong){
			markPanel.addStyleDependentName("wrong");
		}
	}


	@Override
	public void unmark() {
		markPanel.removeStyleDependentName("none");
		markPanel.removeStyleDependentName("correct");
		markPanel.removeStyleDependentName("wrong");
	}


	public void setGapWidth(String width) {
		textBox.setWidth(width);
	}

	public void setGapHeight(String height) {
		textBox.setHeight(height);
	}

}
