package eu.ydp.empiria.player.client.module.interaction.inlinechoice;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;

import eu.ydp.empiria.player.client.controller.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.IActivity;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;
import eu.ydp.empiria.player.client.util.RandomizedSet;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class InlineChoiceModule  implements IInteractionModule{

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
	  // TODO STATE MUS BE COMMON FOR ALL CONTROLLERS
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
	
}
