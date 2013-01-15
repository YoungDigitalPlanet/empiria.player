package eu.ydp.empiria.player.client.module.inlinechoice;

import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_INLINECHOICE_EMPTY_OPTION;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_INLINECHOICE_POPUP_POSITION;
import static eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants.EMPIRIA_INLINECHOICE_TYPE;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.xml.client.Element;
import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.IActivity;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.module.InteractionModuleBase;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBox;

public class InlineChoiceModule extends InteractionModuleBase implements Factory<InlineChoiceModule> {

	protected InlineChoiceController controller;

	protected boolean moduleInitialized = false;

	@Inject
	protected Provider<InlineChoicePopupController> inlineChoicePopupControllerProvider;

	@Inject
	protected Provider<InlineChoiceDefaultController> inlineChoiceDefaultControllerProvider;

	@Inject
	protected eu.ydp.gwtutil.client.xml.XMLParser xmlParser;

	@Inject
	protected Provider<InlineChoiceModule> inlineChoiceModuleProvider;

	public void initModule() {
		setStyles();
		controller.initModule(getModuleSocket(), getInteractionEventsListener());
		controller.setParentInlineModule(this);
	}

	protected void setStyles() {
		Map<String, String> styles = getStyles();
		if (styles != null && styles.containsKey(EMPIRIA_INLINECHOICE_TYPE) && styles.get(EMPIRIA_INLINECHOICE_TYPE).equalsIgnoreCase("popup")) {
			controller = inlineChoicePopupControllerProvider.get();
		} else {
			controller =inlineChoiceDefaultControllerProvider.get();
		}
		if (styles != null && styles.containsKey(EMPIRIA_INLINECHOICE_EMPTY_OPTION) && styles.get(EMPIRIA_INLINECHOICE_EMPTY_OPTION).equalsIgnoreCase("hide")) {
			controller.setShowEmptyOption(false);
		} else {
			controller.setShowEmptyOption(true);
		}
		if (styles != null && controller instanceof InlineChoicePopupController && styles.containsKey(EMPIRIA_INLINECHOICE_POPUP_POSITION)
				&& styles.get(EMPIRIA_INLINECHOICE_POPUP_POSITION).equalsIgnoreCase("below")) {
			((InlineChoicePopupController) controller).setPopupPosition(ExListBox.PopupPosition.BELOW);
		}
	}

	protected Map<String, String> getStyles() {
		return getModuleSocket().getStyles(xmlParser.createDocument().createElement("inlinechoiceinteraction"));
	}

	@Override
	public void addElement(Element element) {
		if (!moduleInitialized) {
			moduleInitialized = true;
			initModule();
			setResponseFromElement(element);
		}
		controller.addElement(element);
	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		controller.installViews(placeholders);
	}

	@Override
	public void onBodyLoad() {
		controller.onBodyLoad();
	}

	@Override
	public void onBodyUnload() {
		controller.onBodyUnload();
	}

	@Override
	public void onSetUp() {
		controller.onSetUp();
	}

	@Override
	public void onStart() {
		controller.onStart();
	}

	@Override
	public void onClose() {
	}

	public InlineChoiceController getController() {
		return controller;
	}

	// ------------------------ INTERFACES ------------------------

	@Override
	public void lock(boolean lock) {
		controller.lock(lock);
	}

	/**
	 * @see IActivity#markAnswers()
	 */
	@Override
	public void markAnswers(boolean mark) {
		controller.markAnswers(mark);
	}

	/**
	 * @see IActivity#reset()
	 */
	@Override
	public void reset() {
		controller.reset();
	}

	/**
	 * @see IActivity#showCorrectAnswers()
	 */
	@Override
	public void showCorrectAnswers(boolean show) {

		controller.showCorrectAnswers(show);
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return controller.getJsSocket();
	}

	/**
	 * @see IStateful#getState()
	 */
	@Override
	public JSONArray getState() {
		// IMPORTANT: STATE MUST BE COMMON FOR ALL CONTROLLERS
		return controller.getState();
	}

	/**
	 * @see IStateful#setState(Serializable)
	 */
	@Override
	public void setState(JSONArray newState) {

		controller.setState(newState);
	}

	@Override
	public InlineChoiceModule getNewInstance() {
		return inlineChoiceModuleProvider.get();
	}

}
