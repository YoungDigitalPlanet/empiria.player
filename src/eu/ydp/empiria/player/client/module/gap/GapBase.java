package eu.ydp.empiria.player.client.module.gap;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.empiria.player.client.module.OneViewInteractionModuleBase;
import eu.ydp.empiria.player.client.module.ResponseSocket;
import eu.ydp.empiria.player.client.module.binding.Bindable;
import eu.ydp.empiria.player.client.module.binding.BindingGroupIdentifier;
import eu.ydp.empiria.player.client.module.binding.BindingType;
import eu.ydp.empiria.player.client.module.binding.BindingValue;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.gwtutil.client.StringUtils;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public abstract class GapBase extends OneViewInteractionModuleBase implements Bindable {

	@Inject
	protected EventsBus eventsBus;

	@Inject
	protected GapExpressionReplacer gapExpressionReplacer;

	@Inject
	protected GapBinder gapBinder;

	public static final String INLINE_HTML_NBSP = "&nbsp;";

	protected GapModulePresenter presenter;

	protected boolean markingAnswer = false;
	protected boolean showingAnswer = false;

	protected String lastValue = null;

	protected boolean locked;

	protected abstract void setPreviousAnswer();

	public interface PresenterHandler extends BlurHandler {
	}

	protected abstract ResponseSocket getResponseSocket();

	protected abstract void updateResponse(boolean userInteract, boolean isReset);

	protected abstract void setCorrectAnswer();

	private boolean isGapBinderInitialized = false;

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

	public int getLongestAnswerLength() {
		return getInitializedGapBinder().getLongestAnswerLength();
	}

	@Override
	public BindingGroupIdentifier getBindingGroupIdentifier(BindingType bindingType) {
		return getInitializedGapBinder().getBindingGroupIdentifier(bindingType);

	}

	@Override
	public BindingValue getBindingValue(BindingType bindingType) {
		return getInitializedGapBinder().getBindingValue(bindingType);
	}

	@Override
	public void markAnswers(boolean mark) {
		if (mark && !markingAnswer) {
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

	@Override
	public void showCorrectAnswers(boolean show) {
		if (show && !showingAnswer) {
			setCorrectAnswer();
		} else if (!show && showingAnswer) {
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

	protected void registerBindingContexts() {
		getInitializedGapBinder().registerBindingContexts();
	}

	protected void setBindingValues() {
		getInitializedGapBinder().setBindingValues();
	}

	protected void setMaxlengthBinding(Map<String, String> mathStyles, Element moduleElement) {
		getInitializedGapBinder().setMaxlengthBinding(mathStyles, moduleElement);
	}

	protected void setWidthBinding(Map<String, String> mathStyles, Element moduleElement) {
		getInitializedGapBinder().setWidthBinding(mathStyles, moduleElement);
	}

	public Response getModuleResponse() {
		return getResponse();
	}

	@Override
	public Element getModuleElement() {
		return super.getModuleElement();
	}

	public String getCorrectAnswer() {
		Response response = getResponse();
		String answer;
		if (response.correctAnswers.answersExists()) {
			answer = response.correctAnswers.getSingleAnswer();
		} else {
			answer = "";
		}
		return answer;
	}

	protected String getElementAttributeValue(String attrName) {
		String attrValue = XMLUtils.getAttributeAsString(getModuleElement(), attrName, null);
		return attrValue;
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
		List<Boolean> evaluateResponse = getEvaluatedResponse();

		if (evaluateResponse.size() > 0) {
			isCorrect = evaluateResponse.get(0);
		}
		return isCorrect;
	}

	protected List<Boolean> getEvaluatedResponse() {
		return getResponseSocket().evaluateResponse(getResponse());
	}

	protected boolean isResponseEmpty() {
		return StringUtils.EMPTY_STRING.equals(getCurrentResponseValue());
	}

	public int getFontSize() {
		return presenter.getFontSize();
	}

	protected GapBinder getInitializedGapBinder() {
		if (!isGapBinderInitialized) {
			gapBinder.setGapBase(this);
		}
		return gapBinder;
	}
}