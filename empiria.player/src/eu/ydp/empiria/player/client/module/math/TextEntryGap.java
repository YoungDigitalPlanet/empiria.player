package eu.ydp.empiria.player.client.module.math;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.util.geom.Size;

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

	public static Size getTextEntryGapActualSize(Size orgSize){
		return getTextEntryGapActualSize(orgSize, null);
	}
	
	public static Size getTextEntryGapActualSize(Size orgSize, String textBoxClassName){
		TextEntryGap testGap = new TextEntryGap();
		RootPanel.get().add(testGap);		
		if (textBoxClassName != null  &&  !"".equals(textBoxClassName)){
			testGap.getTextBox().getElement().setClassName(textBoxClassName);
		}
		testGap.getTextBox().setWidth(String.valueOf(orgSize.getWidth())+"px");
		testGap.getTextBox().setHeight(String.valueOf(orgSize.getHeight())+"px");
		Integer actualTextEntryWidth = testGap.getTextBox().getOffsetWidth();
		Integer actualTextEntryHeight = testGap.getTextBox().getOffsetHeight();
		RootPanel.get().remove(testGap);
		return new Size(actualTextEntryWidth, actualTextEntryHeight);
	}
}
