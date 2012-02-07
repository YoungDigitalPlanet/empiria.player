package eu.ydp.empiria.player.client.module.interaction.textentry;

import java.io.Serializable;
import java.util.List;
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
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.IActivity;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class TextEntryModule implements IInteractionModule{

	/** response processing interface */
	private Response 	response;
	private String responseIdentifier;
	/** module state changed listener */
	private ModuleInteractionListener moduleInteractionListener;
	protected ModuleSocket moduleSocket;
	/** widget id */
	private String  id;
	/** text box control */
	private TextBox textBox;
	/** Last selected value */
	private String	lastValue = null;
	private boolean showingAnswers = false;

	protected Element moduleElement;
	protected Panel moduleWidget;
	
	/**	
	 * constructor
	 * @param moduleSocket
	 */
	public TextEntryModule(){
		
	}

	@Override
	public void initModule(ModuleSocket moduleSocket, ModuleInteractionListener moduleInteractionListener) {

		this.moduleSocket = moduleSocket;
		this.moduleInteractionListener = moduleInteractionListener;
	}

	@Override
	public void addElement(Element element) {
		moduleElement = element;
	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		
		responseIdentifier = XMLUtils.getAttributeAsString(moduleElement, "responseIdentifier"); 
		response = moduleSocket.getResponse(responseIdentifier);
		
		textBox = new TextBox();
		if (moduleElement.hasAttribute("expectedLength"))
			textBox.setMaxLength(XMLUtils.getAttributeAsInt(moduleElement, "expectedLength"));
		
		textBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				onTextBoxChange();
			}
		});
		
		if (!response.correctAnswers.get(0).matches(".*[^0-9].*"))
			textBox.getElement().setAttribute("type", "number");

		Panel spanPrefix = new FlowPanel();
		spanPrefix.setStyleName("qp-text-textentry-prefix");
		Panel spanSufix = new FlowPanel();
		spanSufix.setStyleName("qp-text-textentry-sufix");
		Panel spanContent = new FlowPanel();
		spanContent.setStyleName("qp-text-textentry-content");
		spanContent.add(textBox);
		
		moduleWidget = new FlowPanel();
		
		moduleWidget.add(spanPrefix);
		moduleWidget.add(spanContent);
		moduleWidget.add(spanSufix);
		moduleWidget.setStyleName("qp-text-textentry");
		
		placeholders.get(0).add(moduleWidget);
		
		NodeList inlineFeedbackNodes = moduleElement.getElementsByTagName("feedbackInline");
		for (int f = 0 ; f < inlineFeedbackNodes.getLength() ; f ++){
			moduleSocket.addInlineFeedback(new InlineFeedback(moduleWidget, inlineFeedbackNodes.item(f), moduleSocket, moduleInteractionListener));
		}
	}

	// ------------------------ INTERFACES ------------------------ 


	@Override
	public void onBodyLoad() {	
		updateResponse(false);	
	}

	@Override
	public void onBodyUnload() {
		
	}
	@Override
	public void lock(boolean l) {
		textBox.setEnabled(!l);
	}
  
	/**
	 * @see IActivity#markAnswers()
	 */
	public void markAnswers(boolean mark) {
		if (mark){
			textBox.setEnabled(false);
			if (textBox.getText().length() > 0){
				if( response.isCorrectAnswer(lastValue) )
					moduleWidget.setStyleName("qp-text-textentry-correct");
				else
					moduleWidget.setStyleName("qp-text-textentry-wrong");
			} else {
				moduleWidget.setStyleName("qp-text-textentry-none");
			}
		} else {
			textBox.setEnabled(true);
			moduleWidget.setStyleName("qp-text-textentry");
		}
	}

	/**
	 * @see IActivity#reset()
	 */
	public void reset() {
		markAnswers(false);
		showCorrectAnswers(false);
		lock(false);
		textBox.setText("");
		updateResponse(false);
	}

	/**
	 * @see IActivity#showCorrectAnswers()
	 */
	public void showCorrectAnswers(boolean show) {
		if (show  &&  !showingAnswers){
			showingAnswers = true;
			textBox.setText(response.correctAnswers.get(0));
		} else if (!show  &&  showingAnswers) {
			textBox.setText((response.values.size()>0) ? response.values.get(0) : "");
			showingAnswers = false;
		}
	}
		
	public JavaScriptObject getJsSocket(){
		return ModuleJsSocketFactory.createSocketObject(this);
	}
	
  /**
   * @see IStateful#getState()
   */
  public JSONArray getState() {
	  JSONArray jsonArr = new JSONArray();

	  String stateString = "";
	  
	  if (response.values.size() > 0)
		  stateString = response.values.get(0);
	  
	  jsonArr.set(0, new JSONString(stateString));
	  
	  return jsonArr;
  }

  /**
   * @see IStateful#setState(Serializable)
   */
  public void setState(JSONArray newState) {
		
		String state = "";
	
		if (newState == null){
		} else if (newState.size() == 0){
		} else if (newState.get(0).isString() == null){
		} else {
			state = newState.get(0).isString().stringValue();
			lastValue = null;
		}
	
		textBox.setText(state);
		
		updateResponse(false);
		
  }
	
	private void updateResponse(boolean userInteract){
		if (showingAnswers)
			return;
		
		if(lastValue != null)
			response.remove(lastValue);
		
		lastValue = textBox.getText();
		response.add(lastValue);
		moduleInteractionListener.onStateChanged(userInteract, this);
	
	}

	@Override
	public String getIdentifier() {
		return responseIdentifier;
	}
	
	protected void onTextBoxChange(){
		updateResponse(true);
	}

}
