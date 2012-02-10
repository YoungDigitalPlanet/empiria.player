package eu.ydp.empiria.player.client.module.inlinechoice;

import java.io.Serializable;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.IActivity;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;
import eu.ydp.empiria.player.client.util.RandomizedSet;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class InlineChoiceDefaultController implements InlineChoiceController {

	private Response response;
	private String responseIdentifier;
	private ModuleInteractionListener moduleInteractionListener;
	private ModuleSocket moduleSocket;
	private ListBox  listBox;
	private boolean shuffle = false;
	private String	lastValue = null;
	private boolean showingAnswers = false;
	
	protected Element moduleElement;
	
	protected Panel container;

	@Override
	public void initModule(ModuleSocket moduleSocket, ModuleInteractionListener moduleInteractionListener) {

		this.moduleInteractionListener = moduleInteractionListener;
		this.moduleSocket = moduleSocket;
	}

	@Override
	public void addElement(Element element) {
		moduleElement = element;
	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {

		responseIdentifier = XMLUtils.getAttributeAsString(moduleElement, "responseIdentifier"); 

		response = moduleSocket.getResponse(responseIdentifier);
		shuffle = XMLUtils.getAttributeAsBoolean(moduleElement, "shuffle");
		
		listBox = new ListBox();
		if(shuffle)
			initRandom(moduleElement);
		else
			init(moduleElement);

		listBox.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				listBoxChanged();
			}
		});
		
		container = new FlowPanel();
		container.add(listBox);
		
		placeholders.get(0).add(container);
		
		container.setStyleName("qp-text-choice");		

		NodeList inlineFeedbackNodes = moduleElement.getElementsByTagName("feedbackInline");
		for (int f = 0 ; f < inlineFeedbackNodes.getLength() ; f ++){
			moduleSocket.addInlineFeedback(new InlineFeedback(container, inlineFeedbackNodes.item(f), moduleSocket, moduleInteractionListener));
		}
	}

	@Override
	public void onBodyLoad() {		
	}

	@Override
	public void onBodyUnload() {
	}

	@Override
	public void onSetUp() {
		updateResponse(false);
	}

	@Override
	public void onStart() {
	}

	@Override
	public void onClose() {
	}

	// ------------------------ INTERFACES ------------------------ 


	@Override
	public void lock(boolean l) {
		  listBox.setEnabled(!l);
		
	}

	/**
	 * @see IActivity#markAnswers()
	 */
	public void markAnswers(boolean mark) {
		if (mark){
			listBox.setEnabled(false);
			if (listBox.getSelectedIndex() != 0){
				if( response.isCorrectAnswer(lastValue) )
					container.setStyleName("qp-text-choice-correct");
				else
					container.setStyleName("qp-text-choice-wrong");
			} else {
				container.setStyleName("qp-text-choice-none");
			}
		} else {
			listBox.setEnabled(true);
			container.setStyleName("qp-text-choice");
		}
	}

	/**
	 * @see IActivity#reset()
	 */
	public void reset() {
		markAnswers(false);
		lock(false);
		listBox.setSelectedIndex(0);
		updateResponse(false);
	  listBox.setEnabled(true);
	  container.setStyleName("qp-text-choice");
	}

	/**
	 * @see IActivity#showCorrectAnswers()
	 */
	public void showCorrectAnswers(boolean show) {

		if (show  &&  !showingAnswers){
			showingAnswers = true;	
			for(int i = 0; i < listBox.getItemCount(); i++){
				if( listBox.getValue(i).compareTo(response.correctAnswers.get(0)) == 0){
					listBox.setSelectedIndex(i);
					break;
				}
			}
		} else if (!show  &&  showingAnswers) {
			listBox.setSelectedIndex(-1);
			for(int i = 0; i < listBox.getItemCount(); i++){
				if( listBox.getValue(i).compareTo((response.values.size()>0) ? response.values.get(0) : "" ) == 0){
					listBox.setSelectedIndex(i);
					break;
				}
			}
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
	  
	  if (lastValue != null)
		  stateString = lastValue;
	  
	  jsonArr.set(0, new JSONString(stateString));
	  
	  return jsonArr;
  }

  
  	/**
 	 * @see IStateful#setState(Serializable)
 	 */
  	public void setState(JSONArray newState) {
	
		String state = "";
	
		if (newState != null  &&  newState.size() > 0  &&  newState.get(0).isString() != null){
			state = newState.get(0).isString().stringValue();
			lastValue = null;
		}
	
		for(int i = 0; i < listBox.getItemCount(); i++){
			if( listBox.getValue(i).compareTo(state) == 0){
				listBox.setSelectedIndex(i);
				break;
			}
		}
		updateResponse(false);
  }
  
	/**
	 * init widget view
	 * @param element
	 */
	private void init(Element inlineChoiceElement){
		NodeList nodes = inlineChoiceElement.getChildNodes();

		// Add no answer as first option
		listBox.addItem("");
		
		for(int i = 0; i < nodes.getLength(); i++){
			if(nodes.item(i).getNodeName().compareTo("inlineChoice") == 0){
				Element choiceElement = (Element)nodes.item(i);
				listBox.addItem(XMLUtils.getText(choiceElement), 
				    XMLUtils.getAttributeAsString(choiceElement, "identifier"));
			}
		}
	}
	
	/**
	 * init widget view. Randomize options
	 * @param element
	 */
	private void initRandom(Element inlineChoiceElement){
		RandomizedSet<Element>	randomizedNodes = new RandomizedSet<Element>();
		NodeList nodes = inlineChoiceElement.getChildNodes();

		// Add no answer as first option
		listBox.addItem("");
		
		// Add nodes to temporary list
		for(int i = 0; i < nodes.getLength(); i++){
			if(nodes.item(i).getNodeName().compareTo("inlineChoice") == 0){
				randomizedNodes.push((Element)nodes.item(i));
			}
		}
		
		while(randomizedNodes.hasMore()){
			Element choiceElement = randomizedNodes.pull();
      listBox.addItem(XMLUtils.getText(choiceElement), 
          XMLUtils.getAttributeAsString(choiceElement, "identifier"));
		}
		
	}
	
	private void updateResponse(boolean userInteract){
		if (showingAnswers)
			return;

		if(lastValue != null)
			response.remove(lastValue);
		
		lastValue = listBox.getValue(listBox.getSelectedIndex());
		response.add(lastValue);
		moduleInteractionListener.onStateChanged(userInteract, this);
	}

	@Override
	public String getIdentifier() {
		return responseIdentifier;
	}
	
	protected void listBoxChanged(){
		updateResponse(true);
	}
}
