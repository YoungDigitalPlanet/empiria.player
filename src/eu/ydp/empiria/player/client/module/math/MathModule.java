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
import com.mathplayer.player.geom.Font;
import com.mathplayer.player.geom.Point;
import com.mathplayer.player.interaction.InteractionManager;

import eu.ydp.empiria.player.client.components.ExListBox;
import eu.ydp.empiria.player.client.components.ExListBoxChangeListener;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;
import eu.ydp.empiria.player.client.util.IntegerUtils;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;
import gwt.g2d.client.graphics.Color;

public class MathModule implements IInteractionModule,Factory<MathModule> {

	protected ModuleSocket moduleSocket;
	protected ModuleInteractionListener moduleInteractionListener;

	protected Element element;
	protected AbsolutePanel outerPanel;
	protected FlowPanel mainPanel;
	protected AbsolutePanel listBoxesLayer;
	protected InteractionManager interactionManager;

	protected List<MathGap> gaps;
	
	protected String responseIdentifier;
	protected Response response;

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
	public void initModule(ModuleSocket moduleSocket, ModuleInteractionListener moduleInteractionListener) {
		this.moduleSocket = moduleSocket;
		this.moduleInteractionListener = moduleInteractionListener;
	}

	@Override
	public void addElement(Element element) {
		this.element = element;
	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {

		responseIdentifier = XMLUtils.getAttributeAsString(element, "responseIdentifier");
		response = moduleSocket.getResponse(responseIdentifier);
		
		outerPanel = new AbsolutePanel();
		outerPanel.setStyleName("qp-mathinteraction");
		mainPanel = new FlowPanel();
		mainPanel.setStyleName("qp-mathinteraction-inner");
		outerPanel.add(mainPanel, 0, 0);
		
		String userClass = XMLUtils.getAttributeAsString(element, "class");
		if (userClass != null  &&  !"".equals(userClass))
			mainPanel.addStyleName(userClass);
		placeholders.get(0).add(outerPanel);
	}

	@Override
	public void onBodyLoad() {
		MathPlayerManager mpm = new MathPlayerManager();
		Map<String, String> styles = moduleSocket.getStyles(element);
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
		Font f = new Font(fontSize, fontName, fontBold, fontItalic, new Color(fontColorInt / (256 * 256), fontColorInt / 256 % 256, fontColorInt % 256));
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
		
		interactionManager = mpm.createMath(element.getChildNodes().toString(), mainPanel);
		
		listBoxesLayer = new AbsolutePanel();
		listBoxesLayer.setStyleName("qp-mathinteraction-gaps");
		listBoxesLayer.setWidth(String.valueOf(mainPanel.getOffsetWidth()) + "px");
		listBoxesLayer.setHeight(String.valueOf(mainPanel.getOffsetHeight()) + "px");
		outerPanel.add(listBoxesLayer);
		
		Vector<Point> customFieldPositions = interactionManager.getCustomFieldPositions();
		gaps = new ArrayList<MathGap>();
		
		NodeList gapNodes = element.getElementsByTagName("gap");
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
					Widget baseBody = moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(optionNodes.item(o));
					Widget popupBody = moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(optionNodes.item(o));
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
			Vector<Boolean> evaluation = response.evaluateAnswer();

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

	@Override
	public void showCorrectAnswers(boolean show) {
		if (show  &&  !showingAnswer){
			for (int i = 0 ; i < response.correctAnswers.size() ; i ++){
				gaps.get(i).setValue( response.correctAnswers.get(i) );
			}
		} else if (!show  &&  showingAnswer){
			for (int i = 0 ; i < response.values.size() ; i ++){
				gaps.get(i).setValue( response.values.get(i) );
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
		for (int i = 0 ; i < response.values.size() ; i ++){
			JSONString val = new JSONString(response.values.get(i));
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

	@Override
	public String getIdentifier() {
		return responseIdentifier;
	}


	private void updateResponse(boolean userInteract){
		if (showingAnswer)
			return;

		response.values.clear();
		for (int i = 0 ; i < gaps.size() ; i ++){
			response.values.add( gaps.get(i).getValue() );
		}
		moduleInteractionListener.onStateChanged(userInteract, this);
	}

	@Override
	public MathModule getNewInstance() {
		return new MathModule();
	}
}
