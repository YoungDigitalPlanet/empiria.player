package eu.ydp.empiria.player.client.module.gap;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_MAXLENGTH;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_WIDTH_ALIGN;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_MAXLENGTH;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN;

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
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
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventHandler;
import eu.ydp.empiria.player.client.util.events.player.PlayerEventTypes;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.StringUtils;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public abstract class GapBase extends OneViewInteractionModuleBase implements Bindable {

	@Inject
	protected EventsBus eventsBus;

	public static final String INLINE_HTML_NBSP = "&nbsp;";

	protected GapModulePresenter presenter;

	protected boolean markingAnswer = false;

	protected boolean showingAnswer = false;

	protected String lastValue = null;

	protected Integer fontSize = 16;

	protected DefaultBindingGroupIdentifier widthBindingIdentifier;

	protected DefaultBindingGroupIdentifier maxlengthBindingIdentifier;

	protected BindingContext widthBindingContext;

	protected BindingContext maxlengthBindingContext;

	protected String maxLength = "";
	
	protected abstract boolean isResponseCorrect();

	protected abstract String getCurrentResponseValue();

	protected abstract void updateResponse(boolean userInteract);
	
	protected abstract void setCorrectAnswer();

	protected abstract void setPreviousAnswer();

	public abstract String getCorrectAnswer();

	public interface PresenterHandler extends BlurHandler, ChangeHandler{

	}

	protected void addPlayerEventHandlers(){
		eventsBus.addHandler(PlayerEvent.getType(PlayerEventTypes.BEFORE_FLOW), new PlayerEventHandler() {

			@Override
			public void onPlayerEvent(PlayerEvent event) {
				if(event.getType()==PlayerEventTypes.BEFORE_FLOW){
					updateResponse(false);
					presenter.removeFocusFromTextField();
				}
			}
		},new CurrentPageScope());
	}

	@Override
	public void markAnswers(boolean mark) {
		if (mark  && !markingAnswer) {
			if (isResponseEmpty()) {
				presenter.setMarkMode(GapModulePresenter.NONE);
			} else if (isResponseCorrect()) {
				presenter.setMarkMode(GapModulePresenter.CORRECT);
			} else {
				presenter.setMarkMode(GapModulePresenter.WRONG);
			}
		} else if (!mark && markingAnswer) {
			presenter.removeMarking();
		}

		markingAnswer = mark;
	}

	protected boolean isResponseEmpty(){
		return StringUtils.EMPTY_STRING.equals(getCurrentResponseValue());
	}

	@Override
	public void showCorrectAnswers(boolean show) {
		if (show  &&  !showingAnswer) {
			setCorrectAnswer();
		} else if (!show  &&  showingAnswer) {
			setPreviousAnswer();
		}

		showingAnswer = show;
	}

	@Override
	public void lock(boolean lock) {
		presenter.setViewEnabled(!lock);
	}

	@Override
	public void reset() {
		presenter.setText(StringUtils.EMPTY_STRING);
		updateResponse(false);
	}
	
	@Override
	public JSONArray getState() {
		JSONArray jsonArr = new JSONArray();
		jsonArr.set(0, new JSONString(getCurrentResponseValue()));

		return jsonArr;
	}

	@Override
	public void setState(JSONArray newState) {
		String state = "";

		if (newState != null && newState.size() > 0 && newState.get(0).isString() != null) {
			state = newState.get(0).isString().stringValue();
			lastValue = null;
		}

		presenter.setText(state);
		updateResponse(false);
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return ModuleJsSocketFactory.createSocketObject(this);
	}

	protected String getElementAttributeValue(String attrName){
		String attrValue = null;

		if(getModuleElement().hasAttribute(attrName)){
			attrValue = getModuleElement().getAttribute(attrName);
		}

		return attrValue;
	}

	public int getLongestAnswerLength() {
		int longestLength = 0;

		for (int i = 0; i < getResponse().correctAnswers.getResponseValuesCount(); i++) {
			for (String a : getResponse().correctAnswers.getResponseValue(i).getAnswers()){
				if (a.length() > longestLength) {
					longestLength = a.length();
				}
			}
		}

		return longestLength;
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

	@Override
	public BindingValue getBindingValue(BindingType bindingType) {
		BindingValue bindingValue = null;

		if (bindingType == BindingType.GAP_WIDTHS) {
			bindingValue = new GapWidthBindingValue(getLongestAnswerLength());
		}

		if (bindingType == BindingType.GAP_MAXLENGHTS) {
			bindingValue = new GapMaxlengthBindingValue(getLongestAnswerLength());
		}

		return bindingValue;
	}

	protected void setWidthBinding(Map<String, String> styles, Element moduleElement) {
		GapWidthMode widthMode = getGapWidthMode(styles);

		if (widthMode == GapWidthMode.GROUP){
			widthBindingIdentifier = getBindindIdentifier(moduleElement);
		} else if (widthMode == GapWidthMode.GAP){
			if (getResponse().groups.size() > 0) {
				widthBindingIdentifier = new DefaultBindingGroupIdentifier(getResponse().groups.keySet().iterator().next());
			} else {
				int longestAnswer = getLongestAnswerLength();
				presenter.setWidth((longestAnswer * getFontSize()), Unit.PX);
			}
		}
	}

	protected GapWidthMode getGapWidthMode(Map<String, String> styles){
		GapWidthMode widthMode = null;
		String gapWidthAlign = StringUtils.EMPTY_STRING;

		if (styles.containsKey(EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN)) {
			gapWidthAlign = styles.get(EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN).trim().toUpperCase();
		} else if (styles.containsKey(EMPIRIA_MATH_GAP_WIDTH_ALIGN)) {
			gapWidthAlign = styles.get(EMPIRIA_MATH_GAP_WIDTH_ALIGN).trim().toUpperCase();
		}

		if ( !gapWidthAlign.equals(StringUtils.EMPTY_STRING) ) {
			widthMode = GapWidthMode.valueOf(gapWidthAlign);
		}

		return widthMode;
	}

	protected DefaultBindingGroupIdentifier getBindindIdentifier(Element moduleElement) {
		DefaultBindingGroupIdentifier bindingIdentifier;

		if (moduleElement.hasAttribute(EmpiriaTagConstants.ATTR_WIDTH_BINDING_GROUP)){
			bindingIdentifier = new DefaultBindingGroupIdentifier(moduleElement.getAttribute(EmpiriaTagConstants.ATTR_WIDTH_BINDING_GROUP));
		} else {
			bindingIdentifier = new DefaultBindingGroupIdentifier("");
		}

		return bindingIdentifier;
	}

	protected void setMaxlengthBinding(Map<String, String> styles, Element moduleElement) {
		String gapMaxlength = StringUtils.EMPTY_STRING;

		if (styles.containsKey(EMPIRIA_TEXTENTRY_GAP_MAXLENGTH)) {
			gapMaxlength = styles.get(EMPIRIA_TEXTENTRY_GAP_MAXLENGTH).trim().toUpperCase();
		} else if (styles.containsKey(EMPIRIA_MATH_GAP_MAXLENGTH)) {
			gapMaxlength = styles.get(EMPIRIA_MATH_GAP_MAXLENGTH).trim().toUpperCase();
		}

		if ( !gapMaxlength.equals(StringUtils.EMPTY_STRING) ) {
			maxLength = gapMaxlength;
			
			if (maxLength.matches("ANSWER")) {
				maxlengthBindingIdentifier = getBindindIdentifier(moduleElement);
			} else {
				presenter.setMaxLength(Integer.parseInt(maxLength));
			}
		}
		else if (getModuleElement().hasAttribute("expectedLength")) {
			presenter.setMaxLength(XMLUtils.getAttributeAsInt(getModuleElement(), "expectedLength"));
		}
	}

	protected void registerBindingContexts() {
		if (widthBindingIdentifier != null) {
			widthBindingContext = BindingUtil.register(BindingType.GAP_WIDTHS, this, this);
		}

		if (maxlengthBindingIdentifier != null) {
			maxlengthBindingContext = BindingUtil.register(BindingType.GAP_MAXLENGHTS, this, this);
		}
	}

	protected void setBindingValues() {
		if (widthBindingContext != null) {
			int longestAnswerLength = ((GapWidthBindingContext) widthBindingContext).getGapWidthBindingOutcomeValue().getGapCharactersCount();
			int textBoxWidth = calculateTextBoxWidth(longestAnswerLength);
			presenter.setWidth(textBoxWidth, Unit.PX);
		}

		if (maxlengthBindingContext != null) {
			int longestAnswerLength = ((GapMaxlengthBindingContext) maxlengthBindingContext).getGapMaxlengthBindingOutcomeValue().getGapCharactersCount();
			presenter.setMaxLength(longestAnswerLength);
		}
	}

	protected int calculateTextBoxWidth(int longestAnswerLength) {
		return longestAnswerLength * getFontSize();
	}

	public int getFontSize() {
		return presenter.getFontSize();
	}

	@Override
	public void onBodyLoad() {
	}

	@Override
	public void onBodyUnload() {
	}

	@Override
	public void onSetUp() {
	}

	@Override
	public void onStart() {
	}

	@Override
	public void onClose() {
	}

}
