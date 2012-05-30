package eu.ydp.empiria.player.client.module.inlinechoice;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;

import eu.ydp.empiria.player.client.components.ExListBox;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.IActivity;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.module.InteractionModuleBase;

public class InlineChoiceModule extends InteractionModuleBase implements Factory<InlineChoiceModule>{

	private InlineChoiceController controller;
	private boolean moduleInitialized = false;


	public InlineChoiceModule(){

	}

	public void initModule() {

		Map<String, String> styles = getModuleSocket().getStyles(XMLParser.createDocument().createElement("inlinechoiceinteraction"));
		if (styles != null  &&  styles.containsKey("-empiria-inlinechoice-type")  &&  styles.get("-empiria-inlinechoice-type").toLowerCase().equals("popup")){
			controller = new InlineChoicePopupController();
		} else {
			controller = new InlineChoiceDefaultController();
		}
		if (styles != null  &&  styles.containsKey("-empiria-inlinechoice-empty-option")  &&  styles.get("-empiria-inlinechoice-empty-option").toLowerCase().equals("hide")){
			controller.setShowEmptyOption(false);
		} else {
			controller.setShowEmptyOption(true);
		}
		if (styles != null  &&  controller instanceof InlineChoicePopupController  &&  styles.containsKey("-empiria-inlinechoice-popup-position")  &&  styles.get("-empiria-inlinechoice-popup-position").toLowerCase().equals("below")){
			((InlineChoicePopupController)controller).setPopupPosition(ExListBox.PopupPosition.BELOW);
		}
		controller.initModule(getModuleSocket(), getInteractionEventsListener());
	}

	@Override
	public void addElement(Element element) {
		if (!moduleInitialized){
			moduleInitialized = true;
			initModule();
			findResponse(element);
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

	// ------------------------ INTERFACES ------------------------


	@Override
	public void lock(boolean l) {
		controller.lock(l);
	}

	/**
	 * @see IActivity#markAnswers()
	 */
	public void markAnswers(boolean mark) {
		controller.markAnswers(mark);
	}

	/**
	 * @see IActivity#reset()
	 */
	public void reset() {
		controller.reset();
	}

	/**
	 * @see IActivity#showCorrectAnswers()
	 */
	public void showCorrectAnswers(boolean show) {

		controller.showCorrectAnswers(show);
	}

	public JavaScriptObject getJsSocket(){
		return controller.getJsSocket();
	}

  /**
   * @see IStateful#getState()
   */
  public JSONArray getState() {
	  // IMPORTANT: STATE MUST BE COMMON FOR ALL CONTROLLERS
	  return controller.getState();
  }


  	/**
 	 * @see IStateful#setState(Serializable)
 	 */
  	public void setState(JSONArray newState) {

		controller.setState(newState);
  }

	@Override
	public InlineChoiceModule getNewInstance() {
		return new InlineChoiceModule();
	}

}
