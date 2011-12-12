package eu.ydp.empiria.player.client.module.selection;

import java.util.Vector;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.events.internal.InternalEvent;
import eu.ydp.empiria.player.client.controller.events.internal.InternalEventTrigger;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.model.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.model.feedback.InlineFeedbackSocket;
import eu.ydp.empiria.player.client.module.CommonsFactory;
import eu.ydp.empiria.player.client.module.FeedbackModuleInteractionListener;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.ModuleEventsListener;
import eu.ydp.empiria.player.client.module.IUnattachedComponent;
import eu.ydp.empiria.player.client.module.JsSocketFactory;
import eu.ydp.empiria.player.client.module.ModuleInteractionListener;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.components.selectablebutton.ChoiceGroupController;
import eu.ydp.empiria.player.client.module.components.selectablebutton.SingleChoiceButton;
import eu.ydp.empiria.player.client.util.RandomizedSet;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class SelectionModule extends Composite implements IInteractionModule {

	public SelectionModule(Element element, ModuleSocket moduleSocket, ModuleEventsListener moduleEventsListener){

		inlineModules = new Vector<IUnattachedComponent>();
		
		shuffle = XMLUtils.getAttributeAsBoolean(element, "shuffle");
		
		responseIdentifier = XMLUtils.getAttributeAsString(element, "responseIdentifier");
		response = moduleSocket.getResponse(responseIdentifier);
		stateListener = (ModuleInteractionListener)moduleEventsListener;
		
		NodeList choices = element.getElementsByTagName("simpleChoice");
		NodeList items = element.getElementsByTagName("item");
		
		grid = new Grid(items.getLength()+1, choices.getLength()+1);
		grid.setStyleName("qp-selection-table");
		fillGrid(choices, items, moduleSocket, moduleEventsListener);
		
		panel = new VerticalPanel();
		panel.setStyleName("qp-selection-module");
		panel.add(CommonsFactory.getPromptView(XMLUtils.getFirstElementWithTagName(element, "prompt")));
		panel.add(grid);
		
		initWidget(panel);

		NodeList childNodes = element.getChildNodes();
		for (int f = 0 ; f < childNodes.getLength() ; f ++){
			if (childNodes.item(f).getNodeName().compareTo("feedbackInline") == 0)
				moduleSocket.addInlineFeedback(new InlineFeedback(grid, childNodes.item(f), moduleEventsListener));
		}
	}

	/** response processing interface */
	private Response response;
	/** module state changed listener */
	private ModuleInteractionListener stateListener;
	/** response id */
	private String responseIdentifier;
	/** Shuffle answers */
	private boolean shuffle = false;
	
	private VerticalPanel panel;
	private Grid grid;
	private Vector<Vector<SingleChoiceButton>> buttons;
	private Vector<Vector<String>> buttonIds;
	private Vector<String> choiceIdentifiers;
	private Vector<String> itemIdentifiers;

	private boolean showingAnswers = false;
	private boolean locked = false;

	private Vector<IUnattachedComponent> inlineModules;

	@Override
	public void onOwnerAttached() {
		for (IUnattachedComponent uac : inlineModules)
			uac.onOwnerAttached();
		
		updateResponse("", false);

	}
	
	private void fillGrid(NodeList choices, NodeList items, InlineFeedbackSocket inlineFeedbackSocket, FeedbackModuleInteractionListener feedbackListener){
		buttons = new Vector<Vector<SingleChoiceButton>>();

		// header - choices
		
		choiceIdentifiers = new Vector<String>();
		for (int c = 0 ; c < choices.getLength() ; c ++){
			Vector<String> ignoredTags = new Vector<String>();
			ignoredTags.add("feedbackInline");
			
			Widget choiceView = CommonsFactory.getInlineTextView((Element)choices.item(c), ignoredTags, inlineModules);
			choiceView.setStyleName("qp-selection-choice");
			
			grid.setWidget(0, c+1, choiceView);
			
			choiceIdentifiers.add( ((Element)choices.item(c)).getAttribute("identifier") );

			NodeList inlineFeedbackNodes = ((Element)choices.item(c)).getElementsByTagName("feedbackInline");
			for (int f = 0 ; f < inlineFeedbackNodes.getLength() ; f ++){
				inlineFeedbackSocket.addInlineFeedback(new InlineFeedback(choiceView, inlineFeedbackNodes.item(f), feedbackListener));
			}
		}
		
		// body - items
		Vector<Node> itemNodes = new Vector<Node>();
		if (shuffle){
			RandomizedSet<Node> randomizedItemNodes = new RandomizedSet<Node>();
			for (int i = 0 ; i < items.getLength() ; i ++){
				if(!XMLUtils.getAttributeAsBoolean((Element)items.item(i), "fixed")){
					randomizedItemNodes.push(items.item(i));
				}
			}
			for (int i = 0 ; i < items.getLength() ; i ++){
				if(!XMLUtils.getAttributeAsBoolean((Element)items.item(i), "fixed")){
					itemNodes.add(randomizedItemNodes.pull());
				} else {
					itemNodes.add(items.item(i));
				}
			}
		} else {
			for (int i = 0 ; i < items.getLength() ; i ++){
				itemNodes.add(items.item(i));
			}
		}
		
		itemIdentifiers = new Vector<String>();
		for (int i = 0 ; i < itemNodes.size() ; i ++){
			Vector<String> ignoredTags = new Vector<String>();
			ignoredTags.add("feedbackInline");
			
			Widget itemView = CommonsFactory.getInlineTextView((Element)itemNodes.get(i), ignoredTags, inlineModules);
			itemView.setStyleName("qp-selection-item");
			
			grid.setWidget(i+1, 0, itemView);
			
			itemIdentifiers.add( ((Element)itemNodes.get(i)).getAttribute("identifier") );

			NodeList inlineFeedbackNodes = ((Element)itemNodes.get(i)).getElementsByTagName("feedbackInline");
			for (int f = 0 ; f < inlineFeedbackNodes.getLength() ; f ++){
				inlineFeedbackSocket.addInlineFeedback(new InlineFeedback(itemView, inlineFeedbackNodes.item(f), feedbackListener));
			}
		}
		
		// body - buttons
		buttonIds = new Vector<Vector<String>>();
		for (int i = 0 ; i < items.getLength() ; i ++){
			buttons.add(new Vector<SingleChoiceButton>());
			buttonIds.add(new Vector<String>());
			ChoiceGroupController cgc = new ChoiceGroupController();
			for (int c = 0 ; c < choices.getLength() ; c ++){
				SingleChoiceButton arb = new SingleChoiceButton(cgc, "selection");
				
				String buttonId = Document.get().createUniqueId();
			    //com.google.gwt.dom.client.Element buttonElement = (com.google.gwt.dom.client.Element)arb.getElement();
				//(buttonElement.getElementsByTagName("input").getItem(0)).setId(buttonId);
				arb.getElement().setId(buttonId);
				buttonIds.get(i).add(buttonId);
				
				grid.setWidget(i+1, c+1, arb);
				buttons.get(i).add(arb);
			}
		}
	}
	
	@Override
	public String getIdentifier() {
		return responseIdentifier;
	}

	@Override
	public void lock(boolean l) {
		if (locked != l){
			locked = l;
			for (int i = 0 ; i < buttons.size() ; i ++){
				for (int c = 0 ; c < buttons.get(i).size() ; c ++){
					buttons.get(i).get(c).setEnabled(!locked);
				}
			}
			
		}

	}

	@Override
	public void markAnswers(boolean mark) {
		if (mark){
			for (int r = 0 ; r < response.correctAnswers.size() ; r ++){
				String currItemIdentifier = response.correctAnswers.get(r).split(" ")[0];
				if (itemIdentifiers.indexOf(currItemIdentifier) == -1)
					continue;
				if (response.values.contains(response.correctAnswers.get(r))){
					grid.getWidget(itemIdentifiers.indexOf(currItemIdentifier)+1, 0).setStyleName("qp-selection-item-correct");
				} else {
					grid.getWidget(itemIdentifiers.indexOf(currItemIdentifier)+1, 0).setStyleName("qp-selection-item-wrong");
				}
			}
		} else {
			for (int i = 0 ; i < itemIdentifiers.size() ; i ++){
				grid.getWidget(i+1, 0).setStyleName("qp-selection-item");
			}
		}

	}

	@Override
	public void reset() {
		markAnswers(false);
		clearAnswers();
		updateResponse("", false);
	}
	
	private void clearAnswers(){

		for (int i = 0 ; i < buttons.size() ; i ++){
			for (int c = 0 ; c < buttons.get(i).size() ; c ++){
				if (buttons.get(i).get(c).isSelected())
					buttons.get(i).get(c).setSelected(false);
			}
		}

	}

	@Override
	public void showCorrectAnswers(boolean show) {
		if (show  &&  !showingAnswers){
			showingAnswers = true;
			clearAnswers();

			for (int i = 0 ; i < itemIdentifiers.size() ; i ++){
				for (int c = 0 ; c < choiceIdentifiers.size() ; c ++){
					if (response.correctAnswers.contains(itemIdentifiers.get(i) + " " + choiceIdentifiers.get(c)))
						buttons.get(i).get(c).setSelected(true);
				}
			}
				
		} else if (!show  &&  showingAnswers) {
			clearAnswers();

			for (int i = 0 ; i < itemIdentifiers.size() ; i ++){
				for (int c = 0 ; c < choiceIdentifiers.size() ; c ++){
					if (response.values.contains(itemIdentifiers.get(i) + " " + choiceIdentifiers.get(c)))
						buttons.get(i).get(c).setSelected(true);
				}
			}
			showingAnswers = false;
		}

	}
		
	public JavaScriptObject getJsSocket(){
		return JsSocketFactory.createSocketObject(this);
	}

	@Override
	public JSONArray getState() {
		JSONArray newState = new JSONArray();
		
		for (int r = 0 ; r < response.values.size() ; r ++){
			newState.set(newState.size(), new JSONString(response.values.get(r)) );
		}

		return newState;
	}

	@Override
	public void setState(JSONArray newState) {
		for (int e = 0 ; e < newState.size() ; e ++){
			String[] components = newState.get(e).isString().stringValue().split(" ");
			if (components.length == 2  &&  itemIdentifiers.indexOf(components[0]) != -1  &&  choiceIdentifiers.indexOf(components[1]) != -1){
				buttons.get(itemIdentifiers.indexOf(components[0])).get(choiceIdentifiers.indexOf(components[1])).setSelected(true);
			}
			
		}
		updateResponse("", false);

	}

	@Override
	public Vector<InternalEventTrigger> getTriggers() {
		Vector<InternalEventTrigger> triggers = new Vector<InternalEventTrigger>();
		for (int i = 0 ; i < buttons.size() ; i ++){
			for (int c = 0 ; c < buttons.get(i).size() ; c ++){
				triggers.add(new InternalEventTrigger(buttonIds.get(i).get(c), Event.ONMOUSEUP));
				triggers.add(new InternalEventTrigger(buttonIds.get(i).get(c), Event.ONMOUSEMOVE));
				triggers.add(new InternalEventTrigger(buttonIds.get(i).get(c), Event.ONMOUSEOUT));
			}
		}
		
		return triggers;
	}

	@Override
	public void handleEvent(String tagID, InternalEvent event) {
		
		SingleChoiceButton currTarget = null;
		
		if (tagID.length() > 0){
			for (int i = 0 ; i < buttons.size() ; i ++){
				for (int c = 0 ; c < buttons.get(i).size() ; c ++){
					if (buttonIds.get(i).get(c).equals(tagID)){
						currTarget = buttons.get(i).get(c);
						break;
					}
				}
			}
		}
			
		if (currTarget != null){
			if (event.getTypeInt() == Event.ONMOUSEUP ){
				if (!locked){
					currTarget.setSelected(!currTarget.isSelected());
					// pass response

					updateResponse(tagID, true);
				}					
			} else if (event.getTypeInt() == Event.ONMOUSEMOVE){
				currTarget.setMouseOver(true);
			} else if (event.getTypeInt() == Event.ONMOUSEOUT){
				currTarget.setMouseOver(false);
			}
		}

	}
	
	private void updateResponse(String senderId, boolean userInteract){
		if (showingAnswers)
			return;

		Vector<String> currResponseValues = new Vector<String>();
		
		for (int i = 0 ; i < buttons.size() ; i ++){
			
			for (int c = 0 ; c < buttons.get(i).size() ; c ++){
				if (buttons.get(i).get(c).isSelected()){
					currResponseValues.add(itemIdentifiers.get(i) + " " + choiceIdentifiers.get(c));
				}					
			}
		}

		if (!response.compare(currResponseValues)  ||  !response.isInitialized()){
			response.set(currResponseValues);
			stateListener.onStateChanged(userInteract, this);
		}
	}

}
