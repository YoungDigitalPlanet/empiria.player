package eu.ydp.empiria.player.client.module.math;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_WIDTH;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_WIDTH_ALIGN;

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
import com.google.gwt.xml.client.Node;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.binding.Bindable;
import eu.ydp.empiria.player.client.module.binding.BindingGroupIdentifier;
import eu.ydp.empiria.player.client.module.binding.BindingType;
import eu.ydp.empiria.player.client.module.binding.BindingUtil;
import eu.ydp.empiria.player.client.module.binding.BindingValue;
import eu.ydp.empiria.player.client.module.binding.DefaultBindingGroupIdentifier;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthBindingContext;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthBindingValue;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthMode;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.empiria.player.client.util.geom.Size;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class TextEntryGap extends Composite implements MathGap, Bindable {

	private static final String WRONG = "wrong";
	private static final String CORRECT = "correct";
	private static final String NONE = "none";
	private static TextEntryGapUiBinder uiBinder = GWT.create(TextEntryGapUiBinder.class);

	interface TextEntryGapUiBinder extends UiBinder<Widget, TextEntryGap> {
	}

	@UiField
	protected Panel markPanel;
	@UiField
	protected TextBox textBox;
	private BindingGroupIdentifier gapWidthGroupId;
	private BindingGroupIdentifier answerGroupId;
	private Integer gapOwnWidth;
	private Integer gapWidth = 36;
	private Integer gapHeight = 14;
	private GapWidthMode gapWidthMode;
	private GapWidthBindingContext gapWidthBindingContext;
	private List<String> correctAnswers;
	private Integer fontSize = 16;
	private String uid;
	
	private Integer textEntryGapWidth = 36;
	private Integer textEntryGapHeight = 14;
	private Integer textEntryFontSize = 18;
	private Integer textEntrySubSupWidth;
	private Integer textEntrySubSupHeight;
	private Integer textEntrySubSupFontSize;
	
	private boolean isSubOrSupParent;
	private Response response;

	public TextEntryGap() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void init(Element element, ModuleSocket moduleSocket, IModule parentModule, Response response, int index, Map<String, String> styles) {
		this.response = response;
		this.correctAnswers = getCorrectAnwers(index);
		String answerGroupName = getGroupName(index);
		
		if (element.hasAttribute(EmpiriaTagConstants.ATTR_CLASS)) {
			textBox.getElement().setAttribute(EmpiriaTagConstants.ATTR_CLASS, element.getAttribute(EmpiriaTagConstants.ATTR_CLASS));
		}

		if (element.hasAttribute(EmpiriaTagConstants.ATTR_WIDTH_BINDING_GROUP)) {
			gapWidthGroupId = new DefaultBindingGroupIdentifier(element.getAttribute(EmpiriaTagConstants.ATTR_WIDTH_BINDING_GROUP));
		} else {
			gapWidthGroupId = new DefaultBindingGroupIdentifier("");
		}

		if (answerGroupName != null) {
			answerGroupId = new DefaultBindingGroupIdentifier(answerGroupName);
		}

		if (element.hasAttribute(EmpiriaTagConstants.ATTR_UID)) {
			uid = element.getAttribute(EmpiriaTagConstants.ATTR_UID);
		} else {
			uid = "";
		}
		
		Map<String, String> elemStyles = moduleSocket.getStyles(element);
		
		if (elemStyles.containsKey(EMPIRIA_MATH_GAP_WIDTH)) {
			gapOwnWidth = NumberUtils.tryParseInt(elemStyles.get(EMPIRIA_MATH_GAP_WIDTH), null);
		}

		gapWidthMode = GapWidthMode.NORMAL;
		if (elemStyles.containsKey(EMPIRIA_MATH_GAP_WIDTH_ALIGN)) {
			try {
				gapWidthMode = GapWidthMode.valueOf(elemStyles.get(EMPIRIA_MATH_GAP_WIDTH_ALIGN).toUpperCase());
			} catch (Exception e) {
			}
		}
		
		//------------------------------------------
		initStyles(styles);
		
		isSubOrSupParent = isParentOfSubOrSup(element);
		
		if (isSubOrSupParent) {
			fontSize = textEntrySubSupFontSize;
			setGapWidth(textEntrySubSupWidth);
			setGapHeight(textEntrySubSupHeight);
		} else {
			fontSize = textEntryFontSize;
			setGapWidth(textEntryGapWidth);
			setGapHeight(textEntryGapHeight);
		}
		
		textBox.getElement().getStyle().setFontSize(fontSize, Unit.PX);
		
		if ((gapWidthMode == GapWidthMode.GAP || gapWidthMode == GapWidthMode.GROUP) && gapOwnWidth == null) {
			gapWidthBindingContext = (GapWidthBindingContext) BindingUtil.register(BindingType.GAP_WIDTHS, this, parentModule);
		} else if (gapOwnWidth != null) {
			textBox.setWidth(gapOwnWidth + "px");
		}
	}
	
	public boolean isSubOrSupParent(){
		return isSubOrSupParent;
	}
	
	private void initStyles(Map<String, String> styles){
		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_WIDTH)) {
			textEntryGapWidth = NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_WIDTH));
		}

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_HEIGHT)) {
			textEntryGapHeight = NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_HEIGHT));
		}

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_FONT_SIZE)) {
			textEntryFontSize = NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_FONT_SIZE));
		}

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_WIDTH)) {
			textEntrySubSupWidth = NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_WIDTH));
		} else {
			textEntrySubSupWidth = (int) (textEntryGapWidth * 0.7);
		}

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_HEIGHT)) {
			textEntrySubSupHeight = NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_HEIGHT));
		} else {
			textEntrySubSupHeight = (int) (textEntryGapHeight * 0.7);
		}

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_FONT_SIZE)) {
			textEntrySubSupFontSize = NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_SUBSUP_FONT_SIZE));
		} else {
			textEntrySubSupFontSize = (int) (textEntryFontSize * 0.7);
		}
	}

	public void updateGapWidth() {
		if (gapWidthBindingContext != null && fontSize != null && !"".equals(uid)) {
			int len = gapWidthBindingContext.getGapWidthBindingOutcomeValue().getGapCharactersCount();
			gapOwnWidth = len * fontSize;
			textBox.setWidth(len * fontSize + "px");
		}
	}

	public String getUid() {
		return uid;
	}

	public TextBox getTextBox() {
		return textBox;
	}

	@Override
	public String getValue() {
		return textBox.getText();
	}

	@Override
	public void setValue(String value) {
		textBox.setText(value);
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
		if (!correct && !wrong) {
			markPanel.addStyleDependentName(NONE);
		} else if (correct) {
			markPanel.addStyleDependentName(CORRECT);
		} else if (wrong) {
			markPanel.addStyleDependentName(WRONG);
		}
	}

	@Override
	public void unmark() {
		markPanel.removeStyleDependentName(NONE);
		markPanel.removeStyleDependentName(CORRECT);
		markPanel.removeStyleDependentName(WRONG);
	}

	public void setGapWidth(int width) {
		if (gapOwnWidth == null) {
			gapWidth = width;
			textBox.setWidth(width + "px");
		}
	}

	public Integer getGapWidth() {
		if (gapOwnWidth == null) {
			return gapOwnWidth;
		}
		return gapWidth;
	}

	public void setGapHeight(int height) {
		gapHeight = height;
		textBox.setHeight(height + "px");
	}

	public Integer getGapHeight() {
		return gapHeight;
	}

	@Override
	public BindingValue getBindingValue(BindingType bindingType) {
		if (bindingType == BindingType.GAP_WIDTHS) {
			int length = 0;
			for (String currCorrectAnswer : correctAnswers) {
				if (currCorrectAnswer.length() > length) {
					length = currCorrectAnswer.length();
				}
			}
			return new GapWidthBindingValue(length);
		}
		return null;
	}

	@Override
	public BindingGroupIdentifier getBindingGroupIdentifier(BindingType bindingType) {
		if (bindingType == BindingType.GAP_WIDTHS) {
			if (gapWidthMode == GapWidthMode.GROUP) {
				return gapWidthGroupId;
			} else if (gapWidthMode == GapWidthMode.GAP && answerGroupId != null) {
				return answerGroupId;
			}
		}
		return null;
	}

	public static Size getTextEntryGapActualSize(Size orgSize) {
		return getTextEntryGapActualSize(orgSize, null);
	}

	public static Size getTextEntryGapActualSize(Size orgSize, String textBoxClassName) {
		TextEntryGap testGap = new TextEntryGap();
		testGap.getElement().getStyle().setPosition(Position.ABSOLUTE);
		RootPanel.get().add(testGap);
		if (textBoxClassName != null && !"".equals(textBoxClassName)) {
			testGap.getTextBox().getElement().setClassName(textBoxClassName);
		}
		testGap.getTextBox().setWidth(orgSize.getWidth() + "px");
		testGap.getTextBox().setHeight(orgSize.getHeight() + "px");
		Integer actualTextEntryWidth = testGap.getOffsetWidth();
		Integer actualTextEntryHeight = testGap.getOffsetHeight();
		RootPanel.get().remove(testGap);
		return new Size(actualTextEntryWidth, actualTextEntryHeight);
	}
	
	private boolean isSubOrSupElement(Node node) {
		return node.getNodeName().equals("msub")
				|| node.getNodeName().equals("msup")
				|| node.getNodeName().equals("msubsup")
				|| node.getNodeName().equals("mmultiscripts");
	}
	
	private List<String> getCorrectAnwers(int index){
		return response.correctAnswers.getResponseValue(index).getAnswers();
	}
	
	private String getGroupName(int index){
		String groupName = null;
		
		for (String currGroupName : response.groups.keySet()) {
			if (response.groups.get(currGroupName).contains(index)) {
				groupName = currGroupName;
				break;
			}
		}
		return groupName;
	}
	
	private boolean isMathInteractionNode(Node node) {
		return node.getNodeName().equals(
				ModuleTagName.MATH_INTERACTION.tagName());
	}
	
	private boolean isParentOfSubOrSup(Element node){
		Node parentNode = node.getParentNode();
		Node prevNode = node;
		boolean subsupParentFound = false;

		if (node.hasAttribute(EmpiriaTagConstants.ATTR_UID)) {
			while (parentNode != null && !isMathInteractionNode(parentNode)) {
				if (isSubOrSupElement(parentNode)
						&& !XMLUtils.getFirstChildElement(
								(Element) parentNode).equals(prevNode)) {
					subsupParentFound = true;
					break;
				}
				prevNode = parentNode;
				parentNode = parentNode.getParentNode();
			}
		}
		
		return subsupParentFound;
	}

}
