package eu.ydp.empiria.player.client.module.textentry;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_FONT_SIZE;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_WIDTH;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.IActivity;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.empiria.player.client.module.OneViewInteractionModuleBase;
import eu.ydp.empiria.player.client.module.binding.Bindable;
import eu.ydp.empiria.player.client.module.binding.BindingContext;
import eu.ydp.empiria.player.client.module.binding.BindingGroupIdentifier;
import eu.ydp.empiria.player.client.module.binding.BindingType;
import eu.ydp.empiria.player.client.module.binding.BindingUtil;
import eu.ydp.empiria.player.client.module.binding.BindingValue;
import eu.ydp.empiria.player.client.module.binding.DefaultBindingGroupIdentifier;
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


	private TextBox textBox;
	private String	lastValue = null;
	private boolean showingAnswers = false;
	private Panel moduleWidget;
	private final EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
	private GapWidthMode widthMode;
	private DefaultBindingGroupIdentifier widthBindingIdentifier;
	private Integer fontSize;
	private BindingContext widthBindingContext;
	protected StyleNameConstants styleNames = PlayerGinjector.INSTANCE.getStyleNameConstants();
	@Override
	public void installViews(List<HasWidgets> placeholders) {

		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.BEFORE_FLOW), new PlayerEventHandler() {

			@Override
			public void onPlayerEvent(PlayerEvent event) {
				if(event.getType()==PlayerEventTypes.BEFORE_FLOW){
					if(textBox!=null){
						updateResponse(false);
						textBox.getElement().blur();
					}
				}
			}
		},new CurrentPageScope());

		textBox = new TextBox();
		if (getModuleElement().hasAttribute("expectedLength")) {
			textBox.setMaxLength(XMLUtils.getAttributeAsInt(getModuleElement(), "expectedLength"));
		}

		textBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				onTextBoxChange();
			}
		});
		if (UserAgentChecker.getMobileUserAgent() != MobileUserAgent.UNKNOWN){
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

		Map<String, String> styles = getModuleSocket().getStyles(getModuleElement());
		if (styles.containsKey(EMPIRIA_TEXTENTRY_GAP_FONT_SIZE)){
			fontSize = NumberUtils.tryParseInt(styles.get(EMPIRIA_TEXTENTRY_GAP_FONT_SIZE), null);
			textBox.getElement().getStyle().setFontSize(fontSize, Unit.PX);
		}
		if (styles.containsKey(EMPIRIA_TEXTENTRY_GAP_WIDTH)){
			Integer gapWidth = NumberUtils.tryParseInt(styles.get(EMPIRIA_TEXTENTRY_GAP_WIDTH), null);
			textBox.setWidth(gapWidth + "px");
		} else if (styles.containsKey(EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN)){
			try {
				GapWidthMode widthm = GapWidthMode.valueOf(styles.get(EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN).trim().toUpperCase());
				widthMode = widthm;
			} catch (Exception e) {	}
			if (widthMode == GapWidthMode.GROUP){
				if (getModuleElement().hasAttribute("widthBindingGroup")){
					widthBindingIdentifier = new DefaultBindingGroupIdentifier(getModuleElement().getAttribute("widthBindingGroup"));
				} else {
					widthBindingIdentifier = new DefaultBindingGroupIdentifier("");
				}
			}
		}
		if (widthMode == GapWidthMode.GAP){
			if (getResponse().groups.size() > 0) {
				widthBindingIdentifier = new DefaultBindingGroupIdentifier(getResponse().groups.keySet().iterator().next());
			} else if (fontSize != null) {
				int longestAnswer = getLongestAnswerLength();
				textBox.setWidth((longestAnswer * fontSize) + "px");
			}

		}

		Panel spanPrefix = new FlowPanel();
		spanPrefix.setStyleName(styleNames.QP_TEXT_TEXTENTRY_PREFIX());
		Panel spanSufix = new FlowPanel();
		spanSufix.setStyleName(styleNames.QP_TEXT_TEXTENTRY_SUFIX());
		Panel spanContent = new FlowPanel();
		spanContent.setStyleName(styleNames.QP_TEXT_TEXTENTRY_CONTENT());
		spanContent.add(textBox);

		moduleWidget = new FlowPanel();

		moduleWidget.add(spanPrefix);
		moduleWidget.add(spanContent);
		moduleWidget.add(spanSufix);
		moduleWidget.setStyleName(styleNames.QP_TEXT_TEXTENTRY());
		applyIdAndClassToView(moduleWidget);

		placeholders.get(0).add(moduleWidget);

		NodeList inlineFeedbackNodes = getModuleElement().getElementsByTagName("feedbackInline");
		for (int f = 0 ; f < inlineFeedbackNodes.getLength() ; f ++){
			getModuleSocket().addInlineFeedback(new InlineFeedback(moduleWidget, inlineFeedbackNodes.item(f), getModuleSocket(), getInteractionEventsListener()));
		}

	}

	// ------------------------ INTERFACES ------------------------


	@Override
	public void onBodyLoad() {
	}

	@Override
	public void onBodyUnload() {

	}

	@Override
	public void onSetUp() {
		updateResponse(false);
		if (widthBindingIdentifier != null) {
			widthBindingContext = BindingUtil.register(BindingType.GAP_WIDTHS, this, this);
		}
	}

	@Override
	public void onStart() {
		if (widthBindingContext != null  &&  fontSize != null){
			int longestAnswerLength = ((GapWidthBindingContext)widthBindingContext).getGapWidthBindingOutcomeValue().getGapCharactersCount();
			int textBoxWidth = longestAnswerLength * fontSize;
			textBox.setWidth(textBoxWidth + "px");
		}
	}

	@Override
	public void onClose() {
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
		if (bindingType == BindingType.GAP_WIDTHS){
			int longestLength = getLongestAnswerLength();
			return new GapWidthBindingValue(longestLength);
		}
		return null;
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

	@Override
	public BindingGroupIdentifier getBindingGroupIdentifier(BindingType bindingType) {
		if (bindingType == BindingType.GAP_WIDTHS){
			return widthBindingIdentifier;
		}
		return null;
	}
}
