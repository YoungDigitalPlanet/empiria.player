package eu.ydp.empiria.player.client.module;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.controller.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.empiria.player.client.structure.ModuleBean;

/**
 *
 * @author MKaldonek
 *
 * @param <T> typ modułu
 * @param <H> typ modelu
 * @param <U> typ beana
 */
public abstract class AbstractInteractionModule<T extends AbstractInteractionModule<?, ?, ?>, H extends AbstractResponseModel<?>, U extends ModuleBean> extends OneViewInteractionModuleBase implements Factory<T>, ResponseModelChangeListener {

	protected boolean locked = false;

	protected boolean showingAnswers = false;

	protected boolean markingAnswers = false;

	private ActivityPresenter<H, U> presenter;

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		getStructure().createFromXml(getModuleElement().toString());
		getPresenter().setModuleSocket(getModuleSocket());
		initalizeModule();
		initializePresenter();
		initializeAndInstallFeedbacks();
		applyIdAndClassToView(getView());
		placeholders.get(0).add(getView());
	}

	private void initializePresenter(){
		presenter = getPresenter();
		presenter.setBean(getStructure().getBean());
		presenter.setModel(getResponseModel());
		presenter.bindView();
	}

	protected void initializeAndInstallFeedbacks() {
		NodeList childNodes = getModuleElement().getElementsByTagName(EmpiriaTagConstants.NAME_FEEDBACK_INLINE);
		for (int f = 0; f < childNodes.getLength(); f++) {
			InlineFeedback feedback = createInlineFeedback(getView(), childNodes.item(f));
			getModuleSocket().addInlineFeedback(feedback);
		}
	}

	protected InlineFeedback createInlineFeedback(IsWidget mountingPoint, Node feedbackNode) {
		return new InlineFeedback((Widget) mountingPoint, feedbackNode, getModuleSocket(), getInteractionEventsListener());
	}

	protected abstract ActivityPresenter<H, U> getPresenter();

	protected abstract void initalizeModule();

	protected abstract H getResponseModel();

	protected abstract AbstractModuleStructure<U, ? extends JAXBParserFactory<U>> getStructure();

	protected Widget getView() {
		return presenter.asWidget();
	}

	protected void generateInlineBody(Node node, Widget parentWidget) {
		getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(node, parentWidget.getElement());
	}

	@Override
	public void markAnswers(boolean mark) {
		markingAnswers = mark;
		MarkAnswersMode markAnswerMode = (mark)? MarkAnswersMode.MARK: MarkAnswersMode.UNMARK;
		
		presenter.markAnswers(MarkAnswersType.CORRECT, markAnswerMode);
		presenter.markAnswers(MarkAnswersType.WRONG, markAnswerMode);
	}

	@Override
	public void showCorrectAnswers(boolean show) {
		ShowAnswersType showAnswersType = (show)? ShowAnswersType.CORRECT: ShowAnswersType.USER;
		presenter.showAnswers(showAnswersType);
	}

	@Override
	public void lock(boolean lock) {
		locked = lock;
		presenter.setLocked(lock);
	}

	@Override
	public void reset() {
		presenter.reset();
		clearModel();
		fireStateChanged(false);
	}

	@Override
	public JSONArray getState() {
		return getResponseModel().getState();
	}

	@Override
	public void setState(JSONArray newState) {
		clearModel();
		getResponseModel().setState(newState);
		presenter.showAnswers(ShowAnswersType.USER);
		fireStateChanged(false);
	}

	protected void clearModel() {
		getResponseModel().reset();
	}

	@Override
	public void onResponseModelChange() {
		fireStateChanged(true);
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
