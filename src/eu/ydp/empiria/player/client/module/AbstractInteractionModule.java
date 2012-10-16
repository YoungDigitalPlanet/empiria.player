package eu.ydp.empiria.player.client.module;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;

public abstract class AbstractInteractionModule<T, H> extends OneViewInteractionModuleBase implements Factory<T> {

	protected boolean locked = false;

	protected boolean showingAnswers = false;

	protected boolean markingAnswers = false;

	private ActivityPresenter<H> presenter;

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		presenter = createPresenter();
		presenter.bindView();

		applyIdAndClassToView(getView());
		initalizeModule();
		initializeAndInstallFeedbacks();
		placeholders.get(0).add(getView());
	}

	protected void initializeAndInstallFeedbacks() {
		NodeList childNodes = getModuleElement().getElementsByTagName(EmpiriaTagConstants.NAME_FEEDBACK_INLINE);
		for (int f = 0; f < childNodes.getLength(); f++) {
			InlineFeedback feedback = createInlineFeedback(getView(), childNodes.item(f));
			getModuleSocket().addInlineFeedback(feedback);
		}
	}

	protected InlineFeedback createInlineFeedback(Widget mountingPoint, Node feedbackNode) {
		return new InlineFeedback(mountingPoint, feedbackNode, getModuleSocket(), getInteractionEventsListener());
	}

	protected abstract ActivityPresenter<H> createPresenter();

	protected abstract void initalizeModule();

	protected Widget getView() {
		return presenter.asWidget();
	}

	protected void generateInlineBody(Node node, Widget parentWidget) {
		getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(node, parentWidget.getElement());
	}

	protected List<H> getCorrectAnswers() {
		return parseResponse(getResponse().correctAnswers.getAllAnswers());
	}

	protected List<H> getCurrentAnswers() {
		return parseResponse(getResponse().values);
	}

	/**
	 * Konwertuje odpowiedz z postaci String do typu H. Domyslnie przepakowuje kolekcje dla bardziej zlozonych typow
	 * powinna powstac implementacja na poziomie modulu i przeciazyc ta.
	 *
	 * @param values
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List<H> parseResponse(Collection<String> values) {
		return new ArrayList(values);
	}

	@Override
	public void markAnswers(boolean mark) {
		markingAnswers = mark;
		presenter.markAnswers(getCorrectAnswers(), mark);
	}

	@Override
	public void showCorrectAnswers(boolean show) {
		List<H> answers = Collections.emptyList();

		if (show && !showingAnswers) {
			answers = getCorrectAnswers();
			showingAnswers = true;
		} else if (!show && showingAnswers) {
			answers = getCurrentAnswers();
			showingAnswers = false;
		}

		presenter.showAnswers(answers);
	}

	@Override
	public void lock(boolean lock) {
		locked = lock;
		presenter.setEnabled(!lock);
	}

	@Override
	public void reset() {
		presenter.reset();
		clearResponse();
		fireStateChanged(false);
	}

	@Override
	public JSONArray getState() {
		JSONArray state = new JSONArray();

		for (String responseValue : getResponse().values) {
			state.set(state.size(), createJSONString(responseValue));
		}

		return state;
	}

	private JSONString createJSONString(String value) {
		return new JSONString(value);
	}

	@Override
	public void setState(JSONArray newState) {
		clearResponse();

		for (int i = 0; i < newState.size(); i++) {
			String choiceIdentifier = newState.get(i).isString().stringValue();
			getResponse().add(choiceIdentifier);
		}

		presenter.showAnswers(parseResponse(getResponse().values));
		fireStateChanged(false);
	}

	protected void clearResponse() {
		getResponse().reset();
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return ModuleJsSocketFactory.createSocketObject(this);
	}

	@Override
	public void onBodyLoad() { // NOPMD by MKaldonek on 15.10.12 08:30
		// eu.ydp.empiria.player.client.module.ILifecycleModule.onBodyLoad
	}

	@Override
	public void onBodyUnload() { // NOPMD by MKaldonek on 15.10.12 08:30
		// eu.ydp.empiria.player.client.module.ILifecycleModule.onBodyUnload
	}

	@Override
	public void onSetUp() { // NOPMD by MKaldonek on 15.10.12 08:30
		// eu.ydp.empiria.player.client.module.ILifecycleModule.onSetUp
	}

	@Override
	public void onStart() { // NOPMD by MKaldonek on 15.10.12 08:31
		// eu.ydp.empiria.player.client.module.ILifecycleModule.onStart
	}

	@Override
	public void onClose() { // NOPMD by MKaldonek on 15.10.12 08:31
		// eu.ydp.empiria.player.client.module.ILifecycleModule.onClose
	}

}
