package eu.ydp.empiria.player.client.module.selection;

import java.util.ArrayList;
import java.util.Collections;
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
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.PlayerGinjector;
import eu.ydp.empiria.player.client.controller.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.module.Factory;
import eu.ydp.empiria.player.client.module.IInteractionModule;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.empiria.player.client.module.OneViewInteractionModuleBase;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceButtonBase;
import eu.ydp.empiria.player.client.module.components.choicebutton.ChoiceGroupController;
import eu.ydp.empiria.player.client.module.components.choicebutton.MultiChoiceButton;
import eu.ydp.empiria.player.client.module.components.choicebutton.SingleChoiceButton;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.collections.RandomizedSet;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class SelectionModule extends OneViewInteractionModuleBase implements IInteractionModule, Factory<SelectionModule> {

	/** Shuffle answers */
	private boolean shuffle = false;
	private boolean multi = false;

	private VerticalPanel panel;
	private Grid grid;
	private Vector<Vector<ChoiceButtonBase>> buttons;
	private List<List<Widget>> buttonsPanels;
	private Vector<Vector<String>> buttonIds;
	private Vector<String> choiceIdentifiers;
	private Vector<String> itemIdentifiers;
	private List<Widget> itemLabels;

	private boolean showingAnswers = false;
	private boolean locked = false;

	private StyleNameConstants styleNameConstants = PlayerGinjector.INSTANCE.getStyleNameConstants();

	public SelectionModule(){
	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {

		shuffle = XMLUtils.getAttributeAsBoolean(getModuleElement(), "shuffle");
		multi = XMLUtils.getAttributeAsBoolean(getModuleElement(), "multi");

		NodeList choices = getModuleElement().getElementsByTagName("simpleChoice");
		NodeList items = getModuleElement().getElementsByTagName("item");

		grid = new Grid(items.getLength()+1, choices.getLength()+1);
		grid.setStyleName("qp-selection-table");
		fillGrid(choices, items);

		Widget promptWidget = new InlineHTML();
		promptWidget.setStyleName("qp-prompt");
		getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(XMLUtils.getFirstElementWithTagName(getModuleElement(), "prompt"), promptWidget.getElement());

		panel = new VerticalPanel();
		panel.setStyleName("qp-selection-module");
		applyIdAndClassToView(panel);
		panel.add(promptWidget);
		panel.add(grid);

		placeholders.get(0).add(panel);

		NodeList childNodes = getModuleElement().getChildNodes();
		for (int f = 0 ; f < childNodes.getLength() ; f ++){
			if (childNodes.item(f).getNodeName().compareTo("feedbackInline") == 0)
				getModuleSocket().addInlineFeedback(new InlineFeedback(grid, childNodes.item(f), getModuleSocket(), getInteractionEventsListener()));
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

	private void fillGrid(NodeList choices, NodeList items){
		buttons = new Vector<Vector<ChoiceButtonBase>>();
		buttonsPanels = new ArrayList<List<Widget>>();
		// header - choices

		choiceIdentifiers = new Vector<String>();
		for (int c = 0 ; c < choices.getLength() ; c ++){
			Widget choiceView = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody((Element)choices.item(c));
			choiceView.setStyleName(styleNameConstants.QP_SELECTION_CHOICE());

			grid.setWidget(0, c+1, choiceView);

			choiceIdentifiers.add( ((Element)choices.item(c)).getAttribute("identifier") );

			NodeList inlineFeedbackNodes = ((Element)choices.item(c)).getElementsByTagName("feedbackInline");
			for (int f = 0 ; f < inlineFeedbackNodes.getLength() ; f ++){
				getModuleSocket().addInlineFeedback(new InlineFeedback(choiceView, inlineFeedbackNodes.item(f), getModuleSocket(), getInteractionEventsListener()));
			}
		}

		// body - items
		Vector<Node> itemNodes = new Vector<Node>();
		itemLabels = new ArrayList<Widget>();
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
			Widget itemView = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody((Element)itemNodes.get(i));
			itemView.setStyleName(styleNameConstants.QP_SELECTION_ITEM_LABEL());
			itemView.addStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_INACTIVE());
			Panel itemContainer = new FlowPanel();
			itemContainer.setStyleName(styleNameConstants.QP_SELECTION_ITEM());
			itemContainer.addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_INACTIVE());
			itemContainer.add(itemView);
			
			itemLabels.add(itemView);

			grid.setWidget(i+1, 0, itemContainer);

			itemIdentifiers.add( ((Element)itemNodes.get(i)).getAttribute("identifier") );

			NodeList inlineFeedbackNodes = ((Element)itemNodes.get(i)).getElementsByTagName("feedbackInline");
			for (int f = 0 ; f < inlineFeedbackNodes.getLength() ; f ++){
				getModuleSocket().addInlineFeedback(new InlineFeedback(itemView, inlineFeedbackNodes.item(f), getModuleSocket(), getInteractionEventsListener()));
			}
		}

		// body - buttons
		buttonIds = new Vector<Vector<String>>();
		for (int i = 0 ; i < items.getLength() ; i ++){
			buttons.add(new Vector<ChoiceButtonBase>());
			buttonsPanels.add(new ArrayList<Widget>());
			buttonIds.add(new Vector<String>());
			ChoiceGroupController cgc = new ChoiceGroupController();
			for (int c = 0 ; c < choices.getLength() ; c ++){
				final ChoiceButtonBase scb = (multi)?new MultiChoiceButton("selection-multi") : new SingleChoiceButton(cgc, "selection");

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
				
				Panel buttonPanel = new FlowPanel();
				buttonPanel.setStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_INACTIVE());				
				buttonPanel.add(scb);

				grid.setWidget(i+1, c+1, buttonPanel);
				buttons.get(i).add(scb);
				buttonsPanels.get(i).add(buttonPanel);
			}
		}
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
			for (int ii = 0 ; ii < itemIdentifiers.size() ; ii ++ ){
				boolean passed = true;
				
				List<Boolean> buttonsCorrectness = new ArrayList<Boolean>();
				for (String c : choiceIdentifiers)
					buttonsCorrectness.add(false);

				for (int r = 0 ; r < getResponse().correctAnswers.getResponseValuesCount() ; r ++){
					String currItemIdentifier = getResponse().correctAnswers.getResponseValue(r).getAnswers().get(0).split(" ")[0];
					String currChoiceIdentifier = getResponse().correctAnswers.getResponseValue(r).getAnswers().get(0).split(" ")[1];
					if (currItemIdentifier.equals(itemIdentifiers.get(ii))){
						boolean correct = getResponse().values.contains(getResponse().correctAnswers.getResponseValue(r).getAnswers().get(0));
						if (!correct){
							passed = false;
						}
						buttonsCorrectness.set(choiceIdentifiers.indexOf(currChoiceIdentifier), correct);
					}
				}

				for (int r = 0 ; r < getResponse().values.size() ; r ++){
					String currItemIdentifier = getResponse().values.get(r).split(" ")[0];
					if (currItemIdentifier.equals(itemIdentifiers.get(ii))){
						if (!getResponse().correctAnswers.containsAnswer(getResponse().values.get(r))){
							passed = false;
							break;
						}
					}
					
				}

				boolean itemSelected = false;
				for (ChoiceButtonBase btn : buttons.get(ii)){
					if (btn.isSelected()){
						itemSelected = true;
						break;
					}
				}

				if (itemSelected){
					if (passed){
						grid.getWidget(ii+1, 0).setStyleName(styleNameConstants.QP_SELECTION_ITEM_CORRECT());
						grid.getWidget(ii+1, 0).addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_CORRECT());
						itemLabels.get(ii).setStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_CORRECT());
					} else {
						grid.getWidget(ii+1, 0).setStyleName(styleNameConstants.QP_SELECTION_ITEM_WRONG());
						grid.getWidget(ii+1, 0).addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_WRONG());
						itemLabels.get(ii).setStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_WRONG());
					}
				} else {
					grid.getWidget(ii+1, 0).setStyleName(styleNameConstants.QP_SELECTION_ITEM_NONE());
					grid.getWidget(ii+1, 0).addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_NONE());
					itemLabels.get(ii).setStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_NONE());
				}
				
				for (int i = 0 ; i < buttonsCorrectness.size() ; i ++){
					if (buttonsCorrectness.get(i)) {
						buttonsPanels.get(ii).get(i).setStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_CORRECT());
					} else if (buttons.get(ii).get(i).isSelected()) {
						buttonsPanels.get(ii).get(i).setStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_WRONG());
					} else {
						buttonsPanels.get(ii).get(i).setStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_NONE());
					}
					
				}

			}

		} else {
			for (int i = 0 ; i < itemIdentifiers.size() ; i ++){
				grid.getWidget(i+1, 0).setStyleName(styleNameConstants.QP_SELECTION_ITEM());
				grid.getWidget(i+1, 0).addStyleName(styleNameConstants.QP_MARKANSWERS_MARKER_INACTIVE());
				itemLabels.get(i).setStyleName(styleNameConstants.QP_MARKANSWERS_LABEL_INACTIVE());
			}
			
			for (int i = 0 ; i < buttonsPanels.size() ; i ++){
				for (int c = 0 ; c < buttonsPanels.get(i).size() ; c ++){
					buttonsPanels.get(i).get(c).setStyleName(styleNameConstants.QP_MARKANSWERS_BUTTON_INACTIVE());
				}
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
					if (getResponse().correctAnswers.containsAnswer(itemIdentifiers.get(i) + " " + choiceIdentifiers.get(c)))
						buttons.get(i).get(c).setSelected(true);
				}
			}

		} else if (!show  &&  showingAnswers) {
			clearAnswers();

			for (int i = 0 ; i < itemIdentifiers.size() ; i ++){
				for (int c = 0 ; c < choiceIdentifiers.size() ; c ++){
					if (getResponse().values.contains(itemIdentifiers.get(i) + " " + choiceIdentifiers.get(c)))
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

		for (int r = 0 ; r < getResponse().values.size() ; r ++){
			newState.set(newState.size(), new JSONString(getResponse().values.get(r)) );
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

		if (!getResponse().compare(currResponseValues)  ||  !getResponse().isInitialized()){
			getResponse().set(currResponseValues);
			fireStateChanged(userInteract);
		}
	}

	private void onButtonClick(ChoiceButtonBase btn){
		btn.setSelected(!btn.isSelected());
		updateResponse(true);
	}

	@Override
	public SelectionModule getNewInstance() {
		return new SelectionModule();
	}
}
