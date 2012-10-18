package eu.ydp.empiria.player.client.module;

import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.ModuleBean;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;

/**
 * 
 * @author MKaldonek
 *
 * @param <T> typ modułu
 * @param <H> typ modelu
 * @param <U> typ beana
 */
public abstract class AbstractInteractionModule<T extends AbstractInteractionModule<?, ?, ?>, H extends AbstractResponseModel<?>, U extends ModuleBean> extends OneViewInteractionModuleBase implements Factory<T> {

	protected boolean locked = false;

	protected boolean showingAnswers = false;

	protected boolean markingAnswers = false;

	private ActivityPresenter<H, U> presenter;

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		getStructure().createFromXml(getModuleElement().toString());
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

	protected InlineFeedback createInlineFeedback(Widget mountingPoint, Node feedbackNode) {
		return new InlineFeedback(mountingPoint, feedbackNode, getModuleSocket(), getInteractionEventsListener());
	}

	protected abstract ActivityPresenter<H, U> getPresenter();

	protected abstract void initalizeModule();
	
	protected abstract H getResponseModel();
	
	protected abstract AbstractModuleStructure<U> getStructure();

	protected Widget getView() {
		return presenter.asWidget();
	}

	protected void generateInlineBody(Node node, Widget parentWidget) {
		getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(node, parentWidget.getElement());
	}

	@Override
	public void markAnswers(boolean mark) {
		markingAnswers = mark;
		
		if(mark){
			presenter.markCorrectAnswers();
			presenter.markWrongAnswers();
		}else{
			presenter.unmarkCorrectAnswers();
			presenter.unmarkWrongAnswers();
		}
	}

	@Override
	public void showCorrectAnswers(boolean show) {
		if(show){
			presenter.showCorrectAnswers();
		}else{
			presenter.showCurrentAnswers();
		}
	}

	@Override
	public void lock(boolean lock) {
		locked = lock;
		presenter.setLocked(!lock);
	}

	@Override
	public void reset() {
		presenter.reset();
		clearResponse();
		//delegate to model
		fireStateChanged(false);
	}

	@Override
	public JSONArray getState() {
		JSONArray state = new JSONArray();
		//TODO: delegate to model
		return state;
	}

	@Override
	public void setState(JSONArray newState) {
		clearResponse();

		for (int i = 0; i < newState.size(); i++) {
			String choiceIdentifier = newState.get(i).isString().stringValue();
			getResponse().add(choiceIdentifier);
		}
		
		presenter.showCurrentAnswers();
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
