package eu.ydp.empiria.player.client.controller.feedback;

import java.util.Map;
import java.util.Vector;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import eu.ydp.empiria.player.client.controller.events.interaction.FeedbackInteractionEventListner;
import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.IUniqueModule;
import eu.ydp.empiria.player.client.module.ModuleSocket;

public class FeedbackManager implements InlineFeedbackSocket{
	
	public Vector<IItemFeedback> feedbacks;
	private final FlowPanel container;
	protected ModuleSocket moduleSocket;
	private final String baseUrl;

	@Inject
	private FeedbackRegistry feedbackRegistry;
	
	@Inject
	private ModuleFeedbackProcessor moduleFeedbackProcessor;

	@Inject
	public FeedbackManager(@Assisted NodeList feedbackNodes, @Assisted String baseUrl, 
							@Assisted ModuleSocket moduleSocket, @Assisted FeedbackInteractionEventListner feedbackListener){
		
		this.baseUrl = baseUrl;
		this.moduleSocket = moduleSocket;

		feedbacks = new Vector<IItemFeedback>();
		ModalFeedback currFeedback;
		for (int n = 0 ; n < feedbackNodes.getLength() ; n ++){
			try {
				currFeedback = new ModalFeedback( feedbackNodes.item(n) , baseUrl , moduleSocket, feedbackListener);
				feedbacks.add(currFeedback);
			} catch (Exception e) {	}
		}
		
		container = new FlowPanel();
		container.setStyleName("qp-feedback-modal-container");
	}
	
	
	public Panel getModalFeedbackView(){
		return container;
	}
	
	public void process (Map<String, Response> responses, Map<String, Outcome> outcomes, IUniqueModule sender){
		if(feedbackRegistry.hasFeedbacks()){
			moduleFeedbackProcessor.process(sender, outcomes);
		}
	}

	@Override
	public void addInlineFeedback(InlineFeedback inlineFeedback) {
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
