package eu.ydp.empiria.player.client.module.math;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.mathplayer.player.MathPlayerManager;
import com.mathplayer.player.geom.Color;
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Point;
import com.mathplayer.player.interaction.InteractionManager;

import eu.ydp.empiria.player.client.components.ExListBox;
import eu.ydp.empiria.player.client.components.ExListBoxChangeListener;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.empiria.player.client.module.OneViewInteractionModuleBase;
import eu.ydp.empiria.player.client.util.IntegerUtils;

public class MathModule extends OneViewInteractionModuleBase implements Factory<MathModule> {

	protected AbsolutePanel outerPanel;
	protected FlowPanel mainPanel;
	protected AbsolutePanel listBoxesLayer;
	protected InteractionManager interactionManager;

	protected List<MathGap> gaps;

	protected boolean showingAnswer = false;
	protected boolean markingAnswer = false;
	protected boolean locked = false;
	
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
		Integer textEntryWidth = 36;
		Integer textEntryHeight = 14;
		Integer inlineChoiceWidth = 48;
		Integer inlineChoiceHeight = 24;
		if (styles.containsKey("-empiria-math-font-size")){
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
			textEntryWidth = IntegerUtils.tryParseInt(styles.get("-empiria-math-gap-width"));
		}
		if (styles.containsKey("-empiria-math-gap-height")){
			textEntryHeight = IntegerUtils.tryParseInt(styles.get("-empiria-math-gap-height"));
		}
		if (styles.containsKey("-empiria-math-drop-width")){			
			inlineChoiceWidth = IntegerUtils.tryParseInt(styles.get("-empiria-math-drop-width"));
		}
		if (styles.containsKey("-empiria-math-drop-height")){			
			inlineChoiceHeight = IntegerUtils.tryParseInt(styles.get("-empiria-math-drop-height"));
		}
		Integer fontColorInt = IntegerUtils.tryParseInt(fontColor.trim().substring(1), 16, 0); 
		Font f = new Font(fontSize, fontName, fontItalic, fontBold, new Color(fontColorInt / (256 * 256), fontColorInt / 256 % 256, fontColorInt % 256));
		mpm.setFont(f);
		
		TextEntryGap testGap = new TextEntryGap();
		RootPanel.get().add(testGap);
		testGap.getTextBox().setWidth(String.valueOf(textEntryWidth)+"px");
		testGap.getTextBox().setHeight(String.valueOf(textEntryHeight)+"px");
		Integer actualTextEntryWidth = testGap.getTextBox().getOffsetWidth();
		Integer actualTextEntryHeight = testGap.getTextBox().getOffsetHeight();
		RootPanel.get().remove(testGap);

		mpm.setCustomFieldWidth(GapType.TEXT_ENTRY.getName(), actualTextEntryWidth);
		mpm.setCustomFieldHeight(GapType.TEXT_ENTRY.getName(), actualTextEntryHeight);

		mpm.setCustomFieldWidth(GapType.INLINE_CHOICE.getName(), inlineChoiceWidth);	
		mpm.setCustomFieldHeight(GapType.INLINE_CHOICE.getName(), inlineChoiceHeight);		
		
		interactionManager = mpm.createMath(getModuleElement().getChildNodes().toString(), mainPanel);
		
		listBoxesLayer = new AbsolutePanel();
		listBoxesLayer.setStyleName("qp-mathinteraction-gaps");
		listBoxesLayer.setWidth(String.valueOf(mainPanel.getOffsetWidth()) + "px");
		listBoxesLayer.setHeight(String.valueOf(mainPanel.getOffsetHeight()) + "px");
		outerPanel.add(listBoxesLayer);
		
		Vector<Point> customFieldPositions = interactionManager.getCustomFieldPositions();
		gaps = new ArrayList<MathGap>();
		
		NodeList gapNodes = getModuleElement().getElementsByTagName("gap");
		Iterator<Point> customFieldPositionsIterator = customFieldPositions.iterator();
		
		for (int i = 0 ; i < gapNodes.getLength() ; i ++){
			Element currElement = (Element)gapNodes.item(i);
			
			if (currElement.hasAttribute("type")  &&  GapType.INLINE_CHOICE.getName().equals( currElement.getAttribute("type") )  &&  customFieldPositionsIterator.hasNext()){

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
				Point position = customFieldPositionsIterator.next();
				listBoxesLayer.add(gaps.get(gaps.size()-1).getContainer(), position.x, position.y);
				
			} else if (currElement.hasAttribute("type")  &&  GapType.TEXT_ENTRY.getName().equals( currElement.getAttribute("type") )  &&  customFieldPositionsIterator.hasNext()){
				
				TextEntryGap teg = new TextEntryGap();
				teg.getTextBox().addChangeHandler(new ChangeHandler() {
					
					@Override
					public void onChange(ChangeEvent event) {
						updateResponse(true);
					}
				});
				gaps.add(teg);
				Point position = customFieldPositionsIterator.next();
				listBoxesLayer.add(teg.getContainer(), position.x, position.y);
				teg.setGapWidth(String.valueOf(textEntryWidth) + "px");
				teg.setGapHeight(String.valueOf(textEntryHeight) + "px");
				
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
