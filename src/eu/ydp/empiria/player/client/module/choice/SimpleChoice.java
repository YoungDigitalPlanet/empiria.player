package eu.ydp.empiria.player.client.module.choice;

import java.util.Vector;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.components.AccessibleCheckBox;
import eu.ydp.empiria.player.client.components.AccessibleRadioButton;
import eu.ydp.empiria.player.client.components.ElementWrapperWidget;
import eu.ydp.empiria.player.client.controller.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.controller.feedback.InlineFeedbackSocket;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceButtonBase;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceGroupController;
import eu.ydp.empiria.player.client.module.components.choicebutton.MultiChoiceButton;
import eu.ydp.empiria.player.client.module.components.choicebutton.SingleChoiceButton;
import eu.ydp.empiria.player.client.module.listener.FeedbackModuleInteractionListener;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;
import eu.ydp.empiria.player.client.util.BrowserCompatibility;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class SimpleChoice extends FlowPanel {

	public String identifier;

	private ChoiceButtonBase button;
	private AbsolutePanel cover;
	private AbsolutePanel container;
	private Panel optionPanel;
	private Panel labelPanel;
	private Panel markAnswersPanel; 
	protected boolean multi;
	
	
	public SimpleChoice(Element element,  boolean multi, final SimpleChoiceListener listener, ModuleSocket ms, ModuleInteractionListener mil,
			ChoiceGroupController ctrl) {
		
		this.multi = multi;

		identifier = XMLUtils.getAttributeAsString(element, "identifier");
		
		setStyleName("qp-choice-option-box");
		
		// button
		if (multi)
			button = new MultiChoiceButton("choice-multi");
		else
			button = new SingleChoiceButton(ctrl, "choice-single");

		button.addMouseOverHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				setMouseOver();
			}
		});
		button.addMouseOutHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				setMouseOut();
			}
		});
		final SimpleChoice instance = this;
		button.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				listener.onSimpleChoiceClick(instance);
			}
		});

		Widget contentWidget = ms.getInlineBodyGeneratorSocket().generateInlineBody(element);
		
		cover = new AbsolutePanel();
		cover.setStyleName("qp-choice-option-cover");
		cover.addDomHandler(new MouseOverHandler() {
			public void onMouseOver(MouseOverEvent event) {
				setMouseOver();
			}
		}, MouseOverEvent.getType());
		cover.addDomHandler(new MouseOutHandler() {
			public void onMouseOut(MouseOutEvent event) {
				setMouseOut();
			}
		}, MouseOutEvent.getType());
		cover.addDomHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				listener.onSimpleChoiceClick(instance);
			}
		}, ClickEvent.getType());
		
		container = new AbsolutePanel();
		container.setStyleName("qp-choice-option-container");
		container.add(contentWidget, 0, 0);
		container.add(cover, 0, 0);
		
		labelPanel = new FlowPanel();
		labelPanel.setStyleName("qp-choice-label");
		labelPanel.add(container);
		
		markAnswersPanel = new FlowPanel();
		String buttonTypeName;
		if (multi)
			buttonTypeName = "multi";
		else
			buttonTypeName = "single";
		markAnswersPanel.setStyleName("qp-choice-button-"+buttonTypeName+"-markanswers");

		optionPanel = new FlowPanel();
		optionPanel.setStyleName("qp-choice-option");
		optionPanel.add(markAnswersPanel);
		optionPanel.add(button);
		optionPanel.add(labelPanel); // tmp

		add(optionPanel);
		
		// feedback
		
		NodeList inlineFeedbackNodes = element.getElementsByTagName("feedbackInline");
		for (int f = 0 ; f < inlineFeedbackNodes.getLength() ; f ++){
			ms.addInlineFeedback(new InlineFeedback(labelPanel, inlineFeedbackNodes.item(f), ms, mil));
		}
		
	    
	}
	
	public void onOwnerAttached(){
	}

	public void markAnswers(boolean mark, boolean correct) {
		
		String buttonTypeName;
		if (multi)
			buttonTypeName = "multi";
		else
			buttonTypeName = "single";

		if (!mark){
			markAnswersPanel.setStyleName("qp-choice-button-"+buttonTypeName+"-markanswers");
		} else {
			if (isSelected()){
				if( correct )
					markAnswersPanel.setStyleName("qp-choice-button-"+buttonTypeName+"-markanswers-correct");
				else
					markAnswersPanel.setStyleName("qp-choice-button-"+buttonTypeName+"-markanswers-wrong");
			} else {
				markAnswersPanel.setStyleName("qp-choice-button-"+buttonTypeName+"-markanswers-none");
			}
		}
			
			
		setEnabled(!mark);

	}
	
	public void setSelected(boolean sel){
		button.setSelected(sel);
	}

	public boolean isSelected(){
		boolean isc = button.isSelected();
		return isc;
	}

	/**
	 * Make this widget read only
	 */
	public void setEnabled(boolean mode){
		button.setButtonEnabled(mode);
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}
	
	public void reset(){
		button.setSelected(false);
		setEnabled(true);
	}


	public void setMouseOver(){
		button.setMouseOver(true);
	}
	
	public void setMouseOut(){
		button.setMouseOver(false);
	}

}
