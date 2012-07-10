package eu.ydp.empiria.player.client.module.math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.mathplayer.player.MathPlayerManager;
import com.mathplayer.player.geom.Color;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Point;
import com.mathplayer.player.interaction.GapIdentifier;
import com.mathplayer.player.interaction.InteractionManager;
import com.mathplayer.player.model.interaction.CustomFieldDescription;

import eu.ydp.empiria.player.client.components.ExListBox;
import eu.ydp.empiria.player.client.components.ExListBoxChangeListener;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.OneViewInteractionModuleBase;
import eu.ydp.empiria.player.client.util.IntegerUtils;
import eu.ydp.empiria.player.client.util.XMLUtils;
import eu.ydp.empiria.player.client.util.geom.Size;

public class MathModule extends OneViewInteractionModuleBase implements Factory<MathModule> {

	protected AbsolutePanel outerPanel;
	protected FlowPanel mainPanel;
	protected AbsolutePanel listBoxesLayer;
	protected InteractionManager interactionManager;

	protected List<MathGap> gaps;

	protected boolean showingAnswer = false;
	protected boolean markingAnswer = false;
	protected boolean locked = false;
	
	protected WidthMode widthMode = WidthMode.NORMAL;
	
	private enum GapType{
		TEXT_ENTRY(){
			public String getName(){
				return "text-entry";
			}
		}, 
		INLINE_CHOICE(){
			public String getName(){
				return "inline-choice";
			}
		
		};
		public abstract String getName();
	}


	@Override
	public void installViews(List<HasWidgets> placeholders) {
		
		outerPanel = new AbsolutePanel();
		outerPanel.setStyleName("qp-mathinteraction");
		mainPanel = new FlowPanel();
		mainPanel.setStyleName("qp-mathinteraction-inner");
		outerPanel.add(mainPanel, 0, 0);
		
		applyIdAndClassToView(mainPanel);
		placeholders.get(0).add(outerPanel);
	}

	@Override
	public void onBodyLoad() {
		MathPlayerManager mpm = new MathPlayerManager();
		Map<String, String> styles = getModuleSocket().getStyles(getModuleElement());
		int fontSize = 16;
		String fontName = "Arial";
		boolean fontBold = false;
		boolean fontItalic = false;
		String fontColor = "#000000";
		Integer textEntryGapWidth = 36;
		Integer textEntryGapHeight = 14;
		Integer textEntryFontSize = 18;
		Integer textEntrySubSupWidth;
		Integer textEntrySubSupHeight;
		Integer textEntrySubSupFontSize;
		Integer inlineChoiceWidth = 48;
		Integer inlineChoiceHeight = 24;

		if (styles.containsKey("-empiria-math-gap-width-mode")){
			try{
				WidthMode wm = WidthMode.valueOf(styles.get("-empiria-math-gap-width-mode").trim().toUpperCase());
				widthMode = wm;
			} catch (Exception e) {
			}
		}		if (styles.containsKey("-empiria-math-font-size")){
			fontSize = IntegerUtils.tryParseInt(styles.get("-empiria-math-font-size"));
		}
		
		if (styles.containsKey("-empiria-math-font-family")){
			fontName = styles.get("-empiria-math-font-family");
		}
		
		if (styles.containsKey("-empiria-math-font-weight")){
			fontBold = styles.get("-empiria-math-font-weight").toLowerCase().equals("bold");
		}
		
		if (styles.containsKey("-empiria-math-font-style")){
			fontItalic = styles.get("-empiria-math-font-style").toLowerCase().equals("italic");
		}
		
		if (styles.containsKey("-empiria-math-color")){
			fontColor = styles.get("-empiria-math-color").toUpperCase();
		}
		
		if (styles.containsKey("-empiria-math-gap-width")){
			textEntryGapWidth = IntegerUtils.tryParseInt(styles.get("-empiria-math-gap-width"));
		}
		
		if (styles.containsKey("-empiria-math-gap-height")){
			textEntryGapHeight = IntegerUtils.tryParseInt(styles.get("-empiria-math-gap-height"));
		}
		
		if (styles.containsKey("-empiria-math-gap-font-size")){
			textEntryFontSize = IntegerUtils.tryParseInt(styles.get("-empiria-math-gap-font-size"));
		}
		
		if (styles.containsKey("-empiria-math-gap-subsup-width")){
			textEntrySubSupWidth = IntegerUtils.tryParseInt(styles.get("-empiria-math-gap-subsup-width"));
		} else {
			textEntrySubSupWidth = (int) (textEntryGapWidth * 0.7);
		}
			
		if (styles.containsKey("-empiria-math-gap-subsup-height")){
			textEntrySubSupHeight = IntegerUtils.tryParseInt(styles.get("-empiria-math-gap-subsup-height"));
		} else {
			textEntrySubSupHeight = (int) (textEntryGapHeight * 0.7);
		}
		
		if (styles.containsKey("-empiria-math-gap-subsup-font-size")){
			textEntrySubSupFontSize = IntegerUtils.tryParseInt(styles.get("-empiria-math-gap-subsup-font-size"));
		} else {
			textEntrySubSupFontSize = (int) (textEntryFontSize * 0.7);
		}
		
		if (styles.containsKey("-empiria-math-drop-width")){			
			inlineChoiceWidth = IntegerUtils.tryParseInt(styles.get("-empiria-math-drop-width"));
		}
		
		if (styles.containsKey("-empiria-math-drop-height")){			
			inlineChoiceHeight = IntegerUtils.tryParseInt(styles.get("-empiria-math-drop-height"));
		}
		
		// ---------------- SET FONT ----------------
		
		Integer fontColorInt = IntegerUtils.tryParseInt(fontColor.trim().substring(1), 16, 0); 
		Font f = new Font(fontSize, fontName, fontItalic, fontBold, new Color(fontColorInt / (256 * 256), fontColorInt / 256 % 256, fontColorInt % 256));
		mpm.setFont(f);
		
		// ---------------- CALCULATE GAPS PROPERTIES ----------------
		
		NodeList gapNodes = getModuleElement().getElementsByTagName("gap");
		Set<String> allClasses = new HashSet<String>();
		List<String> classes = new ArrayList<String>();
		List<String> uids = new ArrayList<String>();
		List<String> types = new ArrayList<String>();
		List<Boolean> subsups = new ArrayList<Boolean>();
		
		for (int i = 0 ; i < gapNodes.getLength() ; i ++){
			Element currElement = (Element)gapNodes.item(i);
			String currType = currElement.getAttribute("type");
			types.add(currType);
			String currClass = "";
			if (GapType.TEXT_ENTRY.getName().equals(currType)  &&  currElement.hasAttribute("class")){
				// do not care about classes for other nodes than TEXT_ENTRY
				currClass = currElement.getAttribute("class");
				allClasses.add(currClass);
			}
			classes.add(currClass);
			
			String currUid = "";
			if (currElement.hasAttribute("uid")){
				currUid = currElement.getAttribute("uid");
			}
			uids.add(currUid);
			
			Node node = currElement.getParentNode();
			Node prevNode = currElement;
			
			while (node != null  &&  !node.getNodeName().equals(ModuleTagName.MATH_INTERACTION.tagName())){
				if (node.getNodeName().equals("msub")  ||  node.getNodeName().equals("msup") ||  node.getNodeName().equals("msubsup")){
					if (!XMLUtils.getFirstChildElement((Element)node).equals(prevNode)){
						subsups.add(true);
						break;
					}
				}
				prevNode = node;
				node = node.getParentNode();
			}
			if (subsups.size() < uids.size())
				subsups.add(false);
				
		}
		
		// ---------------- CALCULATE GAPS WIDTHS ----------------
		
		int longestAnswerCharsCount = -1;
		List<Integer> longestAnswerCharsCounts = null; 
		
		if (widthMode == WidthMode.MODULE){
			for (int a = 0 ; a < getResponse().correctAnswers.size() ; a ++){
				if (!types.get(a).equals(GapType.TEXT_ENTRY.getName()))
					continue;
				String currAnswer = getResponse().correctAnswers.get(a);
				if (currAnswer != null  &&  currAnswer.length() > longestAnswerCharsCount){
					longestAnswerCharsCount = currAnswer.length();
				}
			}
		} else if (widthMode == WidthMode.GAP){
			longestAnswerCharsCounts = new ArrayList<Integer>();
			
			for (int a = 0 ; a < getResponse().correctAnswers.size() ; a ++){
				if (types.get(a).equals(GapType.TEXT_ENTRY.getName())){
					String currAnswer = getResponse().correctAnswers.get(a);
					longestAnswerCharsCounts.add(currAnswer.length());
				} else {
					longestAnswerCharsCounts.add(-1);
				}
			}
			
			for (int g = 0 ; g < getResponse().groups.size() ; g ++){
				Vector<Integer> currGroup = getResponse().groups.get(g);
				int longest = -1;
				for (Integer a : currGroup){
					String currAnswer = getResponse().correctAnswers.get(a);
					if (currAnswer.length() > longest){
						longest = currAnswer.length();
					}
				}
				if (longest != -1){
					for (Integer a : currGroup){
						longestAnswerCharsCounts.set(a, longest);
					}
				}
			}
		}
		
		// ---------------- CALCULATE CLASSES STYLES ----------------
		
		Map<String, Integer> widthsFromClasses = new HashMap<String, Integer>();
		
		for (String currClass : allClasses){
			Element currClassSampleElement = XMLParser.parse("<gap class=\"" + currClass + "\"/>").getDocumentElement();
			Map<String, String> currStyles = getModuleSocket().getStyles(currClassSampleElement);
			
			Integer currTextEntryGapWidth = null;
			
			if (currStyles.containsKey("-empiria-math-gap-width")){
				currTextEntryGapWidth = IntegerUtils.tryParseInt(currStyles.get("-empiria-math-gap-width"), null);
			}
					
			widthsFromClasses.put(currClass, currTextEntryGapWidth);

		}

		List<Integer> widths = new ArrayList<Integer>();
		List<Integer> totalWidths = new ArrayList<Integer>();
		Map<String, Size> dSize = new HashMap<String, Size>();
		
		for (int i = 0 ; i < types.size() ; i ++){
		
			if (types.get(i).equals(GapType.TEXT_ENTRY.getName())  &&  !"".equals(uids.get(i))){

				if (!dSize.containsKey(classes.get(i))){	
					Size actualTextEntryGapSize = TextEntryGap.getTextEntryGapActualSize(new Size(textEntryGapWidth, textEntryGapHeight), classes.get(i));
					dSize.put(classes.get(i), new Size(actualTextEntryGapSize.getWidth() - textEntryGapWidth, actualTextEntryGapSize.getHeight() - textEntryGapHeight));
				}

				Integer currWidth = null;
				if (widthsFromClasses.containsKey(classes.get(i))){
					currWidth = widthsFromClasses.get(classes.get(i));
				} 
			
				if (currWidth == null){
					if (widthMode == WidthMode.NORMAL){
						if (!subsups.get(i)){
							currWidth = textEntryGapWidth;
						} else {
							currWidth = textEntrySubSupWidth;
						}
					} else if (widthMode == WidthMode.GAP  ||  widthMode == WidthMode.MODULE){
						Integer charsCount = null;
						if (widthMode == WidthMode.GAP){
							charsCount = longestAnswerCharsCounts.get(i);
						} else if (widthMode == WidthMode.MODULE){
							charsCount = longestAnswerCharsCount;
						}
						if (!subsups.get(i)){
							currWidth = charsCount * textEntryFontSize;
						} else {
							currWidth = charsCount * textEntrySubSupFontSize;
						}
					}
				}

				widths.add(currWidth);
				totalWidths.add(currWidth + dSize.get(classes.get(i)).getWidth());
			} else {
				widths.add(-1);
				totalWidths.add(-1);
			}
		}
		
		Size actualTextEntryGapSize;
		if (!dSize.containsKey("")){
			actualTextEntryGapSize = TextEntryGap.getTextEntryGapActualSize(new Size(textEntryGapWidth, textEntryGapHeight), "");
			dSize.put("", new Size(actualTextEntryGapSize.getWidth() - textEntryGapWidth, actualTextEntryGapSize.getHeight() - textEntryGapHeight));
		} else {
			actualTextEntryGapSize = new Size(textEntryGapWidth + dSize.get("").getWidth(), textEntryGapHeight + dSize.get("").getHeight());
		}

		Size actualTextEntryGapSubSupSize = new Size(textEntrySubSupWidth + dSize.get("").getWidth(), textEntrySubSupHeight + dSize.get("").getHeight());
		
		for (int i = 0 ; i < types.size() ; i ++){
			if (types.get(i).equals(GapType.TEXT_ENTRY.getName())){
				if ( !"".equals(uids.get(i)) ){
					mpm.setCustomFieldWidth(GapIdentifier.createIdIdentifier(uids.get(i)), totalWidths.get(i));
					if (subsups.get(i)){
						mpm.setCustomFieldHeight(GapIdentifier.createIdIdentifier(uids.get(i)), actualTextEntryGapSubSupSize.getHeight());
					}
				}
			}
		}
				
		mpm.setCustomFieldWidth(GapIdentifier.createTypeIdentifier(GapType.TEXT_ENTRY.getName()), actualTextEntryGapSize.getWidth());
		mpm.setCustomFieldHeight(GapIdentifier.createTypeIdentifier(GapType.TEXT_ENTRY.getName()), actualTextEntryGapSize.getHeight());

		mpm.setCustomFieldWidth(GapIdentifier.createTypeIdentifier(GapType.INLINE_CHOICE.getName()), inlineChoiceWidth);	
		mpm.setCustomFieldHeight(GapIdentifier.createTypeIdentifier(GapType.INLINE_CHOICE.getName()), inlineChoiceHeight);		
		
		interactionManager = mpm.createMath(getModuleElement().getChildNodes().toString(), mainPanel);
		
		listBoxesLayer = new AbsolutePanel();
		listBoxesLayer.setStyleName("qp-mathinteraction-gaps");
		listBoxesLayer.setWidth(String.valueOf(mainPanel.getOffsetWidth()) + "px");
		listBoxesLayer.setHeight(String.valueOf(mainPanel.getOffsetHeight()) + "px");
		outerPanel.add(listBoxesLayer);
		
		Vector<CustomFieldDescription> customFieldDescriptions = interactionManager.getCustomFieldDescriptions();
		gaps = new ArrayList<MathGap>();
		
		Iterator<CustomFieldDescription> customFieldDescriptionsIterator = customFieldDescriptions.iterator();
		
		for (int i = 0 ; i < gapNodes.getLength() ; i ++){
			Element currElement = (Element)gapNodes.item(i);
			
			if (currElement.hasAttribute("type")  &&  GapType.INLINE_CHOICE.getName().equals( currElement.getAttribute("type") )  &&  customFieldDescriptionsIterator.hasNext()){

				ExListBox listBox = new ExListBox();
				listBox.setSelectedIndex(-1);
				listBox.setWidth(String.valueOf(inlineChoiceWidth) + "px");
				listBox.setHeight(String.valueOf(inlineChoiceHeight) + "px");
				listBox.setChangeListener(new ExListBoxChangeListener() {
					
					@Override
					public void onChange() {
						updateResponse(true);
					}
				});
								
				
				NodeList optionNodes = currElement.getElementsByTagName("inlineChoice");
				ArrayList<String> listBoxIdentifiers = new ArrayList<String>();
				
				for (int o = 0 ; o < optionNodes.getLength() ; o ++){
					String currId = ((Element)optionNodes.item(o)).getAttribute("identifier");
					listBoxIdentifiers.add(currId);
					Widget baseBody = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(optionNodes.item(o));
					Widget popupBody = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(optionNodes.item(o));
					listBox.addOption(baseBody, popupBody);
				}
				
				gaps.add(new InlineChoiceGap(listBox, listBoxIdentifiers));
				Point position = customFieldDescriptionsIterator.next().getPosition();
				listBoxesLayer.add(gaps.get(gaps.size()-1).getContainer(), position.x, position.y);
				
			} else if (currElement.hasAttribute("type")  &&  GapType.TEXT_ENTRY.getName().equals( currElement.getAttribute("type") )  &&  customFieldDescriptionsIterator.hasNext()){
				
				TextEntryGap teg = new TextEntryGap();
				teg.getTextBox().addChangeHandler(new ChangeHandler() {
					
					@Override
					public void onChange(ChangeEvent event) {
						updateResponse(true);
					}
				});
				
				gaps.add(teg);
				CustomFieldDescription customFieldDescription = customFieldDescriptionsIterator.next();
				Point position = customFieldDescription.getPosition();
				
				listBoxesLayer.add(teg.getContainer(), position.x, position.y);
				if (!"".equals(uids.get(i))){
					teg.setGapWidth(String.valueOf(widths.get(i)) + "px");
				} else {
					teg.setGapWidth(String.valueOf(textEntryGapWidth) + "px");					
				}
				if (!subsups.get(i)  ||  uids.get(i).equals("")){
					teg.setGapHeight(String.valueOf(textEntryGapHeight) + "px");
					teg.getTextBox().getElement().getStyle().setFontSize(textEntryFontSize, Unit.PX);
				}else {
					teg.setGapHeight(String.valueOf(textEntrySubSupHeight) + "px");
					teg.getTextBox().getElement().getStyle().setFontSize(textEntrySubSupFontSize, Unit.PX);
				}
			}
		}
	}

	@Override
	public void onBodyUnload() {
	}

	@Override
	public void onSetUp() {
		updateResponse(false);
	}

	@Override
	public void onStart() {
	}

	@Override
	public void onClose() {
	}

	@Override
	public void markAnswers(boolean mark) {
		if (mark  && !markingAnswer){
			Vector<Boolean> evaluation = getResponse().evaluateAnswer();

			for (int i = 0 ; i < evaluation.size()  &&  i < gaps.size() ; i ++){
				if ("".equals( getResponse().values.get(i)) ){
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

	@Override
	public void showCorrectAnswers(boolean show) {
		if (show  &&  !showingAnswer){
			for (int i = 0 ; i < getResponse().correctAnswers.size() ; i ++){
				gaps.get(i).setValue( getResponse().correctAnswers.get(i) );
			}
		} else if (!show  &&  showingAnswer){
			for (int i = 0 ; i < getResponse().values.size() ; i ++){
				gaps.get(i).setValue( getResponse().values.get(i) );
			}
		}
		showingAnswer = show;
	}

	@Override
	public void lock(boolean lk) {
		locked = lk;
		for (int i = 0 ; i < gaps.size() ; i ++){
			gaps.get(i).setEnabled(!lk);
		}
	}

	@Override
	public void reset() {
		for (int i = 0 ; i < gaps.size() ; i ++){
			gaps.get(i).reset();
		}
		updateResponse(false);
	}

	@Override
	public JSONArray getState() {
		JSONArray arr = new JSONArray();
		for (int i = 0 ; i < getResponse().values.size() ; i ++){
			JSONString val = new JSONString(getResponse().values.get(i));
			arr.set(i,  val);
		}
		return arr;
	}

	@Override
	public void setState(JSONArray newState) {
		if (newState.isArray() != null){
			for (int i = 0 ; i < gaps.size() ; i ++){
				gaps.get(i).setValue(newState.get(i).isString().stringValue());
			}
		}
		updateResponse(false);
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return ModuleJsSocketFactory.createSocketObject(this);
	}

	private void updateResponse(boolean userInteract){
		if (showingAnswer)
			return;

		getResponse().values.clear();
		for (int i = 0 ; i < gaps.size() ; i ++){
			getResponse().values.add( gaps.get(i).getValue() );
		}
		fireStateChanged(userInteract);
	}

	@Override
	public MathModule getNewInstance() {
		return new MathModule();
	}
}
