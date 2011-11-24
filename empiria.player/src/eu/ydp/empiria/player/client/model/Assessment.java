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
package eu.ydp.empiria.player.client.model;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

import eu.ydp.empiria.player.client.controller.style.StyleLinkDeclaration;
import eu.ydp.empiria.player.client.model.feedback.AssessmentFeedbackManager;
import eu.ydp.empiria.player.client.model.feedback.AssessmentFeedbackSocket;
import eu.ydp.empiria.player.client.util.xml.document.XMLData;

public class Assessment implements AssessmentFeedbackSocket{

	/** Whole assessment title */
	private String title;
	
	/** XML DOM of the assessment */
	private XMLData xmlData;

	public StyleLinkDeclaration styleDeclaration;
	
	private AssessmentFeedbackManager feedbackManager;
	
		
	/**
	 * C'tor
	 * @param data XMLData object as data source
	 */
	public Assessment(XMLData data){
		
		if (data == null){
			
		}
		
		xmlData = data;
		
		Node rootNode = xmlData.getDocument().getElementsByTagName("assessmentTest").item(0);
		
		styleDeclaration = new StyleLinkDeclaration(xmlData.getDocument().getElementsByTagName("styleDeclaration"), data.getBaseURL());
		
		feedbackManager = new AssessmentFeedbackManager(xmlData.getDocument().getElementsByTagName("assessmentFeedback"));
		
	    title = ((Element)rootNode).getAttribute("title");
	    
	    if (title == null)
	    	title = "";
	    
	}
	
	
	/**
	 * @return number of items in assessment
	 */
	public int DEBUGgetAssessmentItemsCount(){
		return 0;
	}

	/**
	 * @return assessment title
	 */
	public String getTitle(){
		return title;
	}
	
	public Widget getFeedbackView(int percentageScore){
		return feedbackManager.getView(percentageScore);
	}
	
	
}
