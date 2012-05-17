package eu.ydp.empiria.player.client.module.choice;

import java.util.List;
import java.util.Vector;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.controller.variables.objects.Cardinality;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceGroupController;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;
import eu.ydp.empiria.player.client.util.RandomizedSet;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class ChoiceModule implements IInteractionModule, SimpleChoiceListener,Factory<ChoiceModule> {
	/** response processing interface */
	private Response response;
	/** module state changed listener */
	private ModuleInteractionListener stateListener;
	private ModuleSocket moduleSocket;
	/** response id */
	private String responseIdentifier;
	/** Work mode single or multiple choice */
	private boolean multi = false;
	/** Shuffle? */
	private boolean shuffle = false;
	/** option widgets */
	private Vector<SimpleChoice> interactionElements;

	private boolean locked = false;
	private boolean showingAnswers = false;

	protected ChoiceGroupController groupController;

	protected Element moduleElement;

	protected Panel mainPanel;


	public ChoiceModule(){
	}

	@Override
	public void initModule(ModuleSocket moduleSocket, ModuleInteractionListener moduleInteractionListener) {

		this.stateListener = moduleInteractionListener;
		this.moduleSocket = moduleSocket;
	}

	@Override
	public void addElement(Element element) {
		moduleElement = element;
	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		shuffle = XMLUtils.getAttributeAsBoolean(moduleElement, "shuffle");
		String userClass = XMLUtils.getAttributeAsString(moduleElement, "class");
		responseIdentifier = XMLUtils.getAttributeAsString(moduleElement, "responseIdentifier");
		response = moduleSocket.getResponse(responseIdentifier);
		multi = response.cardinality == Cardinality.MULTIPLE;

		mainPanel = new FlowPanel();

		mainPanel.setStyleName("qp-choice-module");
		if (userClass != null  &&  !"".equals(userClass))
			mainPanel.addStyleName(userClass);
		Widget promptWidget = new InlineHTML();
		promptWidget.setStyleName("qp-prompt");
		moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(XMLUtils.getFirstElementWithTagName(moduleElement, "prompt"), promptWidget.getElement());

		mainPanel.add(promptWidget);
		mainPanel.add(getOptionsView(moduleElement, moduleSocket, stateListener));

		NodeList childNodes = moduleElement.getChildNodes();
		for (int f = 0 ; f < childNodes.getLength() ; f ++){
			if (childNodes.item(f).getNodeName().compareTo("feedbackInline") == 0)
				moduleSocket.addInlineFeedback(new InlineFeedback(mainPanel, childNodes.item(f), moduleSocket, stateListener));
		}

		placeholders.get(0).add(mainPanel);

	}


	  /**
	   * Get options view
	   * @return
	   */
	  private Widget getOptionsView(Element element, ModuleSocket moduleSocket, ModuleInteractionListener moduleInteractionListener){

		  Panel panel = new FlowPanel();
		  NodeList optionNodes = element.getElementsByTagName("simpleChoice");
		  RandomizedSet<Element> randomizedNodes = new RandomizedSet<Element>();
		  RandomizedSet<Integer> randomizedIndices = new RandomizedSet<Integer>();

		  interactionElements = new Vector<SimpleChoice>();
		  for (int el = 0 ; el < optionNodes.getLength() ; el ++)
			  interactionElements.add(null);

		  // Add randomized nodes to shuffle table
		  if(shuffle){
			  for(int i = 0; i < optionNodes.getLength(); i++){
				  Element	option = (Element)optionNodes.item(i);
				  if(!XMLUtils.getAttributeAsBoolean(option, "fixed")){
					  randomizedNodes.push(option);
					  randomizedIndices.push(i);
				  }
			  }
		  }

		  groupController = new ChoiceGroupController();

		  // Create buttons
		  for(int i = 0; i < optionNodes.getLength(); i++){
			  int optionIndex = i;
			  Element option = (Element)optionNodes.item(i);
			  SimpleChoice currInteractionElement;

			  if(shuffle && !XMLUtils.getAttributeAsBoolean(option, "fixed") ){
				  //option = randomizedNodes.pull();
				  optionIndex = randomizedIndices.pull();
				  option = (Element)optionNodes.item(optionIndex);
			  }

			  currInteractionElement = new SimpleChoice(option, multi, this, moduleSocket, moduleInteractionListener, groupController);
			  //interactionElements.add(currInteractionElement);
			  interactionElements.set(optionIndex, currInteractionElement);
			  panel.add(currInteractionElement);
		  }

		  return panel;
	  }


	// ------------------------- INTERFACES --------------------------------


	@Override
	public void onBodyLoad() {
		for (SimpleChoice c:interactionElements){
			c.onOwnerAttached();
		}
	}

	@Override
	public void onBodyUnload() {
	}

	@Override
	public void onSetUp() {
		updateResponse(null, false);
	}

	@Override
	public void onStart() {
	}

	@Override
	public void onClose() {
	}

	@Override
	public void lock(boolean l) {
		locked = l;
		for (SimpleChoice currSC:interactionElements){
			currSC.setEnabled(!l);
		}

	}

	@Override
	public void markAnswers(boolean mark) {

		Vector<Boolean> evaluation = response.evaluateAnswer();

		if (response.cardinality == Cardinality.SINGLE){
			for (int i = 0 ; i < interactionElements.size() ; i ++){
				interactionElements.get(i).markAnswers(mark, evaluation.get(0));
			}
		} else if (response.cardinality == Cardinality.MULTIPLE){
			for (SimpleChoice currSC:interactionElements){
				boolean correct = response.correctAnswers.contains(currSC.getIdentifier());
				boolean ok = (correct && currSC.isSelected())  ||  (!correct && !currSC.isSelected());
				currSC.markAnswers(mark,  ok);
			}
		}
	}

	@Override
	public void showCorrectAnswers(boolean show) {
		if (show  &&  !showingAnswers){
			showingAnswers = true;
			for (SimpleChoice currSC:interactionElements){
				currSC.setSelected(response.correctAnswers.contains(currSC.getIdentifier()) );
			}
		} else if (!show  &&  showingAnswers) {
			for (SimpleChoice currSC:interactionElements){
				currSC.setSelected(response.values.contains(currSC.getIdentifier()) );
			}
			showingAnswers = false;
		}
	}

	@Override
	public void reset() {
		for (SimpleChoice currSC:interactionElements){
			currSC.reset();
		}
		updateResponse(null, false);
	}

	@Override
	public JSONArray getState() {
		JSONArray  state = new JSONArray();

		for (SimpleChoice currSC:interactionElements){
			//boolean b1 = currSC.isSelected();
			boolean b1 = response.values.contains(currSC.getIdentifier());
			state.set(state.size(), JSONBoolean.getInstance(b1));
		}

		return state;
	}

	@Override
	public void setState(JSONArray newState) {

		Boolean currSelected;

		for (int i  = 0 ; i < newState.size() && i < interactionElements.size(); i ++ ){
			currSelected = newState.get(i).isBoolean().booleanValue();
			interactionElements.get(i).setSelected(currSelected);

		}

		updateResponse(null, false);
		//stateListener.onStateChanged(this);
	}

	private void updateResponse(SimpleChoice target, boolean userInteract){
		if (showingAnswers)
			return;

		Vector<String> currResponseValues = new Vector<String>();

		for (SimpleChoice currSC:interactionElements){
			if (currSC.isSelected()){
				currResponseValues.add(currSC.getIdentifier());
			}
		}

		if (!response.compare(currResponseValues)  ||  !response.isInitialized()){
			response.set(currResponseValues);
			stateListener.onStateChanged(userInteract, this);
		}
	}

	@Override
	public String getIdentifier() {
		return responseIdentifier;
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return ModuleJsSocketFactory.createSocketObject(this);
	}

	@Override
	public void onSimpleChoiceClick(SimpleChoice sc) {
		if (!locked){
			sc.setSelected(!sc.isSelected());
			updateResponse(null, true);
		}
	}

	@Override
	public ChoiceModule getNewInstance() {
		return new ChoiceModule();
	}
}
