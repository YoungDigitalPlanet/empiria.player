package eu.ydp.empiria.player.client.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

import eu.ydp.empiria.player.client.controller.ItemBody;
import eu.ydp.empiria.player.client.controller.communication.DisplayContentOptions;
import eu.ydp.empiria.player.client.controller.communication.PageReference;
import eu.ydp.empiria.player.client.controller.communication.Result;
import eu.ydp.empiria.player.client.controller.communication.sockets.ItemInterferenceSocket;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEvent;
import eu.ydp.empiria.player.client.controller.events.activity.FlowActivityEventType;
import eu.ydp.empiria.player.client.controller.events.interaction.InteractionEventsListener;
import eu.ydp.empiria.player.client.controller.session.sockets.ItemSessionSocket;
import eu.ydp.empiria.player.client.controller.style.StyleLinkDeclaration;
import eu.ydp.empiria.player.client.controller.style.variables.IVariableCreator;
import eu.ydp.empiria.player.client.controller.variables.manager.VariableManager;
import eu.ydp.empiria.player.client.controller.variables.objects.BaseTypeConverter;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.controller.variables.processor.item.VariableProcessor;
import eu.ydp.empiria.player.client.controller.variables.processor.item.VariableProcessorTemplate;
import eu.ydp.empiria.player.client.model.feedback.FeedbackManager;
import eu.ydp.empiria.player.client.model.feedback.InlineFeedback;
import eu.ydp.empiria.player.client.module.IStateful;
import eu.ydp.empiria.player.client.module.ModuleSocket;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.localisation.LocalePublisher;
import eu.ydp.empiria.player.client.util.localisation.LocaleVariable;
import eu.ydp.empiria.player.client.util.xml.document.XMLData;
import eu.ydp.empiria.player.client.view.item.ItemBodyView;

public class Item implements IStateful, ItemVariablesAccessor, ItemInterferenceSocket {
		
	protected ItemBody itemBody;
	
	protected ItemBodyView itemBodyView;
	
	protected Panel scorePanel;
	
	protected VariableProcessor variableProcessor;
	
	private FeedbackManager feedbackManager;
	
	public VariableManager<Response> responseManager;
	
	public VariableManager<Outcome> outcomeManager;
	
	public StyleLinkDeclaration styleDeclaration;
	
	public StyleSocket styleSocket;
	
	private String title;

	private XMLData xmlData;
			
	public Item(XMLData data, DisplayContentOptions options, InteractionEventsListener interactionEventsListener, StyleSocket ss){

		xmlData = data;
		
		styleSocket = ss;
		
		Node rootNode = xmlData.getDocument().getElementsByTagName("assessmentItem").item(0);
		Node itemBodyNode = xmlData.getDocument().getElementsByTagName("itemBody").item(0);
		    
	    variableProcessor = VariableProcessorTemplate.fromNode(xmlData.getDocument().getElementsByTagName("variableProcessing"));
	    
	    feedbackManager = new FeedbackManager(xmlData.getDocument().getElementsByTagName("modalFeedback"), xmlData.getBaseURL(), interactionEventsListener);
	    
	    responseManager = new VariableManager<Response>(xmlData.getDocument().getElementsByTagName("responseDeclaration"), new IVariableCreator<Response>() {
				@Override
				public Response createVariable(Node node) {
					return new Response(node);
				}
			});
	    
	    outcomeManager = new VariableManager<Outcome>(xmlData.getDocument().getElementsByTagName("outcomeDeclaration"), new IVariableCreator<Outcome>() {
			@Override
			public Outcome createVariable(Node node) {
				return new Outcome(node);
			}
		});
	    
	    styleDeclaration = new StyleLinkDeclaration(xmlData.getDocument().getElementsByTagName("styleDeclaration"), data.getBaseURL());
	    
	    variableProcessor.ensureVariables(responseManager.getVariablesMap(), outcomeManager.getVariablesMap());
	    
	    VariableProcessor.interpretFeedbackAutoMark(itemBodyNode, responseManager.getVariablesMap());
   
	    itemBody = new ItemBody(options, moduleSocket, interactionEventsListener);
	    
	    itemBodyView = new ItemBodyView( itemBody.getView((Element)itemBodyNode), itemBody, itemBody );
	    
	    feedbackManager.setBodyView(itemBodyView);
	    
	    title = ((Element)rootNode).getAttribute("title");
	    
	    scorePanel = new FlowPanel();
	    scorePanel.setStyleName("qp-feedback-hidden");
	}
	
	/**
	 * Inner class for module socket implementation
	 */
	private ModuleSocket moduleSocket = new ModuleSocket(){

		public eu.ydp.empiria.player.client.controller.variables.objects.response.Response getResponse(String id) {
			return responseManager.getVariable(id);
		}

		@Override
		public void add(InlineFeedback inlineFeedback) {
			feedbackManager.add(inlineFeedback);
		}
		
		@Override
		public Map<String,String> getStyles(Element element) {
			return (styleSocket!=null)? styleSocket.getStyles(element) : new HashMap<String,String>();
		}
		
		public void setCurrentPages( PageReference pr) {
			if (styleSocket!=null) {
				styleSocket.setCurrentPages(pr);
			}
		}
		
	};
	
	public void close(){
		feedbackManager.hideAllInlineFeedbacks();
	}
	
	public void process(boolean userInteract){
		process(userInteract, "");
	}
	
	public void process(boolean userInteract, String senderIdentifier){
		variableProcessor.processResponseVariables(responseManager.getVariablesMap(), outcomeManager.getVariablesMap(), userInteract);
		if (userInteract){
			feedbackManager.process(responseManager.getVariablesMap(), outcomeManager.getVariablesMap(), senderIdentifier);
		}
	}

	public void handleFlowActivityEvent(JavaScriptObject event){
		handleFlowActivityEvent(FlowActivityEvent.fromJsObject(event));
	}
	
	public void handleFlowActivityEvent(FlowActivityEvent event){
		if (event == null)
			return;
		if (event.getType() == FlowActivityEventType.CHECK){
			checkItem();
		} else if (event.getType() == FlowActivityEventType.CONTINUE){
			continueItem();
		} else if (event.getType() == FlowActivityEventType.SHOW_ANSWERS){
			showAnswers(true);
		} else if (event.getType() == FlowActivityEventType.HIDE_ANSWERS){
			showAnswers(false);
		} else if (event.getType() == FlowActivityEventType.RESET){
			resetItem();
		} else if (event.getType() == FlowActivityEventType.LOCK){
			lockItem(true);
		} else if (event.getType() == FlowActivityEventType.UNLOCK){
			lockItem(false);
		}
		variableProcessor.processFlowActivityVariables(outcomeManager.getVariablesMap(), event);
	}

	public String getTitle(){
		return title;
	}

	public int getModulesCount(){
		return itemBody.getModuleCount();
	}
	
	public Widget getContentView(){
		return itemBodyView;
	}
	
	public Widget getFeedbackView(){
		return feedbackManager.getModalFeedbackView();
	}
	
	public Widget getScoreView(){
		return scorePanel;
	}
		
	@Deprecated
	public Result getResult(){
		
		Result result;
		
		String score = "";
		Float lowerBound = new Float(0);
		Float upperBound = new Float(0);
		
		if (outcomeManager.getVariable("DONE") != null)
			if (outcomeManager.getVariable("DONE").values.size() > 0)
				score = outcomeManager.getVariable("DONE").values.get(0);
		
		Iterator<String> iterator = responseManager.getVariablesMap().keySet().iterator();
		while (iterator.hasNext()){
			String currKey = iterator.next();
			
			if (!responseManager.getVariable(currKey).isModuleAdded())
				continue;
			
			if (responseManager.getVariable(currKey).mapping.lowerBound != null)
				lowerBound += responseManager.getVariable(currKey).mapping.lowerBound;
			
			if (responseManager.getVariable(currKey).mapping.upperBound != null)
				upperBound += responseManager.getVariable(currKey).mapping.upperBound;
			else
				upperBound += 1;
		}
		
		//if (lowerBound == 0  &&  upperBound == 0)
		//	upperBound = 1.0f;
			
		result = new Result(BaseTypeConverter.tryParseFloat(score), lowerBound, upperBound);
		
		return result;
	}
	
	@Deprecated
	public int getMistakesCount(){
		if (outcomeManager.getVariablesMap().containsKey("LASTMISTAKEN")){
			if (outcomeManager.getVariable("LASTMISTAKEN").values.size() == 1){
				int mistakesCount = Integer.parseInt( outcomeManager.getVariable("LASTMISTAKEN").values.get(0) );
				return mistakesCount; 
			}
		}
		return 0;
	}
	
	// -------------------------- SCORE -------------------------------
	
	public void showScore(){
		if (itemBody.getModuleCount() > 0){
			Result r = getResult();
			Label feedbackLabel = new Label(LocalePublisher.getText(LocaleVariable.ITEM_SCORE1) + r.getScore() + LocalePublisher.getText(LocaleVariable.ITEM_SCORE2) + r.getMaxPoints() + LocalePublisher.getText(LocaleVariable.ITEM_SCORE3));
			feedbackLabel.setStyleName("qp-feedback-score-text");
			scorePanel.add(feedbackLabel);
			scorePanel.setStyleName("qp-feedback");
		}
	}

	public void hideScore(){
		scorePanel.clear();
		scorePanel.setStyleName("qp-feedback-hidden");
	}
	
	// -------------------------- NAVIGATION -------------------------------
	
	public int getItemModuleCount(){
		return itemBody.getModuleCount();
	}

	public void checkItem(){
		itemBody.markAnswers(true);
		itemBody.lock(true);
		showScore();
	}
	
	public void continueItem(){
		itemBody.markAnswers(false);
		itemBody.lock(false);
		hideScore();
	}
	
	public void showAnswers(boolean show){
		itemBody.showCorrectAnswers(show);
		itemBody.lock(show);
	}


	public void resetItem(){
		responseManager.reset();
		outcomeManager.reset();
		itemBody.reset();
		hideScore();
	}
	
	public void lockItem(boolean lock){
		itemBody.lock(lock);
	}
	
	public boolean isLocked(){
		return itemBody.isLocked();
	}

	@Override
	public JSONArray getState() {
		return itemBody.getState();
	}

	@Override
	public void setState(JSONArray newState) {
		itemBody.setState(newState);

	}

	@Override
	public String getResponseVariableBaseType(String var) {
		Response r;
		if ((r = responseManager.getVariable(var)) == null)
			return "";
		return r.baseType.toString();
	}

	@Override
	public String getResponseVariableCardinality(String var) {
		Response r;
		if ((r = responseManager.getVariable(var)) == null)
			return "";
		return r.cardinality.toString();
	}

	@Override
	public String getResponseVariableValue(String var) {
		Response r;
		if ((r = responseManager.getVariable(var)) == null)
			return "";
		return r.getValuesShort();
	}

	@Override
	public String getResponseVariables() {
		String names = "";
		Iterator<String> iter = responseManager.getVariablesMap().keySet().iterator();
		while (iter.hasNext()){
			names += iter.next() + ";";
		}
		if (names.length() > 0)
			names = names.substring(0, names.length()-1);
		
		return names;
	}
	
	public JavaScriptObject getJsSocket(){
		return createItemSocket(itemBody.getJsSocket());
	}
	
	private native JavaScriptObject createItemSocket(JavaScriptObject itemBodySocket)/*-{
		var socket = {};
		var instance = this; 
		socket.reset = function(){
			instance.@eu.ydp.empiria.player.client.model.Item::resetItem()();
		}
		socket.showAnswers = function(){
			instance.@eu.ydp.empiria.player.client.model.Item::showAnswers(Z)(true);
		}
		socket.hideAnswers = function(){
			instance.@eu.ydp.empiria.player.client.model.Item::showAnswers(Z)(false);
		}
		socket.lock = function(){
			instance.@eu.ydp.empiria.player.client.model.Item::lockItem(Z)(true);
		}
		socket.unlock = function(){
			instance.@eu.ydp.empiria.player.client.model.Item::lockItem(Z)(false);
		}
		socket.checkItem = function(value){
			instance.@eu.ydp.empiria.player.client.model.Item::checkItem()();
		}
		socket.continueItem = function(value){
			instance.@eu.ydp.empiria.player.client.model.Item::continueItem()();
		}
		socket.getOutcomeVariables = function(){
			return instance.@eu.ydp.empiria.player.client.model.Item::getOutcomeVariablesJsSocket()();
		}
		socket.getResponseVariables = function(){
			return instance.@eu.ydp.empiria.player.client.model.Item::getResponseVariablesJsSocket()();
		}
		socket.getItemBodySocket = function(){
			return itemBodySocket;
		}
		socket.handleFlowActivityEvent = function(event){
			instance.@eu.ydp.empiria.player.client.model.Item::handleFlowActivityEvent(Lcom/google/gwt/core/client/JavaScriptObject;)(event);
		}
		return socket;
	}-*/;
	
	private JavaScriptObject getOutcomeVariablesJsSocket(){
		return outcomeManager.getJsSocket();
	}
	
	private JavaScriptObject getResponseVariablesJsSocket(){
		return responseManager.getJsSocket();
	}

	@Override
	public eu.ydp.empiria.player.client.controller.communication.sockets.ModuleInterferenceSocket[] getModuleSockets() {
		return itemBody.getModuleSockets();
	}
	
	public void updateItemSession(int itemIndex, ItemSessionSocket itemSessionSocket){
		itemSessionSocket.updateItemVariables(itemIndex, outcomeManager.getVariablesMap());
	}
	
	
}