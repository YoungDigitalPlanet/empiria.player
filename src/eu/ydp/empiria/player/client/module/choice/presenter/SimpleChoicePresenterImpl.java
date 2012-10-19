package eu.ydp.empiria.player.client.module.choice.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.choice.ChoiceModuleListener;
import eu.ydp.empiria.player.client.module.choice.structure.SimpleChoiceBean;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceButtonBase;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceGroupController;
import eu.ydp.empiria.player.client.module.components.choicebutton.MultiChoiceButton;
import eu.ydp.empiria.player.client.module.components.choicebutton.SingleChoiceButton;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class SimpleChoicePresenterImpl implements SimpleChoicePresenter{

	private static final String TYPE_SINGLE = "single";

	private static final String TYPE_MULTI = "multi";

	private static final String STYLE_CHOICE_SINGLE = "choice-single";

	private static final String STYLE_CHOICE_MULTI = "choice-multi";

	private static SimpleChoiceViewUiBinder uiBinder = GWT.create(SimpleChoiceViewUiBinder.class);
	
	@UiTemplate("SimpleChoiceView.ui.xml")
	interface SimpleChoiceViewUiBinder extends UiBinder<Widget, SimpleChoicePresenterImpl> {
	}
	
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
	
	private InlineBodyGeneratorSocket bodyGenerator;

	private ChoiceButtonBase button;
	
	private ChoiceModuleListener listener;

	public SimpleChoicePresenterImpl(SimpleChoiceBean option, ChoiceGroupController controller, InlineBodyGeneratorSocket bodyGenerator) {
		bindView();
		this.bodyGenerator = bodyGenerator;
		
		installChildren(option, controller);
	}
	
	private void installChildren(SimpleChoiceBean choiceOption, ChoiceGroupController controller){
		isMulti = choiceOption.isMulti();
		
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
		listener.onChoiceClick(this);
	}
	
	private String getButtonType(){
		return isMulti ? TYPE_MULTI : TYPE_SINGLE;
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
	
	public void setMouseOver(){
		button.setMouseOver(true);
	}

	public void setMouseOut(){
		button.setMouseOver(false);
	}
	
	public Widget asWidget() {
		return mainPanel;
	}
	
	public void bindView() {
		uiBinder.createAndBindUi(this);
	}
	
	public void markCorrectAnswers() {
		removeInactiveStyle();
		markAnswersPanel.setStyleName("qp-choice-button-"+getButtonType()+"-markanswers-correct");
		markAnswersPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_CORRECT());
		optionPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_CORRECT());
		labelPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_CORRECT());
	}
	
	public void markWrongAnswers() {
		removeInactiveStyle();
		markAnswersPanel.setStyleName("qp-choice-button-"+getButtonType()+"-markanswers-wrong");
		markAnswersPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_WRONG());
		optionPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_WRONG());
		labelPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_WRONG());
	}
	
	private void removeInactiveStyle(){
		optionPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_INACTIVE());
		labelPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_INACTIVE());
	}
	
	public void unmarkCorrectAnswers() {
		addInactiveStyle();
		optionPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_CORRECT());
		labelPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_CORRECT());
	}

	public void unmarkWrongAnswers() {
		addInactiveStyle();
		optionPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_WRONG());
		labelPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_WRONG());
	}
	
	private void addInactiveStyle(){
		markAnswersPanel.setStyleName("qp-choice-button-"+getButtonType()+"-markanswers");
		markAnswersPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_INACTIVE());
		optionPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_INACTIVE());
		labelPanel.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_INACTIVE());
		labelPanel.removeStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_NONE());
	}

	public void setListener(ChoiceModuleListener listener) {
		this.listener = listener;
	}

}
