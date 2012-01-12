package eu.ydp.empiria.player.client.controller.feedback;

import java.util.Vector;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.xml.client.NodeList;

public class AssessmentFeedbackManager {

	public AssessmentFeedbackManager(NodeList feedbackMainNode){

		cases = new Vector<AssessmentFeedbackCase>();

		container = new FlowPanel();
		container.setStyleName("qp-feedback-assessment-container");
		
		if (feedbackMainNode.getLength() == 0)
			return;
		
		NodeList feedbackNodes = feedbackMainNode.item(0).getChildNodes();
		
		AssessmentFeedbackCase currCase;
		for (int n = 0 ; n < feedbackNodes.getLength() ; n ++){
			if (feedbackNodes.item(n).getNodeName().compareTo("assessmentFeedbackCase") == 0){
				try {
					currCase = new AssessmentFeedbackCase( feedbackNodes.item(n)  );
					cases.add(currCase);
				} catch (Exception e) {	}
			}
		}
		
	}
	
	private Vector<AssessmentFeedbackCase> cases;
	private FlowPanel container;
	
	public FlowPanel getView(int percentageScore){
		
		container.clear();
		
		for (int c = 0 ; c < cases.size() ; c ++){
			if (cases.get(c).checkCondition(percentageScore)){
				container.add(cases.get(c).getView());
				break;
			}
		}
			
		return container;
	}
}
