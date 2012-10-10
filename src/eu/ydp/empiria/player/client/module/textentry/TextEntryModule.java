package eu.ydp.empiria.player.client.module.textentry;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_FONT_SIZE;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_WIDTH;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_MAXLENGTH;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.IActivity;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.OneViewInteractionModuleBase;
import eu.ydp.empiria.player.client.module.binding.Bindable;
import eu.ydp.empiria.player.client.module.binding.BindingContext;
import eu.ydp.empiria.player.client.module.binding.BindingGroupIdentifier;
import eu.ydp.empiria.player.client.module.binding.BindingType;
import eu.ydp.empiria.player.client.module.binding.BindingUtil;
import eu.ydp.empiria.player.client.module.binding.BindingValue;
import eu.ydp.empiria.player.client.module.binding.DefaultBindingGroupIdentifier;
import eu.ydp.empiria.player.client.module.binding.gapmaxlength.GapMaxlengthBindingContext;
import eu.ydp.empiria.player.client.module.binding.gapmaxlength.GapMaxlengthBindingValue;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthBindingContext;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthBindingValue;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthMode;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.NumberUtils;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class TextEntryModule extends OneViewInteractionModuleBase implements Factory<TextEntryModule>, Bindable{
	
	interface TextEntryModuleUiBinder extends UiBinder<Widget, TextEntryModule>{};
	
	private final TextEntryModuleUiBinder uiBinder = GWT.create(TextEntryModuleUiBinder.class);
	
	@UiField
	protected TextBox textBox;
	
	@UiField
	protected Panel moduleWidget;

	private String lastValue = null;

	private boolean showingAnswers = false;
	
	private final EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
	
	DefaultBindingGroupIdentifier widthBindingIdentifier;
	
	private DefaultBindingGroupIdentifier maxlengthBindingIdentifier;
	
	private Integer fontSize = 16;
	
	private BindingContext widthBindingContext;
	
	private BindingContext maxlengthBindingContext;
	
	protected StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();
	
	String maxLength = "";
	
	@Override
	public void installViews(List<HasWidgets> placeholders) {
		uiBinder.createAndBindUi(this);
		
		addPlayerEventHandlers(textBox);
		addTextBoxHandlers(textBox);
		setDimensions(textBox, getModuleElement());

		installViewPanel(placeholders.get(0), moduleWidget);
		createFeedbacks(moduleWidget, getModuleElement(), getModuleSocket(), getInteractionEventsListener());
	}

	// ------------------------ INTERFACES ------------------------
	
	@Override
	public void onBodyLoad() {
		//eu.ydp.empiria.player.client.module.ILifeCycle.onBodyLoad
	}

	@Override
	public void onBodyUnload() {
		//eu.ydp.empiria.player.client.module.ILifeCycle.onBodyUnload
	}

	@Override
	public void onSetUp() {
		updateResponse(false);
		
		if (widthBindingIdentifier != null) {
			widthBindingContext = BindingUtil.register(BindingType.GAP_WIDTHS, this, this);
		}
		
		if (maxlengthBindingIdentifier != null) {
			maxlengthBindingContext = BindingUtil.register(BindingType.GAP_MAXLENGHTS, this, this);
		}
	}

	@Override
	public void onStart() {
		if (widthBindingContext != null) {
			int longestAnswerLength = ((GapWidthBindingContext) widthBindingContext).getGapWidthBindingOutcomeValue().getGapCharactersCount();
			int textBoxWidth = longestAnswerLength * fontSize;
			textBox.setWidth(textBoxWidth + "px");
		}
		
		if (maxlengthBindingContext != null) {
			int longestAnswerLength = ((GapMaxlengthBindingContext) maxlengthBindingContext).getGapMaxlengthBindingOutcomeValue().getGapCharactersCount();
			textBox.setMaxLength(longestAnswerLength);
		}
	}

	@Override
	public void onClose() {
		//eu.ydp.empiria.player.client.module.ILifeCycle.onClose
	}

	@Override
	public void lock(boolean lock) {
		textBox.setEnabled(!lock);
	}

	/**
	 * @see IActivity#markAnswers()
	 */
	@Override
	public void markAnswers(boolean mark) {
		if (mark){
			textBox.setEnabled(false);
			if (textBox.getText().length() > 0){
				if( getModuleSocket().evaluateResponse(getResponse()).get(0) ) {
					moduleWidget.setStyleName(styleNames.QP_TEXT_TEXTENTRY_CORRECT());
				} else {
					moduleWidget.setStyleName(styleNames.QP_TEXT_TEXTENTRY_WRONG());
				}
			} else {
				moduleWidget.setStyleName(styleNames.QP_TEXT_TEXTENTRY_NONE());
			}
		} else { 
			textBox.setEnabled(true);
			moduleWidget.setStyleName(styleNames.QP_TEXT_TEXTENTRY());
		}
	}

	/**
	 * @see IActivity#reset()
	 */
	@Override
	public void reset() {
		markAnswers(false);
		showCorrectAnswers(false);
		lock(false);
		textBox.setText("");
		updateResponse(false);
	}

	/**
	 * @see IActivity#showCorrectAnswers()
	 */
	@Override
	public void showCorrectAnswers(boolean show) {
		if (show  &&  !showingAnswers){
			showingAnswers = true;
			textBox.setText(getResponse().correctAnswers.getSingleAnswer());
		} else if (!show  &&  showingAnswers) {
			textBox.setText((getResponse().values.size()>0) ? getResponse().values.get(0) : "");
			showingAnswers = false;
		}
	}

	@Override
	public JavaScriptObject getJsSocket(){
		return ModuleJsSocketFactory.createSocketObject(this);
	}

	/**
	 * @see IStateful#getState()
	 */
	@Override
	public JSONArray getState() {
		JSONArray jsonArr = new JSONArray();

		String stateString = "";

		if (getResponse().values.size() > 0) {
			stateString = getResponse().values.get(0);
		}

		jsonArr.set(0, new JSONString(stateString));

		return jsonArr;
	}

	/**
	 * @see IStateful#setState(Serializable)
	 */
	@Override
	public void setState(JSONArray newState) {
		String state = "";

		if (newState != null && newState.size() > 0 && newState.get(0).isString() != null) {
			state = newState.get(0).isString().stringValue();
			lastValue = null;
		}

		textBox.setText(state);
		updateResponse(false);
	}

	private void updateResponse(boolean userInteract){
		if (showingAnswers) {
			return;
		}
		
		if (getResponse() != null){
			if(lastValue != null) {
				getResponse().remove(lastValue);
			}

			lastValue = textBox.getText();
			getResponse().add(lastValue);
			fireStateChanged(userInteract);
		}
	}

	protected void onTextBoxChange(){
		updateResponse(true);
	}

	@Override
	public TextEntryModule getNewInstance() {
		return new TextEntryModule();
	}
	@Override
	public BindingValue getBindingValue(BindingType bindingType) {
		BindingValue bindingValue = null;
		
		if (bindingType == BindingType.GAP_WIDTHS) {
			int longestLength = getLongestAnswerLength();
			bindingValue = new GapWidthBindingValue(longestLength);
		}
		
		if (bindingType == BindingType.GAP_MAXLENGHTS) {
			if (maxLength.matches("ANSWER")) {
				bindingValue = new GapMaxlengthBindingValue(getLongestAnswerLength());
			} else {
				bindingValue = new GapMaxlengthBindingValue(Integer.parseInt(maxLength));
			}
		}
			
		return bindingValue;
	}
	
	@Override
	public BindingGroupIdentifier getBindingGroupIdentifier(BindingType bindingType) {
		BindingGroupIdentifier groupIndentifier = null;
		
		if (bindingType == BindingType.GAP_WIDTHS){
			groupIndentifier = widthBindingIdentifier;
		}
		
		if (bindingType == BindingType.GAP_MAXLENGHTS) {
			groupIndentifier = maxlengthBindingIdentifier;
		}
		
		return groupIndentifier;
	}

	private int getLongestAnswerLength(){
		int longestLength = 0;
		for (String a : getResponse().correctAnswers.getResponseValue(0).getAnswers()){
			if (a.length() > longestLength) {
				longestLength = a.length();
			}
		}
		return longestLength;
	}
	
	private void createFeedbacks(Panel parentPanel, Element moduleElement, ModuleSocket moduleSocket, InteractionEventsListener eventListener){
		NodeList inlineFeedbackNodes = moduleElement.getElementsByTagName("feedbackInline");
		
		for (int f = 0 ; f < inlineFeedbackNodes.getLength() ; f ++){
			InlineFeedback inlineFeedback = createInlineFeedback(parentPanel, inlineFeedbackNodes.item(f), moduleSocket, eventListener);
			moduleSocket.addInlineFeedback(inlineFeedback);
		}
	}
	
	private InlineFeedback createInlineFeedback(Panel parent, Node feedbackNode, ModuleSocket moduleSocket, InteractionEventsListener eventListener){
		return new InlineFeedback(parent, feedbackNode, moduleSocket, eventListener);
	}
	
	private void addPlayerEventHandlers(final TextBox textBox){
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.BEFORE_FLOW), new PlayerEventHandler() {

			@Override
			public void onPlayerEvent(PlayerEvent event) {
				if(event.getType()==PlayerEventTypes.BEFORE_FLOW && textBox != null){
					updateResponse(false);
					textBox.getElement().blur();
				}
			}
		},new CurrentPageScope());
	}
	
	private void addTextBoxHandlers(final TextBox textBox){
		textBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				onTextBoxChange();
			}
		});

		if (UserAgentChecker.getMobileUserAgent() != MobileUserAgent.UNKNOWN){
			textBox.addBlurHandler(new BlurHandler() {
				@Override
				public void onBlur(BlurEvent event) {
					updateResponse(true);
				}
			});

			textBox.addFocusHandler(new FocusHandler() {

				@Override
				public void onFocus(FocusEvent event) {
					Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {

						@Override
						public void execute() {
							if (textBox.getText().length() > 0) {
								textBox.setSelectionRange(0, textBox.getText().length());
							}
						}
					});
				}
			});
		}
	}
	
	private void installViewPanel(HasWidgets placeholder, Panel viewPanel){
		applyIdAndClassToView(viewPanel);
		placeholder.add(viewPanel);
	}
	
	void setDimensions(TextBox textBox, Element moduleElement){
		Map<String, String> styles = getStyles(moduleElement);
		
		if (styles.containsKey(EMPIRIA_TEXTENTRY_GAP_MAXLENGTH)) {
			maxlengthBindingIdentifier = new DefaultBindingGroupIdentifier("");
			maxLength = styles.get(EMPIRIA_TEXTENTRY_GAP_MAXLENGTH).trim().toUpperCase();
		} else if (moduleElement.hasAttribute("expectedLength")) {
			textBox.setMaxLength(XMLUtils.getAttributeAsInt(moduleElement, "expectedLength"));
		}
		
		if (styles.containsKey(EMPIRIA_TEXTENTRY_GAP_FONT_SIZE)){
			fontSize = NumberUtils.tryParseInt(styles.get(EMPIRIA_TEXTENTRY_GAP_FONT_SIZE), null);
		}
		
		setFontSize(fontSize, Unit.PX);
				
		if (styles.containsKey(EMPIRIA_TEXTENTRY_GAP_WIDTH)) {
			Integer gapWidth = NumberUtils.tryParseInt(styles.get(EMPIRIA_TEXTENTRY_GAP_WIDTH), null);
			textBox.setWidth(gapWidth + "px");
		} else {
			GapWidthMode widthMode = getGapWidthMode(styles);
			
			if (widthMode == GapWidthMode.GROUP) {
				if (moduleElement.hasAttribute("widthBindingGroup")) {
					widthBindingIdentifier = new DefaultBindingGroupIdentifier(moduleElement.getAttribute("widthBindingGroup"));
				} else {
					widthBindingIdentifier = new DefaultBindingGroupIdentifier("");
				}
			} else if (widthMode == GapWidthMode.GAP) {
				if (getResponse().groups.size() > 0) {
					widthBindingIdentifier = new DefaultBindingGroupIdentifier(getResponse().groups.keySet().iterator().next());
				} else {
					int longestAnswer = getLongestAnswerLength();
					textBox.setWidth((longestAnswer * fontSize) + "px");
				}
			}
		}
	}

	/**
	 * @param moduleElement
	 * @return
	 */
	Map<String, String> getStyles(Element moduleElement) {
		return getModuleSocket().getStyles(moduleElement);
	}
	
	void setFontSize(Integer value, Unit unit) {
		textBox.getElement().getStyle().setFontSize(value, unit);
	}

	private GapWidthMode getGapWidthMode(Map<String, String> styles){
		GapWidthMode widthMode = null;
		
		if (styles.containsKey(EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN)){
			try {
				widthMode = GapWidthMode.valueOf(styles.get(EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN).trim().toUpperCase());
			} catch (Exception e) {	} // NOPMD by MKaldonek on 27.09.12 08:23
		}
		
		return widthMode;
	}
	
}
