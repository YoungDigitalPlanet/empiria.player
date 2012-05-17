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
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;

public class InlineChoiceModule  implements IInteractionModule,Factory<InlineChoiceModule>{

	private ModuleInteractionListener moduleInteractionListener;
	private ModuleSocket moduleSocket;
	InlineChoiceController controller;


	public InlineChoiceModule(){

	}

	@Override
	public void initModule(ModuleSocket moduleSocket, ModuleInteractionListener moduleInteractionListener) {

		this.moduleInteractionListener = moduleInteractionListener;
		this.moduleSocket = moduleSocket;
		Map<String, String> styles = moduleSocket.getStyles(XMLParser.createDocument().createElement("inlinechoiceinteraction"));
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
		controller.initModule(moduleSocket, moduleInteractionListener);
	}

	@Override
	public void addElement(Element element) {
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
	  // TODO STATE MUST BE COMMON FOR ALL CONTROLLERS
	  return controller.getState();
  }


  	/**
 	 * @see IStateful#setState(Serializable)
 	 */
  	public void setState(JSONArray newState) {

		controller.setState(newState);
  }


	@Override
	public String getIdentifier() {
		return controller.getIdentifier();
	}

	@Override
	public InlineChoiceModule getNewInstance() {
		return new InlineChoiceModule();
	}

}
