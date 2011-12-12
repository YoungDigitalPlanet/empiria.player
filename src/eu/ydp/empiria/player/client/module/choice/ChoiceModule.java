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
package eu.ydp.empiria.player.client.module.choice;

import java.util.Vector;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.events.internal.InternalEvent;
import eu.ydp.empiria.player.client.controller.events.internal.InternalEventTrigger;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.model.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.model.feedback.InlineFeedbackSocket;
import eu.ydp.empiria.player.client.module.CommonsFactory;
import eu.ydp.empiria.player.client.module.FeedbackModuleInteractionListener;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.JsSocketFactory;
import eu.ydp.empiria.player.client.module.ModuleInteractionListener;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.components.selectablebutton.ChoiceGroupController;
import eu.ydp.empiria.player.client.util.RandomizedSet;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class ChoiceModule extends Composite implements IInteractionModule {
	/** response processing interface */
	private Response response;
	/** module state changed listener */
	private ModuleInteractionListener stateListener;
	/** response id */
	private String responseIdentifier;
	/** Work mode single or multiple choice */
	private boolean multi = false;
	/** Shuffle? */
	private boolean shuffle = false;
	/** option widgets */
	private Vector<SimpleChoice> interactionElements;
	
	private boolean locked = false;
	private boolean showingAnswers = false;
	
	protected ChoiceGroupController groupController;
		
	
	public ChoiceModule(Element element, ModuleSocket moduleSocket, ModuleInteractionListener stateChangedListener){
		
		shuffle = XMLUtils.getAttributeAsBoolean(element, "shuffle");

		responseIdentifier = XMLUtils.getAttributeAsString(element, "responseIdentifier");
		response = moduleSocket.getResponse(responseIdentifier);
		multi = response.cardinality == Cardinality.MULTIPLE;
		stateListener = stateChangedListener;
		
		Panel vp = new FlowPanel();
		
		vp.setStyleName("qp-choice-module");
		vp.add(CommonsFactory.getPromptView(XMLUtils.getFirstElementWithTagName(element, "prompt")));
		vp.add(getOptionsView(element, moduleSocket, stateChangedListener));
		
		initWidget(vp);
		
		NodeList childNodes = element.getChildNodes();
		for (int f = 0 ; f < childNodes.getLength() ; f ++){
			if (childNodes.item(f).getNodeName().compareTo("feedbackInline") == 0)
				moduleSocket.addInlineFeedback(new InlineFeedback(vp, childNodes.item(f), stateChangedListener));
		}
	}
	

	// ------------------------- MODULE CERATION --------------------------------
	

	  /**
	   * Get options view
	   * @return
	   */
	  private Widget getOptionsView(Element element, InlineFeedbackSocket inlineFeedbackSocket, FeedbackModuleInteractionListener feedbackListener){

		  Panel panel = new FlowPanel();
		  NodeList optionNodes = element.getElementsByTagName("simpleChoice");
		  RandomizedSet<Element> randomizedNodes = new RandomizedSet<Element>();
		  RandomizedSet<Integer> randomizedIndices = new RandomizedSet<Integer>();

		  interactionElements = new Vector<SimpleChoice>();
		  for (int el = 0 ; el < optionNodes.getLength() ; el ++)
			  interactionElements.add(null);
		  
		  // Add randomized nodes to shuffle table
		  if(shuffle){
			  for(int i = 0; i < optionNodes.getLength(); i++){
				  Element	option = (Element)optionNodes.item(i);
				  if(!XMLUtils.getAttributeAsBoolean(option, "fixed")){
					  randomizedNodes.push(option);
					  randomizedIndices.push(i);
				  }
			  }
		  }
		  
		  groupController = new ChoiceGroupController();

		  // Create buttons
		  for(int i = 0; i < optionNodes.getLength(); i++){
			  int optionIndex = i;
			  Element option = (Element)optionNodes.item(i);
			  SimpleChoice currInteractionElement;
			  String currInputId = Document.get().createUniqueId();
			  String currLabelId = Document.get().createUniqueId();

			  if(shuffle && !XMLUtils.getAttributeAsBoolean(option, "fixed") ){
				  //option = randomizedNodes.pull();
				  optionIndex = randomizedIndices.pull();
				  option = (Element)optionNodes.item(optionIndex);
			  }

			  currInteractionElement = new SimpleChoice(option, currInputId, currLabelId, multi, responseIdentifier, inlineFeedbackSocket, feedbackListener, groupController);
			  //interactionElements.add(currInteractionElement);
			  interactionElements.set(optionIndex, currInteractionElement);
			  panel.add(currInteractionElement);
		  }

		  return panel;
	  }


	// ------------------------- INTERFACES --------------------------------


	@Override
	public void onOwnerAttached() {
		for (SimpleChoice c:interactionElements){
			c.onOwnerAttached();
		}
		updateResponse(null, false);
	}


	@Override
	public void lock(boolean l) {
		locked = l;
		for (SimpleChoice currSC:interactionElements){
			currSC.setEnabled(!l);
		}
		
	}
	
	@Override
	public void markAnswers(boolean mark) {
		
		Vector<Boolean> evaluation = response.evaluateAnswer();
		
		if (response.cardinality == Cardinality.SINGLE){
			for (int i = 0 ; i < interactionElements.size() ; i ++){
				interactionElements.get(i).markAnswers(mark, evaluation.get(0));
			}
		} else if (response.cardinality == Cardinality.MULTIPLE){
			for (SimpleChoice currSC:interactionElements){
				currSC.markAnswers(mark, response.correctAnswers.contains(currSC.getIdentifier()) );
			}
		}
	}

	@Override
	public void showCorrectAnswers(boolean show) {
		if (show  &&  !showingAnswers){
			showingAnswers = true;
			for (SimpleChoice currSC:interactionElements){
				currSC.setSelected(response.correctAnswers.contains(currSC.getIdentifier()) );
			}
		} else if (!show  &&  showingAnswers) {
			for (SimpleChoice currSC:interactionElements){
				currSC.setSelected(response.values.contains(currSC.getIdentifier()) );
			}
			showingAnswers = false;
		}
	}

	@Override
	public void reset() {
		for (SimpleChoice currSC:interactionElements){
			currSC.reset();
		}
		updateResponse(null, false);
	}

	@Override
	public JSONArray getState() {
		JSONArray  state = new JSONArray();

		for (SimpleChoice currSC:interactionElements){
			//boolean b1 = currSC.isSelected();
			boolean b1 = response.values.contains(currSC.getIdentifier());
			state.set(state.size(), JSONBoolean.getInstance(b1));
		}
		
		return state;
	}

	@Override
	public void setState(JSONArray newState) {

		Boolean currSelected;
		 
		for (int i  = 0 ; i < newState.size() && i < interactionElements.size(); i ++ ){
			currSelected = newState.get(i).isBoolean().booleanValue();
			interactionElements.get(i).setSelected(currSelected);
			
		}
		
		updateResponse(null, false);
		//stateListener.onStateChanged(this);
	}

	@Override
	public Vector<InternalEventTrigger> getTriggers() {

		Vector<InternalEventTrigger> ids = new Vector<InternalEventTrigger>();
		for (SimpleChoice currSC:interactionElements){
			ids.add(new InternalEventTrigger(currSC.getInputId(), Event.ONMOUSEUP));
			ids.add(new InternalEventTrigger(currSC.getLabelId(), Event.ONMOUSEUP));

			ids.add(new InternalEventTrigger(currSC.getInputId(), Event.ONMOUSEMOVE));
			ids.add(new InternalEventTrigger(currSC.getLabelId(), Event.ONMOUSEMOVE));

			ids.add(new InternalEventTrigger(currSC.getInputId(), Event.ONMOUSEOUT));
			ids.add(new InternalEventTrigger(currSC.getLabelId(), Event.ONMOUSEOUT));
		}
		return ids;
	}
		
	public JavaScriptObject getJsSocket(){
		return JsSocketFactory.createSocketObject(this);
	}

	@Override
	public void handleEvent(String tagID, InternalEvent param) {

		SimpleChoice targetSC = null;
		
		// check if multi selection mode
		if (param != null){
			String lastSelectedId = param.getEventTargetElement().getId();
		
			boolean targertIsButton = false;

			for (SimpleChoice currSC:interactionElements){
				if (currSC.getInputId().compareTo(lastSelectedId) == 0){
					targetSC = currSC;
					targertIsButton = true;
					break;
				}
			}
					
			if (!targertIsButton) {
				for (SimpleChoice currSC:interactionElements){
					if (currSC.getLabelId().compareTo(lastSelectedId) == 0){
						targetSC = currSC;
						break;
					}
				}
			}
			
			if (targetSC != null){
			
				if (param.getTypeInt() == Event.ONMOUSEUP ){
					if (!locked){
						targetSC.setSelected(!targetSC.isSelected());
						// pass response
						
						updateResponse(targetSC, true);
					}					
				} else if (param.getTypeInt() == Event.ONMOUSEMOVE){
					targetSC.setMouseOver();
				} else if (param.getTypeInt() == Event.ONMOUSEOUT){
					targetSC.setMouseOut();
				}
			}
		}
		
		param.stopPropagation();
		
	}
	
	private void updateResponse(SimpleChoice target, boolean userInteract){
		if (showingAnswers)
			return;
		
		Vector<String> currResponseValues = new Vector<String>();
		
		for (SimpleChoice currSC:interactionElements){
			if (currSC.isSelected()){
				currResponseValues.add(currSC.getIdentifier());
			}
		}
		
		if (!response.compare(currResponseValues)  ||  !response.isInitialized()){
			response.set(currResponseValues);
			stateListener.onStateChanged(userInteract, this);
		}
	}

	@Override
	public String getIdentifier() {
		return responseIdentifier;
	}

}
