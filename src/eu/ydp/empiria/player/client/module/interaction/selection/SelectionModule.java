package eu.ydp.empiria.player.client.module.interaction.selection;

import java.util.List;
import java.util.Vector;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.body.ModuleEventsListener;
import eu.ydp.empiria.player.client.controller.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.controller.feedback.InlineFeedbackSocket;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceGroupController;
import eu.ydp.empiria.player.client.module.components.choicebutton.SingleChoiceButton;
import eu.ydp.empiria.player.client.module.listener.FeedbackModuleInteractionListener;
import eu.ydp.empiria.player.client.module.listener.ModuleInteractionListener;
import eu.ydp.empiria.player.client.util.RandomizedSet;
import eu.ydp.empiria.player.client.util.xml.XMLUtils;

public class SelectionModule extends Composite implements IInteractionModule {
	/** response processing interface */
	private Response response;
	/** module state changed listener */
	private ModuleInteractionListener moduleInteractionListener;
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
	
	protected Element moduleElement;
	protected ModuleSocket moduleSocket;

	public SelectionModule(){
	}

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
		
		shuffle = XMLUtils.getAttributeAsBoolean(moduleElement, "shuffle");
		
		responseIdentifier = XMLUtils.getAttributeAsString(moduleElement, "responseIdentifier");
		response = moduleSocket.getResponse(responseIdentifier);
		
		NodeList choices = moduleElement.getElementsByTagName("simpleChoice");
		NodeList items = moduleElement.getElementsByTagName("item");
		
		grid = new Grid(items.getLength()+1, choices.getLength()+1);
		grid.setStyleName("qp-selection-table");
		fillGrid(choices, items);

		Widget promptWidget = new InlineHTML();
		promptWidget.setStyleName("qp-prompt");
		moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody(XMLUtils.getFirstElementWithTagName(moduleElement, "prompt"), promptWidget.getElement());

		panel = new VerticalPanel();
		panel.setStyleName("qp-selection-module");
		panel.add(promptWidget);
		panel.add(grid);
		
		placeholders.get(0).add(panel);

		NodeList childNodes = moduleElement.getChildNodes();
		for (int f = 0 ; f < childNodes.getLength() ; f ++){
			if (childNodes.item(f).getNodeName().compareTo("feedbackInline") == 0)
				moduleSocket.addInlineFeedback(new InlineFeedback(grid, childNodes.item(f), moduleSocket, moduleInteractionListener));
		}
	}


	@Override
	public void onBodyLoad() {
		updateResponse(false);
		
	}

	@Override
	public void onBodyUnload() {
		// TODO Auto-generated method stub
		
	}
	private void fillGrid(NodeList choices, NodeList items){
		buttons = new Vector<Vector<SingleChoiceButton>>();

		// header - choices
		
		choiceIdentifiers = new Vector<String>();
		for (int c = 0 ; c < choices.getLength() ; c ++){
			Widget choiceView = moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody((Element)choices.item(c));
			choiceView.setStyleName("qp-selection-choice");
			
			grid.setWidget(0, c+1, choiceView);
			
			choiceIdentifiers.add( ((Element)choices.item(c)).getAttribute("identifier") );

			NodeList inlineFeedbackNodes = ((Element)choices.item(c)).getElementsByTagName("feedbackInline");
			for (int f = 0 ; f < inlineFeedbackNodes.getLength() ; f ++){
				moduleSocket.addInlineFeedback(new InlineFeedback(choiceView, inlineFeedbackNodes.item(f), moduleSocket, moduleInteractionListener));
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
			Widget itemView = moduleSocket.getInlineBodyGeneratorSocket().generateInlineBody((Element)itemNodes.get(i));
			itemView.setStyleName("qp-selection-item");
			
			grid.setWidget(i+1, 0, itemView);
			
			itemIdentifiers.add( ((Element)itemNodes.get(i)).getAttribute("identifier") );

			NodeList inlineFeedbackNodes = ((Element)itemNodes.get(i)).getElementsByTagName("feedbackInline");
			for (int f = 0 ; f < inlineFeedbackNodes.getLength() ; f ++){
				moduleSocket.addInlineFeedback(new InlineFeedback(itemView, inlineFeedbackNodes.item(f), moduleSocket, moduleInteractionListener));
			}
		}
		
		// body - buttons
		buttonIds = new Vector<Vector<String>>();
		for (int i = 0 ; i < items.getLength() ; i ++){
			buttons.add(new Vector<SingleChoiceButton>());
			buttonIds.add(new Vector<String>());
			ChoiceGroupController cgc = new ChoiceGroupController();
			for (int c = 0 ; c < choices.getLength() ; c ++){
				final SingleChoiceButton scb = new SingleChoiceButton(cgc, "selection");
				
				scb.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						onButtonClick(scb);
					}
				});
				scb.addMouseOverHandler(new MouseOverHandler() {
					@Override
					public void onMouseOver(MouseOverEvent event) {
						scb.setMouseOver(true);
					}
				});
				scb.addMouseOutHandler(new MouseOutHandler() {					
					@Override
					public void onMouseOut(MouseOutEvent event) {
						scb.setMouseOver(false);
					}
				});
				
				String buttonId = Document.get().createUniqueId();
				scb.getElement().setId(buttonId);
				buttonIds.get(i).add(buttonId);
				
				grid.setWidget(i+1, c+1, scb);
				buttons.get(i).add(scb);
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

				boolean itemSelected = false;
				for (SingleChoiceButton btn : buttons.get(itemIdentifiers.indexOf(currItemIdentifier))){
					if (btn.isSelected()){
						itemSelected = true;
						break;
					}
				}
				
				if (itemSelected){
					if (response.values.contains(response.correctAnswers.get(r))){
						grid.getWidget(itemIdentifiers.indexOf(currItemIdentifier)+1, 0).setStyleName("qp-selection-item-correct");
					} else {
						grid.getWidget(itemIdentifiers.indexOf(currItemIdentifier)+1, 0).setStyleName("qp-selection-item-wrong");
					}
				} else {
					grid.getWidget(itemIdentifiers.indexOf(currItemIdentifier)+1, 0).setStyleName("qp-selection-item-none");
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
		updateResponse(false);
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
		return ModuleJsSocketFactory.createSocketObject(this);
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
		updateResponse(false);

	}
	
	private void updateResponse(boolean userInteract){
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
			moduleInteractionListener.onStateChanged(userInteract, this);
		}
	}

	private void onButtonClick(SingleChoiceButton btn){
		btn.setSelected(!btn.isSelected());
		updateResponse(true);
	}

}
