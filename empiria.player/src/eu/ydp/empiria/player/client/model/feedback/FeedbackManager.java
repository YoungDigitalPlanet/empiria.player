package eu.ydp.empiria.player.client.model.feedback;

import java.util.HashMap;
import java.util.Vector;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.NodeList;

import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionEventListner;
import eu.ydp.empiria.player.client.controller.variables.objects.Variable;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

public class FeedbackManager implements InlineFeedbackSocket{

	public FeedbackManager(NodeList feedbackNodes, String baseUrl, FeedbackInteractionEventListner feedbackListener){
		
		this.baseUrl = baseUrl;

		feedbacks = new Vector<IItemFeedback>();
		ModalFeedback currFeedback;
		for (int n = 0 ; n < feedbackNodes.getLength() ; n ++){
			try {
				currFeedback = new ModalFeedback( feedbackNodes.item(n) , baseUrl , feedbackListener);
				feedbacks.add(currFeedback);
			} catch (Exception e) {	}
		}
		
		container = new FlowPanel();
		container.setStyleName("qp-feedback-modal-container");
	}
	
	public Vector<IItemFeedback> feedbacks; 
	private FlowPanel container;
	
	private String baseUrl;
	
	public Panel getModalFeedbackView(){
		return container;
	}
	
	public void process (HashMap<String, Response> responses, HashMap<String, Outcome> outcomes, String senderIdentifier){
		
		Variable currVar;
		
		for (IItemFeedback currFeedback : feedbacks){
			
			if (responses.containsKey(currFeedback.getVariableIdentifier()))
				currVar = responses.get(currFeedback.getVariableIdentifier());
			else if (outcomes.containsKey(currFeedback.getVariableIdentifier()))
				currVar = outcomes.get(currFeedback.getVariableIdentifier());
			else
				continue;
						
			boolean condition = false;
			boolean validated = (currFeedback.getValue().compareTo("") != 0);
			
			if (currFeedback.getValue().startsWith(">=")  ||  
				currFeedback.getValue().startsWith("<=")  ||  
				currFeedback.getValue().startsWith("==")){
				
				try {
					
					Integer referenceValue = Integer.valueOf(currFeedback.getValue().substring(2));
					Integer testValue = Integer.valueOf(currVar.getValuesShort());
					
					if (currFeedback.getValue().startsWith(">="))
						condition = (testValue >= referenceValue);
					else if (currFeedback.getValue().startsWith("<="))
						condition = (testValue <= referenceValue);
					else if (currFeedback.getValue().startsWith("=="))
						condition = (testValue == referenceValue);
					
				} catch (Exception e) {	}
				
			} else if ( currFeedback.getValue().contains(".")  || 
						currFeedback.getValue().contains("*")  ||
						currFeedback.getValue().contains("[")  ||
						currFeedback.getValue().contains("(")  ||
						currFeedback.getValue().contains("\\")  ||
						currFeedback.getValue().contains("^")  ||
						currFeedback.getValue().contains("$")  ||
						currFeedback.getValue().contains("]")  ||
						currFeedback.getValue().contains(")")){
				String currVarValues = currVar.getValuesShort();
				try {
					condition = currVarValues.matches(currFeedback.getValue());
					//alert("regexp: " + currVarValues + " vs " + currFeedback.getValue());
				} catch (Exception e) {
					//alert("regexp: " + currVarValues + " vs " + currFeedback.getValue());
				}
			} else {
				condition = currVar.compareValues(currFeedback.getValue().split(";"));
			}
			
			boolean senderMatches = true;
			
			if (currFeedback.getSenderIdentifier().length() > 0){
				senderMatches = senderIdentifier.matches(currFeedback.getSenderIdentifier());  
			}
			
			if (!validated)
				continue;
			
			if (currFeedback.hasHTMLContent()){
				
				if (currFeedback.showOnMatch() == condition  &&  senderMatches)
					currFeedback.show(container);
				else
					currFeedback.hide(container);
								
			}

			if (currFeedback.hasSoundContent()){
				
				if (condition == currFeedback.showOnMatch()  &&  senderMatches)
					currFeedback.processSound();
				
			}
			
		}
		
	}

	@Override
	public void add(InlineFeedback inlineFeedback) {
		feedbacks.add(inlineFeedback);
	}
	
	public void setBodyView(Widget bodyView){
		for (IItemFeedback itemf : feedbacks){
			if (itemf instanceof InlineFeedback){
				((InlineFeedback)itemf).setBodyContainer(bodyView);
				((InlineFeedback)itemf).setBaseUrl(baseUrl);
			}
		}
	}
	
	public void hideAllInlineFeedbacks(){

		for (IItemFeedback currFeedback : feedbacks){
			if (currFeedback.getClass() == InlineFeedback.class){
				currFeedback.hide(container);
			}
		}
	}
}
