package eu.ydp.empiria.player.client.module.math;

import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.mathplayer.player.MathPlayerManager;
import com.mathplayer.player.geom.Color;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Point;
import com.mathplayer.player.interaction.GapIdentifier;
import com.mathplayer.player.interaction.InteractionManager;
import com.mathplayer.player.model.interaction.CustomFieldDescription;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.components.ExListBox;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.geom.Size;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class MathModuleHelper {
		
	public static final String INLINE_HTML_NBSP = "&nbsp;";
	private final Element moduleElement;
	private final ModuleSocket moduleSocket;
	private final Response response;
	private List<MathGap> gaps;	
	private final List<Boolean> subsups = new ArrayList<Boolean>();

	private int fontSize = 16;
	private String fontName = "Arial";
	private boolean fontBold = false;
	private boolean fontItalic = false;
	private String fontColor = "#000000";
	private Integer textEntryGapWidth = 36;
	private Integer textEntryGapHeight = 14;
	private Integer textEntryFontSize = 18;
	private Integer textEntrySubSupWidth;
	private Integer textEntrySubSupHeight;
	private Integer textEntrySubSupFontSize;
	private Integer inlineChoiceWidth = 48;
	private Integer inlineChoiceHeight = 24;
	boolean inlineCholiceGapShowEmptyOption = false;
	
	private final MathPlayerManager mpm = createMathPlayerManager();
	
	private InteractionManager interactionManager;
	
	private final IModule module;
	private Map<String, Size> actualSizes;
	protected boolean markingAnswer = false;
	protected boolean showingAnswer = false;

	protected StyleNameConstants styleNames = getPlayerGinjectorInstance().getStyleNameConstants();

	public MathModuleHelper(Element moduleElement, ModuleSocket moduleSocket, Response response, IModule module) {
		this.moduleElement = moduleElement;
		this.moduleSocket = moduleSocket;
		this.response = response;
		this.module = module;
	}

	enum GapType {
		TEXT_ENTRY() {
			@Override
			public String getName() {
				return "text-entry";
			}
		},
		INLINE_CHOICE() {
			@Override
			public String getName() {
				return "inline-choice";
			}

		};
		public abstract String getName();
	}

	public List<MathGap> initGaps() {

		NodeList gapNodes = moduleElement.getElementsByTagName(EmpiriaTagConstants.NAME_GAP);
		gaps = new ArrayList<MathGap>();

		for (int i = 0; i < gapNodes.getLength(); i++) {
			Element currElement = (Element) gapNodes.item(i);

			if (currElement.hasAttribute(EmpiriaTagConstants.ATTR_TYPE) && GapType.INLINE_CHOICE.getName().equals(currElement.getAttribute(EmpiriaTagConstants.ATTR_TYPE))) {

				ExListBox listBox = createExListBox();

				if (inlineCholiceGapShowEmptyOption){
					Widget emptyOptionInBody = createInlineHTML(INLINE_HTML_NBSP);
					emptyOptionInBody.setStyleName(styleNames.QP_MATH_CHOICE_POPUP_OPTION_EMPTY());
					Widget emptyOptionInPopup = createInlineHTML(INLINE_HTML_NBSP);
					emptyOptionInPopup.setStyleName(styleNames.QP_MATH_CHOICE_POPUP_OPTION_EMPTY());
					listBox.addOption(emptyOptionInBody, emptyOptionInPopup);
					listBox.setSelectedIndex(0);
				} else {
					listBox.setSelectedIndex(-1);
				}				
				
				NodeList optionNodes = currElement.getElementsByTagName(EmpiriaTagConstants.NAME_INLINE_CHOICE);
				ArrayList<String> listBoxIdentifiers = new ArrayList<String>();

				for (int o = 0; o < optionNodes.getLength(); o++) {
					String currId = ((Element) optionNodes.item(o)).getAttribute(EmpiriaTagConstants.ATTR_IDENTIFIER);
					listBoxIdentifiers.add(currId);
					Widget baseBody = moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(optionNodes.item(o));
					Widget popupBody = moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(optionNodes.item(o));
					listBox.addOption(baseBody, popupBody);
				}

				gaps.add(createInlineChoiceGap(listBox, listBoxIdentifiers, inlineCholiceGapShowEmptyOption));

			} else if (currElement.hasAttribute(EmpiriaTagConstants.ATTR_TYPE) && GapType.TEXT_ENTRY.getName().equals(currElement.getAttribute(EmpiriaTagConstants.ATTR_TYPE))) {

				TextEntryGap teg = new TextEntryGap();
				int fontSize;
				int gapWidth;
				int gapHeight;
				if (subsups.get(i)) {
					fontSize = textEntrySubSupFontSize;
					gapWidth = textEntrySubSupWidth;
					gapHeight = textEntrySubSupHeight;
				} else {
					fontSize = textEntryFontSize;
					gapWidth = textEntryGapWidth;
					gapHeight = textEntryGapHeight;
				}
				String groupName = null;
				for (String currGroupName : response.groups.keySet()) {
					if (response.groups.get(currGroupName).contains(i)) {
						groupName = currGroupName;
						break;
					}
				}
				teg.init(currElement, moduleSocket, module, response.correctAnswers.getResponseValue(i).getAnswers(), groupName, fontSize);
				teg.setGapWidth(gapWidth);
				teg.setGapHeight(gapHeight);
				gaps.add(teg);
			}
		}
		return gaps;
	}

	public InlineHTML createInlineHTML(String html) {
		return new InlineHTML(html);
	}

	public void markAnswers(List<MathGap> gaps, boolean mark) {
		if (mark  && !markingAnswer){			
			List<Boolean> evaluation = moduleSocket.evaluateResponse(response);

			for (int i = 0 ; i < evaluation.size()  &&  i < gaps.size() ; i ++){
				if ("".equals( response.values.get(i)) ){
					gaps.get(i).mark(false, false);
				} else if (evaluation.get(i)){
					gaps.get(i).mark(true, false);
				} else {
					gaps.get(i).mark(false, true);
				}
			}

		} else  if (!mark && markingAnswer){

			for (int i = 0 ; i < gaps.size() ; i ++){
				gaps.get(i).unmark();
			}
		}
		markingAnswer = mark;
	}	
	
	public void showCorrectAnswers(List<MathGap> gaps, boolean show) {
		if (show  &&  !showingAnswer){
			for (int i = 0 ; i < response.correctAnswers.getResponseValuesCount() ; i ++){
				gaps.get(i).setValue( response.correctAnswers.getResponseValue(i).getAnswers().get(0) );
			}
		} else if (!show  &&  showingAnswer){
			for (int i = 0 ; i < response.values.size() ; i ++){
				gaps.get(i).setValue(response.values.get(i));
			}
		}
		showingAnswer = show;
	}	
	
	public boolean updateResponse(List<MathGap> gaps, boolean userInteract){
		boolean updated = false;
		
		if (!showingAnswer && response != null){
			response.values.clear();
			for (int i = 0 ; i < gaps.size() ; i ++){
				response.values.add( gaps.get(i).getValue() );
			}
			updated = true;
		}
		return updated;
	}	
	
	public void initStyles() {		
		Map<String, String> styles = moduleSocket.getStyles(moduleElement);

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_SIZE)) {
			fontSize = NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_SIZE));
		}

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_FAMILY)) {
			fontName = styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_FAMILY);
		}

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_WEIGHT)) {
			fontBold = styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_WEIGHT).equalsIgnoreCase(EmpiriaStyleNameConstants.VALUE_BOLD);
		}

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_STYLE)) {
			fontItalic = styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_FONT_STYLE).equalsIgnoreCase(EmpiriaStyleNameConstants.VALUE_ITALIC);
		}

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_COLOR)) {
			fontColor = styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_COLOR).toUpperCase();
		}

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

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_WIDTH)) {
			inlineChoiceWidth = NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_WIDTH));
		}

		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_HEIGHT)) {
			inlineChoiceHeight = NumberUtils.tryParseInt(styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_DROP_HEIGHT));
		}
		
		if (styles.containsKey(EmpiriaStyleNameConstants.EMPIRIA_MATH_INLINECHOICE_EMPTY_OPTION)) {
			inlineCholiceGapShowEmptyOption = styles.get(EmpiriaStyleNameConstants.EMPIRIA_MATH_INLINECHOICE_EMPTY_OPTION).equalsIgnoreCase(EmpiriaStyleNameConstants.VALUE_SHOW);
		}		
	}

	public void initGapsProperties() {

		NodeList gapNodes = moduleElement.getElementsByTagName(EmpiriaTagConstants.NAME_GAP);

		for (int i = 0; i < gapNodes.getLength(); i++) {
			Element currElement = (Element) gapNodes.item(i);

			Node node = currElement.getParentNode();
			Node prevNode = currElement;
			boolean subsupParentFound = false;

			if (currElement.hasAttribute(EmpiriaTagConstants.ATTR_UID)) {
				while (node != null && !node.getNodeName().equals(ModuleTagName.MATH_INTERACTION.tagName())) {
					if (node.getNodeName().equals("msub") || node.getNodeName().equals("msup") || node.getNodeName().equals("msubsup")
							|| node.getNodeName().equals("mmultiscripts")) {
						if (!XMLUtils.getFirstChildElement((Element) node).equals(prevNode)) {
							subsupParentFound = true;
							break;
						}
					}
					prevNode = node;
					node = node.getParentNode();
				}
			}
			subsups.add(subsupParentFound);
		}
	}

	public void calculateActualSizes() {

		actualSizes = new HashMap<String, Size>();

		for (int i = 0; i < gaps.size(); i++) {
			if (gaps.get(i) instanceof TextEntryGap) {
				TextEntryGap teg = (TextEntryGap) gaps.get(i);
				((TextEntryGap) gaps.get(i)).updateGapWidth();
				if (!"".equals(teg.getUid())) {
					actualSizes.put(teg.getUid(), new Size(teg.getOffsetWidth(), teg.getOffsetHeight()));
				}
			}
		}

	}

	public void setSizes() {

		for (int i = 0; i < gaps.size(); i++) {
			if (gaps.get(i) instanceof TextEntryGap) {
				TextEntryGap teg = (TextEntryGap) gaps.get(i);
				if (!"".equals(teg.getUid())) {
					mpm.setCustomFieldWidth(GapIdentifier.createIdIdentifier(teg.getUid()), actualSizes.get(teg.getUid()).getWidth());
					if (subsups.get(i)) {
						mpm.setCustomFieldHeight(GapIdentifier.createIdIdentifier(teg.getUid()), actualSizes.get(teg.getUid()).getHeight());
					}
				}
			}
		}

		Size actualTextEntryGapSize = TextEntryGap.getTextEntryGapActualSize(new Size(textEntryGapWidth, textEntryGapHeight));

		mpm.setCustomFieldWidth(GapIdentifier.createTypeIdentifier(GapType.TEXT_ENTRY.getName()), actualTextEntryGapSize.getWidth());
		mpm.setCustomFieldHeight(GapIdentifier.createTypeIdentifier(GapType.TEXT_ENTRY.getName()), actualTextEntryGapSize.getHeight());

		mpm.setCustomFieldWidth(GapIdentifier.createTypeIdentifier(GapType.INLINE_CHOICE.getName()), inlineChoiceWidth);
		mpm.setCustomFieldHeight(GapIdentifier.createTypeIdentifier(GapType.INLINE_CHOICE.getName()), inlineChoiceHeight);
	}

	public void initMath(Panel panel) {

		Integer fontColorInt = NumberUtils.tryParseInt(fontColor.trim().substring(1), 16, 0);
		Font font = new Font(fontSize, fontName, fontItalic, fontBold, new Color(fontColorInt / (256 * 256), fontColorInt / 256 % 256, fontColorInt % 256));
		mpm.setFont(font);

		interactionManager = mpm.createMath(moduleElement.getChildNodes().toString(), panel);

	}

	public void placeGaps(AbsolutePanel listBoxesLayer) {

		for (int i = 0; i < gaps.size(); i++) {
			if (gaps.get(i) instanceof InlineChoiceGap) {
				InlineChoiceGap icg = (InlineChoiceGap) gaps.get(i);
				icg.getListBox().setWidth(inlineChoiceWidth + "px");
				icg.getListBox().setHeight(inlineChoiceHeight + "px");
				listBoxesLayer.add(icg.getContainer());

			} else if (gaps.get(i) instanceof TextEntryGap) {
				TextEntryGap teg = (TextEntryGap) gaps.get(i);
				listBoxesLayer.add(teg.getContainer(), 0, 0);
			}
		}
	}

	public void positionGaps(AbsolutePanel listBoxesLayer) {

		Vector<CustomFieldDescription> customFieldDescriptions = interactionManager.getCustomFieldDescriptions();
		Iterator<CustomFieldDescription> customFieldDescriptionsIterator = customFieldDescriptions.iterator();

		for (int i = 0; i < gaps.size(); i++) {
			if (!customFieldDescriptionsIterator.hasNext()) {
				break;
			}
			Point position = customFieldDescriptionsIterator.next().getPosition();
			if (gaps.get(i) instanceof InlineChoiceGap) {
				listBoxesLayer.setWidgetPosition(gaps.get(i).getContainer(), position.x, position.y);
			} else if (gaps.get(i) instanceof TextEntryGap) {
				listBoxesLayer.setWidgetPosition(gaps.get(i).getContainer(), position.x, position.y);
			}

		}
	}
	
	public MathPlayerManager createMathPlayerManager() {
		return new MathPlayerManager();
	}

	public ExListBox createExListBox() {
		return new ExListBox();
	}

	public InlineChoiceGap createInlineChoiceGap(ExListBox listBox,
			ArrayList<String> listBoxIdentifiers, boolean inlineCholiceGapShowEmptyOption) {
		return new InlineChoiceGap(listBox, listBoxIdentifiers, inlineCholiceGapShowEmptyOption);
	}
	
	protected PlayerGinjector getPlayerGinjectorInstance() {
		return PlayerGinjector.INSTANCE;
	}
		
}
