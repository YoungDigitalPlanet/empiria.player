package eu.ydp.empiria.player.client.module.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.components.ExListBox;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.NumberUtils;

public class InlineChoiceGap implements MathGap {
	public static final String INLINE_HTML_NBSP = "&nbsp;";
	private static final String WRONG = "wrong";
	private static final String CORRECT = "correct";
	private static final String NONE = "none";

	private final Panel container;

	private final List<String> options;

	protected boolean hasEmptyOption = false;

	private final ExListBox listBox;

	private Integer gapWidth = 48;
	private Integer gapHeight = 24;

	protected StyleNameConstants styleNames = getStyleNameConstants();

	public InlineChoiceGap(Element moduleElement, ModuleSocket moduleSocket, Map<String, String> styles) {
		initStyles(styles);

		this.listBox = createExListBox();

		if (hasEmptyOption){
			Widget emptyOptionInBody = createInlineHTML(INLINE_HTML_NBSP);
			emptyOptionInBody.setStyleName(styleNames.QP_MATH_CHOICE_POPUP_OPTION_EMPTY());
			Widget emptyOptionInPopup = createInlineHTML(INLINE_HTML_NBSP);
			emptyOptionInPopup.setStyleName(styleNames.QP_MATH_CHOICE_POPUP_OPTION_EMPTY());
			listBox.addOption(emptyOptionInBody, emptyOptionInPopup);
			listBox.setSelectedIndex(0);
		} else {
			listBox.setSelectedIndex(-1);
		}

		options = createOptions(moduleElement, moduleSocket);

		listBox.setWidth(gapWidth + "px");
		listBox.setHeight(gapHeight + "px");

		container = createFlowPanel(); // NOPMD
		container.setStyleName("qp-mathinteraction-inlinechoicegap");
		container.add(listBox);
	}

	List<String> createOptions(Element moduleElement, ModuleSocket moduleSocket){
		NodeList optionNodes = moduleElement.getElementsByTagName(EmpiriaTagConstants.NAME_INLINE_CHOICE);
		List<String> options = new ArrayList<String>();

		for (int o = 0; o < optionNodes.getLength(); o++) {
			String currId = ((Element) optionNodes.item(o)).getAttribute(EmpiriaTagConstants.ATTR_IDENTIFIER);
			options.add(currId);
			Widget baseBody = moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(optionNodes.item(o));
			Widget popupBody = moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(optionNodes.item(o));
			listBox.addOption(baseBody, popupBody);
		}

		return options;
	}

	private void initStyles(Map<String, String> styles){
		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_WIDTH)) {
			gapWidth = NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_WIDTH));
		}

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_HEIGHT)) {
			gapHeight = NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_HEIGHT));
		}

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_INLINECHOICE_EMPTY_OPTION)) {
			hasEmptyOption = styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_INLINECHOICE_EMPTY_OPTION).equalsIgnoreCase(EmpiriaStyleNameConstants.VALUE_SHOW);
		}
	}

	public InlineHTML createInlineHTML(String html) {
		return new InlineHTML(html);
	}

	public ExListBox createExListBox() {
		return new ExListBox();
	}

	@Override
	public String getValue() {
		String value = "";
		int valueIndex = indexViewToInternal();
		if (valueIndex >= 0) {
			value = options.get(valueIndex);
		}
		return value;
	}

	@Override
	public void setValue(String valueIdentifier) {
		listBox.setSelectedIndex(indexInternalToView(valueIdentifier));
	}

	private int indexInternalToView(String valueIdentifier) {
		int valueIndex = options.indexOf(valueIdentifier);
		if (hasEmptyOption) {
			valueIndex++;
		}
		return valueIndex;
	}

	private int indexViewToInternal() {
		int valueIndex = listBox.getSelectedIndex();
		if (hasEmptyOption) {
			valueIndex--;
		}
		return valueIndex;
	}

	@Override
	public void setEnabled(boolean enabled){
		listBox.setEnabled(enabled);
		if (enabled) {
			container.setStyleDependentName("disabled", !enabled);
		}
	}

	@Override
	public void reset() {
		listBox.setSelectedIndex((hasEmptyOption) ? 0 : -1);
	}

	@Override
	public void mark(boolean correct, boolean wrong){
		if (!correct && !wrong){
			container.setStyleDependentName(NONE, true);
		} else  if (correct){
			container.setStyleDependentName(CORRECT, true);
		} else  if (wrong){
			container.setStyleDependentName(WRONG, true);
		}
	}

	@Override
	public void unmark() {
		container.setStyleDependentName(NONE, false);
		container.setStyleDependentName(CORRECT, false);
		container.setStyleDependentName(WRONG, false);
	}

	@Override
	public Widget getContainer() {
		return container;
	}

	public ExListBox getListBox(){
		return listBox;
	}

	public FlowPanel createFlowPanel() {
		return new FlowPanel();
	}

	public int getGapWidth() {
		return gapWidth;
	}

	public int getGapHeight() {
		return gapHeight;
	}

	protected StyleNameConstants getStyleNameConstants(){
		return PlayerGinjector.INSTANCE.getStyleNameConstants();
	}
}
