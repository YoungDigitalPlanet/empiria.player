package eu.ydp.empiria.player.client.module.textentry;

import java.io.Serializable;
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
import eu.ydp.empiria.player.client.util.IntegerUtils;
import eu.ydp.empiria.player.client.util.XMLUtils;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.gwtutil.client.util.UserAgentChecker;
import eu.ydp.gwtutil.client.util.UserAgentChecker.MobileUserAgent;

public class TextEntryModule extends OneViewInteractionModuleBase implements Factory<TextEntryModule>, Bindable{

	private TextBox textBox;
	private String	lastValue = null;
	private boolean showingAnswers = false;
	private Panel moduleWidget;
	private EventsBus eventsBus = PlayerGinjector.INSTANCE.getEventsBus();
	private GapWidthMode widthMode;
	private DefaultBindingGroupIdentifier widthBindingIdentifier;
	private Integer fontSize;
	private BindingContext widthBindingContext;

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.BEFORE_FLOW), new PlayerEventHandler() {
			
			@Override
			public void onPlayerEvent(PlayerEvent event) {
				if(event.getType()==PlayerEventTypes.BEFORE_FLOW){
					if(textBox!=null){
						updateResponse(false);
					}
				}
			}
		});

		textBox = new TextBox();
		if (getModuleElement().hasAttribute("expectedLength"))
			textBox.setMaxLength(XMLUtils.getAttributeAsInt(getModuleElement(), "expectedLength"));
		
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
							if (textBox.getText().length() > 0)
								textBox.setSelectionRange(0, textBox.getText().length());
						}
					});
				}
			});
		}

		Map<String, String> styles = getModuleSocket().getStyles(getModuleElement());
		if (styles.containsKey("-empiria-textentry-gap-font-size")){
			fontSize = IntegerUtils.tryParseInt(styles.get("-empiria-textentry-gap-font-size"), null);
			textBox.getElement().getStyle().setFontSize(fontSize, Unit.PX);
		}
		if (styles.containsKey("-empiria-textentry-gap-width")){
			Integer gapWidth = IntegerUtils.tryParseInt(styles.get("-empiria-textentry-gap-width"), null);
			textBox.setWidth(gapWidth + "px");
		} else if (styles.containsKey("-empiria-textentry-gap-width-align")){
			try {
				GapWidthMode wm = GapWidthMode.valueOf(styles.get("-empiria-textentry-gap-width-align").trim().toUpperCase());
				widthMode = wm;
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
		spanPrefix.setStyleName("qp-text-textentry-prefix");
		Panel spanSufix = new FlowPanel();
		spanSufix.setStyleName("qp-text-textentry-sufix");
		Panel spanContent = new FlowPanel();
		spanContent.setStyleName("qp-text-textentry-content");
		spanContent.add(textBox);

		moduleWidget = new FlowPanel();

		moduleWidget.add(spanPrefix);
		moduleWidget.add(spanContent);
		moduleWidget.add(spanSufix);
		moduleWidget.setStyleName("qp-text-textentry");
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
		if (widthBindingIdentifier != null)
			widthBindingContext = BindingUtil.register(BindingType.GAP_WIDTHS, this, this);
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
	public void lock(boolean l) {
		textBox.setEnabled(!l);
	}

	/**
	 * @see IActivity#markAnswers()
	 */
	public void markAnswers(boolean mark) {
		if (mark){
			textBox.setEnabled(false);
			if (textBox.getText().length() > 0){
				if( getModuleSocket().evaluateResponse(getResponse()).get(0) )
					moduleWidget.setStyleName("qp-text-textentry-correct");
				else
					moduleWidget.setStyleName("qp-text-textentry-wrong");
			} else {
				moduleWidget.setStyleName("qp-text-textentry-none");
			}
		} else {
			textBox.setEnabled(true);
			moduleWidget.setStyleName("qp-text-textentry");
		}
	}

	/**
	 * @see IActivity#reset()
	 */
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
	public void showCorrectAnswers(boolean show) {
		if (show  &&  !showingAnswers){
			showingAnswers = true;
			textBox.setText(getResponse().correctAnswers.getSingleAnswer());
		} else if (!show  &&  showingAnswers) {
			textBox.setText((getResponse().values.size()>0) ? getResponse().values.get(0) : "");
			showingAnswers = false;
		}
	}

	public JavaScriptObject getJsSocket(){
		return ModuleJsSocketFactory.createSocketObject(this);
	}

  /**
   * @see IStateful#getState()
   */
  public JSONArray getState() {
	  JSONArray jsonArr = new JSONArray();

	  String stateString = "";

	  if (getResponse().values.size() > 0)
		  stateString = getResponse().values.get(0);

	  jsonArr.set(0, new JSONString(stateString));

	  return jsonArr;
  }

  /**
   * @see IStateful#setState(Serializable)
   */
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
		if (showingAnswers)
			return;

		if (getResponse() != null){
			if(lastValue != null)
				getResponse().remove(lastValue);
	
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
			if (a.length() > longestLength)
				longestLength = a.length();
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
