package eu.ydp.empiria.player.client.module.interaction.inlinechoice;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.components.ExListBox;
import eu.ydp.empiria.player.client.components.ExListBoxChangeListener;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;
import eu.ydp.empiria.player.client.util.RandomizedSet;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class InlineChoiceMobileController implements InlineChoiceController, ExListBoxChangeListener {


	private Response response;
	private String responseIdentifier;
	protected List<String> identifiers;
	
	private ModuleInteractionListener moduleInteractionListener;
	private ModuleSocket moduleSocket;
	
	protected Element moduleElement;
	
	protected ExListBox listBox;
	protected Panel container;
	
	protected boolean showingAnswers = false;
	protected boolean locked = false;
	protected boolean shuffle = false;
	
	protected List<Integer> identifiersMap;
	
	@Override
	public void initModule(ModuleSocket moduleSocket, ModuleInteractionListener moduleInteractionListener) {

		this.moduleInteractionListener = moduleInteractionListener;
		this.moduleSocket = moduleSocket;
	}
	
	@Override
	public String getIdentifier() {
		return responseIdentifier;
	}

	@Override
	public void addElement(Element element) {
		moduleElement = element;
	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		responseIdentifier = XMLUtils.getAttributeAsString(moduleElement, "responseIdentifier");
		response = moduleSocket.getResponse(responseIdentifier);
		shuffle = XMLUtils.getAttributeAsBoolean(moduleElement, "shuffle");
		
		NodeList optionsNodes = moduleElement.getElementsByTagName("inlineChoice");
		List<Widget> baseBodies = new ArrayList<Widget>();
		List<Widget> popupBodies = new ArrayList<Widget>();
		List<String> identifiersTemp = new ArrayList<String>();
				
		for (int i = 0 ; i < optionsNodes.getLength() ; i ++){
			Widget baseBody = moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(optionsNodes.item(i));
			baseBodies.add(baseBody);
			Widget popupBody = moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(optionsNodes.item(i));
			popupBodies.add(popupBody);
			identifiersTemp.add(((Element)optionsNodes.item(i)).getAttribute("identifier"));
		}
		
		listBox = new ExListBox();
		listBox.setChangeListener(this);
		
		listBox.addOption(new InlineHTML(), new InlineHTML());
		
		if (shuffle){
			RandomizedSet<Integer> randomizedNodes = new RandomizedSet<Integer>();
			for (int i = 0 ; i < identifiersTemp.size() ; i ++){
				randomizedNodes.push(i);
			}
			identifiers = new ArrayList<String>();
			while (randomizedNodes.hasMore()){
				Integer currIndex = randomizedNodes.pull();
				identifiers.add(identifiersTemp.get(currIndex));
				listBox.addOption(baseBodies.get(currIndex), popupBodies.get(currIndex));
			}
			
		} else {
			identifiers = identifiersTemp;
			for (int i = 0 ; i < baseBodies.size()  &&  i < popupBodies.size() ; i ++){
				listBox.addOption(baseBodies.get(i), popupBodies.get(i));
			}
		}
		
		
		container = new FlowPanel();
		container.setStyleName("qp-text-choice-popup");
		container.add(listBox);
		
		placeholders.get(0).add(container);
	}
	@Override
	public void onBodyLoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyUnload() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void markAnswers(boolean mark) {
		if (mark){
			listBox.setEnabled(false);
			if (listBox.getSelectedIndex() != 0){
				if (response.isCorrectAnswer(identifiers.get(listBox.getSelectedIndex()-1))){
					container.setStyleName("qp-text-choice-popup-correct");
				} else {
					container.setStyleName("qp-text-choice-popup-wrong");
				}
			} else{
				container.setStyleName("qp-text-choice-popup-none");
			}
		} else {
			container.setStyleName("qp-text-choice-popup");
			listBox.setEnabled(true);
		}
	}

	@Override
	public void showCorrectAnswers(boolean show) {
		if (show  &&  !showingAnswers){
			int correctAnswerIndex = identifiers.indexOf( response.correctAnswers.get(0) )+1;
			listBox.setSelectedIndex(correctAnswerIndex);
		} else if (!show && showingAnswers){
			int answerIndex = 0;
			if (response.values.size() > 0)
				answerIndex = identifiers.indexOf( response.values.get(0) )+1;
			listBox.setSelectedIndex(answerIndex);
		}
		showingAnswers = show;
	}

	@Override
	public void lock(boolean l) {
		locked = l;
		listBox.setEnabled(!l);
		if (locked){
			container.addStyleName("qp-text-choice-popup-disabled");
		} else {
			container.removeStyleName("qp-text-choice-popup-disabled");
		}
		
	}

	@Override
	public void reset() {
		markAnswers(false);
		lock(false);
		listBox.setSelectedIndex(0);
		updateResponse(false);
		listBox.setEnabled(true);
		container.setStyleName("qp-text-choice-popup");
	}

	@Override
	public JSONArray getState() {
		  
		  JSONArray jsonArr = new JSONArray();
		  
		  String stateString = "";
		  
		  if (listBox.getSelectedIndex()-1 >= 0)
			  stateString = identifiers.get(listBox.getSelectedIndex()-1);
		  		  
		  jsonArr.set(0, new JSONString(stateString));
		  
		  return jsonArr;
	}

	@Override
	public void setState(JSONArray newState) {

		if (newState != null  &&  newState.size() > 0  &&  newState.get(0).isString() != null){
			int index = identifiers.indexOf(newState.get(0).isString().stringValue());
			listBox.setSelectedIndex(index+1);
		}
		
		
		updateResponse(false);
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return ModuleJsSocketFactory.createSocketObject(this);
	}
		
	private void updateResponse(boolean userInteract){
		if (showingAnswers)
			return;

		response.reset();
		
		if (listBox.getSelectedIndex() != 0){
			String lastValue = identifiers.get(listBox.getSelectedIndex()-1);
			response.add(lastValue);
		}
		moduleInteractionListener.onStateChanged(userInteract, this);
	}

	@Override
	public void onChange() {
		if (!showingAnswers  &&  !locked){
			updateResponse(true);
		}
		
	}

}
