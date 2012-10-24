package eu.ydp.empiria.player.client.module.gap;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_MAXLENGTH;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_MAXLENGTH;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_MATH_GAP_WIDTH_ALIGN;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.components.ExListBox;
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
import eu.ydp.empiria.player.client.module.binding.gapmaxlength.GapMaxlengthBindingContext;
import eu.ydp.empiria.player.client.module.binding.gapmaxlength.GapMaxlengthBindingValue;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthBindingContext;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthBindingValue;
import eu.ydp.empiria.player.client.module.binding.gapwidth.GapWidthMode;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public abstract class GapBase extends OneViewInteractionModuleBase implements Bindable {
	
	protected final static String EMPTY_STRING = "";
	
	public static final String INLINE_HTML_NBSP = "&nbsp;";
	
	protected Presenter presenter;
	
	protected boolean markingAnswer = false;
	
	protected boolean showingAnswer = false;
	
	protected String lastValue = null;
	
	protected Integer fontSize = 16;
	
	protected DefaultBindingGroupIdentifier widthBindingIdentifier;
	
	protected DefaultBindingGroupIdentifier maxlengthBindingIdentifier;
	
	protected BindingContext widthBindingContext;
	
	protected BindingContext maxlengthBindingContext;
	
	protected String maxLength = "";
	
	public interface Presenter {
		
		public static final String WRONG = "wrong";
		
		public static final String CORRECT = "correct";
		
		public static final String NONE = "none";
		
		void setWidth(double value, Unit unit);
		
		int getOffsetWidth();
		
		void setHeight(double value, Unit unit);
		
		int getOffsetHeight();
		
		void setMaxLength(int length);
		
		void setFontSize(double value, Unit unit);
		
		int getFontSize();
		
		void setText(String text);
		
		String getText();
		
		HasWidgets getContainer();
		
		void installViewInContainer(HasWidgets container);

		void setViewEnabled(boolean enabled);
		
		void setMarkMode(String mode);
		
		void removeMarking();
		
		void addPresenterHandler(PresenterHandler handler);
		
		void removeFocusFromTextField();
		
		public ExListBox getListBox();
	}
	
	public interface PresenterHandler extends BlurHandler, ChangeHandler{
		
	}
	
	@Override
	public void markAnswers(boolean mark) {
		if(mark  && !markingAnswer){
			String markMode;
			if(isResponseEmpty()){
				markMode = Presenter.NONE;
			}else if(isResponseCorrect()){
				markMode = Presenter.CORRECT;
			}else{
				markMode = Presenter.WRONG;
			}
			presenter.setMarkMode(markMode);
		}else if (!mark && markingAnswer){
			presenter.removeMarking();
		}
		
		markingAnswer = mark;
	}
	
	protected boolean isResponseEmpty(){
		return EMPTY_STRING.equals(getCurrentResponseValue());
	}
	
	protected abstract boolean isResponseCorrect();
	
	protected abstract String getCurrentResponseValue();

	@Override
	public void showCorrectAnswers(boolean show) {
		if (show  &&  !showingAnswer) {
			setCorrectAnswer();
		} else if (!show  &&  showingAnswer) {
			setPreviousAnswer();
		}
		
		showingAnswer = show;
	}
	
	protected abstract void setCorrectAnswer();
	
	protected abstract void setPreviousAnswer();
	
	public abstract String getCorrectAnswer();
	
	protected abstract void setPresenter();

	@Override
	public void lock(boolean lock) {
		presenter.setViewEnabled(!lock);
	}

	@Override
	public void reset() {
		presenter.setText(EMPTY_STRING);
		updateResponse();
	}
	
	protected abstract void updateResponse();

	/**
	 * @see IStateful#getState()
	 */
	@Override
	public JSONArray getState() {
		JSONArray jsonArr = new JSONArray();
		jsonArr.set(0, new JSONString(getCurrentResponseValue()));

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

		presenter.setText(state);
		updateResponse();
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
			if (maxLength.matches("ANSWER")) {
				bindingValue = new GapMaxlengthBindingValue(getLongestAnswerLength());
			} else {
				bindingValue = new GapMaxlengthBindingValue(Integer.parseInt(maxLength));
			}
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
		String gapWidthAlign = EMPTY_STRING;
		
		if (styles.containsKey(EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN)) {
			gapWidthAlign = styles.get(EMPIRIA_TEXTENTRY_GAP_WIDTH_ALIGN).trim().toUpperCase();
		} else if (styles.containsKey(EMPIRIA_MATH_GAP_WIDTH_ALIGN)) {
			gapWidthAlign = styles.get(EMPIRIA_MATH_GAP_WIDTH_ALIGN).trim().toUpperCase();
		}
		
		if ( !gapWidthAlign.equals(EMPTY_STRING) ) {
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
		String gapMaxlength = EMPTY_STRING;
		
		if (styles.containsKey(EMPIRIA_TEXTENTRY_GAP_MAXLENGTH)) {
			gapMaxlength = styles.get(EMPIRIA_TEXTENTRY_GAP_MAXLENGTH).trim().toUpperCase();
		} else if (styles.containsKey(EMPIRIA_MATH_GAP_MAXLENGTH)) {
			gapMaxlength = styles.get(EMPIRIA_MATH_GAP_MAXLENGTH).trim().toUpperCase();
		}
		
		if ( !gapMaxlength.equals(EMPTY_STRING) ) {
			maxlengthBindingIdentifier = getBindindIdentifier(moduleElement);
			maxLength = gapMaxlength;
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
	public void installViews(List<HasWidgets> placeholders) {
	}

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
	}
	
	@Override
	public void onStart() {
	}
	
	@Override
	public void onClose() {
		//eu.ydp.empiria.player.client.module.ILifeCycle.onClose
	}

}
