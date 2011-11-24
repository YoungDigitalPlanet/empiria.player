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
package eu.ydp.empiria.player.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.model.Assessment;
import eu.ydp.empiria.player.client.model.Item;

public class PlayerWidget extends Composite{

	/** current item */
//	private AssessmentItem 	assessmentItem;
	private Panel 					playerPanel;
	/** Counter label */
	private Panel	counterPanel;
	private Label						counterLabel;
	private ListBox	counterListBox;
	/** Body panel. AssessmentItem view will be shown there */
	private Panel 					bodyPanel;
	/** Assessment item feedback */
	private Label 					feedbackLabel;
	/** Footer */
	private Panel						footer;
	private FlowPanel				footerContainer;
	/** Check button */ 
	private PushButton					checkButton;
	/** Reset button */ 
	private PushButton					resetButton;
	/** Next button */ 
	private PushButton					prevButton;
	/** Next button */ 
	private PushButton					nextButton;
	/** Finish button */ 
	private PushButton					finishButton;
	/** Summary button */ 
	private PushButton					summaryButton;
	/** Page combo */
	
	/**
	 * constructor
	 * @param assessment to show
	 */
	public PlayerWidget(Assessment assessment){
		//this.assessment = assessment;
		
		if (assessment != null)
			initWidget(createView(assessment));
	}
	
	/**
	 * @return check button
	 */
	public PushButton getCheckButton(){
		return checkButton;
	}

	/**
	 * @return finish button
	 */
	public PushButton getFinishButton(){
		return finishButton;
	}
	
	/**
	 * @return summary button
	 */
	public PushButton getSummaryButton(){
		return summaryButton;
	}
	
	/**
	 * @return next button
	 */
	public PushButton getNextButton(){
		return nextButton;
	}
	
	/**
	 * @return previous button
	 */
	public PushButton getPrevButton(){
		return prevButton;
	}
	
	/**
	 * @return reset button
	 */
	public PushButton getResetButton(){
		return resetButton;
	}
	
	public ListBox getCounterListBox(){
		return counterListBox;
	}
	
	/**
	 * Show error instead of page
	 */
	public void showError(String message){

		Label	errorLabel = new Label(message);
		bodyPanel.clear();
		showFeedback("");

		errorLabel.setStyleName("qp-error");
		bodyPanel.add(errorLabel);
	}
	
	/**
	 * Create view for given assessment item and show it in player
	 * @param index of assessment item
	 */
	public void showPage(Assessment assessment, Item assessmentItem, int pageIndex){

		Label itemTitleLabel = new Label();
		
//		this.assessmentItem = assessmentItem; 
		bodyPanel.clear();
		
		footer.setVisible(true);
		counterPanel.setVisible(true);
		showFeedback("");

		counterListBox.setSelectedIndex(pageIndex);
		counterLabel.setText("/" + assessment.DEBUGgetAssessmentItemsCount());		

		itemTitleLabel.setText(String.valueOf(pageIndex+1) + ". " + assessmentItem.getTitle());
		itemTitleLabel.setStyleName("qp-item-title");
		bodyPanel.add(itemTitleLabel);
		
		bodyPanel.add(assessmentItem.getContentView());
		
		bodyPanel.add(assessmentItem.getFeedbackView());
		/*
		InlineHTML h = new InlineHTML();
		h.setHTML("asdasd<script type=\"math/mml\"><math display=\"inline\"><mi>x</mi><mo>=</mo><mn>1</mn></math></script>");
		bodyPanel.add(h);
		*/
		//MathExprInlineModule mein = new MathExprInlineModule("<msup><mfenced><mrow><mi>x</mi><mo>=</mo><mn>1</mn></mrow></mfenced><mn>4</mn></msup>");
		//bodyPanel.add(mein);
		
	}
	
	public void bodyPanelAdd(Widget w){
		bodyPanel.add(w);
	}
	
	public void showFeedback(String feedback){
		feedbackLabel.setText(feedback);
		if (feedback.length() > 0){
		    feedbackLabel.setStyleName("qp-feedback");
		} else {
			feedbackLabel.setStyleName("qp-feedback-hidden");
		}
	}
	
	/**
	 * Show view with assessment score
	 * @param index of assessment item
	 */
	public void showResultPage(Widget resultInfo){

		bodyPanel.clear();
		footer.setVisible(false);
		counterPanel.setVisible(false);
		showFeedback("");
		bodyPanel.add(resultInfo);
	}
	
  /**
   * @return view with player
   */
  private Widget createView(Assessment assessment){
	  
    Label           label;
    HorizontalPanel header = new HorizontalPanel();

    playerPanel = new FlowPanel();
    playerPanel.setStyleName("qp-player");
    header.setStyleName("qp-header");
    label = new Label(assessment.getTitle());
    label.setStyleName("qp-assessment-title");
    header.add(label);
    
    counterListBox = new ListBox();
    counterListBox.setVisibleItemCount(1);
    counterListBox.setStyleName("qp-page-counter-list");
    for (int p = 0 ; p <  assessment.DEBUGgetAssessmentItemsCount(); p ++)
    	counterListBox.addItem(String.valueOf(p+1));
        
    
    counterLabel = new Label("/" + assessment.DEBUGgetAssessmentItemsCount());
    counterLabel.setStyleName("qp-page-counter-count");
    
    counterPanel = new FlowPanel();
    counterPanel.setStyleName("qp-page-counter");
    counterPanel.add(counterListBox);
    counterPanel.add(counterLabel);
    
    header.add(counterPanel);
    playerPanel.add(header);

    bodyPanel = new VerticalPanel();
    bodyPanel.setStyleName("qp-body");
    playerPanel.add(bodyPanel);
    
    feedbackLabel = new Label();
    feedbackLabel.setStyleName("qp-feedback-hidden");
    playerPanel.add(feedbackLabel);

    footerContainer = new FlowPanel();
    footerContainer.setStyleName("qp-footer-buttons");

    checkButton = new PushButton();
    checkButton.setStylePrimaryName("qp-check-button");
    footerContainer.add(checkButton);
    
    resetButton = new PushButton();
    resetButton.setStylePrimaryName("qp-reset-button");
    footerContainer.add(resetButton);

    prevButton = new PushButton();
    prevButton.setStylePrimaryName("qp-prev-button");
    footerContainer.add(prevButton);
    
    nextButton = new PushButton();
    nextButton.setStylePrimaryName("qp-next-button");
    footerContainer.add(nextButton);
    
    finishButton = new PushButton();
    finishButton.setStylePrimaryName("qp-finish-button");
    footerContainer.add(finishButton);
    
    summaryButton = new PushButton();
    summaryButton.setStylePrimaryName("qp-summary-button");
    footerContainer.add(summaryButton);
    
    footer = new FlowPanel();
    footer.setStyleName("qp-footer");
    footer.add(footerContainer);
    playerPanel.add(footer);
    
    return playerPanel;
  }
  
}
