package eu.ydp.empiria.player.client.module.choice;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.ActivityPresenter;
import eu.ydp.empiria.player.client.module.choice.structure.SimpleChoiceBean;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceButtonBase;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceGroupController;
import eu.ydp.empiria.player.client.module.components.choicebutton.MultiChoiceButton;
import eu.ydp.empiria.player.client.module.components.choicebutton.SingleChoiceButton;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.choice.ChoiceModuleEvent;
import eu.ydp.empiria.player.client.util.events.choice.ChoiceModuleEventType;

public class SimpleChoiceView implements ActivityPresenter<ChoiceModuleModel, SimpleChoiceBean>{

	private static final String TYPE_SINGLE = "single";

	private static final String TYPE_MULTI = "multi";

	private static final String STYLE_CHOICE_SINGLE = "choice-single";

	private static final String STYLE_CHOICE_MULTI = "choice-multi";

	private static SimpleChoiceViewUiBinder uiBinder = GWT.create(SimpleChoiceViewUiBinder.class);

	interface SimpleChoiceViewUiBinder extends UiBinder<Widget, SimpleChoiceView> {
	}
	
	private EventsBus eventBus = PlayerGinjector.INSTANCE.getEventsBus();
	
	private StyleNameConstants styleNameConstants = PlayerGinjector.INSTANCE.getStyleNameConstants();
	
	@UiField
	Panel optionPanel;
	
	@UiField
	Panel mainPanel;
	
	@UiField
	Panel cover;
	
	@UiField
	Panel contentWidgetPlace;
	
	@UiField
	Panel markAnswersPanel;
	
	@UiField
	Panel labelPanel;
	
	@UiField
	Panel buttonPlace;
	
	private boolean isMulti;
	
	private String identifier;
	
	private InlineBodyGeneratorSocket bodyGenerator;

	private ChoiceButtonBase button;

	public SimpleChoiceView(SimpleChoiceBean option, ChoiceGroupController controller, InlineBodyGeneratorSocket bodyGenerator) {
		bindView();
		this.bodyGenerator = bodyGenerator;
		
		installChildren(option, controller);
	}
	
	private void installChildren(SimpleChoiceBean choiceOption, ChoiceGroupController controller){
		isMulti = choiceOption.isMulti();
		identifier = choiceOption.getIdentifier();
		
		createButton(controller);
		buttonPlace.add(button);
		addButtonListeners();
		
		createAndInstallContent(choiceOption);
		
		markAnswersPanel.addStyleName("qp-choice-button-"+getButtonType()+"-markanswers");
		addListenersToCover();	
	}
	
	private void createButton(ChoiceGroupController controller){
		if (isMulti) {
			button = new MultiChoiceButton(STYLE_CHOICE_MULTI);
		} else {
			button = new SingleChoiceButton(controller, STYLE_CHOICE_SINGLE);
		}
	}
	
	private void addButtonListeners(){
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
		
		button.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				onChoiceClick();
			}
		});
	}
	
	private void createAndInstallContent(SimpleChoiceBean choiceOption){
		Widget contentWidget = bodyGenerator.generateInlineBody(choiceOption.getContent(),true);
		contentWidgetPlace.add(contentWidget);
	}
	
	private void addListenersToCover(){
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
				onChoiceClick();
			}
		}, ClickEvent.getType());
	}
	
	private void onChoiceClick(){
		eventBus.fireEvent(new ChoiceModuleEvent(ChoiceModuleEventType.ON_CHOICE_CLICK, getIdentifier()));
	}
	
	private String getButtonType(){
		return isMulti ? TYPE_MULTI : TYPE_SINGLE;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setSelected(boolean select) {
		button.setSelected(select);
	}
	
	public boolean isSelected(){
		return button.isSelected();
	}
	
	public Widget getFeedbackPlaceHolder(){
		return labelPanel;
	}

	public void setLocked(boolean enabled) {
		button.setButtonEnabled(enabled);
	}

	public void reset() {
		button.setSelected(false);
		markAnswersPanel.setStyleName("qp-choice-button-"+getButtonType()+"-markanswers-none");
	}
	
	public void markAnswers(boolean mark, boolean correct) {
		String buttonTypeName = getButtonType();
		
		if (!mark){
			markAnswersPanel.setStyleName("qp-choice-button-"+buttonTypeName+"-markanswers");
			markAnswersPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_INACTIVE());
			optionPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_INACTIVE());
			optionPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_CORRECT());
			optionPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_WRONG());
			optionPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_NONE());
			labelPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_INACTIVE());
			labelPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_CORRECT());
			labelPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_WRONG());
			labelPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_NONE());
		} else {
			optionPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_INACTIVE());
			labelPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_INACTIVE());
			
			if (isSelected()){
				if( correct ) {
					markAnswersPanel.setStyleName("qp-choice-button-"+buttonTypeName+"-markanswers-correct");
					markAnswersPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_CORRECT());
					optionPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_CORRECT());
					labelPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_CORRECT());
				} else {
					markAnswersPanel.setStyleName("qp-choice-button-"+buttonTypeName+"-markanswers-wrong");
					markAnswersPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_WRONG());
					optionPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_WRONG());
					labelPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_WRONG());
				}
			} else {
				markAnswersPanel.setStyleName("qp-choice-button-"+buttonTypeName+"-markanswers-none");
				markAnswersPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_NONE());
				optionPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_NONE());
				labelPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_NONE());
			}
		}
	}
	
	public void setMouseOver(){
		button.setMouseOver(true);
	}

	public void setMouseOut(){
		button.setMouseOver(false);
	}

	@Override
	public Widget asWidget() {
		return mainPanel;
	}

	@Override
	public void bindView() {
		uiBinder.createAndBindUi(this);
	}
	
	public void showAnswers(List<String> answers) {
		// TODO Auto-generated method stub
	}

	@Override
	public void markCorrectAnswers() {
		// TODO Auto-generated method stub
	}

	@Override
	public void markWrongAnswers() {
		// TODO Auto-generated method stub
	}

	@Override
	public void unmarkCorrectAnswers() {
		// TODO Auto-generated method stub
	}

	@Override
	public void unmarkWrongAnswers() {
		// TODO Auto-generated method stub
	}

	@Override
	public void showCorrectAnswers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showCurrentAnswers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setModel(ChoiceModuleModel model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBean(SimpleChoiceBean bean) {
		// TODO Auto-generated method stub
		
	}

}
