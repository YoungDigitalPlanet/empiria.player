package eu.ydp.empiria.player.client.module.gap;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_MAXLENGTH;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_WIDTH_ALIGN;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_MAXLENGTH;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN;

import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.empiria.player.client.module.OneViewInteractionModuleBase;
import eu.ydp.empiria.player.client.module.ResponseSocket;
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
import eu.ydp.gwtutil.client.StringUtils;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public abstract class GapBase extends OneViewInteractionModuleBase implements Bindable {

	@Inject
	protected EventsBus eventsBus;
	
	@Inject
	protected GapExpressionReplacer gapExpressionReplacer;
	
	public static final String INLINE_HTML_NBSP = "&nbsp;";

	public interface PresenterHandler extends BlurHandler, ChangeHandler {}
	
	public String maxLength = StringUtils.EMPTY_STRING;
	
	protected abstract void setPreviousAnswer();
	protected abstract ResponseSocket getResponseSocket();
	
	protected GapModulePresenter presenter;
	protected String lastValue = null;
	protected boolean markingAnswer = false;
	protected boolean showingAnswer = false;
	protected boolean locked;
	
	@Override
	public void onBodyLoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyUnload() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onSetUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
	}
	protected DefaultBindingGroupIdentifier widthBindingIdentifier;
	protected DefaultBindingGroupIdentifier maxlengthBindingIdentifier;
	
	protected BindingContext widthBindingContext;
	protected BindingContext maxlengthBindingContext;

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

	protected void registerBindingContexts() {
		if (widthBindingIdentifier != null) {
			widthBindingContext = BindingUtil.register(BindingType.GAP_WIDTHS, this, this);
		}

		if (maxlengthBindingIdentifier != null) {
			maxlengthBindingContext = BindingUtil.register(BindingType.GAP_MAXLENGHTS, this, this);
		}
	}
	
	protected void setWidthBinding(Map<String, String> styles, Element moduleElement) {
		GapWidthMode widthMode = getGapWidthMode(styles);

		if (widthMode == GapWidthMode.GROUP){
			widthBindingIdentifier = getBindindIdentifier(moduleElement);
		} else if (widthMode == GapWidthMode.GAP){
			if (getCurrentResponse().groups.size() > 0) {
				widthBindingIdentifier = new DefaultBindingGroupIdentifier(getCurrentResponse().groups.get(0));
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
		else if (getCurrentModuleElement().hasAttribute("expectedLength")) {
			presenter.setMaxLength(XMLUtils.getAttributeAsInt(getCurrentModuleElement(), "expectedLength"));
		}
	}

	protected DefaultBindingGroupIdentifier getBindindIdentifier(Element moduleElement) {
		DefaultBindingGroupIdentifier bindingIdentifier;

		if (moduleElement.hasAttribute(EmpiriaTagConstants.ATTR_WIDTH_BINDING_GROUP)){
			bindingIdentifier = new DefaultBindingGroupIdentifier(moduleElement.getAttribute(EmpiriaTagConstants.ATTR_WIDTH_BINDING_GROUP));
		} else {
			bindingIdentifier = new DefaultBindingGroupIdentifier(StringUtils.EMPTY_STRING);
		}

		return bindingIdentifier;
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
		locked = lock;
		presenter.setViewEnabled(!lock);
	}

	@Override
	public void reset() {
		if (!Strings.isNullOrEmpty(presenter.getText())) {
			presenter.setText(StringUtils.EMPTY_STRING);
			updateResponse(false, true);
		}
	}

	@Override
	public JSONArray getState() {
		JSONArray jsonArr = new JSONArray();
		jsonArr.set(0, new JSONString(getCurrentResponseValue()));

		return jsonArr;
	}

	@Override
	public void setState(JSONArray newState) {
		String state = StringUtils.EMPTY_STRING;

		if (newState != null && newState.size() > 0 && newState.get(0).isString() != null) {
			state = newState.get(0).isString().stringValue();
			lastValue = null;
		}

		presenter.setText(state);
		updateResponse(false, false);
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return ModuleJsSocketFactory.createSocketObject(this);
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
	
	protected int getFontSize() {
		return presenter.getFontSize();
	}

	public String getCorrectAnswer() {
		Response response = getResponse();
		String answer;
		if(response.correctAnswers.answersExists()){
			answer = response.correctAnswers.getSingleAnswer();
		}else{
			answer = ""; 
		}
		return answer;
	}
	
	public int getLongestAnswerLength() {
		int longestLength = 0;
		
		for (int i = 0; i < getResponse().correctAnswers.getAnswersCount(); i++) {
			for (String a : getResponse().correctAnswers.getResponseValue(i).getAnswers()){
				if (a.length() > longestLength) {
					longestLength = a.length();
				}
			}
		}
		
		return longestLength;
	}
	
	public Element getCurrentModuleElement() {
		return getModuleElement();
	}
	
	protected Response getCurrentResponse(){
		return getResponse();
	}

	protected void updateResponse(boolean userInteract, boolean isReset) {
		if (showingAnswer) {
			return;
		}
		if (getResponse() != null) {
			if (lastValue != null) {
				getResponse().remove(lastValue);
			}

			lastValue = presenter.getText();
			getResponse().add(lastValue);
			fireStateChanged(userInteract, isReset);
		}
	}
	
	protected void setCorrectAnswer() {
		String correctAnswer = getCorrectAnswer();
		String replaced = gapExpressionReplacer.ensureReplacement(correctAnswer);
		presenter.setText(replaced);
	}
	
	protected String getCurrentResponseValue() {
		String answer = StringUtils.EMPTY_STRING;
		
		if (getResponse().values.size() > 0) {
			answer = getResponse().values.get(0);
		}
		
		return answer;
	}
	
	protected boolean isResponseCorrect() {
		boolean isCorrect = false;
		List<Boolean> evaluateResponse = getResponseSocket().evaluateResponse(getResponse());
		
		if (evaluateResponse.size() > 0) {
			isCorrect = evaluateResponse.get(0);				
		}
		return isCorrect;
	}

	protected boolean isResponseEmpty(){
		return StringUtils.EMPTY_STRING.equals(getCurrentResponseValue());
	}

	protected String getElementAttributeValue(String attrName){
		String attrValue = null;

		if(getModuleElement().hasAttribute(attrName)){
			attrValue = getModuleElement().getAttribute(attrName);
		}

		return attrValue;
	}
}