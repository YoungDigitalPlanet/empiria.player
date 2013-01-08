package eu.ydp.empiria.player.client.module.inlinechoice;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.events.interaction.StateChangedInteractionEvent;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.gin.PlayerGinjector;
import eu.ydp.empiria.player.client.module.IUniqueModule;
import eu.ydp.empiria.player.client.module.ModuleJsSocketFactory;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.module.ParentedModuleBase;
import eu.ydp.empiria.player.client.util.events.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEvent;
import eu.ydp.empiria.player.client.util.events.state.StateChangeEventTypes;
import eu.ydp.gwtutil.client.collections.RandomizedSet;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBox;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBoxChangeListener;
import eu.ydp.gwtutil.client.xml.XMLUtils;

public class InlineChoicePopupController extends ParentedModuleBase implements InlineChoiceController, ExListBoxChangeListener {


	private Response response;
	private String responseIdentifier;
	protected List<String> identifiers;

	private InteractionEventsListener interactionEventsListener;

	protected Element moduleElement;

	protected ExListBox listBox;
	protected Panel container;

	protected boolean showingAnswers = false;
	protected boolean locked = false;
	protected boolean shuffle = false;

	protected List<Integer> identifiersMap;
	protected boolean showEmptyOption = true;
	private final EventsBus eventsBus = getEventsBus();
	protected ExListBox.PopupPosition popupPosition = ExListBox.PopupPosition.ABOVE;
	
	IUniqueModule parentModule;

	@Override
	public void initModule(ModuleSocket moduleSocket, InteractionEventsListener moduleInteractionListener) {
		super.initModule(moduleSocket);
		this.interactionEventsListener = moduleInteractionListener;
	}
	
	protected EventsBus getEventsBus() {
		return PlayerGinjector.INSTANCE.getEventsBus();
	}

	@Override
	public String getIdentifier() {
		return responseIdentifier;
	}

	@Override
	public void addElement(Element element) {
		moduleElement = element;
	}

	@Override
	public void installViews(List<HasWidgets> placeholders) {
		responseIdentifier = XMLUtils.getAttributeAsString(moduleElement, "responseIdentifier");
		response = getModuleSocket().getResponse(responseIdentifier);
		shuffle = XMLUtils.getAttributeAsBoolean(moduleElement, "shuffle");
		String userClass = XMLUtils.getAttributeAsString(moduleElement, "class");

		NodeList optionsNodes = moduleElement.getElementsByTagName("inlineChoice");
		List<Widget> baseBodies = new ArrayList<Widget>();
		List<Widget> popupBodies = new ArrayList<Widget>();
		List<String> identifiersTemp = new ArrayList<String>();

		for (int i = 0 ; i < optionsNodes.getLength() ; i ++){
			Widget baseBody = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(optionsNodes.item(i));
			baseBodies.add(baseBody);
			Widget popupBody = getModuleSocket().getInlineBodyGeneratorSocket().generateInlineBody(optionsNodes.item(i));
			popupBodies.add(popupBody);
			identifiersTemp.add(((Element)optionsNodes.item(i)).getAttribute("identifier"));
		}

		listBox = new ExListBox();
		listBox.setPopupPosition(popupPosition);
		listBox.setChangeListener(this);

		if (showEmptyOption){
			Widget emptyOptionInBody = new InlineHTML("&nbsp;");
			emptyOptionInBody.setStyleName("qp-text-choice-popup-option-empty");
			Widget emptyOptionInPopup = new InlineHTML("&nbsp;");
			emptyOptionInPopup.setStyleName("qp-text-choice-popup-option-empty");
			listBox.addOption(emptyOptionInBody, emptyOptionInPopup);
			listBox.setSelectedIndex(0);
		} else {
			listBox.setSelectedIndex(-1);
		}

		if (shuffle){
			RandomizedSet<Integer> randomizedNodes = new RandomizedSet<Integer>();
			for (int i = 0 ; i < identifiersTemp.size() ; i ++){
				randomizedNodes.push(i);
			}
			identifiers = new ArrayList<String>();
			while (randomizedNodes.hasMore()){
				Integer currIndex = randomizedNodes.pull();
				identifiers.add(identifiersTemp.get(currIndex));
				listBox.addOption(baseBodies.get(currIndex), popupBodies.get(currIndex));
			}

		} else {
			identifiers = identifiersTemp;
			for (int i = 0 ; i < baseBodies.size()  &&  i < popupBodies.size() ; i ++){
				listBox.addOption(baseBodies.get(i), popupBodies.get(i));
			}
		}


		container = new FlowPanel();
		container.setStyleName("qp-text-choice-popup");
		if (userClass != null  &&  !"".equals(userClass)) {
			container.addStyleName(userClass);
		}
		container.add(listBox);


		placeholders.get(0).add(container);
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

	@Override
	public void markAnswers(boolean mark) {
		if (mark){
			listBox.setEnabled(false);
			int selectedIndex = listBox.getSelectedIndex();
			if (selectedIndex != ((showEmptyOption)?0:-1) ){
				
				if (isResponseCorrect()){
					container.setStyleName("qp-text-choice-popup-correct");
				} else {
					container.setStyleName("qp-text-choice-popup-wrong");
				}
			} else{
				container.setStyleName("qp-text-choice-popup-none");
			}
		} else {
			container.setStyleName("qp-text-choice-popup");
			listBox.setEnabled(true);
		}
	}

	private boolean isResponseCorrect(){
		ModuleSocket moduleSocket = getModuleSocket();
		List<Boolean> evaluateResponse = moduleSocket.evaluateResponse(response);
		return evaluateResponse.get(0);
	}

	@Override
	public void showCorrectAnswers(boolean show) {
		if (show  &&  !showingAnswers){
			int correctAnswerIndex = identifiers.indexOf( response.correctAnswers.getSingleAnswer() ) + ((showEmptyOption)?1:0);
			listBox.setSelectedIndex(correctAnswerIndex);
		} else if (!show && showingAnswers){
			int answerIndex = ((showEmptyOption)?0:-1) ;
			if (response.values.size() > 0) {
				answerIndex = identifiers.indexOf( response.values.get(0) ) + ((showEmptyOption)?1:0);
			}
			listBox.setSelectedIndex(answerIndex);
		}
		showingAnswers = show;
	}

	@Override
	public void lock(boolean l) {
		locked = l;
		listBox.setEnabled(!l);
		if (locked){
			container.addStyleName("qp-text-choice-popup-disabled");
		} else {
			container.removeStyleName("qp-text-choice-popup-disabled");
		}

	}

	@Override
	public void reset() {
		markAnswers(false);
		lock(false);
		listBox.setSelectedIndex( ((showEmptyOption)?0:-1) );
		updateResponse(false);
		listBox.setEnabled(true);
		container.setStyleName("qp-text-choice-popup");
	}

	@Override
	public JSONArray getState() {
		  JSONArray jsonArr = new JSONArray();

		  String stateString = "";

		  if (listBox.getSelectedIndex() - ((showEmptyOption)?1:0) >= 0) {
			if (response.values.size() > 0) {
				stateString = response.values.get(0);
			}
		}

		  jsonArr.set(0, new JSONString(stateString));

		  return jsonArr;
	}

	@Override
	public void setState(JSONArray newState) {
		if (newState != null  &&  newState.size() > 0  &&  newState.get(0).isString() != null){
			int index = identifiers.indexOf(newState.get(0).isString().stringValue());
			listBox.setSelectedIndex( index + ((showEmptyOption)?1:0) );
		}

		updateResponse(false);
	}

	@Override
	public JavaScriptObject getJsSocket() {
		return ModuleJsSocketFactory.createSocketObject(this);
	}
	
	@Override
	public void setParentInlineModule(IUniqueModule module) {
		parentModule = module;
	}
	
	@Override
	public IUniqueModule getParentInlineModule() {
		return parentModule;
	}

	private void updateResponse(boolean userInteract){
		if (showingAnswers) {
			return;
		}

		response.reset();

		if (listBox.getSelectedIndex() != ((showEmptyOption)?0:-1)){
			String lastValue = identifiers.get(listBox.getSelectedIndex() - ((showEmptyOption)?1:0));
			response.add(lastValue);
		}
		eventsBus.fireEvent(new StateChangeEvent(StateChangeEventTypes.STATE_CHANGED, new StateChangedInteractionEvent(userInteract, parentModule)), new CurrentPageScope());
	}

	@Override
	public void onChange() {
		if (!showingAnswers  &&  !locked){
			updateResponse(true);
		}

	}

	@Override
	public void setShowEmptyOption(boolean seo) {
		showEmptyOption = seo;
	}

	public void setPopupPosition(ExListBox.PopupPosition pp){
		popupPosition = pp;
	}
	
}
