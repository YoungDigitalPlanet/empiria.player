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

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.components.AccessibleCheckBox;
import eu.ydp.empiria.player.client.components.AccessibleRadioButton;
import eu.ydp.empiria.player.client.components.ElementWrapperWidget;
import eu.ydp.empiria.player.client.model.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.model.feedback.InlineFeedbackSocket;
import eu.ydp.empiria.player.client.module.CommonsFactory;
import eu.ydp.empiria.player.client.module.FeedbackModuleInteractionEventsListener;
import eu.ydp.empiria.player.client.module.IUnattachedComponent;
import eu.ydp.empiria.player.client.util.BrowserCompatibility;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class SimpleChoice extends Composite {

	public String identifier;

	private AccessibleCheckBox button;
	private AbsolutePanel cover;
	private AbsolutePanel container;
	private Panel optionPanel;
	private Panel labelPanel;

	public String inputId;
	//public String labelId;
	public String coverId;
	
	private Vector<IUnattachedComponent> inlineModules;
	
	
	public SimpleChoice(Element element, String inputId, String labelId, boolean multi, String moduleIdentifier, InlineFeedbackSocket inlineFeedbackSocket, FeedbackModuleInteractionEventsListener feedbackListener) {
		
		
		this.inputId = inputId;
		//this.labelId = labelId;
		this.coverId = labelId;

		identifier = XMLUtils.getAttributeAsString(element, "identifier");
		
		inlineModules = new Vector<IUnattachedComponent>();
		
		// button
		if (multi)
			button = new AccessibleCheckBox();
		else
			button = new AccessibleRadioButton(this.inputId);
		button.setStyleName("qp-choice-button");

		Vector<String> ignoredTags = new Vector<String>();
		ignoredTags.add("feedbackInline");
		//com.google.gwt.dom.client.Element dom = XMLConverter.getDOM(element, ignoredTags);
		//ElementWrapperWidget domWidget = new ElementWrapperWidget(dom);

		Widget contentWidget = CommonsFactory.getInlineTextView(element, ignoredTags, inlineModules);
		
		cover = new AbsolutePanel();
		cover.getElement().setId(coverId);
		cover.setStyleName("qp-choice-option-cover");
		
		container = new AbsolutePanel();
		container.setStyleName("qp-choice-option-container");
		container.add(contentWidget, 0, 0);
		container.add(cover, 0, 0);
		
		
		// tmp
		//button.setHTML(dom.getInnerHTML());
		// /tmp
		
	    com.google.gwt.dom.client.Element buttonElement = (com.google.gwt.dom.client.Element)button.getElement();
		(buttonElement.getElementsByTagName("input").getItem(0)).setId(inputId);
		// tmp 
		/*
		if (buttonElement.getElementsByTagName("img").getLength() > 0)
			(buttonElement.getElementsByTagName("img").getItem(0)).setId(labelId);
		else
			(buttonElement.getElementsByTagName("label").getItem(0)).setId(labelId);
		*/
		// /tmp
		
		labelPanel = new FlowPanel();
		labelPanel.setStyleName("qp-choice-label");
		labelPanel.add(container);

		optionPanel = new HorizontalPanel();
		optionPanel.setStyleName("qp-choice-option");
		optionPanel.add(button);
		optionPanel.add(labelPanel); // tmp
		
		Widget widgetWrapped;
		
		if (BrowserCompatibility.detectIPhone()){			
			com.google.gwt.dom.client.Element a = Document.get().createElement("a");
			a.setAttribute("href", "javascript:void(0)");
			a.appendChild(optionPanel.getElement());
			
			widgetWrapped = new ElementWrapperWidget(a);
		} else {
			widgetWrapped = optionPanel;
		}
		

		initWidget(widgetWrapped);
		
		// feedback
		
		NodeList inlineFeedbackNodes = element.getElementsByTagName("feedbackInline");
		for (int f = 0 ; f < inlineFeedbackNodes.getLength() ; f ++){
			inlineFeedbackSocket.add(new InlineFeedback(labelPanel, inlineFeedbackNodes.item(f), feedbackListener));
		}
		
	    
	}
	
	public void onOwnerAttached(){
		for (IUnattachedComponent uac : inlineModules)
			uac.onOwnerAttached();
	}

	public void markAnswers(boolean mark, boolean correct) {

		if (mark){
			if(button.isChecked()){
				if( correct )
					button.setStyleName("qp-choice-selected-correct");
				else
					button.setStyleName("qp-choice-selected-wrong");
			}
			else{
				if( correct )
					button.setStyleName("qp-choice-notselected-wrong");
				else
					button.setStyleName("qp-choice-notselected-correct");
			}
			
			setEnabled(false);
			
		} else {

			button.setStyleName("qp-choice-button");
			setEnabled(true);
		}

	}
	
	public void setSelected(boolean sel){
		button.setChecked(sel);
	}

	public boolean isSelected(){
		boolean isc = button.isChecked();
		return isc;
	}

	/**
	 * Make this widget read only
	 */
	public void setEnabled(boolean mode){
		button.setEnabled(mode);
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}
	

	/**
	 * @return the input ID
	 */
	public String getInputId() {
		return inputId;
	}
	
	/**
	 * @return the label ID
	 */
	public String getLabelId() {
		return coverId;
	}

	public void reset(){
		button.setStyleName("qp-choice-button");
		button.setValue(false);
		setEnabled(true);
	}



}
