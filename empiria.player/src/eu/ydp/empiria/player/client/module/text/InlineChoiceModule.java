/*
  The MIT License
  
  Copyright (c) 2009 Krzysztof Langner
  
  Permission is hereby granted, free of charge, to any person obtaining a copy
  of this software and associated documentation files (the "Software"), to deal
  in the Software without restriction, including without limitation the rights
  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
  copies of the Software, and to permit persons to whom the Software is
  furnished to do so, subject to the following conditions:
  
  The above copyright notice and this permission notice shall be included in
  all copies or substantial portions of the Software.
  
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
  THE SOFTWARE.
*/
package eu.ydp.empiria.player.client.module.text;

import java.io.Serializable;
import java.util.Vector;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.events.internal.InternalEvent;
import eu.ydp.empiria.player.client.controller.events.internal.InternalEventTrigger;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.model.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.module.IActivity;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.module.JsSocketFactory;
import eu.ydp.empiria.player.client.module.ModuleInteractionListener;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.util.RandomizedSet;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class InlineChoiceModule extends InlineHTML implements IInteractionModule{

	/** response processing interface */
	private Response response;
	private String responseIdentifier;
	/** module state changed listener */
	private ModuleInteractionListener stateListener;
	/** widget id */
	private String  id;
	/** panel widget */
	private ListBox  listBox;
	/** Shuffle? */	private boolean 		shuffle = false;
	/** Last selected value */
	private String	lastValue = null;
	private boolean showingAnswers = false;
	

	/**
	 * constructor
	 * @param moduleSocket
	 */
	public InlineChoiceModule(Element element, ModuleSocket moduleSocket, ModuleInteractionListener moduleEventsListener){
		
		responseIdentifier = XMLUtils.getAttributeAsString(element, "responseIdentifier"); 

		id = Document.get().createUniqueId();
		response = moduleSocket.getResponse(responseIdentifier);
		stateListener = moduleEventsListener;
		shuffle = XMLUtils.getAttributeAsBoolean(element, "shuffle");
		
		listBox = new ListBox();
		if(shuffle)
			initRandom(element);
		else
			init(element);

		listBox.getElement().setId(id);
		getElement().appendChild(listBox.getElement());
		
		setStyleName("qp-text-choice");		

		NodeList inlineFeedbackNodes = element.getElementsByTagName("feedbackInline");
		for (int f = 0 ; f < inlineFeedbackNodes.getLength() ; f ++){
			moduleSocket.addInlineFeedback(new InlineFeedback(listBox, inlineFeedbackNodes.item(f), moduleEventsListener));
		}
		
	}

	// ------------------------ INTERFACES ------------------------ 

	@Override
	public void onOwnerAttached() {
		// do nothing
		
	}

	@Override
	public void lock(boolean l) {
		  listBox.setEnabled(!l);
		
	}

	/**
	 * @see IActivity#markAnswers()
	 */
	public void markAnswers(boolean mark) {
		if (mark){
			listBox.setEnabled(false);
			if( response.isCorrectAnswer(lastValue) )
				setStyleName("qp-text-choice-correct");
			else
				setStyleName("qp-text-choice-wrong");
		} else {
			listBox.setEnabled(true);
			setStyleName("qp-text-choice");
		}
	}

	/**
	 * @see IActivity#reset()
	 */
	public void reset() {
		markAnswers(false);
		lock(false);
		listBox.setSelectedIndex(0);
		updateResponse(false);
	  listBox.setEnabled(true);
	  setStyleName("qp-text-choice");
	}

	/**
	 * @see IActivity#showCorrectAnswers()
	 */
	public void showCorrectAnswers(boolean show) {

		if (show  &&  !showingAnswers){
			showingAnswers = true;	
			for(int i = 0; i < listBox.getItemCount(); i++){
				if( listBox.getValue(i).compareTo(response.correctAnswers.get(0)) == 0){
					listBox.setSelectedIndex(i);
					break;
				}
			}
		} else if (!show  &&  showingAnswers) {
			listBox.setSelectedIndex(-1);
			for(int i = 0; i < listBox.getItemCount(); i++){
				if( listBox.getValue(i).compareTo((response.values.size()>0) ? response.values.get(0) : "" ) == 0){
					listBox.setSelectedIndex(i);
					break;
				}
			}
			showingAnswers = false;
		}
	}
		
	public JavaScriptObject getJsSocket(){
		return JsSocketFactory.createSocketObject(this);
	}
	
  /**
   * @see IStateful#getState()
   */
  public JSONArray getState() {
	  
	  JSONArray jsonArr = new JSONArray();
	  
	  String stateString = "";
	  
	  if (lastValue != null)
		  stateString = lastValue;
	  
	  jsonArr.set(0, new JSONString(stateString));
	  
	  return jsonArr;
  }

  
  	/**
 	 * @see IStateful#setState(Serializable)
 	 */
  	public void setState(JSONArray newState) {
	
		String state = "";
	
		if (newState == null){
		} else if (newState.size() == 0){
		} else if (newState.get(0).isString() == null){
		} else {
			state = newState.get(0).isString().stringValue();
			lastValue = null;
		}
	
		for(int i = 0; i < listBox.getItemCount(); i++){
			if( listBox.getValue(i).compareTo(state) == 0){
				listBox.setSelectedIndex(i);
				break;
			}
		}
		updateResponse(false);
  }
  
	/**
	 * init widget view
	 * @param element
	 */
	private void init(Element inlineChoiceElement){
		NodeList nodes = inlineChoiceElement.getChildNodes();

		// Add no answer as first option
		listBox.addItem("");
		
		for(int i = 0; i < nodes.getLength(); i++){
			if(nodes.item(i).getNodeName().compareTo("inlineChoice") == 0){
				Element choiceElement = (Element)nodes.item(i);
				listBox.addItem(XMLUtils.getText(choiceElement), 
				    XMLUtils.getAttributeAsString(choiceElement, "identifier"));
			}
		}
	}
	
	/**
	 * init widget view. Randomize options
	 * @param element
	 */
	private void initRandom(Element inlineChoiceElement){
		RandomizedSet<Element>	randomizedNodes = new RandomizedSet<Element>();
		NodeList nodes = inlineChoiceElement.getChildNodes();

		// Add no answer as first option
		listBox.addItem("");
		
		// Add nodes to temporary list
		for(int i = 0; i < nodes.getLength(); i++){
			if(nodes.item(i).getNodeName().compareTo("inlineChoice") == 0){
				randomizedNodes.push((Element)nodes.item(i));
			}
		}
		
		while(randomizedNodes.hasMore()){
			Element choiceElement = randomizedNodes.pull();
      listBox.addItem(XMLUtils.getText(choiceElement), 
          XMLUtils.getAttributeAsString(choiceElement, "identifier"));
		}
		
	}

	
	@Override
	public Vector<InternalEventTrigger> getTriggers() {
		Vector<InternalEventTrigger> v = new Vector<InternalEventTrigger>();
		v.add(new InternalEventTrigger(id, Event.ONCHANGE));
		return v;
	}

	@Override
	public void handleEvent(String tagID, InternalEvent param) {
		updateResponse(true);
	}
	
	private void updateResponse(boolean userInteract){
		if (showingAnswers)
			return;

		if(lastValue != null)
			response.remove(lastValue);
		
		lastValue = listBox.getValue(listBox.getSelectedIndex());
		response.add(lastValue);
		stateListener.onStateChanged(userInteract, this);
	}

	@Override
	public String getIdentifier() {
		return responseIdentifier;
	}
}
