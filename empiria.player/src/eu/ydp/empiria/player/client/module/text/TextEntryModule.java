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
import com.google.gwt.user.client.ui.TextBox;
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
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class TextEntryModule extends InlineHTML implements IInteractionModule{

	/** response processing interface */
	private Response 	response;
	private String responseIdentifier;
	/** module state changed listener */
	private ModuleInteractionListener stateListener;
	/** widget id */
	private String  id;
	/** text box control */
	private TextBox textBox;
	/** Last selected value */
	private String	lastValue = null;
	private boolean showingAnswers = false;

	/**	
	 * constructor
	 * @param moduleSocket
	 */
	public TextEntryModule(Element element, ModuleSocket moduleSocket, ModuleInteractionListener stateChangedListener){

		responseIdentifier = XMLUtils.getAttributeAsString(element, "responseIdentifier"); 

		id = Document.get().createUniqueId();
		response = moduleSocket.getResponse(responseIdentifier);
		stateListener = stateChangedListener;
		textBox = new TextBox();
		if (element.hasAttribute("expectedLength"))
			textBox.setMaxLength(XMLUtils.getAttributeAsInt(element, "expectedLength"));
		textBox.getElement().setId(id);
		
		if (!response.correctAnswers.get(0).matches(".*[^0-9].*"))
			textBox.getElement().setAttribute("type", "number");

		InlineHTML spanPrefix = new InlineHTML();
		spanPrefix.setStyleName("qp-text-textentry-prefix");
		InlineHTML spanSufix = new InlineHTML();
		spanSufix.setStyleName("qp-text-textentry-sufix");
		InlineHTML spanContent = new InlineHTML();
		spanContent.setStyleName("qp-text-textentry-content");
		spanContent.getElement().appendChild(textBox.getElement());
		
		getElement().appendChild(spanPrefix.getElement());
		getElement().appendChild(spanContent.getElement());
		getElement().appendChild(spanSufix.getElement());
		setStyleName("qp-text-textentry");
		
		NodeList inlineFeedbackNodes = element.getElementsByTagName("feedbackInline");
		for (int f = 0 ; f < inlineFeedbackNodes.getLength() ; f ++){
			moduleSocket.addInlineFeedback(new InlineFeedback(this, inlineFeedbackNodes.item(f), stateChangedListener));
		}
		
	}

	// ------------------------ INTERFACES ------------------------ 

	@Override
	public void onOwnerAttached() {
		updateResponse(false);
	}

	@Override
	public void lock(boolean l) {
		textBox.setEnabled(!l);
	}
  
	/**
	 * @see IActivity#markAnswers()
	 */
	public void markAnswers(boolean mark) {
		if (mark){
			textBox.setEnabled(false);
			if (textBox.getText().length() > 0){
				if( response.isCorrectAnswer(lastValue) )
					setStyleName("qp-text-textentry-correct");
				else
					setStyleName("qp-text-textentry-wrong");
			} else {
				setStyleName("qp-text-textentry-none");
			}
		} else {
			textBox.setEnabled(true);
			setStyleName("qp-text-textentry");
		}
	}

	/**
	 * @see IActivity#reset()
	 */
	public void reset() {
		markAnswers(false);
		showCorrectAnswers(false);
		lock(false);
		textBox.setText("");
	}

	/**
	 * @see IActivity#showCorrectAnswers()
	 */
	public void showCorrectAnswers(boolean show) {
		if (show  &&  !showingAnswers){
			showingAnswers = true;
			textBox.setText(response.correctAnswers.get(0));
		} else if (!show  &&  showingAnswers) {
			textBox.setText((response.values.size()>0) ? response.values.get(0) : "");
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
	  
	  if (response.values.size() > 0)
		  stateString = response.values.get(0);
	  
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
	
		textBox.setText(state);
		
		updateResponse(false);
		
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
		
		lastValue = textBox.getText();
		response.add(lastValue);
		stateListener.onStateChanged(userInteract, this);
	
	}

	@Override
	public String getIdentifier() {
		return responseIdentifier;
	}

}
