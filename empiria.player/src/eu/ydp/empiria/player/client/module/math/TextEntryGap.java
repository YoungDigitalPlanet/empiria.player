package eu.ydp.empiria.player.client.module.math;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.binding.Bindable;
import eu.ydp.empiria.player.client.module.binding.BindingGroupIdentifier;
import eu.ydp.empiria.player.client.module.binding.BindingType;
import eu.ydp.empiria.player.client.module.binding.BindingUtil;
import eu.ydp.empiria.player.client.module.binding.BindingValue;
import eu.ydp.empiria.player.client.module.binding.DefaultBindingGroupIdentifier;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthBindingContext;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthBindingValue;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthMode;
import eu.ydp.empiria.player.client.util.IntegerUtils;
import eu.ydp.empiria.player.client.util.geom.Size;

public class TextEntryGap extends Composite implements MathGap, Bindable {

	private static TextEntryGapUiBinder uiBinder = GWT
			.create(TextEntryGapUiBinder.class);

	interface TextEntryGapUiBinder extends UiBinder<Widget, TextEntryGap> {
	}
	
	@UiField
	Panel markPanel;
	@UiField
	TextBox textBox;
	private BindingGroupIdentifier gapWidthGroupId;
	private BindingGroupIdentifier answerGroupId;
	private Integer gapOwnWidth;
	private Integer gapWidth = 36;
	private Integer gapHeight = 14;
	private GapWidthMode gapWidthMode;
	private GapWidthBindingContext gapWidthBindingContext;
	private List<String> correctAnswers;
	private Integer fontSize;
	private String uid;
	
	

	public TextEntryGap() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void init(Element element, ModuleSocket moduleSocket,IModule parentModule, List<String> correctAnswers, String answerGroupName, Integer defaultFontSize) {
		this.correctAnswers = correctAnswers;
		this.fontSize = defaultFontSize;
		
		if (element.hasAttribute("class")){
			textBox.getElement().setAttribute("class", element.getAttribute("class") );
		}
		
		if (element.hasAttribute("widthBindingGroup")){
			gapWidthGroupId = new DefaultBindingGroupIdentifier(element.getAttribute("widthBindingGroup"));
		} else {
			gapWidthGroupId = new DefaultBindingGroupIdentifier("");
		}
		
		if (answerGroupName != null){
			answerGroupId = new DefaultBindingGroupIdentifier(answerGroupName);
		}
		
		if (element.hasAttribute("uid")){
			uid = element.getAttribute("uid");
		} else {
			uid = "";
		}
		
		Map<String, String> styles = moduleSocket.getStyles(element);
		
		if (styles.containsKey("-empiria-math-gap-width")){
			gapOwnWidth = IntegerUtils.tryParseInt(styles.get("-empiria-math-gap-width"), null);
		}
		
		if (styles.containsKey("-empiria-math-gap-font-size")){
			fontSize = IntegerUtils.tryParseInt(styles.get("-empiria-math-gap-font-size"), null);
		}
		
		textBox.getElement().getStyle().setFontSize(fontSize, Unit.PX);
		
		gapWidthMode = GapWidthMode.NORMAL;
		if (styles.containsKey("-empiria-math-gap-width-align")){
			try{
				gapWidthMode = GapWidthMode.valueOf(styles.get("-empiria-math-gap-width-align").toUpperCase());
			}catch (Exception e) { }
		}	
		
		if (gapOwnWidth != null){
			textBox.setWidth(gapOwnWidth + "px");			
		} else if ((gapWidthMode == GapWidthMode.GAP  ||  gapWidthMode == GapWidthMode.GROUP)  &&  gapOwnWidth == null){
			gapWidthBindingContext = (GapWidthBindingContext) BindingUtil.register(BindingType.GAP_WIDTHS, this, parentModule);	
		}
	}
	
	public void updateGapWidth(){
		if (gapWidthBindingContext != null  &&  fontSize != null  &&  !"".equals(uid)){
			int len = gapWidthBindingContext.getGapWidthBindingOutcomeValue().getGapCharactersCount();
			gapOwnWidth = len * fontSize;
			textBox.setWidth(len * fontSize + "px");
		}
	}
	
	public String getUid(){
		return uid;
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


	public void setGapWidth(int width) {
		if (gapOwnWidth == null){
			gapWidth = width;
			textBox.setWidth(width + "px");
		}
	}
	
	public Integer getGapWidth(){
		if (gapOwnWidth == null)
			return gapOwnWidth;
		return gapWidth;
	}

	public void setGapHeight(int height) {
		gapHeight = height;
		textBox.setHeight(height + "px");
	}
	
	public Integer getGapHeight(){
		return gapHeight;
	}

	@Override
	public BindingValue getBindingValue(BindingType bindingType) {
		if (bindingType == BindingType.GAP_WIDTHS){
			int length = 0;
			for (String currCorrectAnswer : correctAnswers){
				if (currCorrectAnswer.length() > length){
					length = currCorrectAnswer.length();
				}
			}
			return new GapWidthBindingValue(length);
		}
		return null;
	}


	@Override
	public BindingGroupIdentifier getBindingGroupIdentifier(BindingType bindingType) {
		if (bindingType == BindingType.GAP_WIDTHS){
			if (gapWidthMode == GapWidthMode.GROUP){
				return gapWidthGroupId;
			} else if (gapWidthMode == GapWidthMode.GAP  &&  answerGroupId != null){
				return answerGroupId;
			}
		}
		return null;
	}
	


	public static Size getTextEntryGapActualSize(Size orgSize){
		return getTextEntryGapActualSize(orgSize, null);
	}
	
	public static Size getTextEntryGapActualSize(Size orgSize, String textBoxClassName){
		TextEntryGap testGap = new TextEntryGap();
		testGap.getElement().getStyle().setPosition(Position.ABSOLUTE);
		RootPanel.get().add(testGap);		
		if (textBoxClassName != null  &&  !"".equals(textBoxClassName)){
			testGap.getTextBox().getElement().setClassName(textBoxClassName);
		}
		testGap.getTextBox().setWidth(String.valueOf(orgSize.getWidth())+"px");
		testGap.getTextBox().setHeight(String.valueOf(orgSize.getHeight())+"px");
		Integer actualTextEntryWidth = testGap.getOffsetWidth();
		Integer actualTextEntryHeight = testGap.getOffsetHeight();
		RootPanel.get().remove(testGap);
		return new Size(actualTextEntryWidth, actualTextEntryHeight);
	}

}
