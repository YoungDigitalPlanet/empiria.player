package eu.ydp.empiria.player.client.module.choice;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceButtonBase;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceGroupController;
import eu.ydp.empiria.player.client.module.components.choicebutton.MultiChoiceButton;
import eu.ydp.empiria.player.client.module.components.choicebutton.SingleChoiceButton;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.XMLUtils;

public class SimpleChoice extends FlowPanel {

	public String identifier;

	private ChoiceButtonBase button;
	private final AbsolutePanel cover;
	private final AbsolutePanel container;
	private final Panel optionPanel;
	private final Panel labelPanel;
	private final Panel markAnswersPanel;
	private final Panel buttonPanel;
	protected boolean multi;
	
	private StyleNameConstants styleNameConstants = PlayerGinjector.INSTANCE.getStyleNameConstants();


	public SimpleChoice(Element element,  boolean multi, final SimpleChoiceListener listener, ModuleSocket moduleSocket, InteractionEventsListener mil,//NOPMD
			ChoiceGroupController ctrl) {

		this.multi = multi;

		identifier = XMLUtils.getAttributeAsString(element, "identifier");

		setStyleName("qp-choice-option-box");

		// button
		if (multi) {
			button = new MultiChoiceButton("choice-multi");
		} else {
			button = new SingleChoiceButton(ctrl, "choice-single");
		}

		button.addMouseOverHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				setMouseOver();
			}
		});
		button.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				setMouseOut();
			}
		});
		final SimpleChoice instance = this;
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				listener.onSimpleChoiceClick(instance);
			}
		});

		Widget contentWidget = moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(element,true);

		cover = new AbsolutePanel();
		cover.setStyleName("qp-choice-option-cover");
		cover.addDomHandler(new MouseOverHandler() {
			@Override
			public void onMouseOver(MouseOverEvent event) {
				setMouseOver();
			}
		}, MouseOverEvent.getType());
		cover.addDomHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				setMouseOut();
			}
		}, MouseOutEvent.getType());
		cover.addDomHandler(new ClickHandler() {
			@Override
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
		if (multi) {
			buttonTypeName = "multi";
		} else {
			buttonTypeName = "single";
		}
		markAnswersPanel.setStyleName("qp-choice-button-"+buttonTypeName+"-markanswers");
		
		buttonPanel = new FlowPanel();
		buttonPanel.setStyleName("qp-choice-button-"+buttonTypeName+"-container");

		optionPanel = new FlowPanel();
		optionPanel.setStyleName("qp-choice-option");
		optionPanel.add(markAnswersPanel);
		optionPanel.add(button);
		optionPanel.add(labelPanel); // tmp

		add(optionPanel);

		// feedback

		NodeList inlineFeedbackNodes = element.getElementsByTagName("feedbackInline");
		for (int f = 0 ; f < inlineFeedbackNodes.getLength() ; f ++){
			moduleSocket.addInlineFeedback(new InlineFeedback(labelPanel, inlineFeedbackNodes.item(f), moduleSocket, mil));
		}


	}

	public void onOwnerAttached(){
	}

	/**
	 * Zwraca typ przycisku multi lub single
	 * @return
	 */
	private String getButtonType(){
		return multi ? "multi" : "single";
	}

	public void markAnswers(boolean mark, boolean correct) {
		String buttonTypeName = getButtonType();
		if (!mark){
			markAnswersPanel.setStyleName("qp-choice-button-"+buttonTypeName+"-markanswers");
			markAnswersPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_INACTIVE());
			buttonPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_INACTIVE());
			buttonPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_CORRECT());
			buttonPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_WRONG());
			buttonPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_NONE());
			labelPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_INACTIVE());
			labelPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_CORRECT());
			labelPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_WRONG());
			labelPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_NONE());
		} else {
			if (isSelected()){
				if( correct ) {
					markAnswersPanel.setStyleName("qp-choice-button-"+buttonTypeName+"-markanswers-correct");
					markAnswersPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_CORRECT());
					buttonPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_CORRECT());
					labelPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_CORRECT());
				} else {
					markAnswersPanel.setStyleName("qp-choice-button-"+buttonTypeName+"-markanswers-wrong");
					markAnswersPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_WRONG());
					buttonPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_WRONG());
					labelPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_WRONG());
				}
			} else {
				markAnswersPanel.setStyleName("qp-choice-button-"+buttonTypeName+"-markanswers-none");
				markAnswersPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_NONE());
				buttonPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_NONE());
				labelPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_NONE());
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
		markAnswersPanel.setStyleName("qp-choice-button-"+getButtonType()+"-markanswers-none");
	}


	public void setMouseOver(){
		button.setMouseOver(true);
	}

	public void setMouseOut(){
		button.setMouseOver(false);
	}

}
